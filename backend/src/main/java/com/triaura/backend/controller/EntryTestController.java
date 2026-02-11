package com.triaura.backend.controller;

import com.triaura.backend.common.api.ApiResponse;
import com.triaura.backend.module.entry.dto.EntryCreateReq;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 临时测试接口：用来验证参数校验 + 全局异常处理。
 * 后面我们会把它迁移成正式的 Entry 模块接口。
 */
@RestController
@RequestMapping("/api/v1/entries")
public class EntryTestController {

    /**
     * 创建 entry（测试）
     * - 传入空 title，会触发校验异常 → GlobalExceptionHandler 返回 HTTP 400 + code=10001
     */
    @PostMapping
    public ApiResponse<String> create(@Valid @RequestBody EntryCreateReq req) {
        return ApiResponse.ok("created: " + req.getTitle());
    }
}