package org.example;


import org.example.controller.FileManager;
import org.example.controller.ShapeController;
import org.example.model.Circle;
import org.example.model.Rectangle;
import org.example.model.Shape;
import org.example.model.Triangle;
import org.example.view.ShapeView;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            FileManager fileManager = new FileManager(scanner);

            System.out.print("Choose source for shapes - type 'builtin' to use the built-in array or 'file' to read from file: ");
            String choice = scanner.nextLine().trim();

            Shape[] shapes;
            if (choice.equalsIgnoreCase("file")) {
                String loadPath = fileManager.askFilePath();
                if (loadPath != null && !loadPath.isEmpty()) {
                    var loadedOpt = fileManager.loadShapesFromFile(loadPath);
                    loadedOpt.ifPresent(_ -> System.out.println("Using shapes loaded from file."));
                    shapes = loadedOpt.orElseGet(Main::getDefaultShapes);
                } else {
                    System.out.println("No file path provided. Using built-in shapes.");
                    shapes = getDefaultShapes();
                }
            } else {
                System.out.println("Using built-in shapes.");
                shapes = getDefaultShapes();
            }

            ShapeView view = new ShapeView();
            ShapeController controller = new ShapeController(shapes, view);

            controller.processShapes();

            // Minimal file interaction: ask user to save current shapes to a file
            String path = fileManager.askFilePath();
            if (path != null && !path.isEmpty()) {
                fileManager.saveShapesToFile(shapes, path);
                System.out.println("Shapes saved to: " + path);
            } else {
                System.out.println("No file path provided. Skipping save operation.");
            }

        } catch (Exception ex) {
            System.err.println("An unexpected error occurred: " + ex.getMessage());
        }
    }

    private static Shape[] getDefaultShapes() {
        return new Shape[]{
            new Rectangle("Red", 5.0, 10.0),
            new Circle("Blue", 7.0),
            new Triangle("Green", 6.0, 8.0),
            new Rectangle("Yellow", 2.0, 3.0),
            new Circle("Red", 10.0),
            new Triangle("Blue", 5.0, 5.0),
            new Rectangle("Green", 7.0, 7.0),
            new Circle("Yellow", 3.5),
            new Triangle("Red", 12.0, 4.0),
            new Rectangle("Blue", 8.0, 1.0)
        };
    }
}