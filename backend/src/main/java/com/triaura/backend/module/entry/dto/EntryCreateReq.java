package com.triaura.backend.module.entry.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 创建 Entry 的请求体（DTO）。
 * 用 jakarta.validation 注解做参数校验：
 * - title 不能为空
 */
public class EntryCreateReq {

    /** 标题：必填 */
    @NotBlank(message = "title 不能为空")
    private String title;

    /** 内容：可选 */
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}