package org.example.util;

import org.example.model.MenuChoice;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

public class InputValidator {

    private static final String EXIT_COMMAND = "exit";
    public static final Set<String> ALLOWED_CONFIGRMATION_INPUTS = Set.of("y", "yes", "n", "no");
    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public Path readFilePath(String prompt) {
        return retryWithExitAndValidation(prompt, input -> {
            if (input.isBlank()) {
                throw new IllegalArgumentException("Path cannot be empty");
            }
            return Paths.get(input);
        });
    }

    public <T> T retryWithExitAndValidation(String prompt, Function<String, T> validator) {
        while (true) {
            System.out.print(prompt + " (or type 'exit' to cancel): ");
            var input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                throw new ExitException();
            }

            try {
                return validator.apply(input);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again or type 'exit' to cancel.");
            }
        }
    }

    public String readUrl(String prompt) {
        return retryWithExitAndValidation(prompt, input -> {
            if (input.isBlank()) {
                throw new IllegalArgumentException("URL cannot be empty");
            }
            if (!input.startsWith("http://") && !input.startsWith("https://")) {
                throw new IllegalArgumentException("URL must start with http:// or https://");
            }
            return input;
        });
    }

    public Character readKey(String prompt) {
        return retryWithExitAndValidation(prompt, input -> {
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Key cannot be empty");
            }
            if (input.length() > 1) {
                System.out.println("Warning: Using only the first character");
            }
            return input.charAt(0);
        });
    }

    public int readMenuChoice(String prompt, int min, int max) {
        return retryWithExitAndValidation(prompt, input -> {
            var choice = Integer.parseInt(input);
            if (choice < min || choice > max) {
                throw new IllegalArgumentException("Please enter a number between " + min + " and " + max);
            }
            return choice;
        });
    }

    public MenuChoice readMenuChoice(String prompt) {
        return retryWithExitAndValidation(prompt, input -> {
            var code = Integer.parseInt(input);
            return MenuChoice.fromCode(code);
        });
    }

    public boolean readConfirmation(String prompt) {
        System.out.print(prompt + " (y/n): ");
        return retryWithExitAndValidation(prompt, input -> {
            if (!ALLOWED_CONFIGRMATION_INPUTS.contains(input)) {
                throw new IllegalArgumentException("Please enter one of the following: " + ALLOWED_CONFIGRMATION_INPUTS);
            }
            return input.equals("y") || input.equals("yes");
        });
    }
}

