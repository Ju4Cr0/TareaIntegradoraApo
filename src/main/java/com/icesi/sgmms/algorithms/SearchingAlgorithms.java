package com.icesi.sgmms.algorithms;

import com.icesi.sgmms.structures.LinkedList;

public class SearchingAlgorithms {

    /**
     * Performs binary search on a sorted linked list.
     *
     * The list must be sorted in ascending order.
     *
     * Time complexity: O(n log n), because
     * indexed access in this linked list is O(n).
     * Auxiliary space complexity: O(1).
     *
     * @param list sorted linked list
     * @param target element to find
     * @return index of the element, or -1 if absent
     */
    public static <T extends Comparable<T>> int binarySearch(
            LinkedList<T> list,
            T target
    ) {
        if (list == null || target == null) {
            throw new IllegalArgumentException(
                    "List and target cannot be null"
            );
        }

        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;

            T middleElement = list.get(middle);
            int comparison = middleElement.compareTo(target);

            if (comparison == 0) {
                return middle;
            }

            if (comparison < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }
}