package game.main;

import javafx.application.Platform;

public class GameManager {

    static boolean isRunning = true;

    static final int[] UPDATES_PER_SECOND = {1, 2, 4, 8};
    static int gameUpsSpeed = 0;

    static Thread gameThread;


    public static void start() {
        isRunning = true;
        gameThread = new Thread(GameManager::run);
        gameThread.start();
    }

    public static void stop() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void run() {

        // Update view on UI thread
        Platform.runLater(() -> ControllersManager.gameViewController.updateView(DataManager.currentGame));

        long initialTime = System.nanoTime();

        final double TIME_U = (double) 1_000_000_000 / UPDATES_PER_SECOND[gameUpsSpeed];
        double deltaU = 0;

        while (isRunning && DataManager.currentGame != null) {

            long currentTime = System.nanoTime();
            long deltaTime = currentTime - initialTime;
            deltaU += deltaTime / TIME_U;
            initialTime = currentTime;

            if (deltaU >= 1) {
                // Game update
                DataManager.currentGame.tick();
                deltaU--;

                // Update view on UI thread
                Platform.runLater(() -> ControllersManager.gameViewController.updateView(DataManager.currentGame));

                // Calculate remaining time until next update
                currentTime = System.nanoTime();
                deltaTime = currentTime - initialTime;
                deltaU += deltaTime / TIME_U;
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
}
