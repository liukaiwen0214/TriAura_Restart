package com.triaura.backend.common.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 请求日志过滤器：打印每次请求的路径、方法、耗时
 */
@Slf4j
@Component
public class RequestLogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long cost = System.currentTimeMillis() - start;
            log.info("[REQ] {} {} -> {} ({}ms)",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    cost);
        }
    }
}