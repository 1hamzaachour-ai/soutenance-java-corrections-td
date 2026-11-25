package corrections.td4.ex3;

// =====================================================================
//  CLASSE TestSalaries  -  Série 4, Exercice 3
//  Rôle : programme de test. Le personnel est rangé dans un tableau
//  polymorphe de type Salarie : la même boucle affiche chaque salarié et
//  additionne les salaires, quel que soit son type.
// =====================================================================

/**
 * Programme de test de la gestion du personnel.
 *
 * Il montre l'intérêt de la classe abstraite : un seul tableau de type Salarie contient des
 * salariés de types différents, et une seule boucle les traite tous. Pour ajouter un nouveau type
 * de salarié, il suffirait d'écrire sa classe ; cette boucle ne changerait pas.
 */
public class TestSalaries {

    /**
     * Point d'entrée : affichage de chaque salarié, puis masse salariale du mois.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // --- 1. Initialisation : un employé, un vendeur et un directeur ---
        // Tableau polymorphe : le type des cases est Salarie (la classe mère abstraite), les objets
        // sont des Employe, Vendeur et Directeur. Permis car chacun EST un Salarie.
        // On ne pourrait pas y mettre new Salarie(...) : la classe est abstraite.
        Salarie[] personnel = {
            new Employe(101, "Karim", 160, 6.5),
            new Vendeur(102, "Nour", 150, 6.0, 20000, 0.05),
            new Directeur(201, "Sonia", 3500, 800)
        };

        // --- 2. Affichage de chaque salarié et calcul de la masse salariale ---
        // total part de 0 : on y ajoute chaque salaire (accumulation). double, comme les salaires.
        double total = 0;
        /*
         * Boucle for sur toutes les cases du tableau (nombre de tours connu : personnel.length).
         * L'indice i va de 0 à 2 ; i < personnel.length évite de sortir du tableau.
         * À chaque tour, personnel[i].afficher() et personnel[i].calculSalaire() exécutent la
         * version de la classe réelle de l'objet (liaison dynamique) : la même instruction donne
         * trois calculs différents.
         */
        for (int i = 0; i < personnel.length; i++) {
            personnel[i].afficher();
            total += personnel[i].calculSalaire();      // total = total + salaire de ce salarié
        }

        // --- 3. Affichage du total ---
        System.out.println("Masse salariale du mois : " + total + " DT");
    }
}
