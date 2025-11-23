package org.example;

import org.example.service.CipherService;
import org.example.service.FileService;
import org.example.service.HtmlTagCounterService;
import org.example.service.MaxWordsLineService;
import org.example.util.ExitException;
import org.example.util.InputValidator;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Scanner;

public class Main {

    private static final FileService fileService = new FileService();
    private static final MaxWordsLineService maxWordsService = new MaxWordsLineService(fileService);
    private static final CipherService cipherService = new CipherService();
    private static final HtmlTagCounterService tagCounterService = new HtmlTagCounterService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputValidator validator = new InputValidator(scanner);

    static void main(String[] args) {
        setUtf8Encoding();
        System.out.println("=== I/O Streams Laboratory Work ===");
        System.out.println();

        var debugMode = args.length > 0 && args[0].equals("--debug");
        var running = true;

        while (running) {
            displayMenu();

            try {
                var choice = validator.readMenuChoice("Enter your choice: ");
                System.out.println();

                switch (choice) {
                    case FIND_MAX_WORDS -> executeTask1();
                    case ENCRYPT_FILE -> executeTask3Encrypt();
                    case DECRYPT_FILE -> executeTask3Decrypt();
                    case COUNT_HTML_TAGS -> executeTask4();
                    case EXIT -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                }
            } catch (ExitException _) {
                System.out.println("Operation cancelled. Returning to main menu...");
            } catch (IOException e) {
                handleError("I/O Error", e, debugMode);
            } catch (IllegalArgumentException e) {
                handleError("Invalid input", e, debugMode);
            } catch (Exception e) {
                handleError("Unexpected error", e, debugMode);
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void setUtf8Encoding() {
        var utf8Stream = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.setOut(utf8Stream);
    }

    private static void displayMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Find line with maximum words (Task 1)");
        System.out.println("2. Encrypt file (Task 3)");
        System.out.println("3. Decrypt file (Task 3)");
        System.out.println("4. Count HTML tag frequencies (Task 4)");
        System.out.println("0. Exit");
        System.out.println();
    }

    private static void executeTask1() throws IOException {
        System.out.println("=== Task 1: Find Line with Maximum Words ===");

        var inputPath = validator.readFilePath("Enter input file path: ");

        if (!fileService.fileExists(inputPath)) {
            throw new IOException("File does not exist: " + inputPath);
        }

        var lineWithMaxWords = maxWordsService.findLineWithMaxWords(inputPath);

        System.out.println("\nResult:");
        System.out.println("Line with maximum words: " + lineWithMaxWords);

        if (validator.readConfirmation("Save result to file?")) {
            var outputPath = validator.readFilePath("Enter output file path: ");

            fileService.ensureDirectoryExists(outputPath);
            fileService.writeText(outputPath, lineWithMaxWords);
            System.out.println(MessageFormat.format("Result saved to: {0}", outputPath));
        }
    }

    private static void executeTask3Encrypt() throws IOException {
        System.out.println("=== Task 3: Encrypt File ===");

        var inputPath = validator.readFilePath("Enter input file path: ");

        if (!fileService.fileExists(inputPath)) {
            throw new IOException("File does not exist: " + inputPath);
        }

        var outputPath = validator.readFilePath("Enter output file path (encrypted): ");
        var key = validator.readKey("Enter encryption key (single character): ");

        fileService.ensureDirectoryExists(outputPath);

        try (var input = Files.newInputStream(inputPath);
             var output = Files.newOutputStream(outputPath)) {
            cipherService.encrypt(input, output, key);
        }

        System.out.println("File encrypted successfully!");
        System.out.println(MessageFormat.format("Encrypted file saved to: {0}", outputPath));
        System.out.println(MessageFormat.format("Encryption key: ''{0}'' (code: {1})", key, (int) key));
    }

    private static void executeTask3Decrypt() throws IOException {
        System.out.println("=== Task 3: Decrypt File ===");

        var inputPath = validator.readFilePath("Enter encrypted file path: ");

        if (!fileService.fileExists(inputPath)) {
            throw new IOException("File does not exist: " + inputPath);
        }

        var outputPath = validator.readFilePath("Enter output file path (decrypted): ");
        var key = validator.readKey("Enter decryption key (single character): ");

        fileService.ensureDirectoryExists(outputPath);

        try (var input = Files.newInputStream(inputPath);
             var output = Files.newOutputStream(outputPath)) {
            cipherService.decrypt(input, output, key);
        }

        System.out.println("File decrypted successfully!");
        System.out.println(MessageFormat.format("Decrypted file saved to: {0}", outputPath));
    }

    private static void executeTask4() throws IOException {
        System.out.println("=== Task 4: Count HTML Tag Frequencies ===");

        var url = validator.readUrl("Enter URL (e.g., https://example.com): ");

        System.out.println(MessageFormat.format("Fetching HTML from: {0}", url));
        System.out.println("Please wait...");

        var frequencies = tagCounterService.countTagFrequencies(url);

        if (frequencies.isEmpty()) {
            System.out.println("No tags found in the HTML document");
            return;
        }

        System.out.println("\n=== Results ===");
        System.out.println(MessageFormat.format("Total unique tags: {0}", frequencies.size()));
        System.out.println();

        System.out.println("--- Tags sorted lexicographically (ascending) ---");
        var sortedByName = tagCounterService.getTagsSortedByName(frequencies);
        System.out.println(tagCounterService.formatTagFrequencies(sortedByName));
        System.out.println();

        System.out.println("--- Tags sorted by frequency (ascending) ---");
        var sortedByFrequency = tagCounterService.getTagsSortedByFrequency(frequencies);
        System.out.println(tagCounterService.formatTagFrequencies(sortedByFrequency));

        if (validator.readConfirmation("\nSave results to file?")) {
            var outputPath = validator.readFilePath("Enter output file path: ");
            fileService.ensureDirectoryExists(outputPath);

            var result = MessageFormat.format("""
                    URL: {0}
                    Total unique tags: {1}
                    
                    Tags sorted lexicographically:
                    {2}
                    
                    Tags sorted by frequency:
                    {3}""",
                    url,
                    frequencies.size(),
                    tagCounterService.formatTagFrequencies(sortedByName),
                    tagCounterService.formatTagFrequencies(sortedByFrequency)
            );

            fileService.writeText(outputPath, result);
            System.out.println(MessageFormat.format("Results saved to: {0}", outputPath));

            if (validator.readConfirmation("Save serialized data?")) {
                var serializedPath = validator.readFilePath("Enter serialized file path: ");

                fileService.ensureDirectoryExists(serializedPath);
                fileService.writeObject(serializedPath, (Serializable) frequencies);
                System.out.println(MessageFormat.format("Serialized data saved to: {0}", serializedPath));
            }
        }
    }

    private static void handleError(String context, Exception e, boolean debugMode) {
        System.err.println(context + ": " + e.getMessage());
        if (debugMode) {
            e.printStackTrace();
        }
    }
}

