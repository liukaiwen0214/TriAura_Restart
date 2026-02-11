-- V2：验证码表（真实场景：验证码不要写进用户表）
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