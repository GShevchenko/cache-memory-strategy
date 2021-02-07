package com.cache.memory.strategy;

import java.util.*;

public class LFUStorage<K, V> implements IStorage<K, V> {

    private final int MAX_SIZE;
    private HashMap<K, V> storage;
    private HashMap<K, Long> keyFrequency;
    private TreeMap<Long, HashSet<K>> sortedFrequencies;



    public LFUStorage(int maxSize) {
        this.MAX_SIZE = maxSize;
        this.storage = new HashMap<>();
        this.keyFrequency = new HashMap<>();
        this.sortedFrequencies = new TreeMap<>();
    }

    private void eviction() {
        Long minFrequency = sortedFrequencies.firstKey();
        K evictionKey = sortedFrequencies.get(minFrequency).iterator().next();
        removeFromSortedFrequencies(evictionKey, minFrequency);
        keyFrequency.remove(evictionKey);
        storage.remove(evictionKey);
    }


    @Override
    public V put(K key, V value) {
        if (storage.size() == MAX_SIZE) {
            eviction();
        }
        V oldValue = storage.put(key, value);
        Long frequency = keyFrequency.computeIfAbsent(key, f -> 1L);
        sortedFrequencies.computeIfAbsent(frequency, f -> new HashSet<>()).add(key);
        return oldValue;
    }

    @Override
    public V get(K key) {
        V value = storage.get(key);
        if (value != null) {
            incrementFrequency(key);
        }
        return value;
    }

    private void incrementFrequency(K key) {
        Long frequency = keyFrequency.compute(key, (k, f) -> f + 1L);
        removeFromSortedFrequencies(key, frequency - 1);
        sortedFrequencies.computeIfAbsent(frequency, keys -> new HashSet<>()).add(key);
    }

    private void removeFromSortedFrequencies(K key, long frequency) {
        if (sortedFrequencies.get(frequency).size() > 1) {
            sortedFrequencies.get(frequency).remove(key);
        } else {
            sortedFrequencies.remove(frequency);
        }
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public String toString() {
        return storage.toString();
    }
}
