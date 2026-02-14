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

-- TriAura - 待办表（MySQL 8+）
-- 说明：
-- 1) 时间精确到分钟：用 DATETIME（你写入时把 seconds 置 00 即可）
-- 2) remind_at 允许早于 due_at
-- 3) source_key 用于追溯/幂等：同一用户 + source_key 唯一（手动创建可为空）
-- 4) done 与 status 二选一也行；这里两者都保留，你后面可删一个

CREATE TABLE IF NOT EXISTS `task_todo` (
                                           `id`            CHAR(26)      NOT NULL COMMENT '主键ID（雪花/UUID转bigint）',
                                           `user_id`       CHAR(26)       NOT NULL COMMENT '归属用户ID',

                                           `title`         VARCHAR(120) NOT NULL COMMENT '待办标题（快捷创建必填）',
                                           `note`          VARCHAR(1000)     NULL COMMENT '详细说明/备注',
                                           `tag`           VARCHAR(32)       NULL COMMENT '标签（如：日记/厨房/工作）',

                                           `priority`      VARCHAR(10)  NOT NULL DEFAULT 'MEDIUM' COMMENT '优先级：LOW/MEDIUM/HIGH',
                                           `status`        VARCHAR(12)  NOT NULL DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE/DONE/CANCELED/ARCHIVED 等',
                                           `done`          TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '是否完成（0/1）',
                                           `done_at`       DATETIME         NULL COMMENT '完成时间',

                                           `due_at`        DATETIME         NULL COMMENT '到期时间（精确到分钟，允许为空）',
                                           `remind_at`     DATETIME         NULL COMMENT '提醒时间（精确到分钟，允许早于/晚于due_at，允许为空）',

                                           `source_type`     VARCHAR(20)  NOT NULL DEFAULT 'MANUAL_QUICK' COMMENT '来源类型：MANUAL_QUICK/MANUAL_DETAIL/AUTO_JOB',
                                           `source_key`      VARCHAR(128)      NULL COMMENT '来源唯一键（追溯/幂等），同一用户下唯一；手动可为空',
                                           `source_ref_type` VARCHAR(32)       NULL COMMENT '来源关联对象类型（可空）',
                                           `source_ref_id`   BIGINT            NULL COMMENT '来源关联对象ID（可空）',
                                           `source_payload`  JSON              NULL COMMENT '来源补充信息JSON（可空，存规则参数/快照）',

                                           `deleted`       TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '软删标记（0/1）',
                                           `deleted_at`    DATETIME         NULL COMMENT '软删时间',

                                           `created_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `updated_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                           PRIMARY KEY (`id`),

    -- 幂等：同一用户同一个 source_key 只能有一条（source_key 为空时不影响）
                                           UNIQUE KEY `uk_user_source_key` (`user_id`, `source_key`),

                                           KEY `idx_user_status` (`user_id`, `status`),
                                           KEY `idx_user_done`   (`user_id`, `done`),
                                           KEY `idx_due_at`      (`user_id`, `due_at`),
                                           KEY `idx_remind_at`   (`user_id`, `remind_at`),
                                           KEY `idx_deleted`     (`user_id`, `deleted`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci
    COMMENT='待办（支持快捷/详细/自动创建，含来源追溯）';