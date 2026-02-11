-- V1：用户表（ULID 主键，避免自增暴露）
CREATE TABLE IF NOT EXISTS app_user (
                                        id CHAR(26) PRIMARY KEY COMMENT 'ULID（后端生成）',

                                        username VARCHAR(50) NULL UNIQUE COMMENT '用户名（可选，唯一）',
                                        display_name VARCHAR(50) NULL COMMENT '显示名/昵称',

                                        email VARCHAR(255) NULL UNIQUE COMMENT '邮箱（可选，唯一）',
                                        email_verified_at DATETIME NULL COMMENT '邮箱验证时间',

                                        phone VARCHAR(30) NULL UNIQUE COMMENT '手机号（可选，唯一，建议带国家码）',
                                        phone_verified_at DATETIME NULL COMMENT '手机号验证时间',

                                        password_hash VARCHAR(255) NOT NULL COMMENT '密码哈希（bcrypt）',

                                        role VARCHAR(20) NOT NULL DEFAULT 'OWNER' COMMENT '角色：OWNER/ADMIN/USER',
                                        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE/DISABLED',

                                        last_login_at DATETIME NULL COMMENT '最后登录时间',
                                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='用户表';