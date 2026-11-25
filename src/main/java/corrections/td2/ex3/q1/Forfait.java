package corrections.td2.ex3.q1;

// =====================================================================
//  CLASSE Forfait (version 1)  -  Série 2, Exercice 3, question 1
//  Rôle : un forfait mobile, décrit par son prix mensuel et son volume
//  de data. Un seul constructeur, avec paramètres.
// =====================================================================

/**
 * Première version du forfait mobile (question 1) : un seul constructeur, avec paramètres.
 *
 * Pourquoi plusieurs versions de Forfait (q1 à q4) : chaque question de l'exercice ajoute ou
 * enlève un constructeur, ce qui change les instructions qui compilent. Chaque version est
 * gardée dans son paquetage pour pouvoir exécuter et comparer toutes les étapes.
 *
 * Responsabilités : garder le prix et la data, calculer le coût annuel et le prix par Go,
 * comparer deux forfaits, s'afficher.
 *
 * Comme cette classe déclare un constructeur, Java ne fournit PAS de constructeur par défaut :
 * new Forfait() ne compile pas avec cette version (c'est ce que montrent les questions suivantes).
 */
public class Forfait {

    // =================================================================
    //  ATTRIBUTS
    // =================================================================
    // float, comme l'impose l'énoncé : un prix (25,5 DT) et un volume de data ont des décimales.
    // private : les valeurs ne changent qu'à travers les méthodes de la classe.
    private float prixMensuel;      // en dinars
    private float dataGo;           // volume d'internet mobile, en Go

    // =================================================================
    //  CONSTRUCTEUR
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
     * Prix d'un Go.
     *
     * @return le prix mensuel divisé par le volume de data
     */
    public float prixParGo() {
        // REMARQUE : avec dataGo = 0, cette division de float ne lève pas d'exception (seule la
        // division entière par 0 en lève une) : elle donne « Infinity ». L'énoncé ne prévoit pas
        // de forfait sans data, aucun contrôle n'est donc ajouté.
        return prixMensuel / dataGo;
    }

    /**
     * Vrai si le prix par Go du forfait courant est inférieur à celui de f.
     *
     * On compare les prix par Go (et non les prix mensuels) : c'est le critère de l'énoncé, qui
     * tient compte à la fois du prix et de la quantité de data.
     *
     * @param f le forfait à comparer
     * @return true si ce forfait est strictement moins cher au Go que f
     */
    public boolean estMeilleurQue(Forfait f) {
        // < strict : à prix par Go égal, aucun des deux n'est « meilleur ».
        return prixParGo() < f.prixParGo();
    }

    // =================================================================
    //  AFFICHAGE
    // =================================================================

    /**
     * Affiche le prix mensuel, la data, le coût annuel et le prix par Go.
     * Les deux derniers sont recalculés à chaque affichage par les méthodes de calcul :
     * on ne les stocke pas dans des attributs, ils ne peuvent donc jamais être faux.
     */
    public void affiche() {
        System.out.println("  Prix mensuel : " + prixMensuel + " DT");
        System.out.println("  Volume data  : " + dataGo + " Go");
        System.out.println("  Coût annuel  : " + coutAnnuel() + " DT");
        System.out.println("  Prix par Go  : " + prixParGo() + " DT");
    }
}
