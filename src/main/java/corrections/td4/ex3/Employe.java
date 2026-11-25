package corrections.td4.ex3;

// =====================================================================
//  CLASSE Employe  -  Série 4, Exercice 3, question 4
//  Rôle : un salarié payé à l'heure. Son salaire est le nombre d'heures
//  travaillées multiplié par le taux horaire.
// =====================================================================

/**
 * Un employé payé à l'heure : une classe fille concrète de Salarie.
 *
 * Concrète : elle écrit le corps de calculSalaire(), la méthode abstraite de Salarie. Sans cela,
 * javac refuserait : « Employe is not abstract and does not override abstract method
 * calculSalaire() in Salarie ». Elle sert aussi de classe mère à Vendeur.
 */
public class Employe extends Salarie {

    // =================================================================
    //  ATTRIBUTS (en plus de ceux de Salarie)
    // =================================================================
    // int pour un nombre d'heures entier ; double pour un taux qui peut avoir des décimales.
    private int nbHeures;
    private double tauxHoraire;                 // en dinars par heure

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Le matricule et le nom sont confiés au constructeur de Salarie, puis les heures et le taux sont rangés.
     *
     * @param matricule   le matricule
     * @param nom         le nom
     * @param nbHeures    le nombre d'heures travaillées dans le mois
     * @param tauxHoraire le taux horaire en dinars
     */
    public Employe(int matricule, String nom, int nbHeures, double tauxHoraire) {
        // super(...) en première instruction : Salarie n'a pas de constructeur sans paramètre, et
        // ses attributs sont private (on ne peut pas les remplir nous-mêmes).
        super(matricule, nom);
        this.nbHeures = nbHeures;
        this.tauxHoraire = tauxHoraire;
    }

    // =================================================================
    //  MÉTHODES
    // =================================================================

    /**
     * Salaire d'un employé : nombre d'heures × taux horaire.
     *
     * int × double donne un double (nbHeures est converti en double avant le calcul).
     *
     * @return le salaire du mois
     */
    public double calculSalaire() {
        return nbHeures * tauxHoraire;
    }

    /**
     * Affiche les informations communes (super.afficher()), puis le détail des heures.
     *
     * Redéfinition qui complète la méthode de la classe mère au lieu de la remplacer : on
     * réutilise son affichage avec super.afficher(), puis on ajoute une ligne.
     */
    public void afficher() {
        super.afficher();
        System.out.println("    " + nbHeures + " h x " + tauxHoraire + " DT de l'heure");
    }
}
