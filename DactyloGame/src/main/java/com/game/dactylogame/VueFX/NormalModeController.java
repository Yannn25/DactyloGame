package com.game.dactylogame.VueFX;

import com.game.dactylogame.Modele.AbstractModeClass;
import com.game.dactylogame.Modele.NormalMode;
import com.game.dactylogame.Modele.Stats;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;


import java.io.IOException;

public class NormalModeController extends AbstractModeClass{
    private Stage stage;
    private Scene scene;
    private Stats stats;
    static int timeCountDown = 20; //temps restant
    static int timeCountUpInt = 0; //temps passe int
    static double timeCountUpDouble = 0.0; //temps passe double
    static double cptWordAddTime = 2.70;
    private LinkedList<String> ListeMots;
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
    @FXML
    private Text test;
    @FXML
    private Text testTextZone;

    private int CptMots; //Compteur des mots

    private NormalMode game;

    public void run(){

    }
    //private Parent parent;
    @FXML
    private void onHomeButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/game/dactylogame/HomeMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }

    /*remarque : changement du nom de la fonction : addOnTextF -> addOnZoneTextInit
                 ce sont des mots initiaux quand on lance le jeu, on aura besoin d'ajouter plus de mots
                 en jouant. c'est la raison pour laquelle j'ai change le nom.
     */
    private void addOnZoneTextInit() {
       this.game = new NormalMode();
       game.RemplirTampon();
       String str = "";
       for (CptMots = 0; CptMots < 15; CptMots++) {
           str += game.getTampon().getAllWords().get(CptMots) + " ";
       }
       str.substring(0, str.length()-1);
       ZoneText.setText(str);
    }

    private void addUnMot(LinkedList<String> list){ //cas erreur a voir
        list.add(game.getTampon().getAllWords().get(CptMots));
        CptMots++;
        String str = ZoneText.getText() + " " + list.getLast();
        ZoneText.setText(str);
    }


    private void removeUnMot(LinkedList<String> list){
        list.removeFirst();
        String str = ZoneText.getText();
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 32) {
                str = str.substring(i + 1, str.length());
                break;
            }
        }
        ZoneText.setText(str);
    }

/*
* compter et ajouter le temps au jeu (Text Time)*/
    private void addTime() {

        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run () {
                if (timeCountDown >= 0) {
                    Time.setText(String.valueOf(timeCountDown) + "s");
                    timeCountDown--;
                    timeCountUpInt++;
                    //timeWord(timeCountUpInt);
                    System.out.println(cptWordAddTime);
                } else {
                    timer.cancel();
                    TextF.setDisable(true); //desactiver le TextF avec le temps ecoule
                    System.out.println("Temps écoulé !");
                }
            }
        } ;
        timer.schedule(task, 0, 1000);
    }

    /*
    * compter le temps double (format 0.0), verifier s'il est le temps d'ajouter un mot et l'ajouter.*/
    private void addTimeUp() {

        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run () {
               timeCountUpDouble += 0.1;
                testTextZone.setText(String.valueOf(Math.round(timeCountUpDouble*1000)/1000.0));
                Double str = Double.valueOf(testTextZone.getText());
                //testTextZone.setText(String.valueOf(timeCountUpDouble));
               if(str == cptWordAddTime) {
                   addUnMot(ListeMots);
                   timeWord(timeCountUpInt);
               }
            }
        } ;
        timer.schedule(task, 0, 100);
    }
    /*
    * calculer le temps a ajouter un mot et accumuler a cptWordAddTime */
    private double timeWord(int time){
        cptWordAddTime += Math.round(((3 * Math.pow(0.9, time))*10)/10.0);
        //cptWordAddTime += (3 * Math.pow(0.9, time));
        return cptWordAddTime;
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
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName);
            addOnZoneTextInit();
            ListeMots = parse(ZoneText.getText());
            addTime();
            addTimeUp();
            addVie();
            addNiveau();
            //timeToAddWord();
            StartButton.setVisible(false);
            //HomeButton.setVisible(false);
            long end = System.currentTimeMillis();
            TextF.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    String c = event.getCharacter();
                    test.setText(c);
                    if(c.charAt(0) == 32){ //Code ASCII de l'espace = 32
                        removeUnMot(ListeMots);
                        TextF.clear();
                    }
                }
            });
        });
        thread.setName("Thread 1");
        thread.start();
    }

}
