package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Game;

import java.io.*;

public class DataManager {

    public static Game currentGame;

    static void save(String saveName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter("saves/" + saveName + ".json");

        gson.toJson(currentGame, writer);

        writer.flush();
        writer.close();

        System.out.println("Game saved");
    }

    static void load(String saveName) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        currentGame = gson.fromJson(new FileReader("saves/" + saveName + ".json"), Game.class);

        System.out.println("Game loaded");
    }
}
