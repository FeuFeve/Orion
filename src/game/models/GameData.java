package game.models;

import java.util.List;

public class GameData {

    public static List<Resource> resourceList;
    public static List<ConfigBuildingParams> configBuildingParamsList;
    public static List<Job> jobList;
    // More to be expected...

    public static void printGameData() {
        System.out.println("Resources: (" + resourceList.size() + ")");
        for (Resource resource : resourceList) {
            System.out.println("\t" + resource.name);
        }

        System.out.println("Buildings: (" + configBuildingParamsList.size() + ")");
        for (ConfigBuildingParams configBuildingParams : configBuildingParamsList) {
            System.out.println("\t" + configBuildingParams.name);
        }

        System.out.println("Jobs: (" + jobList.size() + ")");
        for (Job job : jobList) {
            System.out.println("\t" + job.name);
        }
    }
}
