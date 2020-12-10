package game.models;

import java.util.List;

public class GameData {

    public static List<Resource> resources;
    // More to be expected...

    public static void printGameData() {
        System.out.println("Resources:");
        for (Resource resource : resources) {
            System.out.println("\t" + resource.name);
        }
    }
}
