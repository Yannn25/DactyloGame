package com.game.dactylogame.VueFX;

import com.game.dactylogame.Modele.AbstractModeClass;
import com.game.dactylogame.Modele.NormalMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    private Stage stage;
    private Scene scene;
    private AbstractModeClass game;
    private TextField TextF;
    @FXML
    private void onNormalButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/game/dactylogame/NormalMode.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }
}
