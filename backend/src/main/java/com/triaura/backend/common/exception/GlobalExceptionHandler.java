package com.triaura.backend.common.exception;

import com.triaura.backend.common.api.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 全局异常处理：把后端抛出的异常统一转换成 ApiResponse 返回给前端。
 * 为什么要做这个？
 * 1) 前端永远拿到同一种 JSON 结构：{ code, message, data }
 * 2) 后端用 HTTP 状态码表达“协议层是否成功”（200/400/401/403/404/500…）
 * 3) 同时用业务 code 表达“业务层错误类型”（比如参数错误、未登录、资源不存在等）
 * 推荐约定（我们先做最常见几类）：
 * - 参数/校验错误：HTTP 400 + 业务 code=10001
 * - 服务器异常：HTTP 500 + 业务 code=50000
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数校验失败（@Valid + 请求体 JSON）
     * 场景：@RequestBody + @Valid 的 DTO 校验不通过
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(10001, emptyToDefault(msg, "参数校验失败")));
    }

    /**
     * 参数绑定失败（例如 query/form 参数转换失败）
     * 场景：?page=abc（本该是数字）这类转换错误
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(10001, emptyToDefault(msg, "参数校验失败")));
    }

    /**
     * 参数校验失败（@Validated + 单个参数）
     * 场景：@RequestParam/@PathVariable 上加 @NotBlank 等校验
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        String msg = ex.getConstraintViolations().stream()
                .map(this::formatConstraintViolation)
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(10001, emptyToDefault(msg, "参数校验失败")));
    }

    /**
     * 缺少必填参数（例如缺少 ?id=xxx）
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingParam(MissingServletRequestParameterException ex) {
        String msg = "缺少必填参数：" + ex.getParameterName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(10002, msg));
    }

    /**
     * 请求体解析失败（常见原因：JSON 格式不对、字段类型不匹配）
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotReadable(HttpMessageNotReadableException ex) {
        String msg = "请求体解析失败，请检查 JSON 格式/字段类型";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(10003, msg));
    }

    /**
     * 兜底：其它未捕获异常
     * 注意：生产环境不建议把堆栈信息返回给前端
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAny(Exception ex) {
        ex.printStackTrace(); // 开发期打印异常，方便定位（生产环境会换成日志）
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(50000, "服务器异常"));
    }
    /**
     * 新版 Spring 的方法参数校验异常（Spring 版本不同，抛的异常类可能不同）
     * 目的：不管框架抛哪种“校验失败”异常，我们都统一返回：
     * - HTTP 400
     * - 业务 code = 10001
     */
    @ExceptionHandler({HandlerMethodValidationException.class, MethodValidationException.class})
    public ResponseEntity<ApiResponse<Void>> handleMethodValidation(Exception ex) {
        // 这里先给一个通用提示（想做更精细的字段提示，后面我们再增强）
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(10001, "参数校验失败"));
    }

    private String formatFieldError(FieldError fe) {
        // 例如：title: 不能为空
        String field = Objects.toString(fe.getField(), "");
        String message = Objects.toString(fe.getDefaultMessage(), "参数不合法");
        return field.isBlank() ? message : (field + ": " + message);
    }

    private String formatConstraintViolation(ConstraintViolation<?> v) {
        // 例如：create.arg0: 不能为空（不同场景路径会略有差异）
        String path = Objects.toString(v.getPropertyPath(), "");
        String message = Objects.toString(v.getMessage(), "参数不合法");
        return path.isBlank() ? message : (path + ": " + message);
    }

    private String emptyToDefault(String s, String def) {
        if (s == null || s.trim().isEmpty()) return def;
        return s;
    }
}