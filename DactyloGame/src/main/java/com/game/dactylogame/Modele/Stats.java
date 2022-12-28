package com.game.dactylogame.Modele;


/**
 * Classe ou l'on retrouvera toute les statistiques qui concerne un Mode de Jeu
 */
public class Stats {

    // Nos champs qui compose un Objet de type Stats

    private int Vitesse;
    private int Precision;
    private int Regularite;

    // Getteurs et setteurs de notre classe

    public int getVitesse() {
        return Vitesse;
    }
    public void setVitesse(int Vitesse) {
        this.Vitesse = Vitesse;
    }
    public int getPrecision() {
        return Precision;
    }
    public void setPrecision(int Precision) {
        this.Precision = Precision;
    }
    public int getRegularite() {
        return Regularite;
    }
    public void setRegularite(int Regularite) {
        this.Regularite = Regularite;
    }


    public Stats(int CharUtile, int chrono) {
        this.Vitesse = calculVitesse(CharUtile,chrono);
        this.Regularite = calculRegularite(CharUtile);
        this.Precision = calculPrecision(CharUtile);
    }

    /**
     *
     * @param CharUtile
     * @param chrono
     * @return
     */
    private int calculVitesse(int CharUtile, int chrono) {
        return (CharUtile/chrono) / 5;
    }

    /**
     *
     * @param CharUtile
     * @return
     */
    private int calculRegularite(int CharUtile) {
        return 0;
    }

    /**
     *
     * @param CharUtile
     * @return
     */
    private int calculPrecision(int CharUtile) {
        return 0;
    }




}
