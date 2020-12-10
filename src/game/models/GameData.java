package game.models;

import java.util.List;

public class GameData {

    public static List<Resource> resourceList;
    public static List<BuildingStats> buildingStatsList;
    // More to be expected...

    public static void printGameData() {
        System.out.println("Resources: (" + resourceList.size() + ")");
        for (Resource resource : resourceList) {
            System.out.println("\t" + resource.name);
        }

        System.out.println("Buildings: (" + buildingStatsList.size() + ")");
        for (BuildingStats buildingStats : buildingStatsList) {
            System.out.println("\t" + buildingStats.name);
        }
    }
}
