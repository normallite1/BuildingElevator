package Service;

import model.Building;
import model.Elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BuildingService {

    private final Building building;
    private final Elevator elevator;
    private final Random random = new Random();

    public BuildingService(){
        this.building = new Building();
        this.elevator = new Elevator(building.getFloors());
        fillRandomPassengers();
    }
    private static final int STEPS_TO_VIEW = 10;

    public void startCycle(Elevator elevator){
        for(int i=1; i<=STEPS_TO_VIEW; i++){
            int removedPassengers = this.removePassengersFromLift();
            if(elevator.isEmpty()) //if elevator is empty, recalculate direction
                elevator.setDirection(this.getElevatorDirectionByMajorPartOfPeople());

            int addedPassengers = this.addPassengersToElevator();

            if(removedPassengers == 0 && addedPassengers == 0) i--;
            else {
                createRandomPassengers(removedPassengers); // recreate passengers who leave the elevator (this passengers can`t enter to the lift, because they are busy
                showInformation(i, removedPassengers, addedPassengers);
            }
            elevator.move();
        }
    }
    public void showInformation(int step, int removedPassengers, int addedPassengers){
        System.out.println("----------- Step " + step + " -----------");
        System.out.print(this.toString());
        System.out.println("Leave: "+ removedPassengers + " Entry: " + addedPassengers + "\n");
    }


    private int addPassengersToElevator(){
        elevator.correctDirrection();
        List<Integer> indexesToDelete = new ArrayList<>();
        for(int i=0;i<building.getPassengersInFloor()[elevator.getCurrentFloor()-1].size() && !elevator.isFull();i++){
            if(elevator.isDirection()){ //if lift goes up
                if(building.getPassengersInFloor()[elevator.getCurrentFloor()-1].get(i) > elevator.getCurrentFloor()){//DRY :(
                    indexesToDelete.add(i);
                    elevator.addPassenger(
                            building.getPassengersInFloor()[elevator.getCurrentFloor()-1].get(i));
                }
            } else{
                if(building.getPassengersInFloor()[elevator.getCurrentFloor()-1].get(i) < elevator.getCurrentFloor()){
                    indexesToDelete.add(i);
                    elevator.addPassenger(
                            building.getPassengersInFloor()[elevator.getCurrentFloor()-1].get(i));
                }
            }
        }
        //delete passengers from queue
        for(int i=indexesToDelete.size()-1; i>=0; i--){
            building.getPassengersInFloor()[elevator.getCurrentFloor()-1].remove(i);
        }

        return indexesToDelete.size();
    }


    private int removePassengersFromLift(){
        return elevator.removePassengers();
    }


    private void fillRandomPassengers(){
        for(int i=0;i<building.getFloors();i++){
            building.getPassengersInFloor()[i]= fillFloor(i+1);
        }
    }

    private List<Integer> fillFloor(int currentFloor){
        ArrayList<Integer> floor = new ArrayList<>();
        int passInTheFloor = random.nextInt(11); //0...10
        for(int j=1; j<passInTheFloor; j++) {
            floor.add(createRandomPassenger(currentFloor));
        }
        return floor;
    }

    private int createRandomPassenger(int currentFloor){
        int passengerTargetFloor = currentFloor;
        while(passengerTargetFloor == currentFloor)
            passengerTargetFloor = random.nextInt(building.getFloors())+1;

        return passengerTargetFloor;
    }

    private void createRandomPassengers(int count){
        for(int j=0; j<count; j++)
           building.getPassengersInFloor()[elevator.getCurrentFloor()-1].add(
                    createRandomPassenger(elevator.getCurrentFloor()));
    }

    private boolean getElevatorDirectionByMajorPartOfPeople(){
        if(elevator.getCurrentFloor()==1) return true;
        else if(elevator.getCurrentFloor()==building.getFloors()) return false;
        else {
            int peoplesWhowantUp=0;
            for(int i=0; i < building.getPassengersInFloor()[elevator.getCurrentFloor()-1].size(); i++)
                if(building.getPassengersInFloor()[elevator.getCurrentFloor()-1].get(i) > elevator.getCurrentFloor())
                    peoplesWhowantUp++;

            return building.getPassengersInFloor()[elevator.getCurrentFloor() - 1].size() - peoplesWhowantUp < peoplesWhowantUp;
            //if peoples who want to go up less then another peoples set direction false(down)
            // true otherwise
        }
    }
    public String toString(){
        StringBuilder result= new StringBuilder();

        for(int i=building.getFloors()-1; i>=0; i--){
            if(elevator.getCurrentFloor() != i+1)
                result.append("" + (i+1) + " floor: " + building.getPassengersInFloor()[i].toString()+ "\n");
            else
                result.append("" + (i+1) + " floor: " + building.getPassengersInFloor()[i].toString()+ " Lift:{" +elevator+"}\n" );
        }
        return result.toString();
    }


    public Building getBuilding() {
        return building;
    }

    public Elevator getElevator() {
        return elevator;
    }
}
