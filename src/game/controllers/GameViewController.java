package game.controllers;

import game.main.*;
import game.models.Game;
import game.models.Images;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;
import game.utilities.Chronometer;
import game.utilities.Date;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    @FXML private TextField commandTextField;
    @FXML private TextArea commandHistory;

    @FXML private ImageView playPauseIcon;
    @FXML private Text gameSpeed;

    // Tab 1
    @FXML private Text currentSystemTime;
    @FXML private Text currentUpdateIndex;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("(" + Date.getRealDate() + ") Initializing GameViewController...");
        Chronometer chronometer = new Chronometer();
        chronometer.start();

        TextFields.bindAutoCompletion(commandTextField, CommandManager.availableCommands);

        ControllersManager.gameViewController = this;

        chronometer.stop();
        System.out.println("[GameViewController] Done in " + chronometer.getDurationMsTxt());
    }

    public void sendCommand() {
        String command = commandTextField.getText();
        if (!command.isEmpty() && command.trim().length() > 0) {

            command = command.trim().replaceAll(" +", " ");
            addToCommandHistory("> " + command);
        }
        commandTextField.clear();

        CommandManager.processCommand(command, this);
    }

    public void addToCommandHistory(String string) {
        String commandHistoryText = commandHistory.getText();
        if (!commandHistoryText.isEmpty()) {
            commandHistoryText += "\n";
        }
        commandHistory.setText(commandHistoryText + string);
    }

    public void updateView(Game game) {
        Platform.runLater(() -> {
            String time = Date.getRealTimeMs();
            currentSystemTime.setText("Current system time: " + time);
            currentUpdateIndex.setText("Current update index: " + game.totalTicks);
        });
    }

    public void save() throws IOException {
        if (GameManager.isRunning) {
            GameManager.stop();
            DataManager.saveGame();
            GameManager.start();
        }
        else {
            DataManager.saveGame();
        }
    }

    public void backToMainMenu() {
        DataManager.currentGame = null;
        GameManager.stop();
        SceneManager.loadMainMenuScene();
    }

    public void displayPlayUI() {
        Platform.runLater(() -> playPauseIcon.setImage(Images.playIcon));
    }

    public void displayPauseUI() {
        Platform.runLater(() -> playPauseIcon.setImage(Images.pauseIcon));
    }

    public void updateGameSpeed(int gameSpeed) {
        Platform.runLater(() -> this.gameSpeed.setText("X" + gameSpeed));
    }
}
