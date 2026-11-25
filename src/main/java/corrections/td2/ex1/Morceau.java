package corrections.td2.ex1;

// =====================================================================
//  CLASSE Morceau  -  Série 2, Exercice 1, question 1
//  Rôle : un morceau de musique, avec son titre et sa durée (minutes et
//  secondes).
//  Aucun constructeur n'est écrit : le compilateur ajoute donc lui-même le
//  constructeur par défaut Morceau(), qui laisse les attributs à leur
//  valeur par défaut (null pour titre, 0 pour les int).
// =====================================================================

/**
 * Un morceau de musique d'une application de streaming : un titre et une durée.
 *
 * Pourquoi cette classe existe : l'énoncé demande de regrouper les données d'un morceau
 * (titre, minutes, secondes) et les traitements qui les concernent (calculer la durée,
 * afficher) dans un même type, la classe Morceau. Chaque morceau sera un objet de cette classe.
 *
 * Responsabilités : garder les trois informations du morceau, permettre de les modifier (méthodes
 * set), calculer sa durée en secondes et l'afficher.
 *
 * Pas de constructeur écrit, comme le demande l'énoncé : Java fournit alors un constructeur par
 * défaut sans paramètre, qui laisse chaque attribut à sa valeur par défaut (null pour un String,
 * 0 pour un int). C'est la réponse à la question « Qu'affiche afficherMorceau() si on l'appelle
 * juste après la création ? » : « --- null --- » puis « 0min 0s ».
 */
public class Morceau {

    // =================================================================
    //  ATTRIBUTS (private : encapsulation)
    // =================================================================
    // private : seules les méthodes de Morceau y ont accès ; les autres classes passent par les
    // méthodes set. On pourrait ainsi, plus tard, ajouter des contrôles dans ces méthodes sans
    // changer les programmes qui utilisent Morceau.
    // String pour un texte (le titre) ; int pour les minutes et les secondes (des nombres entiers,
    // une durée ne se compte pas ici en fractions de seconde).
    private String titre;
    private int minutes;
    private int secondes;

    // =================================================================
    //  MODIFICATEURS (méthodes set)
    // =================================================================
    // Les méthodes set (modificateurs) sont le seul moyen, pour une autre classe, de changer les
    // attributs private. Elles sont public pour être appelées depuis TestMorceau.
    // void : elles ne renvoient rien, elles modifient seulement l'objet.

    /**
     * Modifie la valeur de l'attribut titre par la valeur de t.
     *
     * @param t le nouveau titre
     */
    public void setTitre(String t) {
        // Le paramètre s'appelle t et l'attribut titre : les noms sont différents, on peut donc
        // écrire titre = t sans « this. ».
        titre = t;
    }

    /**
     * Modifie la valeur de l'attribut minutes par la valeur de m.
     *
     * @param m le nouveau nombre de minutes
     */
    public void setMinutes(int m) {
        minutes = m;
    }

    /**
     * Modifie la valeur de l'attribut secondes par la valeur de s.
     *
     * @param s le nouveau nombre de secondes
     */
    public void setSecondes(int s) {
        secondes = s;
    }
    // REMARQUE : ces méthodes acceptent n'importe quelle valeur (des secondes négatives ou
    // supérieures à 59). L'énoncé ne demande pas de contrôle ici ; c'est TestMorceau qui
    // n'accepte que 0 à 59 secondes à la saisie.

    // =================================================================
    //  CALCUL ET AFFICHAGE
    // =================================================================

    /**
     * Durée totale du morceau, en secondes.
     *
     * Une minute vaut 60 secondes : durée = minutes * 60 + secondes. La multiplication est faite
     * avant l'addition (priorité des opérateurs), les parenthèses ne sont donc pas nécessaires.
     *
     * @return la durée en secondes (par exemple 3 min 42 s donne 222)
     */
    public int dureeEnSecondes() {
        return minutes * 60 + secondes;
    }

    /**
     * Affiche le morceau selon le modèle « --- Mon titre --- » puis « 3min 42s ».
     *
     * La méthode lit directement les attributs (elle est dans la classe Morceau, elle y a donc
     * accès malgré private).
     */
    public void afficherMorceau() {
        System.out.println("--- " + titre + " ---");
        System.out.println(minutes + "min " + secondes + "s");
    }
}
