package controllers;

import main.DataManager;
import main.SceneManager;
import models.Game;

import java.io.IOException;

public class MainMenuViewController {

    public void newGame() {
        DataManager.currentGame = new Game();
        SceneManager.loadGameScene();
    }

    public void loadGame() throws IOException {
        if (DataManager.loadGame()) {
            SceneManager.loadGameScene();
        }
    }
}
