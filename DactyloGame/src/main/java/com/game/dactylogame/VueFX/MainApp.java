package com.game.dactylogame.VueFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/game/dactylogame/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("DactyloGame ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
       launch();
        //System.out.println(MainApp.class.getResource("/com/game/dactylogame/hello-view.fxml"));
    }
}