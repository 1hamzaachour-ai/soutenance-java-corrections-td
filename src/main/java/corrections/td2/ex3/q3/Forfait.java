package corrections.td2.ex3.q3;

// =====================================================================
//  CLASSE Forfait (version 3)  -  Série 2, Exercice 3, question 3
//  Rôle : le forfait sans le constructeur Forfait(float, float), remplacé
//  par la méthode modifierForfait().
// =====================================================================

/**
 * Troisième version du forfait mobile (question 3) : le constructeur avec paramètres est enlevé.
 *
 * Conséquence : new Forfait(45, 100) ne compile plus. Pour obtenir un forfait à 45 dinars et
 * 100 Go, on crée d'abord un forfait avec Forfait(), puis on change ses valeurs avec la nouvelle
 * méthode modifierForfait(). Différence à retenir : un constructeur n'agit qu'une fois, à la
 * création ; une méthode peut modifier l'objet autant de fois qu'on veut.
 */
public class Forfait {

    // =================================================================
    //  ATTRIBUTS
    // =================================================================
    // float : prix et data avec décimales ; private : modifiés seulement par les méthodes ci-dessous.
    private float prixMensuel;      // en dinars
    private float dataGo;           // volume d'internet mobile, en Go

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Forfait de 10 dinars pour 5 Go.
     *
     * Le constructeur Forfait(float, float) n'existe plus : on ne peut donc plus écrire this(10, 5)
     * comme dans la version 2. Les deux attributs sont initialisés directement.
     */
    public Forfait() {
        prixMensuel = 10;
        dataGo = 5;
    }

    // =================================================================
    //  MODIFICATION
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
