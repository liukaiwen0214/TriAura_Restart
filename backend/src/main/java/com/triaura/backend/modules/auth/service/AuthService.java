package com.triaura.backend.modules.auth.service;

import com.triaura.backend.common.error.BizException;
import com.triaura.backend.common.error.ErrorCode;
import com.triaura.backend.common.id.IdGenerator;
import com.triaura.backend.modules.auth.dto.*;
import com.triaura.backend.modules.auth.entity.AppUserSession;
import com.triaura.backend.modules.auth.entity.VerificationCode;
import com.triaura.backend.modules.auth.mapper.VerificationCodeMapper;
import com.triaura.backend.modules.user.entity.AppUser;
import com.triaura.backend.modules.user.mapper.AppUserMapper;
import com.triaura.backend.modules.user.mapper.AppUserSessionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
@Slf4j
@Service
public class AuthService {

    private final AppUserMapper userMapper;
    private final VerificationCodeMapper codeMapper;
    private final AppUserSessionMapper sessionMapper;

    @Value("${app.auth.session-days:14}")
    private int sessionDays;

    public AuthService(AppUserMapper userMapper,
                       VerificationCodeMapper codeMapper,
                       AppUserSessionMapper sessionMapper) {
        this.userMapper = userMapper;
        this.codeMapper = codeMapper;
        this.sessionMapper = sessionMapper;
    }

    /**
     * 发送验证码（真实场景：这里对接短信/邮件服务）
     * - 注意：不要返回验证码给前端（安全）
     */
    public SendCodeResponse sendCode(SendCodeRequest req) {
        String code = String.valueOf(100000 + new SecureRandom().nextInt(900000));

        VerificationCode v = new VerificationCode();
        v.id = IdGenerator.ulid();
        v.channel = req.channel;
        v.scene = req.scene;
        v.target = req.target;
        v.codeHash = BCrypt.hashpw(code, BCrypt.gensalt(10));
        v.expiresAt = LocalDateTime.now().plusMinutes(10);
        v.usedAt = null;
        v.sendCount = 1;

        codeMapper.insert(v);

        log.info("debug code for {}: {}", req.target, code);

        return SendCodeResponse.of(v.id);
    }

    /**
     * 注册：验证码 + 密码
     * C：注册时视为已验证，写 verified_at
     */
    public String register(RegisterRequest req) {
        boolean isEmail = "EMAIL".equalsIgnoreCase(req.channel);

        AppUser exists = isEmail ? userMapper.findByEmail(req.target) : userMapper.findByPhone(req.target);
        if (exists != null) throw new BizException(ErrorCode.USER_EXISTS);

        // 校验并消耗验证码
        verifyAndConsumeCode(req.verificationId, req.channel, "REGISTER", req.target, req.code);
        AppUser u = new AppUser();
        u.id = IdGenerator.ulid();

        u.username = null;
        u.displayName = null;

        if (isEmail) {
            u.email = req.target;
            u.emailVerifiedAt = LocalDateTime.now(); // C：验证即视为已验证
            u.phone = null;
            u.phoneVerifiedAt = null;
        } else {
            u.phone = req.target;
            u.phoneVerifiedAt = LocalDateTime.now();
            u.email = null;
            u.emailVerifiedAt = null;
        }

        u.passwordHash = BCrypt.hashpw(req.password, BCrypt.gensalt(10));
        u.role = "OWNER";      // 你是自用系统，默认 OWNER
        u.status = "ACTIVE";
        u.lastLoginAt = null;

        userMapper.insert(u);
        return u.id;
    }

    /**
     * 密码登录：邮箱/手机号 + 密码
     */
    public String loginByPassword(LoginPasswordRequest req) {
        AppUser u = findByAccount(req.account);
        if (u == null) throw new BizException(ErrorCode.INVALID_CREDENTIALS);

        // 允许 password_hash 为空（比如自动创建的用户），为空则不能密码登录
        if (u.passwordHash == null || u.passwordHash.isBlank()) {
            throw new BizException(ErrorCode.INVALID_CREDENTIALS, "该账号未设置密码，请用验证码登录");
        }

        if (!BCrypt.checkpw(req.password, u.passwordHash)) {
            throw new BizException(ErrorCode.INVALID_CREDENTIALS);
        }

        userMapper.touchLastLogin(u.id);
        return createSession(u.id);
    }

    /**
     * 验证码登录（B：用户不存在则自动创建；C：验证即视为已验证）
     */
    public String loginByCode(LoginCodeRequest req) {
        boolean isEmail = "EMAIL".equalsIgnoreCase(req.channel);

        // 校验并消耗验证码（LOGIN）
        verifyAndConsumeCode(req.verificationId, req.channel, "LOGIN", req.target, req.code);
        AppUser u = isEmail ? userMapper.findByEmail(req.target) : userMapper.findByPhone(req.target);

        // B：不存在就自动创建用户
        if (u == null) {
            u = new AppUser();
            u.id = IdGenerator.ulid();

            u.username = null;
            u.displayName = null;

            if (isEmail) {
                u.email = req.target;
                u.emailVerifiedAt = LocalDateTime.now(); // C
                u.phone = null;
                u.phoneVerifiedAt = null;
            } else {
                u.phone = req.target;
                u.phoneVerifiedAt = LocalDateTime.now();
                u.email = null;
                u.emailVerifiedAt = null;
            }

            // 因为你老表 password_hash NOT NULL，这里给一个随机密码 hash 占位。
            // 后面你把 password_hash 改成 NULL 后，这段可改为 null。
            String randomPwd = "AUTO_" + IdGenerator.ulid();
            u.passwordHash = BCrypt.hashpw(randomPwd, BCrypt.gensalt(10));

            u.role = "OWNER";
            u.status = "ACTIVE";
            u.lastLoginAt = LocalDateTime.now();

            userMapper.insert(u);
        } else {
            // 已存在：更新最后登录
            userMapper.touchLastLogin(u.id);

            // C：如果之前没验证过，这里也可以顺手补上 verified_at（可选）
            // 由于你目前 mapper 没写 update verified_at，这里先不改，后面需要再补 update 方法即可。
        }

        return createSession(u.id);
    }

    /**
     * 注销：撤销 session
     */
    public void logout(String token) {
        if (token == null || token.isBlank()) return;
        sessionMapper.revokeByToken(token);
    }

    /**
     * 校验会话，返回 userId（无效返回 null）
     */
    public String validateSession(String token) {
        if (token == null || token.isBlank()) return null;

        AppUserSession s = sessionMapper.findByToken(token);
        if (s == null) return null;
        if (s.revokedAt != null) return null;
        if (s.expiresAt == null || s.expiresAt.isBefore(LocalDateTime.now())) return null;

        return s.userId;
    }

    // ---------------- private helpers ----------------

    private AppUser findByAccount(String account) {
        if (account.contains("@")) return userMapper.findByEmail(account);
        return userMapper.findByPhone(account);
    }

    /**
     * 校验并“消费”验证码：
     * - 必须未过期
     * - 必须未使用（used_at == null）
     * - hash 校验通过后，写 used_at=now
     */
    private void verifyAndConsumeCode(String verificationId, String channel, String scene, String target, String code) {
        if (verificationId == null || verificationId.isBlank()) {
            throw new BizException(ErrorCode.CODE_INVALID, "缺少验证码标识");
        }

        VerificationCode v = codeMapper.findById(verificationId);
        if (v == null) throw new BizException(ErrorCode.CODE_INVALID, "验证码不存在");

        // ✅ 防止拿别人的 verificationId
        if (!equalsIgnoreCase(v.channel, channel) ||
                !equalsIgnoreCase(v.scene, scene) ||
                !safeEquals(v.target, target)) {
            throw new BizException(ErrorCode.CODE_INVALID, "验证码不匹配");
        }

        if (v.usedAt != null) throw new BizException(ErrorCode.CODE_INVALID, "验证码已使用");
        if (v.expiresAt == null || v.expiresAt.isBefore(LocalDateTime.now())) {
            throw new BizException(ErrorCode.CODE_INVALID, "验证码已过期");
        }

        if (!BCrypt.checkpw(code, v.codeHash)) {
            throw new BizException(ErrorCode.CODE_INVALID, "验证码错误");
        }

        int updated = codeMapper.markUsedIfNotUsed(v.id);
        if (updated == 0) throw new BizException(ErrorCode.CODE_INVALID, "验证码已使用");
    }

    private boolean safeEquals(String a, String b) {
        return a != null && b != null && a.equals(b);
    }
    private boolean equalsIgnoreCase(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }
    /**
     * 创建会话：生成随机 token，写入 session 表
     */
    private String createSession(String userId) {
        String token = randomToken(48);

        AppUserSession s = new AppUserSession();
        s.id = IdGenerator.ulid();
        s.userId = userId;
        s.token = token;
        s.expiresAt = LocalDateTime.now().plusDays(sessionDays);
        s.revokedAt = null;

        sessionMapper.insert(s);
        return token;
    }

    private String randomToken(int bytes) {
        byte[] b = new byte[bytes];
        new SecureRandom().nextBytes(b);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(b);
    }
}