package com.icesi.sgmms.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    @Test
    void addElementToEmptyList() {

        // ARRANGE
        LinkedList<String> list = new LinkedList<>();

        // ACT
        list.add("Ambulance");

        // ASSERT
        assertEquals(1, list.size());
        assertEquals("Ambulance", list.get(0));
    }
    @Test
    void addElementToNonEmptyList() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");

        // ACT
        list.add("Patrol");

        // ASSERT
        assertEquals(2, list.size());
        assertEquals("Patrol", list.get(1));
    }

    @Test
    void addRepeatedElements() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");

        // ACT
        list.add("Ambulance");

        // ASSERT
        assertEquals(2, list.size());
        assertEquals("Ambulance", list.get(0));
        assertEquals("Ambulance", list.get(1));
    }

    @Test
    void addNullElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();

        // ACT & ASSERT
        assertThrows(
                IllegalArgumentException.class,
                () -> list.add(null)
        );

        assertEquals(0, list.size());
    }

    @Test
    void addElementsPreservesOrder() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();

        // ACT
        list.add("Ambulance");
        list.add("Patrol");
        list.add("Firetruck");

        // ASSERT
        assertEquals(3, list.size());
        assertEquals("Ambulance", list.get(0));
        assertEquals("Patrol", list.get(1));
        assertEquals("Firetruck", list.get(2));
    }
    @Test
    void getFirstElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.add("Patrol");
        list.add("Firetruck");

        // ACT
        String result = list.get(0);

        // ASSERT
        assertEquals("Ambulance", result);
    }

    @Test
    void getMiddleElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.add("Patrol");
        list.add("Firetruck");

        // ACT
        String result = list.get(1);

        // ASSERT
        assertEquals("Patrol", result);
    }

    @Test
    void getLastElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.add("Patrol");
        list.add("Firetruck");

        // ACT
        String result = list.get(2);

        // ASSERT
        assertEquals("Firetruck", result);
    }

    @Test
    void getNegativeIndex() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");

        // ACT & ASSERT
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(-1)
        );
    }

    @Test
    void getIndexOutOfRange() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.add("Patrol");

        // ACT & ASSERT
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(2)
        );
    }
    @Test
    void removeFirstElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // ACT
        String removed = list.remove(0);

        // ASSERT
        assertEquals("A", removed);
        assertEquals(2, list.size());
        assertEquals("B", list.get(0));
    }

    @Test
    void removeMiddleElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // ACT
        String removed = list.remove(1);

        // ASSERT
        assertEquals("B", removed);
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
    }

    @Test
    void removeLastElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // ACT
        String removed = list.remove(2);

        // ASSERT
        assertEquals("C", removed);
        assertEquals(2, list.size());
        assertEquals("B", list.get(1));
    }

    @Test
    void removeNegativeIndex() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("A");

        // ACT & ASSERT
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(-1)
        );

        assertEquals(1, list.size());
        assertEquals("A", list.get(0));
    }

    @Test
    void removeFromEmptyList() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();

        // ACT & ASSERT
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(0)
        );

        assertEquals(0, list.size());
    }
    @Test
    void sizeOfEmptyList() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();

        // ACT
        int result = list.size();

        // ASSERT
        assertEquals(0, result);
    }

    @Test
    void sizeWithOneElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");

        // ACT
        int result = list.size();

        // ASSERT
        assertEquals(1, result);
    }

    @Test
    void sizeWithMultipleElements() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.add("Patrol");
        list.add("Firetruck");

        // ACT
        int result = list.size();

        // ASSERT
        assertEquals(3, result);
    }

    @Test
    void sizeAfterRemovingElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.add("Patrol");
        list.add("Firetruck");

        // ACT
        list.remove(1);
        int result = list.size();

        // ASSERT
        assertEquals(2, result);
    }

    @Test
    void sizeAfterAddingElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.add("Patrol");

        // ACT
        list.add("Firetruck");
        int result = list.size();

        // ASSERT
        assertEquals(3, result);
    }
    @Test
    void isEmptyOnNewList() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();

        // ACT
        boolean result = list.isEmpty();

        // ASSERT
        assertTrue(result);
    }

    @Test
    void isEmptyAfterAddingElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");

        // ACT
        boolean result = list.isEmpty();

        // ASSERT
        assertFalse(result);
    }

    @Test
    void isEmptyAfterRemovingOnlyElement() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.remove(0);

        // ACT
        boolean result = list.isEmpty();

        // ASSERT
        assertTrue(result);
    }

    @Test
    void isEmptyAfterRemovingAllElements() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.add("Patrol");
        list.add("Firetruck");

        list.remove(0);
        list.remove(0);
        list.remove(0);

        // ACT
        boolean result = list.isEmpty();

        // ASSERT
        assertTrue(result);
    }

    @Test
    void isEmptyAfterAddingAgain() {
        // ARRANGE
        LinkedList<String> list = new LinkedList<>();
        list.add("Ambulance");
        list.remove(0);

        // ACT
        list.add("Patrol");
        boolean result = list.isEmpty();

        // ASSERT
        assertFalse(result);
    }
    @Test
    void setFirstElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");

        list.set(0, "X");

        assertEquals("X", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void setMiddleElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        list.set(1, "X");

        assertEquals("X", list.get(1));
        assertEquals(3, list.size());
    }

    @Test
    void setLastElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        list.set(2, "X");

        assertEquals("X", list.get(2));
    }

    @Test
    void setInvalidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.set(3, "X")
        );
    }

    @Test
    void setNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");

        assertThrows(
                IllegalArgumentException.class,
                () -> list.set(0, null)
        );
    }
}