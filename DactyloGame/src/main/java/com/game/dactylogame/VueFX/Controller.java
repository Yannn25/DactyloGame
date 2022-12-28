package com.game.dactylogame.VueFX;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Window;

public class Controller {
    @FXML
    private Label welcomeText;
    private Window Game;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Bienvenue Sur le DactyloGame !");
        /*try {
            wait(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Game.centerOnScreen(); */
    }
}