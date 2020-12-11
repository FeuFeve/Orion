package game.models;

public class Building extends Quantifiable {

    public BuildingConfig buildingConfig;
    public BuildingStats buildingStats;


    public Building(int amount, BuildingConfig buildingConfig) {
        name = buildingConfig.name;
        this.amount = amount;
        this.buildingConfig = buildingConfig;
        buildingStats = new BuildingStats(amount, buildingConfig);
    }
}
