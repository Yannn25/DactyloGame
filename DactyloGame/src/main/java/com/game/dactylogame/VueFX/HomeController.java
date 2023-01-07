package com.game.dactylogame.VueFX;

import com.game.dactylogame.Modele.NormalMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.io.IOException;

/**
 * Controller qui affiche les options de mode de Jeu
 */
public class HomeController {
    private Stage stage;
    private Scene scene;

    @FXML
    private void onNormalButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/game/dactylogame/NormalMode.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();

        //On cache dès le départ notre bouton de Stats
        Button button = (Button) scene.lookup("#StatsButton");
        button.setVisible(false);
    }

    @FXML
    private void onJeuModeButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/game/dactylogame/JeuMode.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }
}
