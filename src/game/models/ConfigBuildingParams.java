package game.models;

import game.utilities.Date;
import game.utilities.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigBuildingParams extends GameObject {

    // Construction params
    public int seasonsToConstruct;
    public int maxBuilders;
    public List<Resource> materialsToConstruct = new ArrayList<>();

    // Building params
    public BuildingStats buildingStats = new BuildingStats();


    public static List<ConfigBuildingParams> init(String folderPath) {
        System.out.print("(" + Date.getRealDate() + ") Loading buildings stats...");

        List<ConfigBuildingParams> configBuildingParamsList = new ArrayList<>();

        File file = new File(folderPath);
        String[] pathNames = file.list();
        assert pathNames != null;
        for (String pathName : pathNames) {
            configBuildingParamsList.add((ConfigBuildingParams) FileManager.loadFromJson(folderPath + pathName, ConfigBuildingParams.class));
        }

        System.out.println(" Done.");
        return configBuildingParamsList;
    }
}
