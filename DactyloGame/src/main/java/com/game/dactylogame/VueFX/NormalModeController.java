package com.game.dactylogame.VueFX;

import com.game.dactylogame.Modele.NormalMode;
import com.game.dactylogame.Modele.Stats;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;

import java.io.IOException;

public class NormalModeController {
    private Stage stage;
    private Scene scene;
    private Stats stats;
    static int count = 60; //temps du jeu
    @FXML
    private TextField TextF;
    @FXML
    private Button StartButton;
    @FXML
    private Button HomeButton;
    @FXML
    private Text ZoneText;
    @FXML
    private Text Time;
    @FXML
    private Text Vie;
    @FXML
    private Text Niveau;

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
    private void addTime() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run () {
                if (count >= 0) {
                    Time.setText(String.valueOf(count) + "s");
                    count--;
                } else {
                    timer.cancel();
                    TextF.setDisable(true); //desactiver le TextF avec le temps ecoule
                    System.out.println("Temps écoulé !");
                }
            }
        } ;
        timer.schedule(task, 1000, 1000);
    }

    private void addVie(){
        stats = new Stats(1,1);
        Vie.setText(String.valueOf(stats.getVie()));
    }

    private void addNiveau(){
        Niveau.setText(String.valueOf(stats.getNiveau()));
    }

    @FXML
    private void OnStartButton(ActionEvent e) {
        addOnTextF();
        addTime();
        addVie();
        addNiveau();
        StartButton.setVisible(false);
        //HomeButton.setVisible(true);
    }

}
