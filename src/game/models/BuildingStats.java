package game.models;

import java.util.ArrayList;
import java.util.List;

public class BuildingStats {

    // Base params
    public int housing;
    public List<Job> jobs = new ArrayList<>();
    public List<Resource> storage = new ArrayList<>();

    // Yields
    public List<Resource> yieldsPerSeason = new ArrayList<>();


    public BuildingStats() {}

    public BuildingStats(int amount, BuildingConfig buildingConfig) {
        housing = buildingConfig.buildingStats.housing * amount;

        jobs = new ArrayList<>(buildingConfig.buildingStats.jobs);
        for (Job job : jobs) {
            job.amount *= amount;
        }

        storage = new ArrayList<>(buildingConfig.buildingStats.storage);
        for (Resource resource : storage) {
            resource.amount *= amount;
        }

        yieldsPerSeason = new ArrayList<>(buildingConfig.buildingStats.yieldsPerSeason);
        for (Resource resource : yieldsPerSeason) {
            resource.amount *= amount;
        }
    }
}
