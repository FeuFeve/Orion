package game.main;

import game.models.*;
import game.utilities.Chronometer;
import game.utilities.Date;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private static final String CONFIG_FOLDER_PATH = "config/";

    private static final String RESOURCES_FILE_PATH = CONFIG_FOLDER_PATH + "resources.json";
    private static final String JOBS_FILE_PATH = CONFIG_FOLDER_PATH + "jobs.json";
    private static final String BUILDINGS_FOLDER_PATH = CONFIG_FOLDER_PATH + "Buildings/";
    private static final String GAME_CONFIG_FILE_PATH = CONFIG_FOLDER_PATH + "game config.json";
    // More to be expected

    private static int loadingErrors;


    public static boolean loadGameData() {
        System.out.println("(" + Date.getRealDate() + ") Loading game data...");

        Chronometer chrono = new Chronometer();
        chrono.start();

        GameData.resourceList = Resource.init(RESOURCES_FILE_PATH);
        GameData.jobList = Job.init(JOBS_FILE_PATH);
        GameData.buildingConfigList = BuildingConfig.init(BUILDINGS_FOLDER_PATH);
        GameData.printGameData();
        GameData.gameConfig = GameConfig.init(GAME_CONFIG_FILE_PATH);
        verifyDataConsistency();

        chrono.stop();
        System.out.println("[DataLoader] Done in " + chrono.getDurationMsTxt());

        return loadingErrors == 0;
    }

    private static void verifyDataConsistency() {
        System.out.println("(" + Date.getRealDate() + ") Verifying data consistency...");

        List<String> referenceResourceList = verifyResourcesDuplicates();
        List<String> referenceJobList = verifyJobsDuplicates();
        List<String> referenceBuildingList = verifyBuildingsConsistency(referenceResourceList, referenceJobList);
        verifyGameConfigConsistency(referenceResourceList, referenceBuildingList);

        System.out.println("Found " + loadingErrors + " errors.");
    }

    private static List<String> verifyResourcesDuplicates() {
        List<String> existingNames = new ArrayList<>();
        for (Resource resource : GameData.resourceList) {
            if (checkNotDuplicateName(null, resource.name, existingNames, "food", null)) {
                existingNames.add(resource.name);
            }
        }
        return existingNames;
    }

    private static List<String> verifyJobsDuplicates() {
        List<String> existingNames = new ArrayList<>();
        for (Job job : GameData.jobList) {
            if (checkNotDuplicateName(null, job.name, existingNames, "job", null)) {
                existingNames.add(job.name);
            }
        }
        return existingNames;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static List<String> verifyBuildingsConsistency(List<String> referenceResourceList, List<String> referenceJobsList) {
        String configType = "building";
        String resourceListType = "resource";
        String jobListType = "job";

        List<String> existingNames = new ArrayList<>();
        for (BuildingConfig buildingConfig : GameData.buildingConfigList) {
            if (checkNotDuplicateBuildingName(buildingConfig.name, existingNames)) {
                existingNames.add(buildingConfig.name);
            }
            checkValueIsEqualToOrGreaterThan(1, buildingConfig.name, "seasonsToConstruct", buildingConfig.seasonsToConstruct, configType, null);
            checkValueIsEqualToOrGreaterThan(1, buildingConfig.name, "maxBuilders", buildingConfig.maxBuilders, configType, null);
            checkValueIsEqualToOrGreaterThan(0, buildingConfig.name, "housing", buildingConfig.buildingStats.housing, configType, null);

            // Verify object's lists
            verifyGameObjectListConsistency(buildingConfig.name, referenceResourceList, (List) buildingConfig.materialsToConstruct, configType, resourceListType, "materialsToConstruct");
            verifyGameObjectListConsistency(buildingConfig.name, referenceResourceList, (List) buildingConfig.buildingStats.storage, configType, resourceListType, "storage");
            verifyGameObjectListConsistency(buildingConfig.name, referenceResourceList, (List) buildingConfig.buildingStats.yieldsPerSeason, configType, resourceListType, "yieldsPerSeason");
            verifyGameObjectListConsistency(buildingConfig.name, referenceJobsList, (List) buildingConfig.buildingStats.jobs, configType, jobListType, "jobs");
        }
        return existingNames;
    }

    private static void verifyGameObjectListConsistency(String gameObjectName, List<String> referenceNames, List<Quantifiable> gameObjectsList,String configType, String listType, String listName) {
        List<String> existingNames = new ArrayList<>();
        for (Quantifiable quantifiableGameObjectToVerify : gameObjectsList) {
            String nameToVerify = quantifiableGameObjectToVerify.name;
            int valueToVerify = quantifiableGameObjectToVerify.amount;

            if (checkNotDuplicateName(gameObjectName, nameToVerify, existingNames, configType, listName)) {
                existingNames.add(quantifiableGameObjectToVerify.name);
            }
            checkNotUnknownName(gameObjectName, nameToVerify, referenceNames, configType, listType, listName);
            checkValueIsEqualToOrGreaterThan(1, gameObjectName, nameToVerify, valueToVerify, configType, listName);
        }
    }

    private static void verifyGameConfigConsistency(List<String> referenceResourceList, List<String> referenceBuildingList) {
        String configType = "game config";

        int villagersCount = GameData.gameConfig.startingVillagers;
        checkValueIsEqualToOrGreaterThan(1,null,"startingVillagers", villagersCount, configType, null);

        verifyStringListConsistency(referenceResourceList, GameData.gameConfig.startingResources, configType, "resource", "startingResources");
        verifyStringListConsistency(referenceBuildingList, GameData.gameConfig.startingBuildings, configType, "building", "startingBuildings");
    }

    private static void verifyStringListConsistency(List<String> referenceNames, List<Pair<String, Integer>> startingList, String configType, String listType, String listName) {
        List<String> existingNames = new ArrayList<>();
        for (Pair<String, Integer> pairToVerify : startingList) {
            String nameToVerify = pairToVerify.getKey();
            int valueToVerify = pairToVerify.getValue();

            if (checkNotDuplicateName(null, nameToVerify, existingNames, configType, listName)) {
                existingNames.add(nameToVerify);
            }
            checkNotUnknownName(null, nameToVerify, referenceNames, configType, listType, listName);
            checkValueIsEqualToOrGreaterThan(1, null, nameToVerify, valueToVerify, configType, listName);
        }
    }

    /**
     * Checks that the nameToVerify is not already in the existingNames list. If it is, prints an error message and adds
     * an error to the loadingErrors count.
     * If a duplicate is found, the example bellow will print:
     * "\t# ERROR # Duplicate building ('Farm') found in one of the files of the Buildings config folder"
     * @param nameToVerify      Name to verify, exemple: "Farm"
     * @param existingNames     List of names previously seen in the buildings config folder
     * @return true if the name is not in the list, false otherwise.
     */
    private static boolean checkNotDuplicateBuildingName(String nameToVerify, List<String> existingNames) {
        if (existingNames.contains(nameToVerify)) {
            System.err.println("\t# ERROR # Duplicate building ('" + nameToVerify + "') found in one of the files of the Buildings config folder");
            loadingErrors++;
            return false;
        }
        return true;
    }

    /**
     * Checks that the nameToVerify is not already in the existingNames list. If it is, prints an error message and adds
     * an error to the loadingErrors count.
     * If a duplicate is found, the example bellow will print:
     * "\t# ERROR # Duplicate of 'Wood' found in 'Farm' building config file (in 'materialsToConstruct')"
     * @param gameObjectName    GameObject name (nullable), exemple: "Farm"
     * @param nameToVerify      Name to verify, exemple: "Wood"
     * @param existingNames     List of names previously seen in the config file
     * @param configType        Name of the config, example: "building"
     * @param listName          Name of the GameObject's list where the duplicate is (nullable), exemple: "materialsToConstruct"
     * @return true if the name is not in the list, false otherwise.
     */
    private static boolean checkNotDuplicateName(@Nullable String gameObjectName, String nameToVerify, List<String> existingNames, String configType, @Nullable String listName) {
        if (existingNames.contains(nameToVerify)) {
            String endOfLine = createCustomEndOfLine(gameObjectName, configType, listName);
            System.err.println("\t# ERROR # Duplicate " + configType + " '" + nameToVerify + "'" + endOfLine);
            loadingErrors++;
            return false;
        }
        return true;
    }

    /**
     * Checks that the nameToVerify is in the referenceNames list. If it is not, prints an error message and adds an
     * error to the loadingErrors count.
     * If an unknown name is found, the example bellow will print:
     * "\t# ERROR # Unknown 'resource' ('Wood') found in 'Farm' building config file (in 'materialsToConstruct')"
     * @param gameObjectName    GameObject name (nullable), exemple: "Farm"
     * @param nameToVerify      Name to verify, exemple: "Wood"
     * @param referenceNames    List of authorized names
     * @param configType        Name of the config, example: "building"
     * @param listType          Type of the list's GameObjects, exemple: "resource"
     * @param listName          Name of the GameObject's list where the duplicate is, exemple: "materialsToConstruct"
     */
    private static void checkNotUnknownName(@Nullable String gameObjectName, String nameToVerify, List<String> referenceNames, String configType, String listType, String listName) {
        if (!referenceNames.contains(nameToVerify)) {
            String endOfLine = createCustomEndOfLine(gameObjectName, configType, listName);
            System.err.println("\t# ERROR # Unknown '" + listType + "' ('" + nameToVerify + "') " + endOfLine);
            loadingErrors++;
        }
    }

    /**
     * Checks that the valueToVerify is greater than 0. If it is not, prints an error message and adds an error to the
     * loadingErrors count.
     * Print exemple when a value <= 0 is found:
     * "\t# ERROR # Incorrect value ('startingVillagers' = '-10', should be >= 1) found in game config file"
     * If a value <= 0 is found, the example bellow will print:
     * "\t# ERROR # Incorrect value ('Wood' = '-10', should be >= 1) found in Farm building config file (in 'materialsToConstruct')
     * @param value             Threshold value, exemple: 1
     * @param gameObjectName    GameObject name (nullable), exemple: "Farm"
     * @param nameToVerify      Name of the object to verify, exemple: "Wood"
     * @param valueToVerify     Value of the object to verify, exemple: -10
     * @param configType        Name of the config, example: "building"
     * @param listName          Name of the GameObject's list where the duplicate is (nullable), exemple: materialsToConstruct
     */
    private static void checkValueIsEqualToOrGreaterThan(int value, @Nullable String gameObjectName, String nameToVerify, int valueToVerify, String configType, @Nullable String listName) {
        if (valueToVerify < value) {
            String endOfLine = createCustomEndOfLine(gameObjectName, configType, listName);
            System.err.println("\t# ERROR # Incorrect value ('" + nameToVerify + "' = '" + valueToVerify + "', should be >= " + value + ")" + endOfLine);
            loadingErrors++;
        }
    }

    private static String createCustomEndOfLine(@Nullable String gameObjectName, String configType, @Nullable String listName) {
        String addGameObjectName = "";
        String addListName = "";
        if (gameObjectName != null && !gameObjectName.isEmpty()) {
            addGameObjectName = "'" + gameObjectName + "' ";
        }
        if (listName != null && !listName.isEmpty()) {
            addListName = " (in the '" + listName + "' list)";
        }
        return " found in " + addGameObjectName + configType + " config file" + addListName;
    }
}
