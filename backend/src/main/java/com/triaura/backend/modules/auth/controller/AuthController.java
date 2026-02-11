package com.triaura.backend.modules.auth.controller;

import com.triaura.backend.common.api.ApiResponse;
import com.triaura.backend.modules.auth.dto.RegisterReq;
import com.triaura.backend.modules.auth.dto.SendCodeReq;
import com.triaura.backend.modules.auth.service.AuthService;
import com.triaura.backend.modules.user.entity.AppUser;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 认证接口（注册/验证码）
 * 统一前缀：/api/v1
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 发送验证码
     * - scene 先支持 REGISTER（注册）
     */
    @PostMapping("/send-code")
    public ApiResponse<String> sendCode(@Valid @RequestBody SendCodeReq req) {
        authService.sendCode(req.getChannel(), req.getTarget(), req.getScene());
        return ApiResponse.ok("ok");
    }

    /**
     * 注册（手机号/邮箱 + 验证码 + 密码）
     */
    @PostMapping("/register")
    public ApiResponse<UserView> register(@Valid @RequestBody RegisterReq req) {
        AppUser u = authService.register(req.getChannel(), req.getTarget(), req.getCode(), req.getPassword(), req.getDisplayName());
        return ApiResponse.ok(UserView.from(u));
    }

    /**
     * 返回给前端的用户视图：不要把 password_hash 这种敏感字段返回
     */
    public record UserView(String id, String username, String displayName, String email, String phone, String role, String status) {
        public static UserView from(AppUser u) {
            return new UserView(u.getId(), u.getUsername(), u.getDisplayName(), u.getEmail(), u.getPhone(), u.getRole(), u.getStatus());
        }
    }
}