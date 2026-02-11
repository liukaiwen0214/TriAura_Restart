package com.triaura.backend.modules.user.entity;

import java.time.LocalDateTime;

/**
 * 用户实体（对应 app_user 表）
 * 注意：passwordHash 只在后端使用，接口返回不要传出去。
 */
public class AppUser {
    private String id;
    private String username;
    private String displayName;
    private String email;
    private LocalDateTime emailVerifiedAt;
    private String phone;
    private LocalDateTime phoneVerifiedAt;
    private String passwordHash;
    private String role;
    private String status;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // getters/setters 省略你可以用 IDEA 自动生成
    // 右键 -> 生成 -> Getter 和 Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getEmailVerifiedAt() { return emailVerifiedAt; }
    public void setEmailVerifiedAt(LocalDateTime emailVerifiedAt) { this.emailVerifiedAt = emailVerifiedAt; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDateTime getPhoneVerifiedAt() { return phoneVerifiedAt; }
    public void setPhoneVerifiedAt(LocalDateTime phoneVerifiedAt) { this.phoneVerifiedAt = phoneVerifiedAt; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}