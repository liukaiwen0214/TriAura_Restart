package com.triaura.backend.modules.auth.controller;

import com.triaura.backend.common.api.ApiResponse;
import com.triaura.backend.common.error.BizException;
import com.triaura.backend.common.error.ErrorCode;
import com.triaura.backend.modules.auth.dto.*;
import com.triaura.backend.modules.auth.service.AuthService;
import com.triaura.backend.modules.user.entity.AppUser;
import com.triaura.backend.modules.user.mapper.AppUserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证接口
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final AppUserMapper userMapper;

    @Value("${app.auth.cookie-name:TA_SESSION}")
    private String cookieName;

    @Value("${app.auth.cookie-secure:false}")
    private boolean cookieSecure;

    @Value("${app.auth.session-days:14}")
    private int sessionDays;

    public AuthController(AuthService authService, AppUserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    /**
     * 发送验证码
     */
    @PostMapping("/send-code")
    public ApiResponse<SendCodeResponse> sendCode(@Valid @RequestBody SendCodeRequest req) {
        authService.sendCode(req);
        return ApiResponse.ok(authService.sendCode(req),"发送成功");
    }

    /**
     * 注册：验证码 + 密码
     */
    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest req) {
        String userId = authService.register(req);
        return ApiResponse.ok(userId,"注册成功");
    }

    /**
     * 密码登录：写 Cookie
     */
    @PostMapping("/login/password")
    public ApiResponse<String> loginPassword(@Valid @RequestBody LoginPasswordRequest req,
                                             HttpServletResponse resp) {
        String token = authService.loginByPassword(req);
        writeSessionCookie(resp, token);
        return ApiResponse.ok(req.account,"登陆成功");
    }

    /**
     * 验证码登录：B 自动创建用户；写 Cookie
     */
    @PostMapping("/login/code")
    public ApiResponse<String> loginCode(@Valid @RequestBody LoginCodeRequest req,
                                         HttpServletResponse resp) {
        String token = authService.loginByCode(req);
        writeSessionCookie(resp, token);
        return ApiResponse.ok("ok","登陆成功");
    }

    /**
     * 退出登录：撤销 session + 清 cookie
     */
    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest req, HttpServletResponse resp) {
        String token = readCookie(req, cookieName);
        authService.logout(token);
        clearCookie(resp);
        return ApiResponse.ok("ok","退出成功");
    }

    /**
     * 当前登录用户：前端启动时用它判断登录态
     */
    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> me(HttpServletRequest req) {
        String token = readCookie(req, cookieName);
        String userId = authService.validateSession(token);
        if (userId == null) {
            throw new BizException(ErrorCode.NOT_LOGIN);
        }

        AppUser u = userMapper.findById(userId);
        if (u == null) throw new BizException(ErrorCode.NOT_LOGIN);

        // 返回安全字段（不返回 password_hash）
        Map<String, Object> data = new HashMap<>();
        data.put("id", u.id);
        data.put("username", u.username);
        data.put("displayName", u.displayName);
        data.put("email", u.email);
        data.put("phone", u.phone);
        data.put("role", u.role);
        data.put("status", u.status);
        data.put("lastLoginAt", u.lastLoginAt);

        return ApiResponse.ok(data,"获取成功");
    }

    // ---------------- Cookie helpers ----------------

    /**
     * 写入 HttpOnly Cookie（浏览器自动保存并随请求携带）
     */
    private void writeSessionCookie(HttpServletResponse resp, String token) {
        Cookie c = new Cookie(cookieName, token);
        c.setHttpOnly(true);
        c.setSecure(cookieSecure);
        c.setPath("/");
        c.setMaxAge(sessionDays * 24 * 3600);
        resp.addCookie(c);
    }

    /**
     * 清除 Cookie
     */
    private void clearCookie(HttpServletResponse resp) {
        Cookie c = new Cookie(cookieName, "");
        c.setHttpOnly(true);
        c.setSecure(cookieSecure);
        c.setPath("/");
        c.setMaxAge(0);
        resp.addCookie(c);
    }

    private String readCookie(HttpServletRequest req, String name) {
        Cookie[] cs = req.getCookies();
        if (cs == null) return null;
        for (Cookie c : cs) {
            if (name.equals(c.getName())) return c.getValue();
        }
        return null;
    }
}