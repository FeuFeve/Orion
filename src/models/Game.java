package models;

import utilities.Date;

public class Game {

    public String saveName;

    public String startRealDate;
    public String lastSavedRealDate;


    public Game() {
        this.startRealDate = Date.getRealDate();
    }
}
