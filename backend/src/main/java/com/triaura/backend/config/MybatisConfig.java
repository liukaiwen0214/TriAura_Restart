package com.triaura.backend.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 配置类

 * 作用：
 * 1) 统一扫描 Mapper 接口，避免每个 Mapper 都写 @Mapper
 * 2) 扫描 modules 下所有子包，识别其中的 mapper 接口
 */
@Configuration
@MapperScan(basePackages = "com.triaura.backend.modules")
public class MybatisConfig {
}