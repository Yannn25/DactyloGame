package com.game.dactylogame.Modele;

/**
 *
 */
public class GameMode extends AbstractModeClass {
    private int Vie = 10;
    private int Niveau = 1;
    private int CptMotsSucces; //compter les mots succes

    public int getVie() { return Vie; }
    public void setVie(int Vie){
        this.Vie = Vie;
    }
    public int getNiveau(){ return Niveau; }
    public void setNiveau(int Niveau) { this.Niveau = Niveau; }
    public int getCptMotsSucces(){return CptMotsSucces; }
    public void setCptMotsSucces(int CptMotsSucces){ this.CptMotsSucces = CptMotsSucces; }
}
