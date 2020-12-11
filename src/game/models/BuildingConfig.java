package game.models;

import game.utilities.Date;
import game.utilities.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuildingConfig extends GameObject {

    // Construction params
    public int seasonsToConstruct;
    public int maxBuilders;
    public List<Resource> materialsToConstruct = new ArrayList<>();

    // Building params
    public BuildingStats buildingStats = new BuildingStats();


    public static List<BuildingConfig> init(String folderPath) {
        System.out.print("(" + Date.getRealDate() + ") Loading buildings stats...");

        List<BuildingConfig> buildingConfigList = new ArrayList<>();

        File file = new File(folderPath);
        String[] pathNames = file.list();
        assert pathNames != null;
        for (String pathName : pathNames) {
            buildingConfigList.add((BuildingConfig) FileManager.loadFromJson(folderPath + pathName, BuildingConfig.class));
        }

        System.out.println(" Done.");
        return buildingConfigList;
    }
}
