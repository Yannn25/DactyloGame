package com.game.dactylogame.Modele;

public class MotsVies {
    private String mot;
    private boolean vie;

    public MotsVies(String mot, boolean vie){
        this.mot = mot;
        this.vie = vie;
    }

    public String getMot(){ return mot; }

    public boolean getVie(){return vie;}

    public void setMot(String mot){ this.mot = mot;}

    public void setVie(boolean vie) {this.vie = vie;}

}
