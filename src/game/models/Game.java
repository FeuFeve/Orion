package game.models;

import game.utilities.Date;

public class Game {

    public String saveName;

    public String startRealDate;
    public String lastSavedRealDate;

    public long totalTicks;


    public Game() {
        this.startRealDate = Date.getRealDate();
    }

    public void tick() {
        // TODO: here will go the main game logic
        totalTicks++;
    }
}
