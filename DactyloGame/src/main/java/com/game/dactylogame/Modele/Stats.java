package com.game.dactylogame.Modele;


/**
 * Classe ou l'on retrouvera toute les statistiques qui concerne un Mode de Jeu
 */
public class Stats {

    // Nos champs qui compose un Objet de type Stats

    private int Vitesse;
    private int Precision;
    private int Regularite;
    private int Vie = 10;
    private int Niveau = 1;
    private int CptMotsSucces; //compter les mots succes

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
    public int getVie() { return Vie; }
    public void setVie(int Vie){
        this.Vie = Vie;
    }
    public int getNiveau(){ return Niveau; }
    public void setNiveau(int Niveau) { this.Niveau = Niveau; }
    public int getCptMotsSucces(){return CptMotsSucces; }
    public void setCptMotsSucces(int CptMotsSucces){ this.CptMotsSucces = CptMotsSucces; }

    public Stats(int CharUtile, int chrono) {
        this.Vitesse = calculVitesse(CharUtile,chrono);
        this.Regularite = calculRegularite(CharUtile);
        this.Precision = calculPrecision(CharUtile);
        this.Vie = getVie();
        this.Niveau = getNiveau();
        this.CptMotsSucces = getCptMotsSucces();
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
