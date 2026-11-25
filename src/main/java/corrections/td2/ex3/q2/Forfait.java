package corrections.td2.ex3.q2;

// =====================================================================
//  CLASSE Forfait (version 2)  -  Série 2, Exercice 3, question 2
//  Rôle : le même forfait, avec un deuxième constructeur sans paramètres
//  (surcharge des constructeurs).
// =====================================================================

/**
 * Deuxième version du forfait mobile (question 2) : on ajoute un constructeur sans paramètres.
 *
 * Responsabilités : les mêmes que la version 1 (garder le prix et la data, calculer, comparer,
 * afficher). Seule nouveauté : deux constructeurs (surcharge), donc deux façons de créer un
 * forfait : new Forfait(25.5f, 30) ou new Forfait().
 */
public class Forfait {

    // =================================================================
    //  ATTRIBUTS
    // =================================================================
    // float : un prix et un volume de data ont des décimales (type imposé par l'énoncé).
    private float prixMensuel;      // en dinars
    private float dataGo;           // volume d'internet mobile, en Go

    // =================================================================
    //  CONSTRUCTEURS (surcharge)
    // =================================================================

    /**
     * Crée un forfait à p dinars par mois pour d Go.
     *
     * @param p le prix mensuel en dinars
     * @param d le volume de data en Go
     */
    public Forfait(float p, float d) {
        prixMensuel = p;
        dataGo = d;
    }

    /**
     * Forfait de 10 dinars pour 5 Go (valeurs données par l'énoncé).
     *
     * this(10, 5) appelle le premier constructeur au lieu de recopier ses deux affectations :
     * l'initialisation n'est écrite qu'une seule fois. Les int 10 et 5 sont convertis
     * implicitement en float. this(...) doit être la première instruction.
     */
    public Forfait() {
        this(10, 5);                // appelle le constructeur Forfait(float, float)
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
     * Prix d'un Go (une division de float par 0 donnerait « Infinity », sans exception).
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
     * @return true si ce forfait est strictement moins cher au Go que f
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
