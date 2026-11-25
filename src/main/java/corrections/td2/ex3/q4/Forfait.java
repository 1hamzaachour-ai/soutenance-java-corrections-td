package corrections.td2.ex3.q4;

// =====================================================================
//  CLASSE Forfait (version 4)  -  Série 2, Exercice 3, question 4
//  Rôle : le forfait sans AUCUN constructeur écrit. Le compilateur ajoute
//  alors le constructeur par défaut Forfait(), qui laisse les deux
//  attributs à 0.0.
// =====================================================================

/**
 * Quatrième version du forfait mobile (question 4) : aucun constructeur n'est écrit.
 *
 * Règle de Java : si une classe ne déclare AUCUN constructeur, le compilateur ajoute un
 * constructeur par défaut sans paramètre, qui laisse les attributs à leur valeur par défaut
 * (0.0 pour un float). new Forfait() compile donc, et donne un forfait à 0 dinar pour 0 Go.
 * Dès qu'on écrit un constructeur (versions 1 à 3), ce constructeur par défaut n'est plus ajouté.
 */
public class Forfait {

    // =================================================================
    //  ATTRIBUTS
    // =================================================================
    // Sans constructeur, ces deux attributs valent 0.0 tant que modifierForfait n'est pas appelée.
    private float prixMensuel;      // en dinars
    private float dataGo;           // volume d'internet mobile, en Go

    // =================================================================
    //  MODIFICATION (aucun constructeur n'est écrit)
    // =================================================================

    /**
     * Change le prix et le volume de data du forfait.
     *
     * @param p le nouveau prix mensuel en dinars
     * @param d le nouveau volume de data en Go
     */
    public void modifierForfait(float p, float d) {
        prixMensuel = p;
        dataGo = d;
    }

    // =================================================================
    //  CALCULS
    // =================================================================

    /**
     * Coût du forfait sur 12 mois.
     *
     * @return le prix mensuel multiplié par 12
     */
    public float coutAnnuel() {
        return prixMensuel * 12;
    }

    /**
     * Prix d'un Go : avec dataGo = 0, le calcul donne NaN ou Infinity (pas d'exception en float).
     *
     * Avec le constructeur par défaut, prixMensuel et dataGo valent 0 : 0 / 0 en float donne NaN
     * (« Not a Number », pas un nombre). C'est ce qu'affiche le test de la question 4.
     *
     * @return le prix mensuel divisé par le volume de data
     */
    public float prixParGo() {
        return prixMensuel / dataGo;
    }

    /**
     * Vrai si le prix par Go du forfait courant est inférieur à celui de f.
     *
     * @param f le forfait à comparer
     * @return true si ce forfait est strictement moins cher au Go que f (toujours false avec NaN)
     */
    public boolean estMeilleurQue(Forfait f) {
        return prixParGo() < f.prixParGo();
    }

    // =================================================================
    //  AFFICHAGE
    // =================================================================

    /** Affiche le prix mensuel, la data, le coût annuel et le prix par Go. */
    public void affiche() {
        System.out.println("  Prix mensuel : " + prixMensuel + " DT");
        System.out.println("  Volume data  : " + dataGo + " Go");
        System.out.println("  Coût annuel  : " + coutAnnuel() + " DT");
        System.out.println("  Prix par Go  : " + prixParGo() + " DT");
    }
}
