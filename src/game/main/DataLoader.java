package game.main;

import game.models.BuildingStats;
import game.models.GameData;
import game.models.Resource;
import game.utilities.Chronometer;
import game.utilities.Date;

public class DataLoader {

    private static final String CONFIG_FOLDER_PATH = "config/";

    private static final String RESOURCES_FILE_PATH = CONFIG_FOLDER_PATH + "resources.json";
    private static final String BUILDINGS_FOLDER_PATH = CONFIG_FOLDER_PATH + "Buildings/";
    // More to be expected

    public static int loadingErrors;


    public static boolean loadGameData() {
        System.out.println("(" + Date.getRealDate() + ") Loading game data...");

        Chronometer chrono = new Chronometer();
        chrono.start();

        GameData.resourceList = Resource.init(RESOURCES_FILE_PATH);
        GameData.buildingStatsList = BuildingStats.init(BUILDINGS_FOLDER_PATH);
        GameData.printGameData();

        chrono.stop();
        System.out.println("[DataLoader] Done in " + chrono.getDurationMsTxt());

        return true;
    }

    // TODO: add a method to check that no game resources are missing (and list missing resources if there is)
}
