package com.triaura.backend.modules.auth.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 验证码记录实体（对应 verification_code 表）
 * 明文验证码不会存库，只存 hash（更安全）。
 */
@Data
public class VerificationCode {
    public String id;
    public String channel; // PHONE/EMAIL
    public String target;  // 手机号/邮箱
    public String scene;   // REGISTER/LOGIN/RESET
    public String codeHash;
    public LocalDateTime expiresAt;
    public LocalDateTime usedAt;
    public Integer sendCount;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}