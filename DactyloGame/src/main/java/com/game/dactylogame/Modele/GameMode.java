package com.game.dactylogame.Modele;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 */
public class GameMode extends AbstractModeClass {
    private int Vie;
    private int Niveau;
    private int cptMots;
    private int CptMotsSucces;//compter les mots succes

    private String currentWord;
    /**
     * Hashmap de mot qui peuvent redonner de la vie
     */
    protected HashMap<String,Boolean> listeMV;

    /**
     * Constructeur
     */
    public GameMode() {
        this.Vie = 10;
        this.Niveau = 1;
        this.cptMots = 0;
        this.CptMotsSucces = 0;
        this.listeMV = new HashMap<>();
    }

    /**
     * Getteur
     * @return > vie
     */
    public int getVie() { return Vie; }

    /**
     * Setteur
     * @param Vie > modifier la vie
     */
    public void setVie(int Vie){
        this.Vie = Vie;
    }

    /**
     * Getteur
     * @return > niveau
     */
    public int getNiveau(){ return Niveau; }

    /**
     * Setteur
     * @param Niveau > modifier le niveau
     */
    public void setNiveau(int Niveau) { this.Niveau = Niveau; }

    /**
     * Getteur
     * @return > le nombre de mot valide
     */
    public int getCptMotsSucces(){return CptMotsSucces; }

    /**
     * Setteur
     * @param CptMotsSucces > mot correctement taper
     */
    public void setCptMotsSucces(int CptMotsSucces){ this.CptMotsSucces = CptMotsSucces; }

    /**
     * Getteur
     * @return le mot courant
     */
    public String getCurrentWord() { return currentWord; }

    /**
     * Setteur
     * @param currentWord > mot courant
     */
    public void setCurrentWord(String currentWord) { this.currentWord = currentWord; }

    /**
     * Getteur
     * @return liste des Mot ajoutant de la vie
     */
    public HashMap getlisteMV() { return this.listeMV; }

    /**
     * Chargement des mots dans le mode Jeu
     */
    public void RemplirTamponJeu() {
        int i = 0;
        Scanner scanner = new Scanner(getClass().getResourceAsStream("/com/game/dactylogame/ENListeMots.txt"));
        while (scanner.hasNextLine()) {
            tampon.getAllWords().add(scanner.nextLine());
        }
        Random ran = new Random();
        while(i < 100) {
            tampon.getVisibleWords().add(tampon.getAllWords().get(ran.nextInt(0,tampon.getAllWords().size())));
            Boolean cle = (Math.random() <= 2.0) ? true : false; // 20 % de chance d'avoir un mot avec une vie
            listeMV.put(tampon.getVisibleWords().getLast(), cle);
            if(i < 15)
                tampon.getFile().add(tampon.getVisibleWords().getLast());
            i++;
        }
        scanner.close();
    }
}
