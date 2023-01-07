package com.game.dactylogame.VueFX;

import com.game.dactylogame.Modele.AbstractModeClass;
import com.game.dactylogame.Modele.NormalMode;
import com.game.dactylogame.Modele.Stats;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Text let;
    @FXML
    private Text infoZone; //a enlever
    private Stats stats;
    static double timeCount = 0.0; //temps restant. on aura pas besoin de le mettre a la tache finale.
    long startReg;
    long endReg;
    static int Chrono = 10; //1minute pour Taper le Max de Mot
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
       // Instant startInstant = Instant.ofEpochMilli(startTime);
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
                        timeCount+= 0.1 ;
                    } else {
                        timer.cancel();
                        ZoneText.setText("Ready to start Again");
                        Time.setText("- : -");
                        TextF.clear();
                        TextF.setDisable(true);//desactiver le TextF avec le temps ecoule
                        HomeButton.setVisible(true);
                        StartButton.setVisible(true);
                        ChronoOption.setVisible(true);
                        //afficher les stats a partrir d'ici
                        System.out.println("Temps ecoule !\nnb de c taper : "+game.getKeyPress()+" tmp ecouler : "+timeCount);
                        afficheStats(timeCount);
                    }
                } else {
                  //  System.out.println("-- " + timeCount + " ---");
                    if (timeCount <= 5.0 || !verifywin()) {
                        Time.setText("Temps écoule : " + String.valueOf(timeCount * 10) + "s");
                        if (timeCount == 1.0 || timeCount == 2.0 || timeCount == 3.0 || timeCount == 4.0 ||
                        timeCount == 1.1 || timeCount == 2.1 || timeCount == 3.1 || timeCount == 4.1) {
                            infoZone.setText("1min viens de s'écouler !");
                        }  else {
                            infoZone.setText("-");
                        }
                        if (timeCount == 4.9) {
                            infoZone.setText("Trop de temps écouler FIN DE JEU!!!");
                        }
                        //if(ZoneText.)
                        infoZone.setText("-");
                        timeCount += 0.1;
                    } else {
                        timer.cancel();
                        ZoneText.setText("Ready to start Again");
                        Time.setText("- : -");
                        TextF.clear();
                        TextF.setDisable(true);//desactiver le TextF avec le temps ecoule
                        HomeButton.setVisible(true);
                        StartButton.setVisible(true);
                        ChronoOption.setVisible(true);
                        //afficher les stats a partrir d'ici
                        System.out.println("Temps ecoule !");
                        afficheStats(timeCount);
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
            game.getTampon().getVisibleWords().removeFirst();
        }
    }

    private void TamponMaj() {
        String str = "";
        for (String s : game.getTampon().getFile()) {
            str += s + " ";
        }
        if(str.length() > 0)
            str.substring(0, str.length()-1);
        ZoneText.setText(str);
    }

    /**
     * Démarre une partie
     * @param event > Clique sur le bouton(ActionEvent)
     */
    @FXML
    public void OnStartButton(ActionEvent event) {
        startReg = System.currentTimeMillis();

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

            TextF.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    String c = event.getCharacter();
                    game.addAllKeypress(1);
                    if(c.charAt(0) != 8) { //gestion de 'delete' qui retire -1
                        game.addKeyPress(1);
                        endReg = System.currentTimeMillis();
                        game.getReg().add((endReg-startReg));
                        startReg = endReg;
                    } else {
                        game.addKeyPress(-1);
                    }
                    let.setText(c);
                    if (c.charAt(0) == 32) { //Code ASCII de l'espace = 32
                        //currentWord = TextF.getText().substring(0, TextF.getText().length() - 1);
                        game.getTampon().getFile().removeFirst();
                        if(!game.getTampon().getVisibleWords().isEmpty())
                            game.getTampon().getFile().addLast(game.getTampon().getVisibleWords().removeFirst());
                        TamponMaj();
                        //colorerChar(true);
                        curseur = 0;
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
     * @param time_Count > le temps pris par le player
     */
    @FXML
    private void afficheStats(double time_Count) {
        this.stats = new Stats(game.getKeyPress(),time_Count,game.getAllKeyPress(),game.getReg());
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
            pop.setHeaderText("Vitesse (MPM): "+stats.getVitesse()+"\nRégularité : "+stats.getRegularite()+"%\nPrécison : "+stats.getPrecision()+"%");
            pop.setHeight(400);pop.setWidth(400);
        });
        pop.showAndWait();
    }

    /**
     * @return Vrai si plus aucun mot contenu dans la file
     */
    private boolean verifywin(){
        return game.getTampon().getFile().isEmpty();
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