package game.main;

import com.google.gson.reflect.TypeToken;
import game.models.Game;
import game.utilities.Chronometer;
import game.utilities.Date;
import game.utilities.FileManager;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    // To add new names to the male/female first/last names config files
    public static void main(String[] args) {
        addToNames("female", "Edmonda Nellwyn Edith Valora Kaelyn ");
        addToNames("last", "Priestleye Harlande Burkee Ewart Sweete");
    }

    /**
     * Add names to one of the names.json file in the configs.
     * Exemples:
     * addToNames("female", "Ana Mia Rachel")
     * addToNames("last", "Holmes Dickson")
     * @param nameType  Type of the names to add ("male", "female" or "last)
     * @param names     String of names separated by a space
     */
    private static void addToNames(String nameType, String names) {
        System.out.println("******************************");

        final String NAME_CONFIG_FOLDER = "config/Names/";
        String filePath;
        switch (nameType) {
            case "male": filePath = NAME_CONFIG_FOLDER + "male first names.json"; break;
            case "female": filePath = NAME_CONFIG_FOLDER + "female first names.json"; break;
            case "last": filePath = NAME_CONFIG_FOLDER + "last names.json"; break;
            default: System.out.println(nameType + " does not correspond to any referenced type."); return;
        }

        System.out.println("Saving " + nameType + " names...");

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        List<String> list = (List<String>) FileManager.loadArrayFromJson(filePath, type);

        String[] namesArray = names.split(" ");
        List<String> alreadyInList = new ArrayList<>();
        int count = 0;
        for (String name : namesArray) {
            if (!name.isEmpty()) {
                String stripedName = name.strip();
                if (!list.contains(stripedName)) {
                    list.add(stripedName);
                    count++;
                }
                else {
                    alreadyInList.add(stripedName);
                }
            }
        }

        if (!alreadyInList.isEmpty()) {
            System.out.println(alreadyInList + " were already in the list.");
        }

        Collections.sort(list);
        System.out.println("Added " + count + "/" + namesArray.length + " names.");
        System.out.println("List: " + list);
        System.out.println("Total: " + list.size());
        FileManager.saveToJson(list, filePath);

        System.out.println("Done.");
        System.out.println("******************************\n");
    }
}
