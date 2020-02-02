package com.github.borisskert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * https://stackoverflow.com/a/22636750
 * @param <K>
 * @param <V>
 */
public class ImmutableMap<K, V> implements Map<K, V> {

    private static final Map<String, String> EMPTY_IMMUTABLE_MAP = new ImmutableMap<>(new HashMap<>(0));

    private final Map<K, V> protectedMap;

    private ImmutableMap(Map<K, V> protectedMap) {
        this.protectedMap = protectedMap;
    }

    @Override
    public int size() {
        return protectedMap.size();
    }

    @Override
    public boolean isEmpty() {
        return protectedMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return protectedMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return protectedMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return protectedMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        throw new IllegalStateException("You must not put an element to this map");
    }

    @Override
    public V remove(Object key) {
        throw new IllegalStateException("You must not remove an element from this map");
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new IllegalStateException("You must not put a elements to this map");
    }

    @Override
    public void clear() {
        throw new IllegalStateException("You must not clear this map");
    }

    @Override
    public Set<K> keySet() {
        return protectedMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return protectedMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return protectedMap.entrySet()
                .stream()
                .map(ImmutableEntry::new)
                .collect(Collectors.toUnmodifiableSet());
    }

    public static Map<String, String> empty() {
        return EMPTY_IMMUTABLE_MAP;
    }

    public static <K, V> Map<K, V> of(Map.Entry<K, V> entry, Map.Entry<K, V>... otherEntries) {
        Map<K, V> protectedMap = new HashMap<>();

        protectedMap.put(entry.getKey(), entry.getValue());

        if (otherEntries != null) {
            for (Entry<K, V> otherEntry : otherEntries) {
                protectedMap.put(otherEntry.getKey(), otherEntry.getValue());
            }
        }

        return new ImmutableMap<>(protectedMap);
    }

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new ImmutableEntry<>(key, value);
    }

    private static class ImmutableEntry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private final V value;

        private ImmutableEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        private ImmutableEntry(Map.Entry<K, V> entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new IllegalStateException("You must not change the value of this entry");
        }
    }
}
