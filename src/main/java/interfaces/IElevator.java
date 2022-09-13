package interfaces;

public interface IElevator {
    int move();
    boolean isEmpty();
    boolean isFull();
    void correctDirrection();
    boolean isDirection();
    int getCurrentFloor();
    void addPassenger(int passengerFloor);
    int removePassengers();
    void setDirection(boolean direction);
}
