package game.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.models.Game;
import game.utilities.Chronometer;
import game.utilities.Date;

import javax.swing.*;
import java.io.*;

public class DataManager {

    public static Game currentGame;


    public static void saveGame() throws IOException {
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
        else return;

        currentGame.saveName = selectedFile.getName();
        currentGame.lastSavedRealDate = Date.getRealDate();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter(new File(filePath));

        gson.toJson(currentGame, writer);

        writer.flush();
        writer.close();

        chrono.stop();
        System.out.println(" Done in " + chrono.getDurationMsTxt());
    }

    public static boolean loadGame() throws FileNotFoundException {
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
        else return false;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        currentGame = gson.fromJson(new FileReader(selectedFile), Game.class);

        currentGame.saveName = selectedFile.getName();

        chrono.stop();
        System.out.println(" Done in " + chrono.getDurationMsTxt());
        return true;
    }
}
