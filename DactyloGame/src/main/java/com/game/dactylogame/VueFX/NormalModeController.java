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


/**
 * Controller pour une partie en mode Normal
 */
public class NormalModeController  {
    /**
     * Stage
     */
    protected Stage stage = new Stage();
    /**
     * Scene
     */
    protected Scene scene;
    /**
     * Checkbox pour l'option Chrono
     */
    @FXML
    protected CheckBox ChronoOption = new CheckBox();
    /**
     * Zone ounle texte est taper
     */
    @FXML
    protected TextField TextF;
    /**
     * Bouton qui ramener au menu principal
     */
    @FXML
    protected Button HomeButton;
    /**
     * Bouton qui débute la partie
     */
    @FXML
    protected Button StartButton;
    /**
     * Bouton qui affiche les stats
     */
    @FXML
    protected Button StatsButton;
    /**
     * Temps
     */
    @FXML
    protected Text Time;
    /**
     * Zone de text
     */
    @FXML
    protected Text ZoneText;
    /**
     * afficher du char taper
     */
    @FXML
    protected Text let;
    /**
     * Affichage d'informations sur la partie en cours
     */
    @FXML
    protected Text infoZone;
    /**
     * Stats afficher en fin de partie
     */
    protected Stats stats;
    /**
     * temps écouler
     */
    protected double timeCount = 0.0; //temps écoulé
    long startReg;
    long endReg;
    static int Chrono = 30; //30s pour Taper le Max de Mot
    /**
     * Game
     */
    protected AbstractModeClass game;

    /**
     * Retour sur la page Home
     * @param event >
     * @throws IOException >
     */
    @FXML
    protected void onHomeButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/game/dactylogame/HomeMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       // if(Thread.currentThread().isAlive()) Thread.interrupted();
        scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * lance le chronomètre
     */
    protected void addTime() {
        boolean flag = false;
        if(ChronoOption.isSelected()) flag = true;
        Timer timer = new Timer();
        boolean finalFlag = flag;
        TimerTask task = new TimerTask(){
            @Override
            public void run () {
                if (finalFlag) {
                    if(Chrono > 0 && !verifywin()) {
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
                        TextF.setDisable(true);
                        HomeButton.setVisible(true);
                        StartButton.setVisible(true);
                        ChronoOption.setVisible(true);
                        afficheStats(timeCount);
                    }
                } else {
                    if (timeCount <= 5.0 && !verifywin()) {
                        Time.setText("Temps écoule : " + String.valueOf(Math.round(timeCount * 10)) + "s");
                        if (timeCount == 1.0 || timeCount == 2.0 || timeCount == 3.0 || timeCount == 4.0 ||
                        timeCount == 1.1 || timeCount == 2.1 || timeCount == 3.1 || timeCount == 4.1) {
                            infoZone.setText("1min viens de s'écouler !");
                        }  else {
                            infoZone.setText("-");
                        }
                        if (timeCount == 4.9) {
                            infoZone.setText("Trop de temps écouler FIN DE JEU!!!");
                        }
                        infoZone.setText("-");
                        timeCount += 0.1;
                    } else {
                        timer.cancel();
                        ZoneText.setText("Ready to start Again");
                        Time.setText("- : -");
                        TextF.setDisable(true);//desactiver le TextF avec le temps ecoule
                        HomeButton.setVisible(true);
                        StartButton.setVisible(true);
                        ChronoOption.setVisible(true);
                        afficheStats(timeCount);
                    }
                }
            }
        };
        timer.schedule(task, 0, 1000);
        timeCount = 0;Chrono = 30;
    }



    /**
     * Affiche le texte compris dans le Tampon sur la Zone de Texte
     */
    protected void addOnZoneTextInit() {
        this.game = new NormalMode();
        game.RemplirTampon();
        String str = "";
        for (String s : game.getTampon().getFile()) {
            str += s + " ";
        }
        str.substring(0, str.length()-1);//L'espace de fin
        ZoneText.setText(str);
    }

    /**
     * Enleve les 15 1er element 'Visible' du tampon
     */
    protected void VisibleMaj() {
        for(int i = 0; i < 15; i++) {
            game.getTampon().getVisibleWords().removeFirst();
        }
    }

    /**
     * Met a jour la zone de texte
     */
    protected void TamponMaj() {
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
    protected void OnStartButton(ActionEvent event) {
        startReg = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            this.game = new NormalMode();
            addOnZoneTextInit();
            VisibleMaj();
            addTime();
            StartButton.setVisible(false);
            HomeButton.setVisible(false);
            ChronoOption.setVisible(false);
            TextF.clear();
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
                        if(!game.getTampon().getFile().isEmpty()) {
                            game.getTampon().getFile().removeFirst();
                            if (!game.getTampon().getVisibleWords().isEmpty()) {
                                game.getTampon().getFile().addLast(game.getTampon().getVisibleWords().removeFirst());
                            }
                        }
                        TamponMaj();
                        TextF.clear();
                    }
                }

            });
        });
        thread.start();
    }


    /**
     *  Méthode qui nous permet d'instancier notre attribut stats,
     *  puis va rendre possible la visualitation des stats.
     * @param time_Count > le temps pris par le player
     */
    @FXML
    protected void afficheStats(double time_Count) {
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
     * Vérification de la condition de victoire
     * @return Vrai si plus aucun mot contenu dans la file
     */
    private boolean verifywin(){
        return game.getTampon().getFile().isEmpty();
    }

}
