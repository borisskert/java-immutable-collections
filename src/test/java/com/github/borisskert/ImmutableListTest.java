package com.github.borisskert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.fail;

class ImmutableListTest {

    private List<String> emptyList;
    private List<String> listWithThreeElements;
    private List<String> listWithThreeDuplicateElements;

    @BeforeEach
    public void setup() throws Exception {
        emptyList = ImmutableList.empty();
        listWithThreeElements = ImmutableList.of("A", "B", "C");
        listWithThreeDuplicateElements = ImmutableList.of("A", "B", "C", "A", "B", "C");
    }

    @Test
    public void shouldHaveSpecificSize() throws Exception {
        assertThat(emptyList.size(), is(equalTo(0)));
        assertThat(listWithThreeElements.size(), is(equalTo(3)));
        assertThat(listWithThreeDuplicateElements.size(), is(equalTo(6)));
    }

    @Test
    public void shouldIndicateIfEmpty() throws Exception {
        assertThat(emptyList.isEmpty(), is(true));
        assertThat(listWithThreeElements.isEmpty(), is(false));
        assertThat(listWithThreeDuplicateElements.isEmpty(), is(false));
    }

    @Test
    public void shouldContainElements() throws Exception {
        assertThat(emptyList.contains("A"), is(false));
        assertThat(emptyList.contains("B"), is(false));
        assertThat(emptyList.contains("C"), is(false));


        assertThat(listWithThreeElements.contains("A"), is(true));
        assertThat(listWithThreeElements.contains("B"), is(true));
        assertThat(listWithThreeElements.contains("C"), is(true));

        assertThat(listWithThreeElements.contains("X"), is(false));
        assertThat(listWithThreeElements.contains("Y"), is(false));
        assertThat(listWithThreeElements.contains("Z"), is(false));


        assertThat(listWithThreeDuplicateElements.contains("A"), is(true));
        assertThat(listWithThreeDuplicateElements.contains("B"), is(true));
        assertThat(listWithThreeDuplicateElements.contains("C"), is(true));

        assertThat(listWithThreeDuplicateElements.contains("X"), is(false));
        assertThat(listWithThreeDuplicateElements.contains("Y"), is(false));
        assertThat(listWithThreeDuplicateElements.contains("Z"), is(false));
    }

    @Test
    public void shouldReturnArray() throws Exception {
        Object[] asArray = listWithThreeElements.toArray();
        assertThat(asArray, is(equalTo(new Object[]{"A", "B", "C"})));
    }

    @Test
    public void shouldReturnTypedArray() throws Exception {
        String[] asArray = listWithThreeElements.toArray(new String[0]);
        assertThat(asArray, is(equalTo(new String[]{"A", "B", "C"})));
    }

    @Test
    public void shouldNotAllowToAddElement() throws Exception {
        try {
            listWithThreeElements.add("D");
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(equalTo("You must not add an element to this list")));
        } catch (Throwable e) {
            fail("Should throw IllegalStateException");
        }
    }

    @Test
    public void shouldNotAllowToRemoveElement() throws Exception {
        try {
            listWithThreeElements.remove("A");
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(equalTo("You must not remove an element from this list")));
        } catch (Throwable e) {
            fail("Should throw IllegalStateException");
        }
    }

    @Test
    public void shouldContainAllElements() throws Exception {
        boolean containsAll = listWithThreeElements.containsAll(ImmutableList.of("A", "B"));
        assertThat(containsAll, is(true));
    }

    @Test
    public void shouldNotContainAllElements() throws Exception {
        boolean containsAll = listWithThreeElements.containsAll(ImmutableList.of("A", "D"));
        assertThat(containsAll, is(false));
    }

    @Test
    public void shouldNotAllowToAddAllElements() throws Exception {
        try {
            listWithThreeElements.addAll(ImmutableList.of("D", "E"));
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(equalTo("You must not add elements to this list")));
        } catch (Throwable e) {
            fail("Should throw IllegalStateException");
        }
    }

    @Test
    public void shouldNotAllowToInsertElements() throws Exception {
        try {
            listWithThreeElements.addAll(0, ImmutableList.of("D", "E"));
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(equalTo("You must not add elements to this list")));
        } catch (Throwable e) {
            fail("Should throw IllegalStateException");
        }
    }

    @Test
    public void shouldNotAllowToRemoveAllElements() throws Exception {
        try {
            listWithThreeElements.removeAll(ImmutableList.of("A", "B"));
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(equalTo("You must not remove elements from this list")));
        } catch (Throwable e) {
            fail("Should throw IllegalStateException");
        }
    }

    @Test
    public void shouldNotAllowToRetainElements() throws Exception {
        try {
            listWithThreeElements.retainAll(ImmutableList.of("A", "B"));
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(equalTo("You must not remove elements from this list")));
        } catch (Throwable e) {
            fail("Should throw IllegalStateException");
        }
    }
    
    @Test
    public void shouldNotAllowToClear() throws Exception {
        try {
            listWithThreeElements.clear();
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(equalTo("You must not clear this list")));
        } catch (Throwable e) {
            fail("Should throw IllegalStateException");
        }
    }
    
    @Test
    public void shouldGetOneElement() throws Exception {
        assertThat(listWithThreeElements.get(0), is(equalTo("A")));
        assertThat(listWithThreeElements.get(1), is(equalTo("B")));
        assertThat(listWithThreeElements.get(2), is(equalTo("C")));
    }

    @Test
    public void shouldNotAllowSetElement() throws Exception {
        try {
            listWithThreeElements.set(0, "D");
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(equalTo("You must not set an element in this list")));
        } catch (Throwable e) {
            fail("Should throw IllegalStateException");
        }
    }

    @Test
    public void shouldNotAllowRemoveElementByIndex() throws Exception {
        try {
            listWithThreeElements.remove(0);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(equalTo("You must not remove an element from this list")));
        } catch (Throwable e) {
            fail("Should throw IllegalStateException");
        }
    }

    @Test
    public void shouldGetIndexOfAnElement() throws Exception {
        assertThat(listWithThreeElements.indexOf("A"), is(equalTo(0)));
        assertThat(listWithThreeElements.indexOf("B"), is(equalTo(1)));
        assertThat(listWithThreeElements.indexOf("C"), is(equalTo(2)));
    }

    @Test
    public void shouldGetLastIndexOfAnElement() throws Exception {
        assertThat(listWithThreeElements.lastIndexOf("A"), is(equalTo(0)));
        assertThat(listWithThreeElements.lastIndexOf("B"), is(equalTo(1)));
        assertThat(listWithThreeElements.lastIndexOf("C"), is(equalTo(2)));


    }

    @Test
    public void shouldProvideIterator() throws Exception {
        Iterator<String> iterator = listWithThreeElements.iterator();

        assertThat(iterator.next(), is(equalTo("A")));
        assertThat(iterator.next(), is(equalTo("B")));
        assertThat(iterator.next(), is(equalTo("C")));

        assertThat(iterator.hasNext(), is(equalTo(false)));
    }

    @Test
    public void shouldProvideListIterator() throws Exception {
        Iterator<String> iterator = listWithThreeElements.listIterator();

        assertThat(iterator.next(), is(equalTo("A")));
        assertThat(iterator.next(), is(equalTo("B")));
        assertThat(iterator.next(), is(equalTo("C")));

        assertThat(iterator.hasNext(), is(equalTo(false)));
    }

    @Test
    public void shouldProvideListIteratorWithSpecifiedIndex() throws Exception {
        Iterator<String> iterator = listWithThreeElements.listIterator(1);

        assertThat(iterator.next(), is(equalTo("B")));
        assertThat(iterator.next(), is(equalTo("C")));

        assertThat(iterator.hasNext(), is(equalTo(false)));
    }

    @Test
    public void shouldProvideSubLists() throws Exception {
        assertThat(listWithThreeElements.subList(0, 3), is(equalTo(ImmutableList.of("A", "B", "C"))));
        assertThat(listWithThreeElements.subList(1, 3), is(equalTo(ImmutableList.of("B", "C"))));
        assertThat(listWithThreeElements.subList(2, 3), is(equalTo(ImmutableList.of("C"))));
        assertThat(listWithThreeElements.subList(0, 2), is(equalTo(ImmutableList.of("A", "B"))));
        assertThat(listWithThreeElements.subList(1, 2), is(equalTo(ImmutableList.of("B"))));
        assertThat(listWithThreeElements.subList(0, 1), is(equalTo(ImmutableList.of("A"))));
    }

    @Test
    public void shouldEqualsSameList() throws Exception {
        assertThat(Objects.equals(ImmutableList.empty(), ImmutableList.empty()), is(equalTo(true)));

        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        assertThat(Objects.equals(ImmutableList.of(list), ImmutableList.of(list)), is(equalTo(true)));
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
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        assertThat(
                Objects.equals(
                        ImmutableList.of("A", "B", "C"),
                        list)
                , is(equalTo(true))
        );
    }

    @Test
    public void shouldNotEqualNull() throws Exception {
        assertThat(
                listWithThreeElements.equals(null),
                is(equalTo(false))
        );
    }
}