package com.triaura.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 相关配置
 *
 * 目前先做一件“马上有价值”的事：CORS 跨域
 * - 你前端以后用 Vue 本地跑（比如 5173）访问后端 8080，就会遇到跨域
 * - 这里先放开常用开发环境的跨域，后面上线再收紧
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // 开发阶段：放开本地前端端口（Vue/Vite 常用 5173）
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://127.0.0.1:5173",
                        "http://localhost:8080",
                        "http://127.0.0.1:8080"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}