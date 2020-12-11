package game.main;

import game.models.Game;
import game.utilities.Chronometer;
import game.utilities.Date;
import game.utilities.FileManager;

import javax.swing.*;
import java.io.*;

public class DataManager {

    public static Game currentGame;


    public static void createNewGame() {
        currentGame = new Game().loadFromConfig();
    }

    public static int saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("saves"));

        int result = fileChooser.showSaveDialog(new JFrame());

        Chronometer chrono = new Chronometer();
        chrono.start();

        File selectedFile;
        boolean endsWithJson;
        String filePath;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            endsWithJson = selectedFile.getName().endsWith(".json");
            if (endsWithJson) {
                filePath = "saves/" + selectedFile.getName();
            }
            else {
                filePath = "saves/" + selectedFile.getName() + ".json";
            }
            System.out.print("(" + Date.getRealDate() + ") Saving to " + filePath + "...");
        }
        else return 0;

        currentGame.saveName = selectedFile.getName();
        currentGame.lastSavedRealDate = Date.getRealDate();

        boolean saved = FileManager.saveToJson(currentGame, filePath);
        if (!saved) {
            return -1;
        }

        chrono.stop();
        System.out.println(" Done in " + chrono.getDurationMsTxt());
        return 1;
    }

    public static int loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("saves"));

        int result = fileChooser.showOpenDialog(new JFrame());

        Chronometer chrono = new Chronometer();
        chrono.start();

        File selectedFile;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.print("(" + Date.getRealDate() + ") Loading save from " + selectedFile.getName() + "...");
        }
        else return 0;

        currentGame = (Game) FileManager.loadFromJson(selectedFile, Game.class);
        if (currentGame == null) {
            return -1;
        }

        currentGame.saveName = selectedFile.getName();

        chrono.stop();
        System.out.println(" Done in " + chrono.getDurationMsTxt());
        return 1;
    }
}
