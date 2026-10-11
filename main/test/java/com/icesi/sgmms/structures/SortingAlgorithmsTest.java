package com.icesi.sgmms.algorithms;

import com.icesi.sgmms.structures.LinkedList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SortingAlgorithmsTest {

    @Test
    void sortEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();

        SortingAlgorithms.insertionSort(list);

        assertEquals(0, list.size());
    }

    @Test
    void sortSingleElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(5);

        SortingAlgorithms.insertionSort(list);

        assertEquals(5, list.get(0));
        assertEquals(1, list.size());
    }

    @Test
    void sortUnorderedList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(8);
        list.add(3);
        list.add(5);
        list.add(1);

        SortingAlgorithms.insertionSort(list);

        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(8, list.get(3));
    }

    @Test
    void sortAlreadyOrderedList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        SortingAlgorithms.insertionSort(list);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void sortRepeatedElements() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(4);
        list.add(2);
        list.add(4);
        list.add(1);

        SortingAlgorithms.insertionSort(list);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(4, list.get(2));
        assertEquals(4, list.get(3));
    }

    @Test
    void sortNullList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> SortingAlgorithms.insertionSort(null)
        );
    }
}