package com.game.dactylogame.VueFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 */
public class MainApp extends Application {

    /**
     * Start and load the first Page
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException >
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/game/dactylogame/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("DactyloGame");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Main
     * @param args >
     */
    public static void main(String[] args) {
       launch(args);
    }
}