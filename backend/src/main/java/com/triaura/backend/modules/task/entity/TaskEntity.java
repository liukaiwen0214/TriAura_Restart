package com.triaura.backend.modules.task.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 待办（task_todo）
 */
@Data
@TableName("task_todo")
public class TaskTodo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID（雪花/UUID转bigint） */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /** 归属用户ID */
    @TableField("user_id")
    private Long userId;

    /** 待办标题（快捷创建必填） */
    @TableField("title")
    private String title;

    /** 详细说明/备注 */
    @TableField("note")
    private String note;

    /** 标签（如：日记/厨房/工作） */
    @TableField("tag")
    private String tag;

    /** 优先级：LOW/MEDIUM/HIGH */
    @TableField("priority")
    private String priority;

    /** 状态：ACTIVE/DONE/CANCELED/ARCHIVED 等 */
    @TableField("status")
    private String status;

    /** 是否完成（0/1） */
    @TableField("done")
    private Boolean done;

    /** 完成时间 */
    @TableField("done_at")
    private LocalDateTime doneAt;

    /** 到期时间（精确到分钟，允许为空） */
    @TableField("due_at")
    private LocalDateTime dueAt;

    /** 提醒时间（精确到分钟，允许早于/晚于due_at，允许为空） */
    @TableField("remind_at")
    private LocalDateTime remindAt;

    /** 来源类型：MANUAL_QUICK/MANUAL_DETAIL/AUTO_JOB */
    @TableField("source_type")
    private String sourceType;

    /** 来源唯一键（追溯/幂等），同一用户下唯一；手动可为空 */
    @TableField("source_key")
    private String sourceKey;

    /** 来源关联对象类型（可空） */
    @TableField("source_ref_type")
    private String sourceRefType;

    /** 来源关联对象ID（可空） */
    @TableField("source_ref_id")
    private Long sourceRefId;

    /** 来源补充信息JSON（可空，存规则参数/快照） */
    @TableField(value = "source_payload", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    private Object sourcePayload;

    /** 软删标记（0/1） */
    @TableLogic(value = "0", delval = "1")
    @TableField("deleted")
    private Integer deleted;

    /** 软删时间 */
    @TableField("deleted_at")
    private LocalDateTime deletedAt;

    /** 创建时间 */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}