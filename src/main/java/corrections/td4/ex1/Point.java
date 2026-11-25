package corrections.td4.ex1;

// =====================================================================
//  CLASSE Point  -  Série 4, Exercice 1 (classe donnée par l'énoncé)
//  Rôle : un point du plan, défini par ses coordonnées x et y. C'est le
//  code de l'énoncé, présenté sur plusieurs lignes. x et y sont private :
//  même la classe dérivée PointNom ne peut pas y accéder directement.
// =====================================================================

/**
 * Un point du plan, tel que l'énoncé le donne (classe de base de l'exercice).
 *
 * Elle est gardée exactement comme dans l'énoncé, y compris « class Point » sans public :
 * la classe n'est alors visible que dans son paquetage (ex1), où se trouvent aussi PointNom et
 * TestPointNom. On ne la modifie pas : l'exercice consiste justement à l'étendre sans la changer.
 *
 * Responsabilités : garder les coordonnées et les afficher.
 */
class Point {

    // =================================================================
    //  ATTRIBUTS (private)
    // =================================================================
    // int : des coordonnées entières, comme dans l'énoncé. private : même une classe fille ne peut
    // pas les lire directement ; c'est pourquoi PointNom passe par super(x, y) et super.affiche().
    private int x, y;

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Crée le point de coordonnées (x, y).
     *
     * @param x l'abscisse
     * @param y l'ordonnée
     */
    public Point(int x, int y) {
        // this.x : l'attribut ; x : le paramètre (même nom, d'où le « this. »).
        this.x = x;
        this.y = y;
    }

    // =================================================================
    //  MÉTHODE
    // =================================================================

    /** Affiche les coordonnées du point. PointNom redéfinit cette méthode et la réutilise avec super. */
    public void affiche() {
        System.out.println("coordonnées : " + x + " " + y);
    }
}
