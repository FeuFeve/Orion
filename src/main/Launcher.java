package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    public static Stage window;
    public static Scene mainMenuScene, gameScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("../views/main_menu_view.fxml")));
        gameScene = new Scene(FXMLLoader.load(getClass().getResource("../views/game_view.fxml")));

        window.setTitle("Orion");
        window.setScene(mainMenuScene);
        window.setResizable(false);
        window.show();
    }


    public static void main(String[] args) throws IOException {
        System.out.print("Initializing CommandManager...");
        CommandManager.init();
        System.out.println(" Done");

        System.out.println("Starting the game");
        launch(args);
    }
}
