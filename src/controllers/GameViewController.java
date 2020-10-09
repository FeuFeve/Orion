package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.CommandManager;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    @FXML private TextField commandTextField;
    @FXML public TextArea commandHistory;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialization done.");
        TextFields.bindAutoCompletion(commandTextField, CommandManager.availableCommands);
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
}
