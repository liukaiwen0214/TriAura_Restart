-- V1：用户表（ULID 主键，避免自增暴露）
CREATE TABLE IF NOT EXISTS app_user (
                                        id CHAR(26) PRIMARY KEY COMMENT 'ULID（后端生成）',

                                        username VARCHAR(50) NULL UNIQUE COMMENT '用户名（可选，唯一）',
                                        display_name VARCHAR(50) NULL COMMENT '显示名/昵称',

                                        email VARCHAR(255) NULL UNIQUE COMMENT '邮箱（可选，唯一）',
                                        email_verified_at DATETIME NULL COMMENT '邮箱验证时间',

                                        phone VARCHAR(30) NULL UNIQUE COMMENT '手机号（可选，唯一，建议带国家码）',
                                        phone_verified_at DATETIME NULL COMMENT '手机号验证时间',

                                        password_hash VARCHAR(255)  NULL COMMENT '密码哈希（bcrypt）',

                                        role VARCHAR(20) NOT NULL DEFAULT 'OWNER' COMMENT '角色：OWNER/ADMIN/USER',
                                        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE/DISABLED',

                                        last_login_at DATETIME NULL COMMENT '最后登录时间',
                                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='用户表';

-- 验证码表（真实场景：验证码不要写进用户表）
-- code_hash 存 hash，不存明文；明文只发给用户
CREATE TABLE IF NOT EXISTS verification_code (
                                                 id CHAR(26) PRIMARY KEY COMMENT 'ULID',
                                                 channel VARCHAR(10) NOT NULL COMMENT '渠道：PHONE/EMAIL',
                                                 target VARCHAR(255) NOT NULL COMMENT '手机号或邮箱',
                                                 scene VARCHAR(20) NOT NULL COMMENT '场景：REGISTER/LOGIN/RESET',
                                                 code_hash VARCHAR(255) NOT NULL COMMENT '验证码哈希（bcrypt）',
                                                 expires_at DATETIME NOT NULL COMMENT '过期时间',
                                                 used_at DATETIME NULL COMMENT '使用时间（null=未用）',
                                                 send_count INT NOT NULL DEFAULT 1 COMMENT '发送次数（可用于限流）',
                                                 created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                 updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                 INDEX idx_target_scene (target, scene),
                                                 INDEX idx_expires (expires_at)
) COMMENT='验证码记录表';

-- 登录会话表：用 HttpOnly Cookie 保存 token，对应这里存的 session
CREATE TABLE IF NOT EXISTS app_user_session (
                                                id CHAR(26) PRIMARY KEY COMMENT 'ULID',
                                                user_id CHAR(26) NOT NULL COMMENT '用户ID(app_user.id)',
                                                token VARCHAR(64) NOT NULL COMMENT '会话token（随机字符串，存到Cookie里）',
                                                expires_at DATETIME NOT NULL COMMENT '过期时间',
                                                revoked_at DATETIME NULL COMMENT '注销时间（null=未注销）',
                                                created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                                UNIQUE KEY uk_token (token),
                                                KEY idx_user (user_id),
                                                KEY idx_expires (expires_at)
) COMMENT='用户登录会话表';