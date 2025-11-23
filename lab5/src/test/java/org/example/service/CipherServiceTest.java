package org.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CipherServiceTest {

    @Test
    void testEncryptDecrypt(@TempDir Path tempDir) throws IOException {
        var service = new CipherService();
        var originalText = "Hello, World! 123";
        var key = 'K';

        var inputFile = tempDir.resolve("input.txt");
        var encryptedFile = tempDir.resolve("encrypted.txt");
        var decryptedFile = tempDir.resolve("decrypted.txt");

        Files.writeString(inputFile, originalText);

        try (var input = Files.newInputStream(inputFile);
             var output = Files.newOutputStream(encryptedFile)) {
            service.encrypt(input, output, key);
        }

        assertTrue(Files.size(encryptedFile) > 0, "Encrypted file should not be empty");

        try (var input = Files.newInputStream(encryptedFile);
             var output = Files.newOutputStream(decryptedFile)) {
            service.decrypt(input, output, key);
        }

        var decryptedText = Files.readString(decryptedFile);
        assertEquals(originalText, decryptedText, "Decrypted text should match original");
    }

    @Test
    void testEmptyFile(@TempDir Path tempDir) throws IOException {
        var service = new CipherService();
        var key = 'X';

        var inputFile = tempDir.resolve("empty.txt");
        var outputFile = tempDir.resolve("output.txt");

        Files.writeString(inputFile, "");

        try (var input = Files.newInputStream(inputFile);
             var output = Files.newOutputStream(outputFile)) {
            service.encrypt(input, output, key);
        }

        assertEquals("", Files.readString(outputFile));
    }
}
