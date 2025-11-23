package org.example;

import org.example.exception.VehicleFullException;
import org.example.model.human.Firefighter;
import org.example.model.human.Human;
import org.example.model.human.Policeman;
import org.example.model.vehicle.Bus;
import org.example.model.vehicle.FireTruck;
import org.example.model.vehicle.PoliceCar;
import org.example.model.vehicle.Taxi;
import org.example.road.Road;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("=== ПОЧАТОК ТЕСТУВАННЯ ===\n");

        Human civilian = new Human("Олексій (Цивільний)");
        Firefighter firefighter = new Firefighter("Петро (Пожежник)");
        Policeman policeman = new Policeman("Дмитро (Поліцейський)");

        Taxi taxi = new Taxi(2);
        PoliceCar policeCar = new PoliceCar(2);
        Bus bus = new Bus(30);
        FireTruck fireTruck = new FireTruck(4);

        System.out.println("--- Тест 1: Посадка пасажирів ---");
        try {
            taxi.boardPassenger(civilian);
            taxi.boardPassenger(firefighter);

            policeCar.boardPassenger(policeman);


            fireTruck.boardPassenger(new Firefighter("Іван (Пожежник 2)"));
            bus.boardPassenger(new Human("Бабуся"));

        } catch (VehicleFullException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n--- Тест 2: Переповнення ---");
        try {
            taxi.boardPassenger(policeman);
        } catch (VehicleFullException e) {
            System.out.println("ОЧІКУВАНА ПОМИЛКА: " + e.getMessage());
        }

        System.out.println("\n--- Тест 3: Дорога та підрахунок ---");
        Road road = new Road();
        road.addCarToRoad(taxi);
        road.addCarToRoad(policeCar);
        road.addCarToRoad(fireTruck);
        road.addCarToRoad(bus);

        int total = road.getCountOfHumans();
        System.out.println("\nВсього людей на дорозі: " + total);

        if (total == 5) {
            System.out.println("Тест підрахунку пройдено успішно.");
        } else {
            System.out.println("Помилка підрахунку.");
        }
    }
}