import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class contains only the main method. This runs the HangmanApplication
 * upon start
 *
 * @author Steven Sommer
 * @version 1.0.0 November 2017
 */
public class MyHangmanApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root =
                    FXMLLoader.load(getClass().getResource("MyHangmanView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Hangman");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);
            primaryStage.setMaxWidth(800);
            primaryStage.setMaxHeight(900);
        } catch (IOException e) {
            System.out.println("Could not locate FXML file");
            e.printStackTrace();
        }
    }
}
