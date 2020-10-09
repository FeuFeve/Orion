package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private TextField commandTextField;
    @FXML private TextArea commandHistory;

    String[] commands = {
            "Build house <amount>",
            "Build farm <amount>",
            "Build granary <amount>",
            "Add job builder <amount>"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialization done.");
        TextFields.bindAutoCompletion(commandTextField, commands);
    }

    public void sendCommand() {
        String command = commandTextField.getText();
        if (!command.isEmpty() && command.trim().length() > 0) {

            command = command.trim().replaceAll(" +", " ");
            String commandHistoryText = commandHistory.getText();
            if (!commandHistoryText.isEmpty()) {
                commandHistoryText += "\n";
            }
            commandHistory.setText(commandHistoryText + "> " + command);
        }
        commandTextField.clear();
    }
}
