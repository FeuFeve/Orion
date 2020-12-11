package game.models;

import java.util.List;

public class GameData {

    public static List<Resource> resourceList;
    public static List<BuildingConfig> buildingConfigList;
    public static List<Job> jobList;
    public static GameConfig gameConfig;
    // More to be expected...

    public static void printGameData() {
        System.out.println("Resources: (" + resourceList.size() + ")");
        for (Resource resource : resourceList) {
            System.out.println("\t" + resource.name);
        }

        System.out.println("Buildings: (" + buildingConfigList.size() + ")");
        for (BuildingConfig buildingConfig : buildingConfigList) {
            System.out.println("\t" + buildingConfig.name);
        }

        System.out.println("Jobs: (" + jobList.size() + ")");
        for (Job job : jobList) {
            System.out.println("\t" + job.name);
        }
    }
}
