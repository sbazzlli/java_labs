package org.example.model;

import java.io.Serial;
import java.io.Serializable;

public abstract class Shape implements Drawable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public abstract double calcArea();

    @Override
    public String toString() {
        return "Shape color: " + shapeColor;
    }

    @Override
    public void draw() {
        System.out.println(this.toString() + ", area: " + String.format("%.2f", calcArea()));
    }
}