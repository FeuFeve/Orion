package game.main;

import game.utilities.Chronometer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import game.utilities.Date;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Chronometer chrono = new Chronometer();
        chrono.start();

        CommandManager.init();
        SceneManager.init(primaryStage);
        if (!DataLoader.loadGameData()) {
            exit();
        }

        primaryStage.setOnCloseRequest(t -> exit());

        primaryStage.setTitle("Orion");
        primaryStage.setResizable(false);

        SceneManager.loadMainMenuScene();

        System.out.println("(" + Date.getRealDate() + ") Starting the game...");
        primaryStage.show();

        chrono.stop();
        System.out.println("Total loading time: " + chrono.getDurationMsTxt());
    }

    public void exit() {
        System.out.println("(" + Date.getRealDate() + ") Exiting...");
        Platform.exit();
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
