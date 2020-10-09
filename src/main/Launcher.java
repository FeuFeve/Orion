package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/game_view.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Writer writer = new FileWriter("config/commands.json");

//        TestObject testObject = new TestObject(2, 5);

//        gson.toJson(testObject, writer);

//        writer.flush();
//        writer.close();

//        TestObject testObject = gson.fromJson(new FileReader("test.json"), TestObject.class);
//        System.out.println(testObject);

        CommandManager.init();
        System.out.println("Done");

        launch(args);
    }
}
