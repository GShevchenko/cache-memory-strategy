package com.cache.memory.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CacheTest {


    @Test
    void checkException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cache<>(Cache.StrategyType.LRU, 0);
        });
        assertEquals(Cache.CACHE_MAX_SIZE_ERROR, exception.getMessage());
    }
}
