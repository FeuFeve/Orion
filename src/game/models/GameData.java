package game.models;

import java.util.List;
import java.util.Random;

public class GameData {

    // Names
    public static List<String> maleFirstNames;
    public static List<String> femaleFirstNames;
    public static List<String> lastNames;

    // Configs
    public static List<Resource> resourceList;
    public static List<BuildingConfig> buildingConfigList;
    public static List<Job> jobList;

    public static GameConfig gameConfig;

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

    public static String getRandomName(String type) {
        Random random = new Random();
        switch (type) {
            case "Male": return maleFirstNames.get(random.nextInt(maleFirstNames.size()));
            case "Female": return femaleFirstNames.get(random.nextInt(femaleFirstNames.size()));
            case "Last": return lastNames.get(random.nextInt(lastNames.size()));
            default: return "ERROR";
        }
    }

    public static BuildingConfig getBuildingConfig(String name) {
        for (BuildingConfig buildingConfig : buildingConfigList) {
            if (buildingConfig.name.equals(name)) {
                return buildingConfig;
            }
        }
        return null;
    }

    public static Resource getResource(String name) {
        for (Resource resource : resourceList) {
            if (resource.name.equals(name)) {
                return new Resource(resource.name, resource.type);
            }
        }
        return null;
    }
}
