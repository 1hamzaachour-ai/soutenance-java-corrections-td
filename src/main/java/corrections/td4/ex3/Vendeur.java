package corrections.td4.ex3;

// =====================================================================
//  CLASSE Vendeur  -  Série 4, Exercice 3, question 4
//  Rôle : un vendeur est un employé (extends Employe) qui touche en plus
//  une commission : commission = vente × pourcentage.
// =====================================================================

/**
 * Un vendeur : un employé (payé à l'heure) qui touche en plus une commission sur ses ventes.
 *
 * Pourquoi extends Employe (et non extends Salarie) : l'énoncé dit « les vendeurs sont des
 * employés ». En héritant d'Employe, Vendeur récupère les heures, le taux et leur calcul, et
 * n'ajoute que la commission. La hiérarchie est donc Salarie > Employe > Vendeur.
 */
public class Vendeur extends Employe {

    // =================================================================
    //  ATTRIBUTS (en plus de ceux d'Employe)
    // =================================================================
    // double : un montant de ventes et un pourcentage ont des décimales.
    private double vente;                       // montant des ventes du mois, en dinars
    private double pourcentage;                 // par exemple 0.05 pour 5 %

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * La partie « employé » est confiée au constructeur d'Employe, puis la vente et le pourcentage sont rangés.
     *
     * @param matricule   le matricule
     * @param nom         le nom
     * @param nbHeures    le nombre d'heures travaillées
     * @param tauxHoraire le taux horaire
     * @param vente       le montant des ventes du mois
     * @param pourcentage la part des ventes reversée (0.05 pour 5 %)
     */
    public Vendeur(int matricule, String nom, int nbHeures, double tauxHoraire, double vente, double pourcentage) {
        // super(...) appelle le constructeur d'Employe, qui appelle lui-même celui de Salarie :
        // chaque niveau initialise sa propre partie.
        super(matricule, nom, nbHeures, tauxHoraire);
        this.vente = vente;
        this.pourcentage = pourcentage;
    }

    // =================================================================
    //  MÉTHODES
    // =================================================================

    /**
     * Commission = vente × pourcentage.
     * Méthode séparée car elle sert deux fois : dans le calcul du salaire et dans l'affichage.
     *
     * @return la commission du mois
     */
    public double commission() {
        return vente * pourcentage;
    }

    /**
     * Salaire d'un vendeur : son salaire d'employé (super.calculSalaire()) plus la commission.
     *
     * super.calculSalaire() réutilise la formule d'Employe (heures × taux) au lieu de la recopier.
     * Écrire calculSalaire() sans super rappellerait CETTE méthode sans fin (StackOverflowError).
     *
     * @return le salaire du mois, commission comprise
     */
    public double calculSalaire() {
        return super.calculSalaire() + commission();
    }

    /**
     * Affiche les informations d'employé (super.afficher()), puis le détail de la commission.
     * super.afficher() exécute l'afficher() d'Employe, qui lui-même commence par celui de Salarie.
     */
    public void afficher() {
        super.afficher();
        System.out.println("    + commission : " + vente + " x " + pourcentage + " = " + commission() + " DT");
    }
}
