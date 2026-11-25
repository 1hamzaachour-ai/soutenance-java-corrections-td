package corrections.outils;

// =====================================================================
//  CLASSE TD
//  Rôle : représenter une série de TD : son numéro, son intitulé, le
//  tableau de ses objectifs et le tableau de ses exercices, rangés dans
//  l'ordre de l'énoncé.
// =====================================================================

/**
 * Une série de TD (par exemple « Série N°3 »), vue comme un objet.
 *
 * Pourquoi cette classe existe : sans elle, Main devrait manipuler pour chaque série quatre
 * informations séparées (numéro, intitulé, objectifs, exercices) et les faire correspondre à la
 * main. En les regroupant dans un objet, Main range les cinq séries dans un seul tableau
 * (SERIES) et un seul code de menu fonctionne pour toutes les séries, quel que soit leur nombre
 * d'exercices (c'est une exigence de l'énoncé de la soutenance).
 *
 * Responsabilités :
 * - garder les informations de la série (attributs private et final) ;
 * - donner accès aux objectifs et aux exercices par leur numéro (1, 2, 3...), comme les voit
 *   l'utilisateur, en s'occupant elle-même du décalage avec les indices des tableaux (0, 1, 2...).
 */
public class TD {

    // =================================================================
    //  ATTRIBUTS (final : fixés une fois pour toutes par le constructeur)
    // =================================================================
    // private : Main ne peut pas modifier une série par erreur, il passe par les accesseurs.
    // int pour le numéro (un entier), String pour l'intitulé (un texte).
    // Des tableaux (et non des ArrayList) : le nombre d'objectifs et d'exercices d'une série est
    // connu à l'avance et ne change jamais pendant l'exécution ; un tableau suffit et c'est la
    // structure la plus simple (cours, chapitre 5). Une ArrayList sert quand la taille varie.
    private final int numero;
    private final String intitule;
    private final String[] objectifs;
    private final Exercice[] exercices;   // tableau polymorphe : des objets TD1_Ex1, TD2_Ex3...

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Crée une série à partir de ses informations ; appelé cinq fois dans le tableau SERIES de Main.
     *
     * Pourquoi un constructeur : il garantit qu'une série n'existe jamais à moitié remplie. Les
     * attributs étant final, c'est d'ailleurs le seul endroit où on peut leur donner une valeur.
     *
     * @param numero    numéro de la série (1 à 5)
     * @param intitule  intitulé affiché dans les menus, par exemple « Série N°1 : ... »
     * @param objectifs objectifs de la série, affichés par l'option 7 du menu principal
     * @param exercices exercices de la série, dans l'ordre de l'énoncé
     */
    public TD(int numero, String intitule, String[] objectifs, Exercice[] exercices) {
        // « this. » distingue l'attribut (à gauche) du paramètre de même nom (à droite).
        this.numero = numero;
        this.intitule = intitule;
        // On garde la référence du tableau reçu (pas de copie) : les tableaux de Main sont
        // private et final, personne d'autre ne peut les modifier.
        this.objectifs = objectifs;
        this.exercices = exercices;
    }

    // =================================================================
    //  ACCESSEURS
    // =================================================================

    /**
     * @return le numéro du TD (1, 2...), affiché dans les titres des menus
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @return l'intitulé du TD, par exemple « Série N°1 : ... »
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * Nombre d'objectifs de la série : Main s'en sert comme borne de sa boucle d'affichage.
     * length est un attribut de tout tableau Java (sans parenthèses, contrairement à
     * la méthode length() de String).
     *
     * @return la taille du tableau des objectifs
     */
    public int getNombreObjectifs() {
        return objectifs.length;
    }

    /**
     * Renvoie l'objectif numéro n.
     *
     * Pourquoi n - 1 : l'utilisateur et Main comptent à partir de 1, alors qu'un tableau Java
     * commence à l'indice 0. La conversion est faite ici, une seule fois, au lieu d'être répétée
     * partout dans Main. Avec n = 0 ou n trop grand, Java lèverait une
     * ArrayIndexOutOfBoundsException : Main n'appelle donc cette méthode qu'avec 1 à
     * getNombreObjectifs().
     *
     * @param n numéro de l'objectif, de 1 à getNombreObjectifs()
     * @return le texte de l'objectif
     */
    public String getObjectif(int n) {
        return objectifs[n - 1];
    }

    /**
     * Nombre d'exercices de la série : sert de borne au menu de la série (le choix est accepté
     * de 0 à ce nombre). Grâce à cette méthode, le menu s'adapte tout seul à une série de 7
     * exercices comme à une série de 2.
     *
     * @return la taille du tableau des exercices
     */
    public int getNombreExercices() {
        return exercices.length;
    }

    /**
     * Renvoie l'exercice numéro n (même décalage n - 1 que pour getObjectif).
     *
     * Le type renvoyé est Exercice (la classe mère) : Main peut appeler getTitre(),
     * getObjectif() et executer() sans savoir s'il s'agit d'un TD1_Ex3 ou d'un TD4_Ex2.
     *
     * @param n numéro de l'exercice, de 1 à getNombreExercices()
     * @return l'objet exercice correspondant
     */
    public Exercice getExercice(int n) {
        return exercices[n - 1];
    }
}
