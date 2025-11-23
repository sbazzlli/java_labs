package org.example.model.vehicle;

import org.example.model.human.Policeman;

public class PoliceCar extends Car<Policeman> {
    public PoliceCar(int maxSeats) {
        super(maxSeats);
    }
}