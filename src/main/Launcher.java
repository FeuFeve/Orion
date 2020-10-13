package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.init(primaryStage);

        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setTitle("Orion");
        primaryStage.setResizable(false);

        SceneManager.loadMainMenuScene();

        System.out.println("Starting the game...");
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        CommandManager.init();

        launch(args);
    }
}
