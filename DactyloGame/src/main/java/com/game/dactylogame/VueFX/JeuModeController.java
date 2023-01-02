package com.game.dactylogame.VueFX;

 import com.game.dactylogame.Modele.NormalMode;
 import javafx.fxml.Initializable;
 import com.game.dactylogame.Modele.AbstractModeClass;
 import com.game.dactylogame.Modele.MotsVies;
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
 import java.net.URL;
 import java.util.*;
 import java.io.IOException;

public class JeuModeController extends AbstractModeClass implements Initializable {


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
    private Text testTextZone; //a enlever

    private int cptMots; //Compteur des mots

    private String tampon;
    private String motEnleve;
    private boolean vieMotEnleve;
    private int i = 0;

    private AbstractModeClass game;

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
        for (cptMots = 0; cptMots < 15; cptMots++) {
            str += game.getTampon().getVisibleWords().get(cptMots) + " ";
        }
        str.substring(0, str.length()-1);
        ZoneText.setText(str);
    }

    private void addUnMot(){ //cas erreur a voir
        String mot = game.getTampon().getAllWords().get(cptMots);
        cptMots++;
        Double r = Math.random();
        boolean vie = (r <= 0.2) ? true : false;
        MotsVies mv = new MotsVies(mot, vie);
        listeMV.add(mv);
        String str = ZoneText.getText() + " " + listeMV.getLast().getMot();
        ZoneText.setText(str);
    }


    private void removeUnMot(){
        MotsVies mv = listeMV.pop();
        motEnleve = mv.getMot();
        vieMotEnleve = mv.getVie();
        System.out.println(vieMotEnleve);
        String str = ZoneText.getText();
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 32) {
                str = str.substring(i + 1, str.length());
                break;
            }
        }
        ZoneText.setText(str);
    }

    /**
     * compter et ajouter le temps au jeu (Text Time)
     */
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
     * compter le temps double (format 0.0), verifier s'il est le temps d'ajouter un mot et l'ajouter.
     */
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
                    addUnMot();
                    timeWord(timeCountUpInt);
                }
            }
        } ;
        timer.schedule(task, 0, 100);
    }
    /*
     * calculer le temps a ajouter un mot et accumuler a cptWordAddTime */
    private double timeWord(int time){ //a voir, le calcul n'est pas exact.
        cptWordAddTime += Math.round(((3 * Math.pow(0.9, time))*10)/10.0);
        //cptWordAddTime += (3 * Math.pow(0.9, time));
        return cptWordAddTime;
    }

    private void addVie(){
        Vie.setText(String.valueOf(stats.getVie()));
    }

    private void addNiveau(){
        Niveau.setText(String.valueOf(stats.getNiveau()));
    }

    /*
     * setListeMotsVies */
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

    private void colorerMots(){ // A FAIRE ! comment faire : sauvegarder position deb / position fin de mot
        //                           et appliquer la couleur avec richtextfx sur ces positions.
        String [] mots = arrMV();
        int i = 0;
        String str = null;
        int ZoneTextLen = ZoneText.getText().length();
        while(ZoneTextLen != i){
            if(ZoneText.getText().charAt(i) == 32){
                if(findMot(mots, str)){
                    // ZoneText.setStyle(0, 10, "-fx-font-weight: bold;");
                }

            }else{
                str += ZoneText.getText().charAt(i);
            }
        }
    }


    @FXML
    private void OnStartButton(ActionEvent e) {
        long start = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName);
            addOnZoneTextInit();
            listeMots = parse(ZoneText.getText());
            setListeMV();
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
                        tampon = TextF.getText().substring(0,TextF.getText().length()-1);
                        removeUnMot();
                        System.out.println(motEnleve);
                        if(!tampon.equals(motEnleve)) {
                            System.out.println(tampon);
                            stats.setVie(stats.getVie() - 1);
                            if(stats.getVie() == 0){
                                timeCountDown = 0; // fin du jeu
                            }
                            addVie();
                        }
                        if(tampon.equals(motEnleve) && vieMotEnleve == true){
                            stats.setVie(stats.getVie() + 1);
                            addVie();
                        }
                        TextF.clear();
                    }
                }
            });
        });
        thread.setName("Thread 1");
        thread.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { //a voir!
        ZoneText.setStyle("-fx-highlight-fill: #ADFF2F; -fx-highlight-text-fill: #B22222; -fx-font-size: 18px;");
    }
}

