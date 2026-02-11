package com.triaura.backend.common.api;

/**
 * 统一 API 响应包装类（后端所有接口建议都返回它）。
 * 目的：
 * 1) 前端处理更简单：不管哪个接口，拿到的都是同一种结构。
 * 2) 便于扩展：以后可以在这里统一增加 traceId、分页信息等，而不需要改每个接口。
 * 约定：
 * - code == 0：业务成功
 * - code != 0：业务失败（message 为失败原因）
 * - data：业务数据（成功时通常有值，失败时一般为 null）
 */
public class ApiResponse<T> {

    /**
     * 业务状态码：0=成功；非0=失败。
     */
    private int code;

    /**
     * 提示信息：成功一般为 "ok"；失败时为具体错误原因。
     */
    private String message;

    /**
     * 业务数据：不同接口返回不同类型。
     */
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 快捷创建“成功”响应。
     *
     * @param data 业务数据
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(0, "ok", data);
    }

    /**
     * 快捷创建“失败”响应。
     *
     * @param code    失败码（你可以自己定义，例如 10001 表示参数错误）
     * @param message 失败原因
     */
    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
    /**
     * 失败响应（使用统一错误码 + 默认提示）。
     */
    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getDefaultMessage(), null);
    }

    /**
     * 失败响应（使用统一错误码 + 自定义提示）。
     * 适合：需要把具体字段错误告诉前端，例如 “title 不能为空”
     */
    public static <T> ApiResponse<T> fail(ErrorCode errorCode, String message) {
        return new ApiResponse<>(errorCode.getCode(), message, null);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}