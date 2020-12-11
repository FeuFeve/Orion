package game.models;

import game.utilities.Date;

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


    public Game() {
        this.startRealDate = Date.getRealDate();
    }

    public void tick() {
        // TODO: here will go the main game logic
        totalTicks++;
    }
}
