package com.github.borisskert;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implements an Immutable {@link Map}
 * Attention: Works internal with a {@link HashMap} implementation.
 * https://stackoverflow.com/a/22636750
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class ImmutableMap<K, V> implements Map<K, V> {

    /* *****************************************************************************************************************
     * Constants
     ***************************************************************************************************************** */

    private static final Map EMPTY_IMMUTABLE_MAP = new ImmutableMap(new HashMap<>(0));

    /* *****************************************************************************************************************
     * Readonly fields
     ***************************************************************************************************************** */

    private final Map<K, V> protectedMap;

    /* *****************************************************************************************************************
     * Constructor(s)
     ***************************************************************************************************************** */

    private ImmutableMap(Map<K, V> protectedMap) {
        this.protectedMap = protectedMap;
    }

    /* *****************************************************************************************************************
     * Implementation of Map<K,V> interface
     **************************************************************************************************************** */

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (getClass() == o.getClass()) {
            ImmutableMap<?, ?> that = (ImmutableMap<?, ?>) o;
            return Objects.equals(protectedMap, that.protectedMap);
        }

        return equalsMap(o);
    }

    @Override
    public int hashCode() {
        return protectedMap.hashCode();
    }

    @Override
    public String toString() {
        return protectedMap.toString();
    }

    /* *****************************************************************************************************************
     * Private methods
     **************************************************************************************************************** */

    /**
     * Similar implementation to {@link AbstractMap#equals(Object)}
     */
    private boolean equalsMap(Object other) {
        if (!(other instanceof Map))
            return false;

        Map<?, ?> otherMap = (Map<?, ?>) other;
        if (otherMap.size() != size())
            return false;

        for (Entry<K, V> entry : entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (value == null) {
                if (!(otherMap.get(key) == null && otherMap.containsKey(key)))
                    return false;
            } else {
                if (!value.equals(otherMap.get(key)))
                    return false;
            }
        }

        return true;
    }

    /* *****************************************************************************************************************
     * Factory methods
     **************************************************************************************************************** */

    /**
     * Returns an empty instance of an {@link ImmutableMap}
     *
     * @param <K> the key type
     * @param <V> the value type
     * @return an empty instance (not a new one)
     */
    public static <K, V> Map<K, V> empty() {
        return EMPTY_IMMUTABLE_MAP;
    }

    /**
     * Creates an immutable {@link Map} with the specified entries.
     *
     * @param entry        the first {@link Map.Entry}, which must not be null
     * @param otherEntries potential further {@link Map.Entry}s to be added
     * @param <K>          the type of the key
     * @param <V>          the type of the values
     * @return a new instance of an {@link ImmutableMap} containing the specified items
     * @see <a href="https://stackoverflow.com/a/14252221">Java SafeVarargs annotation</a>
     */
    @SafeVarargs
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

    /**
     * Creates a new immutable {@link java.util.Map.Entry}
     *
     * @param key   the key of the {@link java.util.Map.Entry}
     * @param value the value of the {@link java.util.Map.Entry}
     * @param <K>   the key type
     * @param <V>   the value type
     * @return a new anonymous instance of an {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new Map.Entry<>() {

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
        };
    }

    /**
     * Provides a {@link Collector} to collect {@link Stream}s
     *
     * @param keyMapper   the mapper {@link Function} to get the key for each element
     * @param valueMapper the mapper {@link Function} to get the value for each element
     * @param <T>         the type of the {@link Stream} elements
     * @param <K>         the key type
     * @param <V>         the value type
     * @return a new {@link Collector} instance
     */
    public static <T, K, V> Collector<T, ?, Map<K, V>> collect(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends V> valueMapper
    ) {
        return new ImmutableMapCollector<>(keyMapper, valueMapper);
    }

    /* *****************************************************************************************************************
     * Inner class(es)
     **************************************************************************************************************** */

    private static class ImmutableEntry<K, V> implements Map.Entry<K, V> {
        private final Map.Entry<K, V> entry;

        private ImmutableEntry(Map.Entry<K, V> entry) {
            this.entry = entry;
        }

        @Override
        public K getKey() {
            return entry.getKey();
        }

        @Override
        public V getValue() {
            return entry.getValue();
        }

        @Override
        public V setValue(V value) {
            throw new IllegalStateException("You must not change the value of this entry");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;

            if (getClass() == o.getClass()) {
                ImmutableEntry<?, ?> that = (ImmutableEntry<?, ?>) o;
                return this.entry.equals(that.entry);
            }

            if (o instanceof Map.Entry) {
                return this.entry.equals(o);
            }

            return false;
        }

        @Override
        public int hashCode() {
            return entry.hashCode();
        }

        @Override
        public String toString() {
            return entry.toString();
        }
    }

    private static class ImmutableMapCollector<T, K, V> implements Collector<T, Map<K, V>, Map<K, V>> {

        private final Function<? super T, ? extends K> keyMapper;
        private final Function<? super T, ? extends V> valueMapper;

        private ImmutableMapCollector(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
            this.keyMapper = keyMapper;
            this.valueMapper = valueMapper;
        }

        @Override
        public Supplier<Map<K, V>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<Map<K, V>, T> accumulator() {
            return (map, element) -> {
                K key = keyMapper.apply(element);
                V value = Objects.requireNonNull(valueMapper.apply(element));
                V previousValue = map.putIfAbsent(key, value);
                if (previousValue != null)
                    throw new IllegalStateException(
                            String.format(
                                    "Duplicate key %s (attempted merging values %s and %s)", key, previousValue, value
                            )
                    );
                ;
            };
        }

        @Override
        public BinaryOperator<Map<K, V>> combiner() {
            return (map, otherMap) -> {
                for (Map.Entry<K, V> entry : otherMap.entrySet()) {
                    K key = entry.getKey();
                    V value = Objects.requireNonNull(entry.getValue());
                    V previousValue = map.putIfAbsent(key, value);
                    if (previousValue != null) throw new IllegalStateException(
                            String.format(
                                    "Duplicate key %s (attempted merging values %s and %s)", key, previousValue, value
                            )
                    );
                }
                return map;
            };
        }

        @Override
        public Function<Map<K, V>, Map<K, V>> finisher() {
            return ImmutableMap::new;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }
    }
}
