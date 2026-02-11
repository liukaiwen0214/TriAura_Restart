package com.triaura.backend.common.error;

import com.triaura.backend.common.api.ErrorCode;

/**
 * 业务异常（用于：参数合法但业务不允许/业务状态不匹配等）
 *
 * 设计要点：
 * 1) 携带 ErrorCode（你自定义的业务码），方便前端和日志统一处理
 * 2) message 允许自定义：比如 "邮箱已注册"、"验证码错误" 这种更友好
 * 3) 继承 RuntimeException：在 Service 层直接 throw，不用到处 try/catch
 */
public class BizException extends RuntimeException {

    /** 业务错误码（比如 10001、10002、50000） */
    private final int code;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.code = errorCode.getCode();
    }

    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    /** 给全局异常处理器取业务码用 */
    public int getCode() {
        return code;
    }
}