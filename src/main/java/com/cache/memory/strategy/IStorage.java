package com.cache.memory.strategy;

public interface IStorage<K, V> {

    V put(K key, V value);

    V get(K key);

    int size();
}
