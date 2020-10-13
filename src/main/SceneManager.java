package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.Chronometer;
import utilities.Date;

import java.io.IOException;

public class SceneManager {

    private static Stage window;
    private static Scene mainMenuScene, gameScene;


    public static void init(Stage window) throws IOException {
        System.out.println("(" + Date.getRealDate() + ") Initializing SceneManager...");
        Chronometer chrono = new Chronometer();
        chrono.start();

        SceneManager.window = window;

        mainMenuScene = new Scene(FXMLLoader.load(SceneManager.class.getResource("../views/main_menu_view.fxml")));
        gameScene = new Scene(FXMLLoader.load(SceneManager.class.getResource("../views/game_view.fxml")));

        chrono.stop();
        System.out.println("[SceneManager] Done in " + chrono.getDurationMsTxt());
    }

    public static void loadMainMenuScene() {
        System.out.print("(" + Date.getRealDate() + ") Loading main_menu_view.fxml...");
        window.setScene(mainMenuScene);
        System.out.println(" Done");
    }

    public static void loadGameScene() {
        System.out.print("(" + Date.getRealDate() + ") Loading game_view.fxml...");
        window.setScene(gameScene);
        System.out.println(" Done");
    }
}
