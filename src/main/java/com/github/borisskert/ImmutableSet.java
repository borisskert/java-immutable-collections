package com.github.borisskert;

import java.util.*;

public class ImmutableSet<E> implements Set<E> {

    private final Set<E> protectedSet;

    private ImmutableSet(Set<E> protectedSet) {
        this.protectedSet = protectedSet;
    }

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
    public boolean add(E e) {
        throw new UnsupportedOperationException("You must not add an element to this Set");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("You must not remove an element from this Set");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return protectedSet.containsAll(c);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if(getClass() == o.getClass()) {
            ImmutableSet<?> that = (ImmutableSet<?>) o;
            return protectedSet.equals(that.protectedSet);
        }

        if(o instanceof Set) {
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

    public static <T> Set<T> empty() {
        Set<T> hashSet = new HashSet<>(0);
        return new ImmutableSet<>(hashSet);
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
        HashSet<T> hashSet = new HashSet<>(items);
        return new ImmutableSet<>(hashSet);
    }
}
