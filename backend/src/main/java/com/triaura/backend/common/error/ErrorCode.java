package com.triaura.backend.common.error;

import lombok.Getter;

/**
 * 业务错误码枚举（统一管理，避免到处写魔法数字）。

 * 注意：这不是 HTTP 状态码！
 * - HTTP 状态码：200/400/401/403/404/500... 用来表达“协议层成功/失败”
 * - 业务 code：用来表达“业务层错误类型”，便于前端统一处理
 */
@Getter
public enum ErrorCode {

    /** 成功（一般不需要用到，成功我们用 ApiResponse.ok(...)） */
    OK(0, "ok"),
    LOGIN_SUCCESS(200,"登陆成功"),

    /** 参数校验失败（@Valid/@Validated 等） */
    VALIDATION_ERROR(10001, "参数校验失败"),

    /** 缺少必填参数（例如缺少 ?id=xxx） */
    MISSING_PARAMETER(10002, "缺少必填参数"),

    /** 请求体解析失败（JSON 格式错误 / 字段类型不匹配） */
    BODY_NOT_READABLE(10003, "请求体解析失败"),

    NOT_LOGIN(10004, "未登录或会话已失效"),
    INVALID_CREDENTIALS(10005, "账号或密码错误"),
    CODE_INVALID(10006, "验证码错误或已过期"),
    USER_EXISTS(10007, "用户已存在"),

    /** 服务器异常（兜底） */
    INTERNAL_ERROR(50000, "服务器异常");

    private final int code;
    private final String defaultMessage;

    ErrorCode(int code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

}