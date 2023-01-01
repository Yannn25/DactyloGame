package com.game.dactylogame.Modele;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Timer;
import org.fxmisc.richtext.*;


/**
 * L'interface nous servira a regrouper toutes les attributs et mthodes qui
 * sont commun a nos trois mode de Jeu
 */
public abstract class AbstractModeClass {

    private int KeyPress;
    private  Timer time;
    private  Timer reg; //(Régularité)
    private Tampon tampon;

    public AbstractModeClass() {
        this.tampon = new Tampon();
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

    public int getKeyPress() {
        return KeyPress;
    }
    public void setKeyPress(int keyPress) {
        KeyPress = keyPress;
    }
    public Timer getTime() {
        return time;
    }
    public void setTime(Timer time) {
        this.time = time;
    }
    public Timer getReg() {
        return reg;
    }
    public void setReg(Timer reg) {
        this.reg = reg;
    }
    public Tampon getTampon() {
        return tampon;
    }

    public void RemplirTampon() {
        int i = 0;
        Scanner scanner = new Scanner(getClass().getResourceAsStream("/com/game/dactylogame/ENListeMots.txt"));
        while (scanner.hasNextLine() && i < 32 ) {
            tampon.getAllWords().add(scanner.nextLine());
            i++;
        }
        scanner.close();
    }
}
