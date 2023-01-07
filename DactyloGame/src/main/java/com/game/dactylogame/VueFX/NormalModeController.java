package com.game.dactylogame.VueFX;

import com.almasb.fxgl.core.collection.grid.CellGenerator;
import com.game.dactylogame.Modele.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.io.IOException;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


public class NormalModeController  {

    private Stage stage = new Stage();
    private Scene scene;
    @FXML
    private CheckBox ChronoOption = new CheckBox();
    @FXML
    private TextField TextF;
    @FXML
    private Button HomeButton;
    @FXML
    private Button StartButton;
    @FXML
    private Button StatsButton;
    @FXML
    private Text Time;
    @FXML
    private Text ZoneText;
    @FXML
    private Text test;
    @FXML
    private int cptMots; //Compteur des mots
    @FXML
    private Text infoZone; //a enlever
    private Stats stats;
    static int timeCount; //temps restant. on aura pas besoin de le mettre a la tache finale.
    static int Chrono = 10; //1minute pour Taper le Max de Mot
    private String currentWord;
    private String motEnleve;
    private AbstractModeClass game;
    private int curseur = 0;

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void onHomeButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/game/dactylogame/HomeMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        if(Thread.currentThread().isAlive()) Thread.interrupted();
        scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     */
    private void addTime() {
        boolean flag = false;
        if(ChronoOption.isSelected()) flag = true;
        Timer timer = new Timer();
        boolean finalFlag = flag;
        TimerTask task = new TimerTask(){
            @Override
            public void run () {
                if (finalFlag) {
                   // System.out.println("-- "+ Chrono+" ---");
                    if(Chrono > 0) {
                        Time.setText("Temps restant : "+String.valueOf(Chrono) + "s");
                        if(Chrono == 5 || Chrono == 4) {
                            infoZone.setText("Il ne vous reste que 5s !");
                        } else {
                            infoZone.setText("-");
                        }
                        Chrono--;
                        timeCount++;
                    } else {
                        timer.cancel();
                        ZoneText.setText("Ready to start Again");
                        TextF.clear();
                        TextF.setDisable(true);//desactiver le TextF avec le temps ecoule
                        HomeButton.setVisible(true);
                        StartButton.setVisible(true);
                        ChronoOption.setVisible(true);
                        //afficher les stats a partrir d'ici
                        System.out.println("Temps ecoule !\nnb de c taper : "+game.getKeyPress()+" tmp ecouler : "+timeCount);
                        afficheStats(game.getKeyPress(),timeCount);
                    }
                } else {
                  //  System.out.println("-- " + timeCount + " ---");
                    if (timeCount <= 300) {
                        Time.setText("Temps écoule : " + String.valueOf(timeCount) + "s");
                        if (timeCount == 60 || timeCount == 120 || timeCount == 180 || timeCount == 240 ||
                        timeCount == 61 || timeCount == 121 || timeCount == 181 || timeCount == 241) {
                            infoZone.setText("1min viens de s'écouler !");
                        }  else {
                            infoZone.setText("-");
                        }
                        if (timeCount == 299) {
                            infoZone.setText("Trop de temps écouler FIN DE JEU!!!");
                        }
                        //if(ZoneText.)
                        infoZone.setText("-");
                        timeCount++;
                    } else {
                        timer.cancel();
                        ZoneText.setText("Ready to start Again");
                        TextF.clear();
                        TextF.setDisable(true);//desactiver le TextF avec le temps ecoule
                        HomeButton.setVisible(true);
                        StartButton.setVisible(true);
                        ChronoOption.setVisible(true);
                        //afficher les stats a partrir d'ici
                        System.out.println("Temps ecoule !");
                        afficheStats(game.getKeyPress(),timeCount);
                    }
                }
            }
        };
        timer.schedule(task, 0, 1000);
        timeCount = 0;Chrono = 10;
    }



    /**
     *
     */
    private void addOnZoneTextInit() {
        this.game = new NormalMode();
        game.RemplirTampon();
        String str = "";
        for (String s : game.getTampon().getFile()) {
            str += s + " ";
        }
        str.substring(0, str.length()-1);//L'espace de fin
        ZoneText.setText(str);
    }

    private void VisibleMaj() {
        for(int i = 0; i < 15; i++) {
            System.out.println(game.getTampon().getVisibleWords().removeFirst());
        }
    }

    private void TamponMaj() {
        String str = "";
        for (String s : game.getTampon().getFile()) {
            str += s + " ";
        }
        if(str.length() > 0)
            str.substring(0, str.length()-1);
        else
            //win donc methode a faire
        ZoneText.setText(str);
    }

    /**
     * Démarre une partie
     * @param event > Clique sur le bouton(ActionEvent)
     */
    @FXML
    public void OnStartButton(ActionEvent event) {
        //long start = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            this.game = new NormalMode();
            // initFlow();
            addOnZoneTextInit();
            VisibleMaj();
            addTime();
            StartButton.setVisible(false);
            HomeButton.setVisible(false);
            ChronoOption.setVisible(false);
            TextF.setDisable(false);
            StatsButton.setVisible(false);

            // long end = System.currentTimeMillis();
            TextF.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    String c = event.getCharacter();
                    if(c.charAt(0) != 127)//gestion de 'delete' qui retire -1
                        game.addKeyPress(1);
                    else
                        game.addKeyPress(-1);
                    test.setText(c);
                    if (c.charAt(0) == 32) { //Code ASCII de l'espace = 32
                        currentWord = TextF.getText().substring(0, TextF.getText().length() - 1);
                        game.getTampon().getFile().removeFirst();
                        if(!game.getTampon().getVisibleWords().isEmpty())
                            game.getTampon().getFile().addLast(game.getTampon().getVisibleWords().removeFirst());
                        TamponMaj();
                        //colorerChar(true);
                        curseur = 0;
                        //System.out.println(motEnleve);
                        TextF.clear();
                    }
                    /*if(c.equals(game.getTampon().getVisibleWords().get(0).charAt(curseur)))
                        colorerChar(true);
                    else
                        colorerChar(false);*/
                    curseur++;
                    //System.out.println(curseur);
                }

            });
        });
        thread.start();
    }

    /**
     *
     */
    private void initFlow() {

    }

    /**
     *  Indication graphique sur la position courante dans le text,
     *  ainsi la coloration de tous les chars déja taper
     * @param etat > Vrai si le caractère taper correspond à celui
     *             qui se trouve dans le texte, faux sinon
     */
    @FXML
    private void colorerChar(boolean etat){

    }


    /**
     *  Méthode qui nous permet d'instancier notre attribut stats,
     *  puis va rendre possible la visualitation des stats.
     * @param key_Press > le nombre char utile
     * @param time_Count > le temps pris par le player
     */
    @FXML
    private void afficheStats(int key_Press, int time_Count) {
        this.stats = new Stats(key_Press,time_Count);
        StatsButton.setVisible(true);
    }

    /**
     *  Affiche les Stats dans une fenetre d'alerte
     * @param e > Clique sur le bouton(ActionEvent)
     */
    @FXML
    public void StatsButton(ActionEvent e) {
        Alert pop = new Alert(Alert.AlertType.INFORMATION);
        Platform.runLater(() -> {
            pop.setHeaderText("Vitesse (MPM): "+stats.getVitesse()+"\nRégularité : "+stats.getRegularite()+"\nPrécison : "+stats.getPrecision());
            pop.setHeight(400);pop.setWidth(400);
        });
        pop.showAndWait();
    }

  // A gérer la fin du thread sur une fermeture de la fenetre

}
// -------------------------  PISTE POUR LA COLORATION DES MOTS -----------------------------------
/**
 * // Create a text field to display the current word
 * TextField currentWordField = new TextField();
 * currentWordField.setEditable(false);
 *
 * // Create a text area to display the typed text
 * TextArea typedTextArea = new TextArea();
 * typedTextArea.setEditable(false);
 *
 * // Set up a listener to update the current word field and highlight errors in the typed text
 * typedTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
 *     // Update the current word field
 *     currentWordField.setText(getCurrentWord(newValue));
 *
 *     // Highlight errors in the typed text
 *     highlightErrors(typedTextArea, newValue);
 * });
 *
 * // Define a method to extract the current word from the typed text
 * private String getCurrentWord(String typedText) {
 *     // Split the typed text into words
 *     String[] words = typedText.split("\\s+");
 *     // Return the last word, or an empty string if no words have been typed
 *     return words.length > 0 ? words[words.length - 1] : "";
 * }
 *
 * // Define a method to highlight errors in the typed text
 * private void highlightErrors(TextArea textArea, String typedText) {
 *     // Split the typed text into words
 *     String[] words = typedText.split("\\s+");
 *
 *     // Check each word for errors
 *     for (int i = 0; i < words.length; i++) {
 *         String word = words[i];
 *         if (wordContainsError(word)) {
 *             // Calculate the start and end indices of the error word in the text area
 *             int start = getWordStartIndex(textArea.getText(), i);
 *             int end = getWordEndIndex(textArea.getText(), i);
 *
 *             // Highlight the error word in the text area
 *             textArea.setStyle(start, end, "-fx-background-color: yellow");
 *         }
 *     }
 * }
 */