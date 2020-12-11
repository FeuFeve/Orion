package game.main;

import game.models.*;
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
        GameData.configBuildingParamsList = ConfigBuildingParams.init(BUILDINGS_FOLDER_PATH);
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
        List<String> configJobs = verifyJobsDuplicates();
        verifyBuildingsConsistency(configResources, configJobs);

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

    private static List<String> verifyJobsDuplicates() {
        List<String> configJobs = new ArrayList<>();
        for (Job job : GameData.jobList) {
            if (configJobs.contains(job.name)) {
                System.err.println("\t# ERROR # Duplicate job '" + job.name + "' found in jobs config file");
                loadingErrors++;
            }
            else {
                configJobs.add(job.name);
            }
        }
        return configJobs;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void verifyBuildingsConsistency(List<String> configResources, List<String> configJobs) {
        List<String> configBuildings = new ArrayList<>();
        for (ConfigBuildingParams configBuildingParams : GameData.configBuildingParamsList) {
            if (configBuildings.contains(configBuildingParams.name)) {
                System.err.println("\t# ERROR # Duplicate building (building with the same name '" + configBuildingParams.name + "') found in buildings config folder");
                loadingErrors++;
            }
            else {
                configBuildings.add(configBuildingParams.name);
            }
            verifyListConsistency("resource", configBuildingParams.name, "materialsToConstruct", configResources, (List) configBuildingParams.materialsToConstruct);
            verifyListConsistency("resource", configBuildingParams.name, "storage", configResources, (List) configBuildingParams.buildingStats.storage);
            verifyListConsistency("resource", configBuildingParams.name, "yieldsPerSeason", configResources, (List) configBuildingParams.buildingStats.yieldsPerSeason);
            verifyListConsistency("job", configBuildingParams.name, "jobs", configJobs, (List) configBuildingParams.buildingStats.jobs);
        }
    }

    private static void verifyListConsistency(String listType, String objectName, String listName, List<String> configResources, List<GameObject> configBuildingStatsResourcesList) {
        List<String> configBuildingStatsResources = new ArrayList<>();
        for (GameObject gameObject : configBuildingStatsResourcesList) {
            if (!configResources.contains(gameObject.name)) {
                System.err.println("\t# ERROR # Unknown " + listType + " ('" + gameObject.name + "') found in '" + objectName + "' buildings file (in " + listName + ")");
                loadingErrors++;
            }
            else if (configBuildingStatsResources.contains(gameObject.name)) {
                System.err.println("\t# ERROR # Duplicate of '" + gameObject.name + "' found in '" + objectName + "' buildings file (in " + listName + ")");
                loadingErrors++;
            }
            else {
                configBuildingStatsResources.add(gameObject.name);
            }
        }
    }
}
