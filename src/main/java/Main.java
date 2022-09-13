import Service.BuildingService;
import model.Building;

import java.util.Random;

public class Main {


    public static void main(String[] args) {
        BuildingService buildingService = new BuildingService();
        Building building = buildingService.getBuilding();
        System.out.println("START");
        System.out.println(building);
        buildingService.startCycle(buildingService.getElevator());
    }

}
