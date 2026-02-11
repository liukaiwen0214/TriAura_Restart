package com.triaura.backend.common.api;

/**
 * 业务错误码枚举（统一管理，避免到处写魔法数字）。

 * 注意：这不是 HTTP 状态码！
 * - HTTP 状态码：200/400/401/403/404/500... 用来表达“协议层成功/失败”
 * - 业务 code：用来表达“业务层错误类型”，便于前端统一处理
 */
public enum ErrorCode {

    /** 成功（一般不需要用到，成功我们用 ApiResponse.ok(...)） */
    OK(0, "ok"),

    /** 参数校验失败（@Valid/@Validated 等） */
    VALIDATION_ERROR(10001, "参数校验失败"),

    /** 缺少必填参数（例如缺少 ?id=xxx） */
    MISSING_PARAMETER(10002, "缺少必填参数"),

    /** 请求体解析失败（JSON 格式错误 / 字段类型不匹配） */
    BODY_NOT_READABLE(10003, "请求体解析失败"),

    /** 服务器异常（兜底） */
    INTERNAL_ERROR(50000, "服务器异常");

    private final int code;
    private final String defaultMessage;

    ErrorCode(int code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public int getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}