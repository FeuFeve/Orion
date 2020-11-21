package game.main;

import game.models.GameResources;
import game.models.Resource;
import game.utilities.Chronometer;
import game.utilities.Date;

public class ResourceLoader {

    private static final String CONFIG_FOLDER_PATH = "config/";

    private static final String RESOURCES_FILE_PATH = CONFIG_FOLDER_PATH + "resources.json";
    // More to be expected


    public static boolean loadResources() {
        System.out.println("(" + Date.getRealDate() + ") Loading game resources...");

        Chronometer chrono = new Chronometer();
        chrono.start();

        GameResources.resources = Resource.init(RESOURCES_FILE_PATH);

        chrono.stop();
        System.out.println("[ResourceLoader] Done in " + chrono.getDurationMsTxt());

        return true;
    }

    // TODO: add a method to check that no game resources are missing (and list missing resources if there is)
}
