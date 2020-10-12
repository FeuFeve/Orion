package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.DataManager;
import main.Launcher;
import models.Game;

public class MainMenuViewController {

    @FXML private Button newGameButton;
    @FXML private Button loadButton;


    public void newGame() {
        DataManager.currentGame = new Game();
        Launcher.window.setScene(Launcher.gameScene);
    }
}
