package org.example.model.vehicle;

import org.example.exception.PassengerNotFoundException;
import org.example.exception.VehicleFullException;
import org.example.model.human.Human;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle<T extends Human> {
    private int maxSeats;
    private List<T> passengers;

    public Vehicle(int maxSeats) {
        this.maxSeats = maxSeats;
        this.passengers = new ArrayList<>();
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public int getOccupiedSeats() {
        return passengers.size();
    }

    public void boardPassenger(T passenger) throws VehicleFullException {
        if (passengers.size() >= maxSeats) {
            throw new VehicleFullException("Транспорт (" + this.getClass().getSimpleName() + ") переповнений! Немає місць для " + passenger);
        }
        passengers.add(passenger);
        System.out.println(passenger + " сів у " + this.getClass().getSimpleName());
    }

    public void disembarkPassenger(T passenger) throws PassengerNotFoundException {
        if (!passengers.contains(passenger)) {
            throw new PassengerNotFoundException("Пасажира " + passenger + " немає в цьому транспорті!");
        }
        passengers.remove(passenger);
        System.out.println(passenger + " вийшов з " + this.getClass().getSimpleName());
    }
}