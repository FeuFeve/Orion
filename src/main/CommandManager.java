package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.GameViewController;
import utilities.Chronometer;
import utilities.Date;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CommandManager {

    public static String[] availableCommands;


    public static void init() throws IOException {
        System.out.println("(" + Date.getRealDate() + ") Initializing CommandManager...");
        Chronometer chrono = new Chronometer();
        chrono.start();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        availableCommands = gson.fromJson(new FileReader("config/commands.json"), String[].class);
        System.out.println("# List of available in-game commands:\n" +
                "# " + Arrays.toString(availableCommands));

        chrono.stop();
        System.out.println("[CommandManager] Done in " + chrono.getDurationMsTxt());
    }

    public static void processCommand(String command, GameViewController controller) {
        String[] words = command.split(" ");

        switch (words[0]) {
            case "add":
                controller.addToCommandHistory(getName() + "Adding " + words[1]);
                break;
            case "remove":
                controller.addToCommandHistory(getName() + "Removing " + words[1]);
                break;
            case "build":
                controller.addToCommandHistory(getName() + "Building " + words[1]);
                break;
            case "destroy":
                controller.addToCommandHistory(getName() + "Destroying " + words[1]);
                break;
            default:
                printUnknownCommand(controller);
                break;
        }
    }

    private static String getName() {
        return "Console: ";
    }

    private static void printUnknownCommand(GameViewController controller) {
        controller.addToCommandHistory(getName() + "## Unknown command ##");
    }
}
