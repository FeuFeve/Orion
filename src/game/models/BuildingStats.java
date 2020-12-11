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
}
