package corrections.td2.ex2.q1;

// =====================================================================
//  CLASSE Carte (première version)  -  Série 2, Exercice 2, question 1
//  Rôle : une carte à collectionner, avec son nom et sa valeur en points.
//  Les attributs n'ont pas de modificateur d'accès : toute classe du même
//  paquetage peut les lire et les modifier directement (c'est la triche
//  de la question 3). Aucun constructeur n'est écrit : on utilise le
//  constructeur par défaut.
// =====================================================================

/**
 * Première version de la carte à collectionner, écrite exactement comme le demande la question 1.
 *
 * Pourquoi garder cette version « fragile » : la question 3 demande de montrer qu'on peut
 * tricher avec elle. On la garde donc dans son propre paquetage (q1), à côté de la version
 * corrigée (q4), pour pouvoir exécuter et comparer les deux.
 *
 * Responsabilités : garder un nom et une valeur, s'afficher, se modifier.
 */
public class Carte {

    // =================================================================
    //  ATTRIBUTS (sans modificateur : visibles dans tout le paquetage)
    // =================================================================
    // Sans private, public ni protected, l'accès est dit « paquetage » : TestCarte, qui est dans
    // le même paquetage, peut écrire c.valeur = 9999 sans passer par modifier(). C'est le défaut
    // que la question 3 met en évidence ; la version q4 le corrige avec private.
    // int pour la valeur : des points entiers.
    String nom;
    int valeur;

    // =================================================================
    //  MÉTHODES
    // =================================================================

    /** Affiche la carte sous la forme [Dragon de feu — 80 pts]. */
    public void afficher() {
        System.out.println("[" + nom + " — " + valeur + " pts]");
    }

    /**
     * Change le nom et la valeur de la carte par les valeurs reçues en arguments.
     *
     * Aucune vérification : une valeur négative est acceptée. La version q4 ajoute ce contrôle.
     *
     * @param n le nouveau nom
     * @param v la nouvelle valeur en points
     */
    public void modifier(String n, int v) {
        nom = n;
        valeur = v;
    }
}
