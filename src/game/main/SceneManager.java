package game.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import game.utilities.Chronometer;
import game.utilities.Date;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {

    private static Stage window;
    private static Scene mainMenuScene, gameScene;

    private static final HashMap<KeyCode, Boolean> currentlyActiveKeys = new HashMap<>();


    public static void init(Stage window) throws IOException {
        System.out.println("(" + Date.getRealDate() + ") Initializing SceneManager...");
        Chronometer chrono = new Chronometer();
        chrono.start();

        SceneManager.window = window;

        mainMenuScene = new Scene(FXMLLoader.load(SceneManager.class.getResource("../views/main_menu_view.fxml")));
        gameScene = new Scene(FXMLLoader.load(SceneManager.class.getResource("../views/game_view.fxml")));

        initGameSceneKeyEvents();

        chrono.stop();
        System.out.println("[SceneManager] Done in " + chrono.getDurationMsTxt());
    }

    private static void initGameSceneKeyEvents() {
        gameScene.setOnKeyPressed(keyEvent -> {

            KeyCode currentKey = keyEvent.getCode();
            if (currentlyActiveKeys.get(currentKey) == null) {

                currentlyActiveKeys.put(currentKey, true);

                switch (currentKey) {
                    case SPACE:
                        if (GameManager.isRunning) GameManager.stop();
                        else GameManager.start();
                        break;
                    case DIGIT1:
                        GameManager.updateGameSpeed(0);
                        ControllersManager.gameViewController.updateGameSpeed(GameManager.UPDATES_PER_SECOND[0]);
                        break;
                    case DIGIT2:
                        GameManager.updateGameSpeed(1);
                        ControllersManager.gameViewController.updateGameSpeed(GameManager.UPDATES_PER_SECOND[1]);
                        break;
                    case DIGIT3:
                        GameManager.updateGameSpeed(2);
                        ControllersManager.gameViewController.updateGameSpeed(GameManager.UPDATES_PER_SECOND[2]);
                        break;
                    case DIGIT4:
                        GameManager.updateGameSpeed(3);
                        ControllersManager.gameViewController.updateGameSpeed(GameManager.UPDATES_PER_SECOND[3]);
                        break;
                }
            }
        });

        gameScene.setOnKeyReleased(keyEvent -> currentlyActiveKeys.remove(keyEvent.getCode()));
    }

    public static void loadMainMenuScene() {
        System.out.print("(" + Date.getRealDate() + ") Loading main_menu_view.fxml...");
        Chronometer chrono = new Chronometer();
        chrono.start();

        window.setScene(mainMenuScene);

        chrono.stop();
        System.out.println(" Done in " + chrono.getDurationMsTxt());
    }

    public static void loadGameScene() {
        System.out.print("(" + Date.getRealDate() + ") Loading game_view.fxml...");
        Chronometer chrono = new Chronometer();
        chrono.start();

        window.setScene(gameScene);

        chrono.stop();
        System.out.println(" Done in " + chrono.getDurationMsTxt());
    }
}
