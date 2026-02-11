package com.triaura.backend.modules.auth.service;

import com.triaura.backend.common.id.IdGenerator;
import com.triaura.backend.modules.auth.entity.VerificationCode;
import com.triaura.backend.modules.auth.mapper.VerificationCodeMapper;
import com.triaura.backend.modules.user.entity.AppUser;
import com.triaura.backend.modules.user.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final VerificationCodeMapper codeMapper;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final SecureRandom random = new SecureRandom();

    public AuthService(VerificationCodeMapper codeMapper, UserMapper userMapper) {
        this.codeMapper = codeMapper;
        this.userMapper = userMapper;
    }

    /**
     * 发送验证码（真实流程：生成验证码 -> 存 hash + 过期时间 -> 调用短信/邮件服务发送）
     * 这里先把“发送动作”抽象出去，当前默认你可以先看日志（后续接腾讯云短信/邮件只替换实现）。
     */
    public void sendCode(String channel, String target, String scene) {
        // 1) 生成 6 位数字验证码
        String code = String.format("%06d", random.nextInt(1_000_000));

        // 2) 入库：只存 hash，不存明文
        VerificationCode vc = new VerificationCode();
        vc.setId(IdGenerator.ulid());
        vc.setChannel(channel);
        vc.setTarget(target);
        vc.setScene(scene);
        vc.setCodeHash(encoder.encode(code));
        vc.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        vc.setUsedAt(null);
        vc.setSendCount(1);
        codeMapper.insert(vc);

        // 3) 发送（这里先输出日志；你后面接腾讯云短信/邮件就是把这行换成真正发送）
        System.out.println("[VERIFY_CODE] channel=" + channel + ", target=" + target + ", scene=" + scene + ", code=" + code);
    }

    /**
     * 注册：校验验证码 -> 创建用户
     */
    public AppUser register(String channel, String target, String code, String rawPassword, String displayName) {
        // 1) 先判断账号是否已存在
        if ("EMAIL".equalsIgnoreCase(channel)) {
            if (userMapper.findByEmail(target) != null) {
                throw new IllegalArgumentException("邮箱已被注册");
            }
        } else if ("PHONE".equalsIgnoreCase(channel)) {
            if (userMapper.findByPhone(target) != null) {
                throw new IllegalArgumentException("手机号已被注册");
            }
        } else {
            throw new IllegalArgumentException("channel 仅支持 PHONE/EMAIL");
        }

        // 2) 校验验证码（取最新一条）
        VerificationCode latest = codeMapper.findLatest(channel.toUpperCase(), target, "REGISTER");
        if (latest == null) {
            throw new IllegalArgumentException("请先获取验证码");
        }
        if (latest.getUsedAt() != null) {
            throw new IllegalArgumentException("验证码已使用");
        }
        if (latest.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("验证码已过期");
        }
        if (!encoder.matches(code, latest.getCodeHash())) {
            throw new IllegalArgumentException("验证码错误");
        }

        // 3) 标记验证码已使用（避免重复注册）
        codeMapper.markUsed(latest.getId());

        // 4) 创建用户
        AppUser u = new AppUser();
        u.setId(IdGenerator.ulid());
        u.setUsername(buildUsername(channel, target, u.getId()));
        u.setDisplayName(displayName == null || displayName.isBlank() ? "用户" : displayName);
        u.setPasswordHash(encoder.encode(rawPassword));
        u.setRole("OWNER");   // 你单人系统先 OWNER；以后多用户注册可以默认 USER
        u.setStatus("ACTIVE");

        LocalDateTime now = LocalDateTime.now();
        if ("EMAIL".equalsIgnoreCase(channel)) {
            u.setEmail(target);
            u.setEmailVerifiedAt(now);
        } else {
            u.setPhone(target);
            u.setPhoneVerifiedAt(now);
        }

        userMapper.insert(u);
        return u;
    }

    private String buildUsername(String channel, String target, String id) {
        // 用户名策略：避免直接暴露手机号/邮箱，默认用 u_ + ULID 末尾6位
        String suffix = id.substring(Math.max(0, id.length() - 6));
        return "u_" + suffix.toLowerCase();
    }
}