package com.cache.memory.strategy;

public class Cache<K, V> {

    public static final String CACHE_MAX_SIZE_ERROR = "Max size must be greater than 0";
    public static final String UNKNOWN_CACHE_MEMORY_STRATEGY = "Unknown cache memory strategy";
    private IStorage<K, V> storage;

    public Cache(StrategyType strategyType, int maxSize) {
        if (maxSize == 0) {
            throw new IllegalArgumentException(CACHE_MAX_SIZE_ERROR);
        }

        switch (strategyType) {
            case LFU:
              this.storage = new LFUStorage<>(maxSize);
            case LRU:
              this.storage = new LRUStorage<>(maxSize);
            default:
                throw new IllegalArgumentException(UNKNOWN_CACHE_MEMORY_STRATEGY);
        }

    }


    public enum StrategyType {
        LRU, LFU
    }


    public V put(K key, V value) {
        return storage.put(key, value);
    }

    public V get(K key) {
        return storage.get(key);
    }

    public int size() {
        return storage.size();
    }


}
