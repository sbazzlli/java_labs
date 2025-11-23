package org.example.model.vehicle;

import org.example.model.human.Human;

public class Taxi extends Car<Human> {
    public Taxi(int maxSeats) {
        super(maxSeats);
    }
}