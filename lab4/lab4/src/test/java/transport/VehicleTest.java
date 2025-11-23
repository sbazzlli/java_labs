package transport;

import org.example.exception.PassengerNotFoundException;
import org.example.exception.VehicleFullException;
import org.example.model.human.Human;
import org.example.model.human.Policeman;
import org.example.model.vehicle.PoliceCar;
import org.example.model.vehicle.Taxi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class VehicleTest {

    @Test
    void testBoardPassengerSuccess() {
        Taxi taxi = new Taxi(2);
        Human human = new Human("Test Human");

        try {
            taxi.boardPassenger(human);
        } catch (VehicleFullException e) {
            fail("Помилки не повинно бути, місця є");
        }

        assertEquals(1, taxi.getOccupiedSeats(), "Кількість зайнятих місць має бути 1");
    }

    @Test
    void testVehicleFullException() {
        Taxi taxi = new Taxi(1);

        try {
            taxi.boardPassenger(new Human("Pass 1"));
        } catch (VehicleFullException e) {
            fail("Тут помилки ще не має бути");
        }


        assertThrows(VehicleFullException.class, () -> {
            taxi.boardPassenger(new Human("Pass 2"));
        }, "Має виникнути помилка VehicleFullException");
    }

    @Test
    void testDisembarkPassenger() throws VehicleFullException, PassengerNotFoundException {
        Taxi taxi = new Taxi(2);
        Human h = new Human("John");

        taxi.boardPassenger(h);
        assertEquals(1, taxi.getOccupiedSeats());

        taxi.disembarkPassenger(h);
        assertEquals(0, taxi.getOccupiedSeats(), "Машина має бути порожньою після висадки");
    }

    @Test
    void testDisembarkMissingPassenger() {
        Taxi taxi = new Taxi(2);
        Human h = new Human("Ghost");

        assertThrows(PassengerNotFoundException.class, () -> {
            taxi.disembarkPassenger(h);
        });
    }

    @Test
    void testPoliceCarRestriction() {
        PoliceCar copCar = new PoliceCar(2);
        Policeman cop = new Policeman("Robo");

        assertDoesNotThrow(() -> {
            copCar.boardPassenger(cop);
        });
    }
}