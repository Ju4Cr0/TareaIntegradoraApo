package com.icesi.sgmms.algorithms;

import com.icesi.sgmms.structures.LinkedList;

public class SortingAlgorithms {

    /**
     * Sorts a linked list in ascending order
     * using the insertion sort algorithm.
     *
     * Best case: O(n^2) with indexed linked-list access.
     * Worst case: O(n^3) with indexed linked-list access.
     * Auxiliary space: O(1).
     *
     * @param list list to sort
     * @param <T> comparable element type
     */
    public static <T extends Comparable<T>> void insertionSort(
            LinkedList<T> list
    ) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }

        for (int i = 1; i < list.size(); i++) {

            T key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j).compareTo(key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }
    }
}