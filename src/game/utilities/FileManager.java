package game.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.models.Game;

import java.io.*;
import java.lang.reflect.Type;

public class FileManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static boolean saveToJson(Object object, String filePath) {
        try {
            Writer writer = new FileWriter(new File(filePath));

            gson.toJson(object, writer);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static Object loadFromJson(File file, Type objectType) {
        try {
            return gson.fromJson(new FileReader(file), objectType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object loadFromJson(String filePath, Type objectType) {
        try {
            File file = new File(filePath);
            return gson.fromJson(new FileReader(file), objectType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
