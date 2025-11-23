package org.example.model;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileLocationTest {

    @Test
    void testCreateFileLocation() {
        var path = Paths.get("test.txt");
        var location = new FileLocation(path);

        assertEquals(path, location.path());
    }

    @Test
    void testNullPathThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new FileLocation(null));
    }

    @Test
    void testToString() {
        var path = Paths.get("test.txt");
        var location = new FileLocation(path);

        assertEquals("test.txt", location.toString());
    }
}
