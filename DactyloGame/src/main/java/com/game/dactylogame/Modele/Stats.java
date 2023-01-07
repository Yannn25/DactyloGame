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

    public int getVitesse() {
        return Vitesse;
    }
    public double getPrecision() {
        return Precision;
    }
    public double getRegularite() {
        return Regularite;
    }


    public Stats(int CharUtile, double chrono, int AllKeyPress, List<Long> ListReg) {
        this.Vitesse = calculVitesse(CharUtile,chrono);
        this.Regularite = calculRegularite(CharUtile,ListReg);
        this.Precision = calculPrecision(CharUtile,AllKeyPress);
    }

    /**
     *
     * @param CharUtile
     * @param chrono
     * @return
     */
    private int calculVitesse(int CharUtile, double chrono) {
        return (int) ( (CharUtile/5) / chrono);
    }

    /**
     *
     * @param CharUtile
     * @return
     */
    private double calculRegularite(int CharUtile,List<Long> Reg ) {
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
     *
     * @param CharUtile
     * @return
     */
    private double calculPrecision(int CharUtile,int All_Key_Press) {
        double copcu = (double) CharUtile * 1.0; double copakp =(double) All_Key_Press * 1.0;
        return Math.round((copcu/copakp) * 100);
    }




}
