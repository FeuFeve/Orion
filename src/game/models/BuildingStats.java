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

        for (Job job : buildingConfig.buildingStats.jobs) {
            Job newJob = new Job(job);
            newJob.amount *= amount;
            jobs.add(newJob);
        }

        for (Resource resource : buildingConfig.buildingStats.storage) {
            Resource newResource = new Resource(resource);
            newResource.amount *= amount;
            storage.add(newResource);
        }

        for (Resource resource : buildingConfig.buildingStats.yieldsPerSeason) {
            Resource newResource = new Resource(resource);
            newResource.amount *= amount;
            storage.add(newResource);
        }
    }
}
