package com.github.borisskert;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

class ImmutableSetTest {

    private Set<String> abcSet;
    private Set<String> emptySet;
    private HashSet<String> emptyHashSet;
    private HashSet<String> abcHashSet;

    @BeforeEach
    public void setup() throws Exception {
        abcSet = ImmutableSet.of("A", "B", "C");
        emptySet = ImmutableSet.empty();

        emptyHashSet = new HashSet<>();
        abcHashSet = new HashSet<>();
        abcHashSet.add("A");
        abcHashSet.add("B");
        abcHashSet.add("C");
    }

    @Test
    public void shouldProvideSize() throws Exception {
        assertThat(abcSet.size(), is(equalTo(3)));
        assertThat(emptySet.size(), is(equalTo(0)));
    }

    @Test
    public void shouldIndicateIfEmpty() throws Exception {
        assertThat(abcSet.isEmpty(), is(equalTo(false)));
        assertThat(emptySet.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void shouldIndicateIfContainsElement() throws Exception {
        assertThat(abcSet.contains("A"), is(equalTo(true)));
        assertThat(abcSet.contains("B"), is(equalTo(true)));
        assertThat(abcSet.contains("C"), is(equalTo(true)));

        assertThat(abcSet.contains("X"), is(equalTo(false)));
        assertThat(abcSet.contains("Y"), is(equalTo(false)));
        assertThat(abcSet.contains("Z"), is(equalTo(false)));


        assertThat(emptySet.contains("A"), is(equalTo(false)));
        assertThat(emptySet.contains("B"), is(equalTo(false)));
        assertThat(emptySet.contains("C"), is(equalTo(false)));

        assertThat(emptySet.contains("X"), is(equalTo(false)));
        assertThat(emptySet.contains("Y"), is(equalTo(false)));
        assertThat(emptySet.contains("Z"), is(equalTo(false)));
    }

    @Test
    public void shouldProvideIterator() throws Exception {
        Iterator<String> iterator = abcSet.iterator();

        assertThat(iterator.next(), is(equalTo("A")));
        assertThat(iterator.next(), is(equalTo("B")));
        assertThat(iterator.next(), is(equalTo("C")));

        assertThat(iterator.hasNext(), Is.is(IsEqual.equalTo(false)));


        iterator = emptySet.iterator();
        assertThat(iterator.hasNext(), Is.is(IsEqual.equalTo(false)));
    }

    @Test
    public void shouldConvertToArray() throws Exception {
        Object[] array = abcSet.toArray();

        assertThat(array.length, is(equalTo(3)));
        assertThat(array[0], is(equalTo("A")));
        assertThat(array[1], is(equalTo("B")));
        assertThat(array[2], is(equalTo("C")));


        array = emptySet.toArray();
        assertThat(array.length, is(equalTo(0)));
    }

    @Test
    public void shouldConvertToTypedArray() throws Exception {
        String[] array = abcSet.toArray(new String[0]);

        assertThat(array.length, is(equalTo(3)));
        assertThat(array[0], is(equalTo("A")));
        assertThat(array[1], is(equalTo("B")));
        assertThat(array[2], is(equalTo("C")));


        array = emptySet.toArray(new String[0]);
        assertThat(array.length, is(equalTo(0)));
    }

    @Test
    public void shouldNotAllowToAddElement() throws Exception {
        try {
            abcSet.add("D");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not add an element to this Set")));
        }
    }

    @Test
    public void shouldNotAllowToRemoveElement() throws Exception {
        try {
            abcSet.remove("C");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not remove an element from this Set")));
        }
    }

    @Test
    public void shouldIndicateIfContainsAllElements() throws Exception {
        assertThat(abcSet.containsAll(ImmutableList.of("A", "B", "C")), is(equalTo(true)));
        assertThat(emptySet.containsAll(ImmutableList.of("A", "B", "C")), is(equalTo(false)));
    }

    @Test
    public void shouldNotAllowToAddElements() throws Exception {
        try {
            abcSet.addAll(ImmutableList.of("A", "B", "C"));
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not add elements from this Set")));
        }
    }

    @Test
    public void shouldNotAllowToRetainElements() throws Exception {
        try {
            abcSet.retainAll(ImmutableList.of("A"));
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not retain elements from this Set")));
        }
    }

    @Test
    public void shouldNotAllowToRemoveElements() throws Exception {
        try {
            abcSet.removeAll(ImmutableList.of("A", "B", "C"));
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not remove elements from this Set")));
        }
    }

    @Test
    public void shouldNotAllowToClear() throws Exception {
        try {
            abcSet.clear();
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not clear this Set")));
        }
    }

    @Test
    public void shouldNotAllowCreateImmutableSetWithNullElement() throws Exception {
        try {
            ImmutableSet.of((String) null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("Parameter 'item' must not be null")));
        }
    }

    @Test
    public void shouldProduceSetFromArray() throws Exception {
        String[] array = new String[]{"A", "B", "C"};
        Set<String> producedSet = ImmutableSet.of(array);

        assertThat(producedSet, is(equalTo(abcSet)));
        assertThat(producedSet, is(instanceOf(ImmutableSet.class)));
    }

    @Test
    public void shouldProduceFromCollection() throws Exception {
        Collection<String> collection = ImmutableList.of("A", "B", "C");
        Set<String> producedSet = ImmutableSet.of(collection);

        assertThat(producedSet, is(equalTo(abcSet)));
        assertThat(producedSet, is(instanceOf(ImmutableSet.class)));
    }

    @Test
    public void shouldProduceFromIterator() throws Exception {
        Iterator<String> iterator = ImmutableList.of("A", "B", "C").iterator();
        Set<String> producedSet = ImmutableSet.of(iterator);

        assertThat(producedSet, is(equalTo(abcSet)));
        assertThat(producedSet, is(instanceOf(ImmutableSet.class)));
    }

    @Test
    public void shouldProduceFromIterable() throws Exception {
        Iterable<String> iterable = ImmutableList.of("A", "B", "C");
        Set<String> producedSet = ImmutableSet.of(iterable);

        assertThat(producedSet, is(equalTo(abcSet)));
        assertThat(producedSet, is(instanceOf(ImmutableSet.class)));
    }

    @Test
    public void shouldEqualImmutableSetWithSameElements() throws Exception {
        Set<String> another = ImmutableSet.of("A", "B", "C");

        assertThat(abcSet, is(equalTo(another)));
        assertThat(another, is(equalTo(abcSet)));

        assertThat(emptySet, is(not(equalTo(another))));
        assertThat(another, is(not(equalTo(emptySet))));
    }

    @Test
    public void shouldEqualHashSetWithSameElements() throws Exception {
        assertThat(abcSet, is(equalTo(abcHashSet)));
        assertThat(emptySet, is(equalTo(emptyHashSet)));

        assertThat(abcSet, is(not(equalTo(emptyHashSet))));
        assertThat(emptySet, is(not(equalTo(abcHashSet))));

        assertThat(emptySet, is(not(equalTo(abcSet))));
        assertThat(abcSet, is(not(equalTo(emptySet))));
    }

    @Test
    public void shouldProvideSameHashCodeLikeHashSetWithSameElements() throws Exception {
        assertThat(abcSet.hashCode(), is(equalTo(abcHashSet.hashCode())));
        assertThat(emptySet.hashCode(), is(equalTo(emptyHashSet.hashCode())));

        assertThat(abcSet.hashCode(), is(not(equalTo(emptySet.hashCode()))));
    }

    @Test
    public void shouldProduceSameStringRepresentation() throws Exception {
        assertThat(abcSet.toString(), is(equalTo(abcHashSet.toString())));
        assertThat(emptySet.toString(), is(equalTo(emptyHashSet.toString())));
    }
}
