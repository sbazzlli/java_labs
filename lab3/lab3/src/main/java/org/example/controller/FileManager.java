package org.example.controller;

import org.example.model.Shape;

import java.io.*;
import java.util.Optional;
import java.util.Scanner;

/**
 * FileManager handles saving and loading Shape[] using default Java serialization.
 * It centralizes file I/O and validates user input for file path.
 */
public class FileManager {
    private final Scanner scanner;

    public FileManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public String askFilePath() {
        System.out.print("Enter file path to save/load shapes (e.g. C:\\temp\\shapes.dat): ");
        return scanner.nextLine().trim();
    }

    public void saveShapesToFile(Shape[] shapes, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(shapes);
            System.out.println("Shapes saved successfully to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving shapes: " + e.getMessage());
        }
    }

    public Optional<Shape[]> loadShapesFromFile(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            System.err.println("File does not exist: " + filePath);
            return Optional.empty();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (obj instanceof Shape[] shapes) {
                System.out.println("Shapes loaded successfully from: " + filePath);
                return Optional.of(shapes);
            } else {
                System.err.println("File does not contain Shape[] data.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading shapes: " + e.getMessage());
        }
        return Optional.empty();
    }
}

