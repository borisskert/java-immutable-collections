package com.github.borisskert;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.fail;

class ImmutableMapTest {

    private Map<String, String> emptyMap;
    private Map<String, String> emptyHashMap;
    private Map<String, String> abcMap;
    private Map<String, String> abcHashMap;

    @BeforeEach
    public void setup() throws Exception {
        emptyMap = ImmutableMap.empty();
        emptyHashMap = new HashMap<>();

        abcMap = ImmutableMap.of(
                ImmutableMap.entry("1", "A"),
                ImmutableMap.entry("2", "B"),
                ImmutableMap.entry("3", "C")
        );

        abcHashMap = new HashMap<>();
        abcHashMap.put("1", "A");
        abcHashMap.put("2", "B");
        abcHashMap.put("3", "C");
    }

    @Test
    public void shouldHaveSpecificSize() throws Exception {
        assertThat(abcMap.size(), is(equalTo(3)));
        assertThat(emptyMap.size(), is(equalTo(0)));
    }

    @Test
    public void shouldIndicateIfEmpty() throws Exception {
        assertThat(abcMap.isEmpty(), is(equalTo(false)));
        assertThat(emptyMap.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void shouldIndicateIfContainsKey() throws Exception {
        assertThat(abcMap.containsKey("1"), is(equalTo(true)));
        assertThat(abcMap.containsKey("2"), is(equalTo(true)));
        assertThat(abcMap.containsKey("3"), is(equalTo(true)));

        assertThat(abcMap.containsKey("A"), is(equalTo(false)));
        assertThat(abcMap.containsKey("B"), is(equalTo(false)));
        assertThat(abcMap.containsKey("C"), is(equalTo(false)));


        assertThat(emptyMap.containsKey("1"), is(equalTo(false)));
        assertThat(emptyMap.containsKey("2"), is(equalTo(false)));
        assertThat(emptyMap.containsKey("3"), is(equalTo(false)));

        assertThat(emptyMap.containsKey("A"), is(equalTo(false)));
        assertThat(emptyMap.containsKey("B"), is(equalTo(false)));
        assertThat(emptyMap.containsKey("C"), is(equalTo(false)));
    }

    @Test
    public void shouldIndicateIfContainsValue() throws Exception {
        assertThat(abcMap.containsValue("A"), is(equalTo(true)));
        assertThat(abcMap.containsValue("B"), is(equalTo(true)));
        assertThat(abcMap.containsValue("C"), is(equalTo(true)));

        assertThat(abcMap.containsValue("1"), is(equalTo(false)));
        assertThat(abcMap.containsValue("2"), is(equalTo(false)));
        assertThat(abcMap.containsValue("3"), is(equalTo(false)));


        assertThat(emptyMap.containsValue("A"), is(equalTo(false)));
        assertThat(emptyMap.containsValue("B"), is(equalTo(false)));
        assertThat(emptyMap.containsValue("C"), is(equalTo(false)));

        assertThat(emptyMap.containsValue("1"), is(equalTo(false)));
        assertThat(emptyMap.containsValue("2"), is(equalTo(false)));
        assertThat(emptyMap.containsValue("3"), is(equalTo(false)));
    }

    @Test
    public void shouldProvideValueForKey() throws Exception {
        assertThat(abcMap.get("1"), is(equalTo("A")));
        assertThat(abcMap.get("2"), is(equalTo("B")));
        assertThat(abcMap.get("3"), is(equalTo("C")));

        assertThat(abcMap.get("A"), is(nullValue()));
        assertThat(abcMap.get("B"), is(nullValue()));
        assertThat(abcMap.get("C"), is(nullValue()));


        assertThat(emptyMap.get("1"), is(nullValue()));
        assertThat(emptyMap.get("2"), is(nullValue()));
        assertThat(emptyMap.get("3"), is(nullValue()));

        assertThat(emptyMap.get("A"), is(nullValue()));
        assertThat(emptyMap.get("B"), is(nullValue()));
        assertThat(emptyMap.get("C"), is(nullValue()));
    }

    @Test
    public void shouldNotAllowToPutElement() throws Exception {
        try {
            abcMap.put("A", "1");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not put an element to this map")));
        }
    }

    @Test
    public void shouldNotAllowToRemoveElement() throws Exception {
        try {
            abcMap.remove("1");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not remove an element from this map")));
        }
    }

    @Test
    public void shouldNotAllowToPutElements() throws Exception {
        HashMap<String, String> anotherMap = new HashMap<>();
        anotherMap.put("4", "D");

        try {
            abcMap.putAll(anotherMap);
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not put a elements to this map")));
        }
    }

    @Test
    public void shouldNotAllowToClear() throws Exception {
        try {
            abcMap.clear();
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not clear this map")));
        }
    }

    @Test
    public void shouldProvideKeySet() throws Exception {
        Set<String> keys = abcMap.keySet();
        assertThat(keys, is(hasSize(3)));

        // we need sorted keys for easier testing
        Iterator<String> sortedKeys = sortCollection(keys);

        assertThat(sortedKeys.next(), is(equalTo("1")));
        assertThat(sortedKeys.next(), is(equalTo("2")));
        assertThat(sortedKeys.next(), is(equalTo("3")));


        assertThat(emptyMap.keySet(), hasSize(0));
    }

    @Test
    public void shouldProvideEqualKeySet() throws Exception {
        assertThat(abcMap.keySet(), is(equalTo(abcHashMap.keySet())));
        assertThat(emptyMap.keySet(), is(equalTo(emptyHashMap.keySet())));
    }

    @Test
    public void shouldProvideValues() throws Exception {
        Collection<String> values = abcMap.values();
        assertThat(values, is(hasSize(3)));

        Iterator<String> sortedValues = sortCollection(values);

        assertThat(sortedValues.next(), is(equalTo("A")));
        assertThat(sortedValues.next(), is(equalTo("B")));
        assertThat(sortedValues.next(), is(equalTo("C")));

        assertThat(emptyMap.values(), hasSize(0));
    }

    @Test
    public void shouldProvideEntries() throws Exception {
        Set<Map.Entry<String, String>> entries = abcMap.entrySet();
        assertThat(entries, is(hasSize(3)));

        Iterator<Map.Entry<String, String>> sortedEntries = sortEntries(entries);

        Map.Entry<String, String> entry = sortedEntries.next();
        assertThat(entry.getKey(), is(equalTo("1")));
        assertThat(entry.getValue(), is(equalTo("A")));

        entry = sortedEntries.next();
        assertThat(entry.getKey(), is(equalTo("2")));
        assertThat(entry.getValue(), is(equalTo("B")));

        entry = sortedEntries.next();
        assertThat(entry.getKey(), is(equalTo("3")));
        assertThat(entry.getValue(), is(equalTo("C")));

        assertThat(emptyMap.entrySet(), hasSize(0));
    }

    @Test
    public void shouldNotAllowToChangeEntries() throws Exception {
        Set<Map.Entry<String, String>> entries = abcMap.entrySet();

        Iterator<Map.Entry<String, String>> sortedEntries = sortEntries(entries);

        Map.Entry<String, String> entry = sortedEntries.next();
        try {
            entry.setValue("D");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not change the value of this entry")));
        }

        entry = sortedEntries.next();
        try {
            entry.setValue("D");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not change the value of this entry")));
        }

        entry = sortedEntries.next();
        try {
            entry.setValue("D");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(IsEqual.equalTo("You must not change the value of this entry")));
        }
    }

    @Test
    public void shouldProvideEqualEntries() throws Exception {
        assertThat(abcMap.entrySet(), is(equalTo(abcHashMap.entrySet())));
        assertThat(emptyMap.entrySet(), is(equalTo(emptyHashMap.entrySet())));
    }

    @Test
    public void shouldEqualsSameMap() throws Exception {
        assertThat(abcMap, is(equalTo(abcMap)));
        assertThat(emptyMap, is(equalTo(emptyMap)));
    }

    @Test
    public void shouldEqualsMapWithEqualEntries() throws Exception {
        Map<String, String> anotherMap = ImmutableMap.of(
                ImmutableMap.entry("1", "A"),
                ImmutableMap.entry("2", "B"),
                ImmutableMap.entry("3", "C")
        );

        assertThat(abcMap, is(equalTo(anotherMap)));
        assertThat(anotherMap, is(equalTo(abcMap)));
    }

    @Test
    public void shouldEqualsHashMapWithEqualEntries() throws Exception {
        assertThat(abcMap, is(equalTo(abcHashMap)));
        assertThat(abcHashMap, is(equalTo(abcMap)));
    }

    @Test
    public void shouldNotEqualsMapWithEqualEntries() throws Exception {
        Map<String, String> map = ImmutableMap.of(
                ImmutableMap.entry("A", "1"),
                ImmutableMap.entry("B", "2"),
                ImmutableMap.entry("C", "3")
        );

        assertThat(map, is(not(equalTo(abcMap))));
    }

    @Test
    public void shouldNotEqualsHashMapWithDifferentEntries() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("A", "1");
        map.put("B", "2");
        map.put("C", "3");

        assertThat(map, is(not(equalTo(abcMap))));
        assertThat(abcMap, is(not(equalTo(map))));
    }

    @Test
    public void shouldProduceSameHashCode() throws Exception {
        assertThat(abcMap.hashCode(), is(equalTo(abcHashMap.hashCode())));
        assertThat(emptyMap.hashCode(), is(equalTo(emptyHashMap.hashCode())));
    }

    @Test
    public void shouldProvideStringRepresentation() throws Exception {
        assertThat(abcMap.toString(), is(equalTo("{1=A, 2=B, 3=C}")));
        assertThat(emptyMap.toString(), is(equalTo("{}")));
    }

    @Test
    public void shouldProvideCollector() throws Exception {
        Map<String, String> collectedMap = abcMap.entrySet()
                .stream()
                .collect(
                        ImmutableMap.collect(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );

        assertThat(collectedMap, is(instanceOf(ImmutableMap.class)));
        assertThat(collectedMap, is(not(sameInstance(abcMap))));
        assertThat(collectedMap, is(equalTo(abcMap)));
    }

    private <T extends Comparable<? super T>> Iterator<T> sortCollection(Collection<T> collection) {
        List<T> keysAsList = new ArrayList<>(collection);
        Collections.sort(keysAsList);

        return keysAsList.iterator();
    }

    private <K extends Comparable<? super K>, V> Iterator<Map.Entry<K, V>> sortEntries(Collection<Map.Entry<K, V>> entries) {
        List<Map.Entry<K, V>> keysAsList = new ArrayList<>(entries);
        keysAsList.sort(Map.Entry.comparingByKey());

        return keysAsList.iterator();
    }
}