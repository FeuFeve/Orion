package game.models;

import game.utilities.Date;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public String saveName;

    public String startRealDate;
    public String lastSavedRealDate;

    public long totalTicks;

    // Game related
    public List<Person> villagers = new ArrayList<>();
    public List<Building> buildings = new ArrayList<>();
    public List<Job> jobs = new ArrayList<>();
    public List<Resource> storage = new ArrayList<>();


    public Game() {
        this.startRealDate = Date.getRealDate();
    }

    public Game loadFromConfig() {
        for (int i = 0; i < GameData.gameConfig.startingVillagers; i++) {
            villagers.add(new Person().build());
        }
        for (Pair<String, Integer> pair : GameData.gameConfig.startingBuildings) {
            // Add buildings from game starting config
            BuildingConfig buildingConfig = GameData.getBuildingConfig(pair.getKey());
            assert buildingConfig != null;
            Building building = new Building(pair.getValue(), buildingConfig);
            buildings.add(building);

            setResourcesTypes(building.buildingStats.storage);
            setResourcesTypes(building.buildingStats.yieldsPerSeason);

            // Add resources from buildings storage lists to the game storage (maxAmount)
            for (Resource resource : building.buildingStats.storage) {
                Resource storageResource;
                int index = storage.indexOf(resource);
                if (index == -1) {
                    storageResource = new Resource(resource);
                }
                else {
                    storageResource = storage.get(index);
                }
                storageResource.maxAmount += resource.amount;
                storage.add(storageResource);
            }
        }

        // Add resources from game starting config to the game storage (amount)
        for (Pair<String, Integer> pair : GameData.gameConfig.startingResources) {
            Resource resource = GameData.getResource(pair.getKey());
            assert resource != null;

            Resource storageResource;
            int index = storage.indexOf(resource);
            if (index == -1) {
                storageResource = new Resource(resource);
                storageResource.amount += pair.getValue();
                storage.add(resource);
            }
            else {
                storageResource = storage.get(index);
                storageResource.amount += pair.getValue();
            }
        }
        return this;
    }

    /**
     * Set the resources types in the buildings lists, as the types are generally not specified in the config files.
     * @param list  A building's list of resources
     */
    private static void setResourcesTypes(List<Resource> list) {
        for (Resource resource : list) {
            for (Resource referenceResource : GameData.resourceList) {
                if (referenceResource.name.equals(resource.name)) {
                    resource.type = referenceResource.type;
                    break;
                }
            }
        }
    }

    public void tick() {
        // TODO: here will go the main game logic
        totalTicks++;
    }
}
