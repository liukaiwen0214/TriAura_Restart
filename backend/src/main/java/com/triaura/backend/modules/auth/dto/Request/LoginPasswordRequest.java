package com.triaura.backend.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 密码登录：邮箱或手机号 + 密码
 */
public class LoginPasswordRequest {
    @NotBlank public String account;  // 邮箱 or 手机号
    @NotBlank public String password;
}