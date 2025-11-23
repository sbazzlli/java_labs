package org.example.view;

import org.example.model.Shape;

public class ShapeView {

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printShapes(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            printMessage("No shapes to display.");
            return;
        }
        for (Shape shape : shapes) {
            shape.draw();
        }
    }

    public void printTotalArea(double totalArea) {
        printMessage("Total area of all shapes: " + String.format("%.2f", totalArea));
    }

    public void printAreaByType(String shapeType, double area) {
        printMessage("Total area of all " + shapeType + "s: " + String.format("%.2f", area));
    }
}