package com.game.dactylogame.Modele;

import java.util.LinkedList;

/**
 *
 */
public class Tampon {
    private LinkedList<String> AllWords;
    private LinkedList<String> VisibleWords;

    public Tampon() {
        this.AllWords = new LinkedList<>();
        this.VisibleWords = new LinkedList<>();
    }

    public LinkedList<String> getAllWords() {
        return AllWords;
    }
    public void setAllWords(LinkedList<String> allWords) {
        AllWords = allWords;
    }
    public LinkedList<String> getVisibleWords() {
        return VisibleWords;
    }
    public void setVisibleWords(LinkedList<String> visibleWords) {
        VisibleWords = visibleWords;
    }
}
