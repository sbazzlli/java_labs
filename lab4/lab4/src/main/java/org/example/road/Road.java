package org.example.road;

import org.example.model.human.Human;
import org.example.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Road {
    public List<Vehicle<? extends Human>> carsInRoad = new ArrayList<>();

    public void addCarToRoad(Vehicle<? extends Human> vehicle) {
        carsInRoad.add(vehicle);
        System.out.println("-> На дорогу виїхав транспорт: " + vehicle.getClass().getSimpleName());
    }

    public int getCountOfHumans() {
        int totalHumans = 0;
        for (Vehicle<? extends Human> vehicle : carsInRoad) {
            totalHumans += vehicle.getOccupiedSeats();
        }
        return totalHumans;
    }
}
