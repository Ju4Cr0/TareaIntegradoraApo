package com.icesi.sgmms.structures;

public class LinkedList<T> {

    private Node<T> head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(T element) {

        if (element == null) {
            throw new IllegalArgumentException(
                    "Element cannot be null"
            );
        }

        Node<T> newNode = new Node<>(element);

        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }

        size++;
    }

    public int size() {
        return size;
    }

    public T get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index
            );
        }

        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    public T remove(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index
            );
        }

        T removedData;

        if (index == 0) {
            removedData = head.data;
            head = head.next;
        } else {
            Node<T> previous = head;

            for (int i = 0; i < index - 1; i++) {
                previous = previous.next;
            }

            Node<T> removedNode = previous.next;
            removedData = removedNode.data;

            previous.next = removedNode.next;
        }

        size--;

        return removedData;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Replaces the element at the specified index.
     *
     * Time complexity: O(n).
     * Auxiliary space complexity: O(1).
     *
     * @param index position to update
     * @param element new element
     * @throws IndexOutOfBoundsException if index is invalid
     * @throws IllegalArgumentException if element is null
     */
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }

        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        current.data = element;
    }



}