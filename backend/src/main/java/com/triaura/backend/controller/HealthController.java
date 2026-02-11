package com.triaura.backend.controller;

import com.triaura.backend.common.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查接口（用于验证后端服务是否正常启动）。
 * 说明：
 * - 这里用 /api/v1 作为 API 版本前缀，后续升级可以新增 /api/v2 而不影响旧客户端。
 */
@RestController
@RequestMapping("/api/v1") // API 版本前缀
public class HealthController {

    /**
     * GET /api/v1/health
     *
     * 返回统一响应结构：{ code, message, data }
     * 便于前端统一处理（成功/失败/提示信息）。
     */
    @GetMapping("/health")
    public ApiResponse<String> health() {
        // data 字段返回 "OK"，用于快速判断服务可用
        return ApiResponse.ok("OK");
    }
}