package com.triaura.backend.modules.user.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体（对应 app_user 表）
 * 注意：passwordHash 只在后端使用，接口返回不要传出去。
 */
@Data
public class AppUser {
    public String id;
    public String username;
    public String displayName;
    public String email;
    public LocalDateTime emailVerifiedAt;
    public String phone;
    public LocalDateTime phoneVerifiedAt;
    public String passwordHash;
    public String role;
    public String status;
    public LocalDateTime lastLoginAt;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}