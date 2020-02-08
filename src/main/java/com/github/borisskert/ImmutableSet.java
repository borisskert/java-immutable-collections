package com.github.borisskert;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ImmutableSet<E> implements Set<E> {

    /* *****************************************************************************************************************
     * Constants
     **************************************************************************************************************** */

    private static final ImmutableSet EMPTY_IMMUTABLE_SET = new ImmutableSet<>(new HashSet<>(0));

    /* *****************************************************************************************************************
     * Readonly fields
     **************************************************************************************************************** */

    private final Set<E> protectedSet;

    /* *****************************************************************************************************************
     * Constructor(s)
     **************************************************************************************************************** */

    private ImmutableSet(Set<E> protectedSet) {
        this.protectedSet = protectedSet;
    }

    /* *****************************************************************************************************************
     * Implementation of Set<E>
     **************************************************************************************************************** */

    @Override
    public int size() {
        return protectedSet.size();
    }

    @Override
    public boolean isEmpty() {
        return protectedSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return protectedSet.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return protectedSet.iterator();
    }

    @Override
    public Object[] toArray() {
        return protectedSet.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return protectedSet.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return protectedSet.containsAll(c);
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("You must not add an element to this Set");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("You must not remove an element from this Set");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("You must not add elements from this Set");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("You must not retain elements from this Set");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("You must not remove elements from this Set");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("You must not clear this Set");
    }

    /* *****************************************************************************************************************
     * Overrides of Object
     **************************************************************************************************************** */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (getClass() == o.getClass()) {
            ImmutableSet<?> that = (ImmutableSet<?>) o;
            return protectedSet.equals(that.protectedSet);
        }

        if (o instanceof Set) {
            return protectedSet.equals(o);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return protectedSet.hashCode();
    }

    @Override
    public String toString() {
        return protectedSet.toString();
    }

    /* *****************************************************************************************************************
     * Factory methods
     **************************************************************************************************************** */

    public static <T> Set<T> empty() {
        return EMPTY_IMMUTABLE_SET;
    }

    @SafeVarargs
    public static <T> Set<T> of(T item, final T... others) {
        Objects.requireNonNull(item, "Parameter 'item' must not be null");

        Set<T> hashSet = new HashSet<>(others.length + 1);
        hashSet.add(item);
        Collections.addAll(hashSet, others);

        return new ImmutableSet<>(hashSet);
    }

    public static <T> Set<T> of(T[] items) {
        HashSet<T> hashSet = new HashSet<>(items.length);
        hashSet.addAll(Arrays.asList(items));

        return new ImmutableSet<>(hashSet);
    }

    public static <T> Set<T> of(Collection<T> items) {
        HashSet<T> hashSet = new HashSet<>(items.size());
        hashSet.addAll(items);

        return new ImmutableSet<>(hashSet);
    }

    public static <T> Set<T> of(Iterator<T> items) {
        HashSet<T> hashSet = new HashSet<>();

        while (items.hasNext()) {
            T item = items.next();
            hashSet.add(item);
        }

        return new ImmutableSet<>(hashSet);
    }

    public static <T> Set<T> of(Iterable<T> items) {
        HashSet<T> hashSet = new HashSet<>();

        for (T item : items) {
            hashSet.add(item);
        }

        return new ImmutableSet<>(hashSet);
    }

    public static <T> Collector<T, Set<T>, Set<T>> collect() {
        return new ImmutableSetCollector<>();
    }

    /* *****************************************************************************************************************
     * Inner class(es)
     **************************************************************************************************************** */

    private static class ImmutableSetCollector<T> implements Collector<T, Set<T>, Set<T>> {
        @Override
        public Supplier<Set<T>> supplier() {
            return HashSet::new;
        }

        @Override
        public BiConsumer<Set<T>, T> accumulator() {
            return Set::add;
        }

        @Override
        public BinaryOperator<Set<T>> combiner() {
            return (left, right) -> {
                left.addAll(right);
                return left;
            };
        }

        @Override
        public Function<Set<T>, Set<T>> finisher() {
            return ImmutableSet::of;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return ImmutableSet.empty();
        }
    }
}
