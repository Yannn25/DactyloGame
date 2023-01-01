package com.game.dactylogame.VueFX;

import com.game.dactylogame.Modele.NormalMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class NormalModeController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField TextF;
    @FXML
    private Button StartButton;
    @FXML
    private Button HomeButton;
    @FXML
    private Text ZoneText;
    private NormalMode game;
    //private Parent parent;
    @FXML
    private void onHomeButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/game/dactylogame/HomeMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }

    private void addOnTextF() {
       this.game = new NormalMode();
       game.RemplirTampon();
       String str = "";
       for (int i = 0; i < 15; i++) {
            str += game.getTampon().getAllWords().get(i) + " ";
       }
       ZoneText.setText(str);
    }

    @FXML
    private void OnStartButton(ActionEvent e) {
        addOnTextF();
        StartButton.setVisible(false);
        HomeButton.setVisible(false);
    }

}
