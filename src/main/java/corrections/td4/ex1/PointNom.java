package corrections.td4.ex1;

// =====================================================================
//  CLASSE PointNom  -  Série 4, Exercice 1
//  Rôle : un point qui porte en plus un nom (un caractère). PointNom
//  hérite de Point (extends) : elle réutilise ses coordonnées et sa
//  méthode affiche(), en passant par super car x et y sont private.
// =====================================================================

/**
 * Un point nommé : un Point avec, en plus, un nom d'une lettre (A, B...).
 *
 * Pourquoi l'héritage (extends Point) : un point nommé EST un point. Au lieu de recopier x, y et
 * l'affichage des coordonnées, PointNom hérite de tout ce que fait Point et n'ajoute que ce qui
 * est nouveau : le nom. Si Point changeait sa façon d'afficher, PointNom suivrait automatiquement.
 *
 * Responsabilités : construire un point nommé et l'afficher (nom puis coordonnées).
 */
public class PointNom extends Point {

    // =================================================================
    //  ATTRIBUT (en plus de ceux hérités de Point)
    // =================================================================
    // char : le nom est un seul caractère, comme le dit l'énoncé ('A'). private : encapsulation.
    private char nom;

    // =================================================================
    //  1. CONSTRUCTEUR
    // =================================================================

    /**
     * Les coordonnées sont confiées au constructeur de Point avec super(x, y), puis le nom est rangé.
     *
     * Pourquoi super(x, y) : x et y sont private dans Point, PointNom ne peut donc pas écrire
     * this.x = x (javac : « x has private access in Point »). C'est le constructeur de Point qui les
     * initialise. De plus, Point n'a pas de constructeur sans paramètre : sans super(x, y), javac
     * refuserait de compiler (« constructor Point in class Point cannot be applied to given types »).
     *
     * @param x   l'abscisse
     * @param y   l'ordonnée
     * @param nom le nom du point (une lettre)
     */
    public PointNom(int x, int y, char nom) {
        super(x, y);                            // constructeur de Point : première instruction (cours p.209)
        this.nom = nom;
    }

    // =================================================================
    //  2. AFFICHAGE (redéfinition de la méthode affiche() de Point)
    // =================================================================

    /**
     * Affiche le nom du point, puis ses coordonnées grâce à la méthode affiche() de Point.
     *
     * Redéfinition : même nom, mêmes paramètres et même type de retour que affiche() de Point.
     * Pour un objet PointNom, c'est cette version qui s'exécute, même si on l'appelle à travers une
     * référence de type Point (liaison dynamique, voir TestPointNom).
     */
    public void affiche() {
        System.out.println("Point " + nom);
        // super.affiche() appelle la version de Point. Écrire affiche() seul appellerait CETTE
        // méthode-ci, qui s'appellerait elle-même sans fin (StackOverflowError).
        super.affiche();                        // la version de la classe mère (cours p.210)
    }
}
