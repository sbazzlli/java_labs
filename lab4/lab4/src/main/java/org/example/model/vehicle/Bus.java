package org.example.model.vehicle;

import org.example.model.human.Human;

public class Bus extends Vehicle<Human> {
    public Bus(int maxSeats) {
        super(maxSeats);
    }
}
