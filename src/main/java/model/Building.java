package model;

import java.util.List;
import java.util.Random;

public class Building {
    private final int floors = random.nextInt(5,20);
    private final List<Integer>[] passengersInFloor;
    private static Random random = new Random();



    public Building(){
        Elevator elevator = new Elevator(floors);
        passengersInFloor = new List[floors];

    }


    public List<Integer>[] getPassengersInFloor() {
        return passengersInFloor;
    }

    public int getFloors() {
        return floors;
    }

}
