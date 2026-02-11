package com.triaura.backend.common.id;

import com.github.f4b6a3.ulid.UlidCreator;

/**
 * ID 生成器：ULID（26位），比雪花更“短”，且大体按时间有序。
 */
public class IdGenerator {
    private IdGenerator() {}

    public static String ulid() {
        return UlidCreator.getUlid().toString();
    }
}