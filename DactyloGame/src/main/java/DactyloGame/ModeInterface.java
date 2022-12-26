package DactyloGame;

import java.util.LinkedList;
import java.util.Timer;



/**
 * L'interface nous servira a regrouper toutes les attributs et méthodes qui 
 * sont commun a nos trois mode de Jeu
 */
public abstract class ModeInterface {
   
    private int KeyPress;
    private  Timer time;
    private  Timer reg; //(Régularité)
    
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


