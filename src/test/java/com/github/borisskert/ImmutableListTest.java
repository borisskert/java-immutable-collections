package com.github.borisskert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.fail;

class ImmutableListTest {

    private List<String> emptyList;
    private List<String> abcImmutableList;
    private List<String> listWithThreeDuplicateElements;
    private List<String> abcArrayList;

    @BeforeEach
    public void setup() throws Exception {
        emptyList = ImmutableList.empty();
        abcImmutableList = ImmutableList.of("A", "B", "C");
        listWithThreeDuplicateElements = ImmutableList.of("A", "B", "C", "A", "B", "C");

        abcArrayList = new ArrayList<>();
        abcArrayList.add("A");
        abcArrayList.add("B");
        abcArrayList.add("C");
    }

    @Test
    public void shouldHaveSpecificSize() throws Exception {
        assertThat(emptyList.size(), is(equalTo(0)));
        assertThat(abcImmutableList.size(), is(equalTo(3)));
        assertThat(listWithThreeDuplicateElements.size(), is(equalTo(6)));
    }

    @Test
    public void shouldIndicateIfEmpty() throws Exception {
        assertThat(emptyList.isEmpty(), is(true));
        assertThat(abcImmutableList.isEmpty(), is(false));
        assertThat(listWithThreeDuplicateElements.isEmpty(), is(false));
    }

    @Test
    public void shouldContainElements() throws Exception {
        assertThat(emptyList.contains("A"), is(false));
        assertThat(emptyList.contains("B"), is(false));
        assertThat(emptyList.contains("C"), is(false));


        assertThat(abcImmutableList.contains("A"), is(true));
        assertThat(abcImmutableList.contains("B"), is(true));
        assertThat(abcImmutableList.contains("C"), is(true));

        assertThat(abcImmutableList.contains("X"), is(false));
        assertThat(abcImmutableList.contains("Y"), is(false));
        assertThat(abcImmutableList.contains("Z"), is(false));


        assertThat(listWithThreeDuplicateElements.contains("A"), is(true));
        assertThat(listWithThreeDuplicateElements.contains("B"), is(true));
        assertThat(listWithThreeDuplicateElements.contains("C"), is(true));

        assertThat(listWithThreeDuplicateElements.contains("X"), is(false));
        assertThat(listWithThreeDuplicateElements.contains("Y"), is(false));
        assertThat(listWithThreeDuplicateElements.contains("Z"), is(false));
    }

    @Test
    public void shouldReturnArray() throws Exception {
        Object[] asArray = abcImmutableList.toArray();
        assertThat(asArray, is(equalTo(new Object[]{"A", "B", "C"})));
    }

    @Test
    public void shouldReturnTypedArray() throws Exception {
        String[] asArray = abcImmutableList.toArray(new String[0]);
        assertThat(asArray, is(equalTo(new String[]{"A", "B", "C"})));
    }

    @Test
    public void shouldNotAllowToAddElement() throws Exception {
        try {
            abcImmutableList.add("D");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("You must not add an element to this list")));
        }
    }

    @Test
    public void shouldNotAllowToRemoveElement() throws Exception {
        try {
            abcImmutableList.remove("A");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("You must not remove an element from this list")));
        }
    }

    @Test
    public void shouldContainAllElements() throws Exception {
        boolean containsAll = abcImmutableList.containsAll(ImmutableList.of("A", "B"));
        assertThat(containsAll, is(true));
    }

    @Test
    public void shouldNotContainAllElements() throws Exception {
        boolean containsAll = abcImmutableList.containsAll(ImmutableList.of("A", "D"));
        assertThat(containsAll, is(false));
    }

    @Test
    public void shouldNotAllowToAddAllElements() throws Exception {
        try {
            abcImmutableList.addAll(ImmutableList.of("D", "E"));
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("You must not add elements to this list")));
        }
    }

    @Test
    public void shouldNotAllowToInsertElements() throws Exception {
        try {
            abcImmutableList.addAll(0, ImmutableList.of("D", "E"));
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("You must not add elements to this list")));
        }
    }

    @Test
    public void shouldNotAllowToRemoveAllElements() throws Exception {
        try {
            abcImmutableList.removeAll(ImmutableList.of("A", "B"));
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("You must not remove elements from this list")));
        }
    }

    @Test
    public void shouldNotAllowToRetainElements() throws Exception {
        try {
            abcImmutableList.retainAll(ImmutableList.of("A", "B"));
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("You must not remove elements from this list")));
        }
    }

    @Test
    public void shouldNotAllowToClear() throws Exception {
        try {
            abcImmutableList.clear();
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("You must not clear this list")));
        }
    }

    @Test
    public void shouldGetOneElement() throws Exception {
        assertThat(abcImmutableList.get(0), is(equalTo("A")));
        assertThat(abcImmutableList.get(1), is(equalTo("B")));
        assertThat(abcImmutableList.get(2), is(equalTo("C")));
    }

    @Test
    public void shouldNotAllowSetElement() throws Exception {
        try {
            abcImmutableList.set(0, "D");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("You must not set an element in this list")));
        }
    }

    @Test
    public void shouldNotAllowRemoveElementByIndex() throws Exception {
        try {
            abcImmutableList.remove(0);
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("You must not remove an element from this list")));
        }
    }

    @Test
    public void shouldGetIndexOfAnElement() throws Exception {
        assertThat(abcImmutableList.indexOf("A"), is(equalTo(0)));
        assertThat(abcImmutableList.indexOf("B"), is(equalTo(1)));
        assertThat(abcImmutableList.indexOf("C"), is(equalTo(2)));
    }

    @Test
    public void shouldGetLastIndexOfAnElement() throws Exception {
        assertThat(abcImmutableList.lastIndexOf("A"), is(equalTo(0)));
        assertThat(abcImmutableList.lastIndexOf("B"), is(equalTo(1)));
        assertThat(abcImmutableList.lastIndexOf("C"), is(equalTo(2)));
    }

    @Test
    public void shouldProvideIterator() throws Exception {
        Iterator<String> iterator = abcImmutableList.iterator();

        assertThat(iterator.next(), is(equalTo("A")));
        assertThat(iterator.next(), is(equalTo("B")));
        assertThat(iterator.next(), is(equalTo("C")));

        assertThat(iterator.hasNext(), is(equalTo(false)));
    }

    @Test
    public void shouldProvideListIterator() throws Exception {
        Iterator<String> iterator = abcImmutableList.listIterator();

        assertThat(iterator.next(), is(equalTo("A")));
        assertThat(iterator.next(), is(equalTo("B")));
        assertThat(iterator.next(), is(equalTo("C")));

        assertThat(iterator.hasNext(), is(equalTo(false)));
    }

    @Test
    public void shouldProvideListIteratorWithSpecifiedIndex() throws Exception {
        Iterator<String> iterator = abcImmutableList.listIterator(1);

        assertThat(iterator.next(), is(equalTo("B")));
        assertThat(iterator.next(), is(equalTo("C")));

        assertThat(iterator.hasNext(), is(equalTo(false)));
    }

    @Test
    public void shouldProvideSubLists() throws Exception {
        assertThat(abcImmutableList.subList(0, 3), is(equalTo(ImmutableList.of("A", "B", "C"))));
        assertThat(abcImmutableList.subList(1, 3), is(equalTo(ImmutableList.of("B", "C"))));
        assertThat(abcImmutableList.subList(2, 3), is(equalTo(ImmutableList.of("C"))));
        assertThat(abcImmutableList.subList(0, 2), is(equalTo(ImmutableList.of("A", "B"))));
        assertThat(abcImmutableList.subList(1, 2), is(equalTo(ImmutableList.of("B"))));
        assertThat(abcImmutableList.subList(0, 1), is(equalTo(ImmutableList.of("A"))));
    }

    @Test
    public void shouldEqualsSameList() throws Exception {
        assertThat(Objects.equals(ImmutableList.empty(), ImmutableList.empty()), is(equalTo(true)));
        assertThat(Objects.equals(ImmutableList.of(abcArrayList), ImmutableList.of(abcArrayList)), is(equalTo(true)));
    }

    @Test
    public void shouldEqualsListContainingSameElements() throws Exception {
        assertThat(
                Objects.equals(
                        ImmutableList.of("A", "B", "C"),
                        ImmutableList.of("A", "B", "C")
                ),
                is(equalTo(true))
        );
    }

    @Test
    public void shouldNotEqualsListContainingDifferentElements() throws Exception {
        assertThat(
                Objects.equals(
                        ImmutableList.of("A", "B", "C", "D"),
                        ImmutableList.of("A", "B", "C")
                ),
                is(equalTo(false))
        );
    }

    @Test
    public void shouldNotEqualsListContainingSameElementsInDifferentOrder() throws Exception {
        assertThat(
                Objects.equals(
                        ImmutableList.of("B", "C", "A"),
                        ImmutableList.of("A", "B", "C")
                ),
                is(equalTo(false))
        );
    }

    @Test
    public void shouldEqualArrayListWithSameElements() throws Exception {
        assertThat(
                Objects.equals(
                        ImmutableList.of("A", "B", "C"),
                        abcArrayList)
                , is(equalTo(true))
        );
    }

    @Test
    public void shouldNotEqualNull() throws Exception {
        assertThat(
                abcImmutableList.equals(null),
                is(equalTo(false))
        );
    }

    @Test
    public void shouldProduceSameHashCodeAsArrayList() throws Exception {
        assertThat(abcImmutableList.hashCode(), is(equalTo(abcArrayList.hashCode())));
    }

    @Test
    public void shouldCreateListFromArray() throws Exception {
        List<String> immutableList = ImmutableList.of(new String[]{"A", "B", "C"});

        assertThat(immutableList, is(equalTo(abcImmutableList)));
        assertThat(immutableList, instanceOf(ImmutableList.class));
    }

    @Test
    public void shouldCreateListFromCollection() throws Exception {
        List<String> immutableList = ImmutableList.of(abcArrayList);

        assertThat(immutableList, is(equalTo(abcImmutableList)));
        assertThat(immutableList, instanceOf(ImmutableList.class));
    }

    @Test
    public void shouldCreateListFromIterable() throws Exception {
        Iterable<String> iterable = () -> abcArrayList.iterator();
        List<String> immutableList = ImmutableList.of(iterable);

        assertThat(immutableList, is(equalTo(abcImmutableList)));
        assertThat(immutableList, instanceOf(ImmutableList.class));
    }

    @Test
    public void shouldNotAllowNullElementAsArgumentForFromElementFactory() throws Exception {
        String nullElement = null;

        try {
            ImmutableList.of(nullElement);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is(equalTo("Parameter 'item' must not be null")));
        }
    }

    @Test
    public void shouldNotAllowNullElementAsArgumentForFromArrayFactory() throws Exception {
        String[] nullArray = null;

        try {
            ImmutableList.of(nullArray);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is(equalTo("Parameter 'items' must not be null")));
        }
    }

    @Test
    public void shouldNotAllowNullElementAsArgumentForFromCollectionFactory() throws Exception {
        Collection<String> nullCollection = null;

        try {
            ImmutableList.of(nullCollection);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is(equalTo("Parameter 'items' must not be null")));
        }
    }

    @Test
    public void shouldNotAllowNullElementAsArgumentForFromIterableFactory() throws Exception {
        Iterable<String> nullIterable = null;

        try {
            ImmutableList.of(nullIterable);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is(equalTo("Parameter 'items' must not be null")));
        }
    }

    @Test
    public void shouldCollectStreams() throws Exception {
        List<String> collectedImmutableList = Stream.of("A", "B", "C")
                .collect(ImmutableList.collect());

        assertThat(collectedImmutableList, is(equalTo(abcImmutableList)));
        assertThat(collectedImmutableList, instanceOf(ImmutableList.class));
    }

    @Test
    public void shouldNotAllowManipulateListViaOriginalList() throws Exception {
        List<String> immutableList = ImmutableList.of(abcArrayList);

        abcArrayList.add("D");

        assertThat(immutableList, hasSize(3));
    }
}
