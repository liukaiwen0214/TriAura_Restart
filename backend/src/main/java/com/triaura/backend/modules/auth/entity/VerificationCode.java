package com.triaura.backend.modules.auth.entity;

import java.time.LocalDateTime;

/**
 * 验证码记录实体（对应 verification_code 表）
 * 明文验证码不会存库，只存 hash（更安全）。
 */
public class VerificationCode {
    private String id;
    private String channel; // PHONE/EMAIL
    private String target;  // 手机号/邮箱
    private String scene;   // REGISTER/LOGIN/RESET
    private String codeHash;
    private LocalDateTime expiresAt;
    private LocalDateTime usedAt;
    private Integer sendCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // getters/setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    public String getScene() { return scene; }
    public void setScene(String scene) { this.scene = scene; }
    public String getCodeHash() { return codeHash; }
    public void setCodeHash(String codeHash) { this.codeHash = codeHash; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public LocalDateTime getUsedAt() { return usedAt; }
    public void setUsedAt(LocalDateTime usedAt) { this.usedAt = usedAt; }
    public Integer getSendCount() { return sendCount; }
    public void setSendCount(Integer sendCount) { this.sendCount = sendCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}