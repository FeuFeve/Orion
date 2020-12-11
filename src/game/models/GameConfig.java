package game.models;

import game.utilities.Date;
import game.utilities.FileManager;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GameConfig {

    public int startingVillagers;
    public List<Pair<String, Integer>> startingResources = new ArrayList<>();
    public List<Pair<String, Integer>> startingBuildings = new ArrayList<>();


    public static GameConfig init(String filepath) {
        System.out.print("(" + Date.getRealDate() + ") Loading game config...");

        GameConfig gameConfig = (GameConfig) FileManager.loadFromJson(filepath, GameConfig.class);

        System.out.println(" Done.");
        return gameConfig;
    }
}
