package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.CommandManager;
import main.DataManager;
import main.SceneManager;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    @FXML private TextField commandTextField;
    @FXML public TextArea commandHistory;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing GameViewController...");
        TextFields.bindAutoCompletion(commandTextField, CommandManager.availableCommands);
        System.out.println("# [GameViewController] done");
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

    public void save() throws IOException {
        DataManager.saveGame();
    }

    public void backToMainMenu() {
        SceneManager.loadMainMenuScene();
    }
}
