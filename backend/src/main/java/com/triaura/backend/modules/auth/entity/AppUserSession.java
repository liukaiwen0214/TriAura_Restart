package com.triaura.backend.modules.auth.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * app_user_session 表实体
 */
@Data
public class AppUserSession {
    public String id;
    public String userId;
    public String token;
    public LocalDateTime expiresAt;
    public LocalDateTime revokedAt;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}