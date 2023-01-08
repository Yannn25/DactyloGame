package com.game.dactylogame.Modele;
import java.io.IOException;
import java.util.*;



/**
 * L'interface nous servira a regrouper toutes les attributs et mthodes qui
 * sont commun a nos trois mode de Jeu
 */
public abstract class AbstractModeClass {

    /**
     * Le nombre de caractère utile
     */
    protected int KeyPress;
    /**
     * Le nombre de touche total(y compris les backspaces)
     */
    protected int AllKeyPress;
    /**
     * Liste contenant le temps a chaque char utile presser
      */
    protected List<Long> reg;
    /**
     * Tampon
     */
    protected Tampon tampon;

    /**
     * Constructeur
     */
    public AbstractModeClass() {
        this.KeyPress = 0;
        this.tampon = new Tampon();
        this.reg = new LinkedList<>();
    }


    /**
     * Getteur
     * @return >
     */
    public int getKeyPress() { return KeyPress; }

    /**
     * Incrémentation des Key Press
     * @param keyPress > touche presser(char utile)
     */
    public void addKeyPress(int keyPress) {
        KeyPress += keyPress;
    }

    /**
     * Getteur
     * @return >
     */
    public int getAllKeyPress() { return AllKeyPress; }

    /**
     * Incrementation de KeyPress
     * @param i > int
     */
    public void addAllKeypress(int i) { this.AllKeyPress += i; }

    /**
     * Getteur
     * @return >
     */
    public List<Long> getReg() {
        return reg;
    }

    /**
     * Getteur
     * @return >
     */
    public Tampon getTampon() {
        return tampon;
    }

    /**
     * Chargement des mots
     */
    public void RemplirTampon() {
        int i = 0;
        Scanner scanner = new Scanner(getClass().getResourceAsStream("/com/game/dactylogame/ENListeMots.txt"));
        while (scanner.hasNextLine()) {
            tampon.getAllWords().add(scanner.nextLine());
        }
        Random ran = new Random();
        int plage = ran.nextInt(25 - 17 + 1) + 17;
        while(i < plage) {
            tampon.getVisibleWords().add(tampon.getAllWords().get(ran.nextInt(1,tampon.getAllWords().size())));
            if(i < 15)
                tampon.getFile().add(tampon.getVisibleWords().getLast());
            i++;
        }
        scanner.close();
    }


}
