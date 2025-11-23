package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagFrequencyTest {

    @Test
    void testCreateTagFrequency() {
        var tag = new TagFrequency("div", 10);

        assertEquals("div", tag.tagName());
        assertEquals(10, tag.frequency());
    }

    @Test
    void testCompareTo() {
        var tag1 = new TagFrequency("div", 10);
        var tag2 = new TagFrequency("span", 5);

        assertTrue(tag1.compareTo(tag2) < 0);
        assertTrue(tag2.compareTo(tag1) > 0);
        assertEquals(0, tag1.compareTo(tag1));
    }

    @Test
    void testInvalidTagName() {
        assertThrows(IllegalArgumentException.class, () -> new TagFrequency(null, 10));
        assertThrows(IllegalArgumentException.class, () -> new TagFrequency("", 10));
        assertThrows(IllegalArgumentException.class, () -> new TagFrequency("  ", 10));
    }

    @Test
    void testNegativeFrequency() {
        assertThrows(IllegalArgumentException.class, () -> new TagFrequency("div", -1));
    }

    @Test
    void testFactoryMethod() {
        var tag = TagFrequency.of("p", 15);

        assertEquals("p", tag.tagName());
        assertEquals(15, tag.frequency());
    }
}
