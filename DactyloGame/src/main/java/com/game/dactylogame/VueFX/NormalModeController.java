package com.game.dactylogame.VueFX;

import com.game.dactylogame.Modele.AbstractModeClass;
import com.game.dactylogame.Modele.MotsVies;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class NormalModeController {

    private Stage stage;
    private Scene scene;
    private Stats stats = new Stats(1,1);;
    static int timeCountDown = 20; //temps restant. on aura pas besoin de le mettre a la tache finale.
    static int timeCountUpInt = 0; //temps passe int
    static double timeCountUpDouble = 0.0; //temps passe double
    static double cptWordAddTime = 2.70;
    private LinkedList<String> listeMots;

    private LinkedList<MotsVies> listeMV;
    @FXML
    private TextField TextF;
    @FXML
    private Button StartButton;
    @FXML
    private Text Time;
    @FXML
    private Text ZoneText;
    @FXML
    private Text test;
    @FXML
    private int cptMots; //Compteur des mots

    private String tampon;
    private String motEnleve;
    private AbstractModeClass game;

    @FXML
    private void onHomeButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/game/dactylogame/HomeMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }

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

    /**
     *
     */
    private void addOnZoneTextInit() {
        this.game = new NormalMode();
        game.RemplirTampon();
        String str = "";
        for (cptMots = 0; cptMots < 15; cptMots++) {
            str += game.getTampon().getVisibleWords().get(cptMots) + " ";
        }
        str.substring(0, str.length()-1);
        ZoneText.setText(str);
    }

    /**
     *
     */
    private void addUnMot(){ //cas erreur a voir
        String mot = game.getTampon().getAllWords().get(cptMots);
        cptMots++;
        Double r = Math.random();
        boolean vie = (r <= 0.2) ? true : false;
        MotsVies mv = new MotsVies(mot, vie);
        listeMV.add(mv);
        String str = ZoneText.getText() + " " + listeMV.getLast().getMot();
        ZoneText.setAccessibleText(str);
    }
    private void removeUnMot(){
        MotsVies mv = listeMV.pop();
        motEnleve = mv.getMot();
        String str = ZoneText.getText();
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 32) {
                str = str.substring(i + 1, str.length());
                break;
            }
        }
        ZoneText.setAccessibleText(str);
    }
    /**
     *
     */
    private void setListeMV(){
        listeMV = new LinkedList<>();
        while(!listeMots.isEmpty()) {
            double r = Math.random();
            boolean vie = (r <= 0.2) ? true : false; // 20 % de chance d'avoir un mot avec une vie
            MotsVies mv = new MotsVies(listeMots.pop(), vie);
            listeMV.add(mv);
            //System.out.println(listeMV.pop().getMot());
        }
    }

    /**
     *
     * @return
     */
    private String[] arrMV(){
        String[] arr = new String[30];
        int i = 0;
        int j = 0;
        while(listeMV.size() != i){
            if(listeMV.get(i).getVie()) {
                arr[j] = listeMV.get(i).getMot();
                j++;
            }
            i++;
        }
        return arr;
    }

    /**
     *
     * @param mots
     * @param s
     * @return
     */
    private boolean findMot(String [] mots, String s){
        boolean res = false;
        for(int i = 0; i < mots.length; i++){
            if(mots[i] == null)
                break;
            if(mots[i].equals(s))
                res = true;
        }
        return res;
    }

    /**
     *
     * @param event
     */
    public void OnStartButton(ActionEvent event) {
        long start = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName);
            addOnZoneTextInit();
            listeMots = game.parse(ZoneText.getText());
            addTime();
            setListeMV();
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
                        tampon = TextF.getText().substring(0,TextF.getText().length()-1);
                       // colorerMots();
                        removeUnMot();
                        System.out.println(motEnleve);
                        TextF.clear();
                    }
                }
            });
        });
        thread.start();
        //if (thread.isAlive()) {
           // thread.stop();
       // }
    }

    @FXML
    private void colorerMots(){

       // ZoneText.setStyle(0,10, Collections.singleton("-fx-highlight-fill: red; -fx-highlight-text-fill: firebrick; -fx-font-size: 20px;"));


    }
}