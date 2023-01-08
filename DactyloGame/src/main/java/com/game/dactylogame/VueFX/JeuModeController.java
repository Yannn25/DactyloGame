package com.game.dactylogame.VueFX;

 import com.game.dactylogame.Modele.*;
 import javafx.application.Platform;
 import javafx.event.ActionEvent;
 import javafx.event.EventHandler;
 import javafx.fxml.FXML;
 import javafx.scene.control.Alert;
 import javafx.scene.input.KeyEvent;
 import javafx.scene.text.Text;

 import java.util.*;

/**
 * Controller du mode Jeu
 */
public class JeuModeController  extends NormalModeController{

    static double timeCountUpF = 0.0;
    static double fValue = 5.70;
    @FXML
    private Text Vie;
    @FXML
    private Text Niveau;


    private GameMode game;

    /**
     * Ajout d'un mot apres f(n) seconde
     */
    private void addUnMot(){ //cas erreur a voir
        game.getTampon().getFile().removeFirst();
        if(game.getTampon().getFile().size() > 15) {
            verifWord(TextF.getText(),game.getTampon().getFile().getFirst());
            TextF.clear();
            if(!game.getTampon().getVisibleWords().isEmpty() )
                game.getTampon().getFile().addLast(game.getTampon().getVisibleWords().removeFirst());
        } else {
            TextF.clear();
            if(!game.getTampon().getVisibleWords().isEmpty())
                game.getTampon().getFile().addLast(game.getTampon().getVisibleWords().removeFirst());
        }
        TamponMaj();
    }


    /**
     * compter et ajouter le temps au jeu (Text Time)
     */
    protected void addTime() {
        Timer timer = new Timer();
        Timer timerUp = new Timer();

        TimerTask task = new TimerTask(){
            @Override
            public void run () {
                TextF.setFocusTraversable(true);
                if (game.getVie() > 0) {
                    Time.setText(String.valueOf(Math.round(timeCount * 10)) + "s");
                    timeCount+=0.1;
                } else {
                    timer.cancel();
                    ZoneText.setText("Ready to start Again");
                    Time.setText("- : -");
                    TextF.clear();
                    TextF.setDisable(true);
                    HomeButton.setVisible(true);
                    StartButton.setVisible(true);
                    afficheStats(timeCount);
                }
            }
        };
        timer.schedule(task, 0, 1000);
        timeCount = 0; fValue = 2.70;  timeCountUpF = 0.0;
    }

    /**
     * Calcul des secondes pour f(n)
     */
    private void addTimeUp() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run () {
                if(game.getVie() > 0){
                    timeCountUpF += 0.1;
                    if(timeCountUpF >= fValue ) {
                        addUnMot();
                        timeCountUpF = 0.0;
                    }
                } else {
                    timer.cancel();
                }
            }
        } ;
        timer.schedule(task, 0, 100);

    }


    /**
     * Notre fonction décroissante f(n)
     * @param level > le niveau
     * @return
     */
    private double fun(int level){ //a voir, le calcul n'est pas exact.
        fValue = Math.round(((7 * Math.pow(0.9, level))*10)/10.0);
        return fValue;
    }

    /**
     * change l'affichage de vie
     */
    private void setVie(){
        System.out.println(game.getVie());
        Vie.setText(String.valueOf(game.getVie()));
    }

    /**
     * change l'affichage du niveau
     */
    private void setNiveau(){
        Niveau.setText(String.valueOf(game.getNiveau()));
        infoZone.setText("LEVEL UP !!!");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        infoZone.setText(" ");
    }


    /**
     * Vérifie qu'un mot est correct taper et agit en fonction
     * @param cur > Mot taper
     * @param verif > Mot present dans la file
     */
    private void verifWord(String cur, String verif) {
        System.out.println("cur : "+cur+"\nverif : "+ verif +" test equals : "+cur.equals(verif));
        if(!cur.equals(verif)) {
            //Gestion d'un éventuel IndexOutOfBounds
            boolean flag = cur.length() < verif.length() ? true : false;
            int len = Math.abs(cur.length() - verif.length());
            game.setVie(game.getVie() - len);//On retire d'office les char qui sont en plus ou en moins
            if(len == 0)
                len = verif.length();
            else if(!flag)
                len = cur.length() - len;
            else
                len = verif.length() - len;
            for(int i = 0; i < len; i++) { //vérification de chaque char dans les bonnes limite
                if(cur.charAt(i) != verif.charAt(i))
                    System.out.println("une errrr detecter");
                    game.setVie(game.getVie()-1);
            }
            setNiveau();
            setVie();
            System.out.println(game.getVie());
        } else {
            if(game.getlisteMV().containsKey(cur))
                game.setVie(game.getVie() + 1);
            game.setCptMotsSucces(game.getCptMotsSucces() + 1);
            if (game.getCptMotsSucces() == 3) {
                LevelUp();
                game.RemplirTamponJeu();//On recharge 100 nouveaux mots
            }
            game.setCptMotsSucces(0);
        }
    }

    /**
     * Augmentation du Niveau
     */
    private void LevelUp() {
        game.setNiveau(game.getNiveau() + 1);
        setNiveau();
        fun(game.getNiveau());
    }

    /**
     *
     */
    protected void VisibleMaj() {
        for(int i = 0; i < 15; i++) {
            game.getTampon().getVisibleWords().removeFirst();
        }
    }

    /**
     *
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
     *
     * @param e > Clique sur le bouton(ActionEvent)
     */
    @FXML
    public void OnStartButton(ActionEvent e) {
        long start = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            this.game = new GameMode();
            addOnZoneTextInit();
            VisibleMaj();
            addTimeUp();
            addTime();
            setVie();
            setNiveau();
            StartButton.setVisible(false);
            StatsButton.setVisible(false);
            TextF.setDisable(false);
            focusTextF();
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
                    if(c.charAt(0) == 32){ //Code ASCII de l'espace = 32
                        game.setCurrentWord(TextF.getText().substring(0,TextF.getText().length()-1));
                        verifWord(game.getCurrentWord(),game.getTampon().getFile().getFirst());
                        game.getTampon().getFile().removeFirst();
                        TamponMaj();
                        if(game.getTampon().getFile().size() < 8) {
                            //game.getTampon().getFile().removeFirst();
                            if (!game.getTampon().getVisibleWords().isEmpty())
                                game.getTampon().getFile().addLast(game.getTampon().getVisibleWords().removeFirst());
                            TamponMaj();
                        }
                        TextF.clear();
                    }
                }
            });
        });
        thread.start();
    }

    /**
     *
     */
    protected void addOnZoneTextInit() {
        this.game = new GameMode();
        game.RemplirTamponJeu();
        String str = "";
        for (String s : game.getTampon().getFile()) {
            str += s + " ";
        }
        str.substring(0, str.length()-1);//L'espace de fin
        ZoneText.setText(str);
    }

    @FXML
    public void StatsButton(ActionEvent e) {
        Alert pop = new Alert(Alert.AlertType.INFORMATION);
        Platform.runLater(() -> {
            pop.setHeaderText("Temps de jeu : "+Math.round(timeCount*10)+"\nNiveau atteint : " +game.getNiveau()+"\nVitesse (MPM): "
                    +stats.getVitesse()+"\nRégularité : "+stats.getRegularite()+"%\nPrécison : "
                    +stats.getPrecision()+"%");
            pop.setHeight(400);pop.setWidth(400);
        });
        pop.showAndWait();
    }
    @FXML
    protected void afficheStats(double time_Count) {
        this.stats = new Stats(game.getKeyPress(),time_Count,game.getAllKeyPress(),game.getReg());
        StatsButton.setVisible(true);
    }

    /**
     * Focus sur le TextField
     */
    @FXML
    private void focusTextF() {
        StartButton.setOnAction(event -> TextF.requestFocus());
    }

}

