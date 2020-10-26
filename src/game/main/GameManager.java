package game.main;

import javafx.application.Platform;

public class GameManager {

    public static boolean isRunning = false;

    static final int[] UPDATES_PER_SECOND = {1, 2, 4, 8, 50};
    static int gameSpeed = 0;
    static boolean gameSpeedHasChanged = true;

    static Thread gameThread;


    public static void start() {
        ControllersManager.gameViewController.displayPlayUI();

        isRunning = true;
        gameThread = new Thread(GameManager::run);
        gameThread.start();
    }

    public static void stop() {
        ControllersManager.gameViewController.displayPauseUI();

        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void run() {
        ControllersManager.gameViewController.updateView(DataManager.currentGame);

        long initialTime = System.nanoTime();

        int gameUpsSpeed = gameSpeed;
        double timeU = (double) 1_000_000_000 / UPDATES_PER_SECOND[gameUpsSpeed];
        double deltaU = 0;

        while (isRunning && DataManager.currentGame != null) {

            long currentTime = System.nanoTime();
            long deltaTime = currentTime - initialTime;
            deltaU += deltaTime / timeU;
            initialTime = currentTime;

            if (deltaU >= 1) {
                // Game update
                DataManager.currentGame.tick();

                deltaU--;

                // Update view on UI thread
                Platform.runLater(() -> ControllersManager.gameViewController.updateView(DataManager.currentGame));

                if (gameSpeedHasChanged) {
                    gameUpsSpeed = gameSpeed;
                    timeU = (double) 1_000_000_000 / UPDATES_PER_SECOND[gameUpsSpeed];
                }

                // Calculate remaining time until next update
                currentTime = System.nanoTime();
                deltaTime = currentTime - initialTime;
                deltaU += deltaTime / timeU;
                initialTime = currentTime;

                int msUntilNextUpdate = (1000 / UPDATES_PER_SECOND[gameUpsSpeed]) - ((int) (deltaU * 1000));

                // Sleep for that remaining time
                if (msUntilNextUpdate > 0) {
                    try {
                        Thread.sleep(msUntilNextUpdate);
                    } catch (InterruptedException ignored) {}
                }
            }
        }
    }

    public static void updateGameSpeed(int gameSpeed) {
        GameManager.gameSpeed = gameSpeed;
        gameSpeedHasChanged = true;
    }
}
