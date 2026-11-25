package corrections.td4.ex3;

// =====================================================================
//  CLASSE Directeur  -  Série 4, Exercice 3, question 4
//  Rôle : un salarié qui touche un salaire fixe augmenté d'une prime
//  mensuelle.
// =====================================================================

/**
 * Un directeur : un salarié payé par un salaire fixe et une prime.
 *
 * Il hérite directement de Salarie (et non d'Employe) : il n'est pas payé à l'heure, il n'a donc
 * rien à reprendre d'Employe. Il écrit sa propre formule de salaire.
 */
public class Directeur extends Salarie {

    // =================================================================
    //  ATTRIBUTS (en plus de ceux de Salarie)
    // =================================================================
    // double : des montants en dinars, avec décimales possibles.
    private double salaireFixe;
    private double prime;                       // prime mensuelle

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Le matricule et le nom sont confiés au constructeur de Salarie, puis le fixe et la prime sont rangés.
     *
     * @param matricule   le matricule
     * @param nom         le nom
     * @param salaireFixe le salaire fixe du mois
     * @param prime       la prime mensuelle
     */
    public Directeur(int matricule, String nom, double salaireFixe, double prime) {
        super(matricule, nom);
        this.salaireFixe = salaireFixe;
        this.prime = prime;
    }

    // =================================================================
    //  MÉTHODES
    // =================================================================

    /**
     * Salaire d'un directeur : salaire fixe + prime mensuelle.
     *
     * @return le salaire du mois
     */
    public double calculSalaire() {
        return salaireFixe + prime;
    }

    /** Affiche les informations communes (super.afficher()), puis le détail du fixe et de la prime. */
    public void afficher() {
        super.afficher();
        System.out.println("    " + salaireFixe + " DT de fixe + " + prime + " DT de prime");
    }
}
