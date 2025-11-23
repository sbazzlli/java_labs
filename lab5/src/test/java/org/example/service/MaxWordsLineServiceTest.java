package org.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MaxWordsLineServiceTest {

    @Test
    void testFindLineWithMaxWords(@TempDir Path tempDir) throws IOException {
        var fileService = new FileService();
        var service = new MaxWordsLineService(fileService);
        var testFile = tempDir.resolve("test.txt");

        var content = """
                Hello world
                This is a test file with multiple lines
                Short
                This line has many words in it making it the longest one here
                End
                """;

        Files.writeString(testFile, content);

        var result = service.findLineWithMaxWords(testFile);

        assertEquals("This line has many words in it making it the longest one here", result);
    }

    @Test
    void testEmptyFile(@TempDir Path tempDir) throws IOException {
        var fileService = new FileService();
        var service = new MaxWordsLineService(fileService);
        var testFile = tempDir.resolve("empty.txt");
        Files.writeString(testFile, "");

        var result = service.findLineWithMaxWords(testFile);

        assertEquals("", result);
    }
}

