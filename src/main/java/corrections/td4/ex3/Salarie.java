package corrections.td4.ex3;

// =====================================================================
//  CLASSE ABSTRAITE Salarie  -  Série 4, Exercice 3, question 2
//  Rôle : ce que tous les salariés ont en commun : un matricule, un nom
//  et l'affichage de leurs informations. Le calcul du salaire dépend du
//  type de salarié : calculSalaire() est abstraite, donc la classe aussi.
// =====================================================================

/**
 * Classe mère de tous les salariés de l'entreprise (employés, vendeurs, directeurs).
 *
 * Pourquoi abstraite : tous les salariés ont un salaire, mais on ne peut pas écrire SA formule ici,
 * car elle dépend du type de salarié (heures × taux, plus une commission, ou fixe + prime).
 * calculSalaire() est donc déclarée sans corps (abstract), ce qui rend la classe abstraite :
 * on ne peut pas écrire new Salarie(...) (javac : « Salarie is abstract; cannot be instantiated »).
 *
 * Responsabilités : garder le matricule et le nom (communs à tous), afficher les informations, et
 * obliger chaque classe fille à donner sa propre formule de salaire.
 */
public abstract class Salarie {

    // =================================================================
    //  ATTRIBUTS (communs à tous les salariés)
    // =================================================================
    // int pour le matricule (un numéro), String pour le nom. private : les classes filles n'y
    // accèdent pas directement ; elles les initialisent avec super(matricule, nom).
    private int matricule;
    private String nom;

    // =================================================================
    //  CONSTRUCTEUR PARAMÉTRÉ
    // =================================================================

    /**
     * Appelé par chaque classe fille avec super(matricule, nom) ; protected : réservé aux classes filles.
     *
     * Une classe abstraite peut avoir un constructeur : il ne crée pas d'objet Salarie seul, il
     * initialise la partie commune de chaque Employe, Vendeur ou Directeur.
     *
     * @param matricule le matricule du salarié
     * @param nom       son nom
     */
    protected Salarie(int matricule, String nom) {
        this.matricule = matricule;
        this.nom = nom;
    }

    // =================================================================
    //  MÉTHODES
    // =================================================================

    /**
     * Affiche le matricule, le nom et le salaire (calculSalaire() est celle de la vraie classe de l'objet).
     *
     * Cette méthode concrète appelle une méthode abstraite : c'est possible car, à l'exécution,
     * l'objet est toujours un Employe, un Vendeur ou un Directeur, qui ont chacun leur formule.
     * Java choisit la bonne grâce à la liaison dynamique.
     */
    public void afficher() {
        System.out.println("Matricule " + matricule + " - " + nom + " : salaire " + calculSalaire() + " DT");
    }

    /**
     * Calcul du salaire : la formule dépend du type de salarié, elle est écrite dans chaque classe fille.
     *
     * double : un salaire peut avoir des décimales (6,5 DT de l'heure par exemple).
     *
     * @return le salaire du mois, en dinars
     */
    public abstract double calculSalaire();
}
