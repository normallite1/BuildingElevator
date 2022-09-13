import service.BuildingService;
import model.Building;

public class Main {


    public static void main(String[] args) {
        BuildingService buildingService = new BuildingService();
        Building building = buildingService.getBuilding();
        System.out.println("START");
        System.out.println(building);
        buildingService.startCycle(buildingService.getElevator());
    }

}
