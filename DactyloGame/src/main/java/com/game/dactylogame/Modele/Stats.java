package com.game.dactylogame.Modele;


import java.util.List;

/**
 * Classe ou l'on retrouvera toute les statistiques qui concerne un Mode de Jeu
 */
public class Stats {

    // Nos champs qui compose un Objet de type Stats

    private int Vitesse;
    private double Precision;
    private double Regularite;


    // Getteurs et setteurs de notre classe

    /**
     * Getteur
     * @return  la vitesse
     */
    public int getVitesse() { return Vitesse; }

    /**
     * Getteur
     * @return  La précision
     */
    public double getPrecision() { return Precision; }

    /**
     * La régularité
     * @return  la régularité entre chaque frappe saisie
     */
    public double getRegularite() { return Regularite; }

    /**
     * Constructeur
     * @param CharUtile > caractere utile
     * @param chrono > le temps en minute
     * @param AllKeyPress > nombre total de mot taper
     * @param ListReg > Liste contenant le temp de chaque frappe de clavier
     */
    public Stats(int CharUtile, double chrono, int AllKeyPress, List<Long> ListReg) {
        this.Vitesse = calculVitesse(CharUtile,chrono);
        this.Regularite = calculRegularite(ListReg);
        this.Precision = calculPrecision(CharUtile,AllKeyPress);
    }

    /**
     * Calcul de la vitesse (mot par minute)
     * @param CharUtile > caractères utiles
     * @param chrono > temps en minute
     * @return  le nombre de caractères utiles, divisé par le temps en minute, divisé encore
     * par 5 (ici, on considère par convention qu’un mot fait en moyenne 5 caractères).
     */
    private int calculVitesse(int CharUtile, double chrono) {
        return (int) ( (CharUtile/5) / (chrono * 60));
    }

    /**
     * calcul de la régularité
     * @param Reg > Liste contenant le temp de chaque frappe de clavier
     * @return  l’écart-type de la durée entre 2 caractères utiles consécutifs.
     */
    private double calculRegularite(List<Long> Reg ) {
        if(Reg.isEmpty())
            return 0;
        long sum = 0;
        double sumOfSquares = 0;
        for (long x : Reg) {
            sum += x;
        }
        double moy = (double) sum / Reg.size();
        for ( long x: Reg ) {
            double diff = Math.abs(x - moy);
            sumOfSquares += diff * diff;
        }

        double variance = sumOfSquares / Reg.size();
        double ecart = Math.sqrt(variance);
        //System.out.println("SOMME des REG : "+sum+"\nMOY : "+moy+"\nSOMME au carré : "+sumOfSquares+
            //    "\nVariances : "+variance+"\nECART : "+ ecart+"\nECART int : "+(int)ecart );
        return  Math.round(ecart);
    }

    /**
     * calcul de la précision
     * @param CharUtile > caractères utiles
     * @return  le nombre de caractères utiles divisé par le nombre d’appuis de touche (×100)
     */
    private double calculPrecision(int CharUtile,int All_Key_Press) {
        double copcu = (double) CharUtile * 1.0; double copakp =(double) All_Key_Press * 1.0;
        return Math.round((copcu/copakp) * 100);
    }




}
