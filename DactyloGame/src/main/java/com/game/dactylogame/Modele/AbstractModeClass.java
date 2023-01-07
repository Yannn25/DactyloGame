package com.game.dactylogame.Modele;
import java.io.IOException;
import java.util.*;

import org.fxmisc.richtext.*;


/**
 * L'interface nous servira a regrouper toutes les attributs et mthodes qui
 * sont commun a nos trois mode de Jeu
 */
public abstract class AbstractModeClass {

    private int KeyPress;
    private int AllKeyPress;
    private  int time;//A Voir
    private List<Long> reg; //(Régularité)//A voir Egalement
    private Tampon tampon;

    public AbstractModeClass() {
        this.KeyPress = 0;
        this.tampon = new Tampon();
        this.reg = new LinkedList<>();
    }




    public int getKeyPress() { return KeyPress; }
    public void addKeyPress(int keyPress) {
        KeyPress += keyPress;
    }
    public int getAllKeyPress() { return AllKeyPress; }
    public void addAllKeypress(int i) { this.AllKeyPress += i; }
    public List<Long> getReg() {
        return reg;
    }
    public Tampon getTampon() {
        return tampon;
    }

    /**
     *
     */
    public void RemplirTampon() {
        int i = 0;
        Scanner scanner = new Scanner(getClass().getResourceAsStream("/com/game/dactylogame/ENListeMots.txt"));
        while (scanner.hasNextLine()) {
            tampon.getAllWords().add(scanner.nextLine());
        }
        Random ran = new Random();
        int plage = ran.nextInt(22) + 17;
        System.out.println(plage);
        while(i < plage) {
            tampon.getVisibleWords().add(tampon.getAllWords().get(ran.nextInt(0,tampon.getAllWords().size())));
            if(i < 15)
                tampon.getFile().add(tampon.getVisibleWords().getLast());
            i++;
        }
        scanner.close();
    }

    /**
     *
     * @param str un texte
     * @return une Liste chaine contenant tous les mots compris dans str
     */
    public static LinkedList<String> parse(String str) {
        LinkedList<String> ret = new LinkedList<String>();
        int i = 0, debM = 0, finM = 0;
        while(i < str.length() && finM < str.length()) {
            if(str.charAt(finM) == ' '){
                ret.add(str.substring(debM, finM));
                finM++;
                debM = finM;
                i++;
            } else {
                finM++;
                i++;
            }
        }
        ret.add(str.substring(debM, finM));
        return ret;
    }
}
