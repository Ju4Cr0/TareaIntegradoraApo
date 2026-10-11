package com.icesi.sgmms.algorithms;

import com.icesi.sgmms.structures.LinkedList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchingAlgorithmsTest {

    @Test
    void searchFirstElement() {
        LinkedList<Integer> list = createSortedList();

        int result = SearchingAlgorithms.binarySearch(list, 1);

        assertEquals(0, result);
    }

    @Test
    void searchMiddleElement() {
        LinkedList<Integer> list = createSortedList();

        int result = SearchingAlgorithms.binarySearch(list, 5);

        assertEquals(2, result);
    }

    @Test
    void searchLastElement() {
        LinkedList<Integer> list = createSortedList();

        int result = SearchingAlgorithms.binarySearch(list, 9);

        assertEquals(4, result);
    }

    @Test
    void searchNonExistingElement() {
        LinkedList<Integer> list = createSortedList();

        int result = SearchingAlgorithms.binarySearch(list, 6);

        assertEquals(-1, result);
    }

    @Test
    void searchEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();

        int result = SearchingAlgorithms.binarySearch(list, 5);

        assertEquals(-1, result);
    }

    @Test
    void searchNullList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> SearchingAlgorithms.binarySearch(null, 5)
        );
    }

    @Test
    void searchNullTarget() {
        LinkedList<Integer> list = createSortedList();

        assertThrows(
                IllegalArgumentException.class,
                () -> SearchingAlgorithms.binarySearch(list, null)
        );
    }

    private LinkedList<Integer> createSortedList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(9);
        return list;
    }
}