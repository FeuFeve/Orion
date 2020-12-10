package game.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import game.utilities.Date;

import java.io.IOException;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.init(primaryStage);
        DataLoader.loadGameData();

        primaryStage.setOnCloseRequest(t -> {
            System.out.println("(" + Date.getRealDate() + ") Exiting...");
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setTitle("Orion");
        primaryStage.setResizable(false);

        SceneManager.loadMainMenuScene();

        System.out.println("(" + Date.getRealDate() + ") Starting the game...");
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        CommandManager.init();

        launch(args);
    }
}
