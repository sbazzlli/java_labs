package org.example.controller;

import org.example.model.Shape;
import org.example.model.comparators.AreaComparator;
import org.example.model.comparators.ColorComparator;
import org.example.view.ShapeView;

import java.util.Arrays;

public class ShapeController {
    private Shape[] shapes;
    private ShapeView view;

    public ShapeController(Shape[] shapes, ShapeView view) {
        this.shapes = shapes;
        this.view = view;
    }

    public void processShapes() {
        view.printMessage("--- Initial Shapes ---");
        view.printShapes(shapes);
        view.printMessage("\n");

        view.printMessage("--- Calculating Total Area ---");
        double totalArea = calculateTotalArea();
        view.printTotalArea(totalArea);
        view.printMessage("\n");

        view.printMessage("--- Calculating Area by Type ---");
        double rectangleArea = calculateAreaByType("Rectangle");
        view.printAreaByType("Rectangle", rectangleArea);
        view.printMessage("\n");

        view.printMessage("--- Shapes Sorted by Area ---");
        Arrays.sort(shapes, new AreaComparator());
        view.printShapes(shapes);
        view.printMessage("\n");

        view.printMessage("--- Shapes Sorted by Color ---");
        Arrays.sort(shapes, new ColorComparator());
        view.printShapes(shapes);
        view.printMessage("\n");
    }

    private double calculateTotalArea() {
        double sum = 0;
        for (Shape shape : shapes) {
            sum += shape.calcArea();
        }
        return sum;
    }

    private double calculateAreaByType(String type) {
        double sum = 0;
        for (Shape shape : shapes) {
            if (shape.getClass().getSimpleName().equalsIgnoreCase(type)) {
                sum += shape.calcArea();
            }
        }
        return sum;
    }
}