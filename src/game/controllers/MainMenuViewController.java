package game.controllers;

import game.main.*;
import game.models.Game;
import game.utilities.Chronometer;
import game.utilities.Date;
import javafx.application.Platform;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuViewController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("(" + Date.getRealDate() + ") Initializing MainMenuViewController...");
        Chronometer chronometer = new Chronometer();
        chronometer.start();

        ControllersManager.mainMenuViewController = this;

        chronometer.stop();
        System.out.println("[MainMenuViewController] Done in " + chronometer.getDurationMsTxt());
    }

    public void newGame() {
        DataManager.currentGame = new Game();
        SceneManager.loadGameScene();
        Platform.runLater(() -> ControllersManager.gameViewController.updateView(DataManager.currentGame));
        // GameManager.start();
    }

    public void loadGame() throws IOException {
        if (DataManager.loadGame() == 1) {
            SceneManager.loadGameScene();
            Platform.runLater(() -> ControllersManager.gameViewController.updateView(DataManager.currentGame));
            // GameManager.start();
        }
    }
}
