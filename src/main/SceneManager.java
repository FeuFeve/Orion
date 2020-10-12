package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage window;
    private static Scene mainMenuScene, gameScene;


    public static void init(Stage window) throws IOException {
        System.out.println("Initializing SceneManager...");

        SceneManager.window = window;

        mainMenuScene = new Scene(FXMLLoader.load(SceneManager.class.getResource("../views/main_menu_view.fxml")));
        gameScene = new Scene(FXMLLoader.load(SceneManager.class.getResource("../views/game_view.fxml")));

        System.out.println("# [SceneManager] done");
    }

    public static void loadMainMenuScene() {
        System.out.println("Loading main_menu_view.fxml...");
        window.setScene(mainMenuScene);
        System.out.println("# done");
    }

    public static void loadGameScene() {
        System.out.println("Loading game_view.fxml...");
        window.setScene(gameScene);
        System.out.println("# done");
    }
}
