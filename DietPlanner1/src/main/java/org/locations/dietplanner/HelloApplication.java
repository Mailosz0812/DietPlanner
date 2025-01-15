package org.locations.dietplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("index.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1600,800 );
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Diet App");
        stage.setScene(scene);
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}