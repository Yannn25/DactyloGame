package com.game.dactylogame.Modele;

import java.util.LinkedList;

/**
 * Implémentation de la File
 */
public class Tampon {
    private LinkedList<String> AllWords;
    private LinkedList<String> VisibleWords;

    private LinkedList<String> File;

    /**
     * Constructeur
     */
    public Tampon() {
        this.AllWords = new LinkedList<>();
        this.VisibleWords = new LinkedList<>();
        this.File = new LinkedList<>();
    }

    /**
     * Getteur
     * @return this.AllWords
     */
    public LinkedList<String> getAllWords() {
        return AllWords;
    }

    /**
     * Getteur
     * @return this.VisibleWords
     */
    public LinkedList<String> getVisibleWords() {
        return VisibleWords;
    }

    /**
     * Getteur
     * @return  this.File
     */
    public LinkedList<String> getFile() { return File; }

}
