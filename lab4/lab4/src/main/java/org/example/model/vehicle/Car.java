package org.example.model.vehicle;

import org.example.model.human.Human;

public abstract class Car<T extends Human> extends Vehicle<T> {
    public Car(int maxSeats) {
        super(maxSeats);
    }
}