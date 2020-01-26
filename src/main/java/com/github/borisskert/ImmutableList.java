package com.github.borisskert;

import java.util.*;

public class ImmutableList<E> implements List<E> {
    private static final ImmutableList EMPTY_IMMUTABLE_LIST = new ImmutableList<>(
            new ArrayList<>(0)
    );

    private final List<E> protectedList;

    private ImmutableList(List<E> protectedList) {
        this.protectedList = protectedList;
    }

    public int size() {
        return protectedList.size();
    }

    public boolean isEmpty() {
        return protectedList.isEmpty();
    }

    public boolean contains(Object o) {
        return protectedList.contains(o);
    }

    public Iterator<E> iterator() {
        return protectedList.iterator();
    }

    public Object[] toArray() {
        return protectedList.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return protectedList.toArray(a);
    }

    public boolean add(E t) {
        throw new IllegalStateException("You must not add an element to this list");
    }

    public boolean remove(Object o) {
        throw new IllegalStateException("You must not remove an element from this list");
    }

    public boolean containsAll(Collection<?> c) {
        return protectedList.containsAll(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        throw new IllegalStateException("You must not add elements to this list");
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        throw new IllegalStateException("You must not add elements to this list");
    }

    public boolean removeAll(Collection<?> c) {
        throw new IllegalStateException("You must not remove elements from this list");
    }

    public boolean retainAll(Collection<?> c) {
        throw new IllegalStateException("You must not remove elements from this list");
    }

    public void clear() {
        throw new IllegalStateException("You must not clear this list");
    }

    public E get(int index) {
        return protectedList.get(index);
    }

    public E set(int index, E element) {
        throw new IllegalStateException("You must not set an element in this list");
    }

    public void add(int index, E element) {
        throw new IllegalStateException("You must not add elements to this list");
    }

    public E remove(int index) {
        throw new IllegalStateException("You must not remove an element from this list");
    }

    public int indexOf(Object o) {
        return protectedList.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return protectedList.lastIndexOf(o);
    }

    public ListIterator<E> listIterator() {
        return protectedList.listIterator();
    }

    public ListIterator<E> listIterator(int index) {
        return protectedList.listIterator(index);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return protectedList.subList(fromIndex, toIndex);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (!(other instanceof List))
            return false;

        List<?> otherList = (List<?>) other;
        if (this.size() != ((List<?>) other).size()) {
            return false;
        }

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

    @Override
    public int hashCode() {
        return Objects.hash(protectedList);
    }

    public static <T> List<T> empty() {
        return EMPTY_IMMUTABLE_LIST;
    }

    @SafeVarargs
    public static <T> List<T> of(final T... others) {
        // TODO null check

        List<T> arrayList = new ArrayList<>(others.length);
        Collections.addAll(arrayList, others);

        return new ImmutableList<>(arrayList);
    }

    public static <T> List<T> of(Collection<T> collection) {
        List<T> arrayList = new ArrayList<>(collection.size());
        arrayList.addAll(collection);

        return new ImmutableList<>(arrayList);
    }

    public static <T> List<T> of(Iterable<T> iterable) {
        List<T> arrayList = new ArrayList<>();

        for (T item : iterable) {
            arrayList.add(item);
        }

        return new ImmutableList<>(arrayList);
    }
}
