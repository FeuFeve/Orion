package game.main;

import game.models.BuildingStats;
import game.models.GameData;
import game.models.Job;
import game.models.Resource;
import game.utilities.Chronometer;
import game.utilities.Date;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private static final String CONFIG_FOLDER_PATH = "config/";

    private static final String RESOURCES_FILE_PATH = CONFIG_FOLDER_PATH + "resources.json";
    private static final String BUILDINGS_FOLDER_PATH = CONFIG_FOLDER_PATH + "Buildings/";
    private static final String JOBS_FILE_PATH = CONFIG_FOLDER_PATH + "jobs.json";
    // More to be expected

    private static int loadingErrors;


    public static boolean loadGameData() {
        System.out.println("(" + Date.getRealDate() + ") Loading game data...");

        Chronometer chrono = new Chronometer();
        chrono.start();

        GameData.resourceList = Resource.init(RESOURCES_FILE_PATH);
        GameData.buildingStatsList = BuildingStats.init(BUILDINGS_FOLDER_PATH);
        GameData.jobList = Job.init(JOBS_FILE_PATH);
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
        List<String> configBuildings = verifyBuildingsConsistency(configResources);
        verifyJobsConsistency(configBuildings);

        System.out.println("Found " + loadingErrors + " errors.");
    }

    private static List<String> verifyResourcesDuplicates() {
        List<String> configResources = new ArrayList<>();
        for (Resource resource : GameData.resourceList) {
            if (configResources.contains(resource.name)) {
                System.err.println("\t# ERROR # Duplicate resource '" + resource.name + "' found in resources config file");
                loadingErrors++;
            }
            else {
                configResources.add(resource.name);
            }
        }
        return configResources;
    }

    private static List<String> verifyBuildingsConsistency(List<String> configResources) {
        List<String> configBuildings = new ArrayList<>();
        for (BuildingStats buildingStats : GameData.buildingStatsList) {
            if (configBuildings.contains(buildingStats.name)) {
                System.err.println("\t# ERROR # Duplicate building (building with the same name '" + buildingStats.name + "') found in buildings config folder");
                loadingErrors++;
            }
            else {
                configBuildings.add(buildingStats.name);
            }
            verifyListConsistency(buildingStats.name, "materialsToConstruct", configResources, buildingStats.materialsToConstruct);
            verifyListConsistency(buildingStats.name, "storage", configResources, buildingStats.storage);
            verifyListConsistency(buildingStats.name, "yieldsPerSeason", configResources, buildingStats.yieldsPerSeason);
        }
        return configBuildings;
    }

    private static void verifyJobsConsistency(List<String> configBuildings) {
        List<String> configJobs = new ArrayList<>();
        for (Job job : GameData.jobList) {
            if (configJobs.contains(job.name)) {
                System.err.println("\t# ERROR # Duplicate job '" + job.name + "' found in jobs config file");
                loadingErrors++;
            }
            else {
                configJobs.add(job.name);
            }
            if (!configBuildings.contains(job.associatedBuilding.name)) {
                System.err.println("\t# ERROR # Unknown associated building ('" + job.associatedBuilding.name + "') found in '" + job.name + "' job");
                loadingErrors++;
            }
        }
    }

    private static void verifyListConsistency(String objectName, String listName, List<String> configResources, List<Resource> configBuildingStatsResourcesList) {
        List<String> configBuildingStatsResources = new ArrayList<>();
        for (Resource resource : configBuildingStatsResourcesList) {
            if (!configResources.contains(resource.name)) {
                System.err.println("\t# ERROR # Unknown resource ('" + resource.name + "') found in '" + objectName + "' buildings file (in " + listName + ")");
                loadingErrors++;
            }
            else if (configBuildingStatsResources.contains(resource.name)) {
                System.err.println("\t# ERROR # Duplicate of '" + resource.name + "' found in '" + objectName + "' buildings file (in " + listName + ")");
                loadingErrors++;
            }
            else {
                configBuildingStatsResources.add(resource.name);
            }
        }
    }
}
