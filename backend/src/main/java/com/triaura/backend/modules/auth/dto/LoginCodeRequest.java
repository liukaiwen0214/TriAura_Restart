package com.triaura.backend.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 验证码登录：邮箱/手机号 + 验证码
 */
public class LoginCodeRequest {
    @NotBlank public String channel; // EMAIL / PHONE
    @NotBlank public String target;  // 邮箱/手机号
    @NotBlank public String code;
    public String verificationId;
}