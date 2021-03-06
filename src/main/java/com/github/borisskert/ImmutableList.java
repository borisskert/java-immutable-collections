package com.github.borisskert;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Implements an unmodifiable {@link List}
 * Attention: Works internal with an {@link ArrayList} implementation.
 *
 * @param <E> the type of the elements
 */
public class ImmutableList<E> implements List<E> {

    /* *****************************************************************************************************************
     * Constants
     ***************************************************************************************************************** */

    private static final ImmutableList EMPTY_IMMUTABLE_LIST = new ImmutableList<>(
            new ArrayList<>(0)
    );

    /* *****************************************************************************************************************
     * Readonly fields
     **************************************************************************************************************** */

    private final List<E> protectedList;

    /* *****************************************************************************************************************
     * Constructor(s)
     **************************************************************************************************************** */

    private ImmutableList(List<E> protectedList) {
        this.protectedList = protectedList;
    }

    /* *****************************************************************************************************************
     * Implementation of List<T> interface
     **************************************************************************************************************** */

    public int size() {
        return protectedList.size();
    }

    public boolean isEmpty() {
        return protectedList.isEmpty();
    }

    public boolean contains(Object o) {
        return protectedList.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return protectedList.containsAll(c);
    }

    public E get(int index) {
        return protectedList.get(index);
    }

    public int indexOf(Object o) {
        return protectedList.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return protectedList.lastIndexOf(o);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return protectedList.subList(fromIndex, toIndex);
    }

    public Object[] toArray() {
        return protectedList.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return protectedList.toArray(a);
    }

    public Iterator<E> iterator() {
        return protectedList.iterator();
    }

    public ListIterator<E> listIterator() {
        return protectedList.listIterator();
    }

    public ListIterator<E> listIterator(int index) {
        return protectedList.listIterator(index);
    }

    public boolean add(E t) {
        throw new UnsupportedOperationException("You must not add an element to this list");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("You must not remove an element from this list");
    }

    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("You must not add elements to this list");
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("You must not add elements to this list");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("You must not remove elements from this list");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("You must not remove elements from this list");
    }

    public void clear() {
        throw new UnsupportedOperationException("You must not clear this list");
    }

    public E set(int index, E element) {
        throw new UnsupportedOperationException("You must not set an element in this list");
    }

    public void add(int index, E element) {
        throw new UnsupportedOperationException("You must not add elements to this list");
    }

    public E remove(int index) {
        throw new UnsupportedOperationException("You must not remove an element from this list");
    }

    /* *****************************************************************************************************************
     * Overrides of Object
     **************************************************************************************************************** */

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (!(other instanceof List))
            return false;

        List<?> otherList = (List<?>) other;
        if (this.size() != otherList.size()) {
            return false;
        }

        return containEqualItems(otherList);
    }

    @Override
    public int hashCode() {
        return protectedList.hashCode();
    }

    /* *****************************************************************************************************************
     * Private methods
     **************************************************************************************************************** */

    private boolean containEqualItems(List<?> otherList) {
        ListIterator<E> thisIterator = listIterator();
        ListIterator<?> otherIterator = otherList.listIterator();

        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            E thisElement = thisIterator.next();
            Object otherElement = otherIterator.next();

            if (!(Objects.equals(thisElement, otherElement)))
                return false;
        }

        return !(thisIterator.hasNext() || otherIterator.hasNext());
    }

    /* *****************************************************************************************************************
     * Factory methods
     **************************************************************************************************************** */

    /**
     * Returns an empty instance of an {@link ImmutableList}
     *
     * @param <T> the item type
     * @return an empty instance (not a new one)
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> empty() {
        return EMPTY_IMMUTABLE_LIST;
    }

    /**
     * Creates an immutable {@link List} containing the specified items
     *
     * @param item   the first item
     * @param others the other optional items
     * @param <T>    the item type
     * @return a new instance of an {@link ImmutableList}
     */
    @SafeVarargs
    public static <T> List<T> of(T item, final T... others) {
        Objects.requireNonNull(item, "Parameter 'item' must not be null");

        List<T> arrayList = new ArrayList<>(others.length + 1);
        arrayList.add(item);
        Collections.addAll(arrayList, others);

        return new ImmutableList<>(arrayList);
    }

    /**
     * Creates an immutable {@link List} containing all items of the specified array (in same order)
     *
     * @param items the specified array
     * @param <T>   the item type
     * @return a new instance of an {@link ImmutableList}
     */
    public static <T> List<T> of(T[] items) {
        Objects.requireNonNull(items, "Parameter 'items' must not be null");

        List<T> arrayList = new ArrayList<>(items.length);
        Collections.addAll(arrayList, items);

        return new ImmutableList<>(arrayList);
    }

    /**
     * Creates an immutable {@link List} containing all items of the specified {@link Collection}
     *
     * @param items the specified {@link Collection} containing the items
     * @param <T>   the item type
     * @return a new instance of an {@link ImmutableList}
     */
    public static <T> List<T> of(Collection<T> items) {
        Objects.requireNonNull(items, "Parameter 'items' must not be null");

        List<T> arrayList = new ArrayList<>(items.size());
        arrayList.addAll(items);

        return new ImmutableList<>(arrayList);
    }

    /**
     * Creates an immutable {@link List} containing all items of the specified {@link Iterable}
     *
     * @param items the {@link Iterable} contaning the items
     * @param <T>   the item type
     * @return a new instance of an {@link ImmutableList}
     */
    public static <T> List<T> of(Iterable<T> items) {
        Objects.requireNonNull(items, "Parameter 'items' must not be null");

        List<T> arrayList = new ArrayList<>();

        for (T item : items) {
            arrayList.add(item);
        }

        return new ImmutableList<>(arrayList);
    }

    /**
     * Provides a {@link Collector} to collect a {@link Stream} to an immutable {@link List}
     *
     * @param <T> the item type
     * @return a new instance of an {@link Collector}
     */
    public static <T> Collector<T, List<T>, List<T>> collect() {
        return new ImmutableListCollector<T>();
    }

    /* *****************************************************************************************************************
     * Inner class(es)
     **************************************************************************************************************** */

    private static class ImmutableListCollector<T> implements Collector<T, List<T>, List<T>> {
        @Override
        public Supplier<List<T>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            return List::add;
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            return (left, right) -> {
                left.addAll(right);
                return left;
            };
        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            return ImmutableList::of;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }
    }
}
