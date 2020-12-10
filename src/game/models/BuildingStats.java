package game.models;

import game.utilities.Date;
import game.utilities.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuildingStats extends GameObject {

    // Construction stats
    public int seasonsToConstruct;
    public int maxBuilders;
    public List<Resource> materialsToConstruct = new ArrayList<>();

    // Base stats
    public int workers;
    public int housing;
    public List<Resource> storage = new ArrayList<>();

    // Yields
    public List<Resource> yieldsPerSeason = new ArrayList<>();


    public static List<BuildingStats> init(String folderPath) {
        System.out.print("(" + Date.getRealDate() + ") Loading buildings stats...");

        List<BuildingStats> buildingStatsList = new ArrayList<>();

        File file = new File(folderPath);
        String[] pathNames = file.list();
        assert pathNames != null;
        for (String pathName : pathNames) {
            buildingStatsList.add((BuildingStats) FileManager.loadFromJson(folderPath + pathName, BuildingStats.class));
        }

        System.out.println(" Done.");
        return buildingStatsList;
    }
}
