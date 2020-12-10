package game.main;

import game.models.BuildingStats;
import game.models.GameData;
import game.models.Resource;
import game.utilities.Chronometer;
import game.utilities.Date;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private static final String CONFIG_FOLDER_PATH = "config/";

    private static final String RESOURCES_FILE_PATH = CONFIG_FOLDER_PATH + "resources.json";
    private static final String BUILDINGS_FOLDER_PATH = CONFIG_FOLDER_PATH + "Buildings/";
    // More to be expected

    private static int loadingErrors;


    public static boolean loadGameData() {
        System.out.println("(" + Date.getRealDate() + ") Loading game data...");

        Chronometer chrono = new Chronometer();
        chrono.start();

        GameData.resourceList = Resource.init(RESOURCES_FILE_PATH);
        GameData.buildingStatsList = BuildingStats.init(BUILDINGS_FOLDER_PATH);
        GameData.printGameData();
        verifyDataConsistency();

        chrono.stop();
        System.out.println("[DataLoader] Done in " + chrono.getDurationMsTxt());

        return loadingErrors == 0;
    }

    // TODO: add a method to check that no game resources are missing (and list missing resources if there is)
    private static void verifyDataConsistency() {
        System.out.println("(" + Date.getRealDate() + ") Verifying data consistency...");

        List<String> configResources = verifyResourcesDuplicates();
        verifyBuildingsConsistency(configResources);

        System.out.println("Found " + loadingErrors + " errors.");
    }

    private static List<String> verifyResourcesDuplicates() {
        List<String> configResources = new ArrayList<>();
        for (Resource resource : GameData.resourceList) {
            if (configResources.contains(resource.name)) {
                System.err.println("\t# ERROR # Duplicate of " + resource.name + " found (Resources)");
                loadingErrors++;
            }
            else {
                configResources.add(resource.name);
            }
        }
        return configResources;
    }

    private static void verifyBuildingsConsistency(List<String> configResources) {
        for (BuildingStats buildingStats : GameData.buildingStatsList) {
            verifyListConsistency(buildingStats.name, "materialsToConstruct", configResources, buildingStats.materialsToConstruct);
            verifyListConsistency(buildingStats.name, "storage", configResources, buildingStats.storage);
            verifyListConsistency(buildingStats.name, "yieldsPerSeason", configResources, buildingStats.yieldsPerSeason);
        }
    }

    private static void verifyListConsistency(String objectName, String listName, List<String> configResources, List<Resource> configBuildingStatsResourcesList) {
        List<String> configBuildingStatsResources = new ArrayList<>();
        for (Resource resource : configBuildingStatsResourcesList) {
            if (!configResources.contains(resource.name)) {
                System.err.println("\t# ERROR # Unknown resource ('" + resource.name + "') found in " + objectName + " (" + listName + ")");
                loadingErrors++;
            }
            else if (configBuildingStatsResources.contains(resource.name)) {
                System.err.println("\t# ERROR # Duplicate of " + resource.name + " found in " + objectName + " (" + listName + ")");
                loadingErrors++;
            }
            else {
                configBuildingStatsResources.add(resource.name);
            }
        }
    }
}
