/*
@author Sieben Veldeman
 */

package timetable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Timetable");
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
            launch(args);
        }

}
