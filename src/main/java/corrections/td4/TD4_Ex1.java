package corrections.td4;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;
import corrections.td4.ex1.TestPointNom;

// =====================================================================
//  CLASSE TD4_Ex1  -  Série 4, Exercice 1
//  Rôle : présenter la classe PointNom, dérivée de Point : énoncé,
//  diagrammes UML, explications, messages de javac et exécution. Le code
//  est dans le paquetage ex1 (Point, PointNom et TestPointNom).
// =====================================================================
/**
 * Présentation de l'exercice 1 de la série 4 (point nommé, PointNom dérivée de Point) dans le menu.
 *
 * Le code est dans le paquetage ex1 : Point (donnée par l'énoncé, gardée telle quelle), PointNom
 * et TestPointNom. Cette classe présente l'énoncé, les diagrammes UML, les points clés (super,
 * redéfinition), les messages de javac en cas d'erreur, puis exécute le programme de test.
 */
public class TD4_Ex1 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 4) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD4_Ex1() {
        super("Point nommé (PointNom dérivée de Point)",
                "Dériver PointNom de Point : appeler le constructeur parent avec super(x, y) et redéfinir affiche() avec super.affiche().");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, la classe PointNom avec ses explications, puis l'exécution du test.
     * TestPointNom.main(new String[0]) exécute réellement le programme de test (sans paramètre).
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("On dispose de la classe suivante :");
        String[] point = {"class Point {", "    private int x, y;",
            "    public Point(int x, int y) { this.x = x; this.y = y; }", "    public void affiche()",
            "    { System.out.println(\"coordonnées : \" + x + \" \" + y); }", "}"};
        Ecran.code(point);
        Ecran.enonce("Réaliser une classe PointNom, dérivée de Point, permettant de manipuler des points "
                + "définis par leurs coordonnées et un nom (caractère). On y prévoira : 1. un constructeur "
                + "pour définir les coordonnées et le nom d'un objet de type PointNom ; 2. affiche, pour "
                + "afficher les coordonnées et le nom d'un objet de type PointNom.");

        // --- 2. La classe PointNom (diagrammes UML) et les explications ---
        Ecran.section("La classe PointNom");
        String[] attributsP = {"- x : int", "- y : int"};
        String[] methodesP = {"+ Point(x : int, y : int)", "+ affiche() : void"};
        Ecran.uml("Point", attributsP, methodesP);
        System.out.println("            ^");
        System.out.println("            |   extends : PointNom hérite de Point");
        String[] attributsN = {"- nom : char"};
        String[] methodesN = {"+ PointNom(x : int, y : int, nom : char)", "+ affiche() : void"};
        Ecran.uml("PointNom", attributsN, methodesN);
        Ecran.texte("Code : corrections/td4/ex1/PointNom.java. Points clés :\n"
                + "- x et y sont private dans Point : même la classe fille ne peut pas les lire. PointNom "
                + "passe donc par ce que Point lui offre, son constructeur et sa méthode affiche() ;\n"
                + "- 1. le constructeur appelle super(x, y), le constructeur de Point, en première "
                + "instruction (cours p.209), puis range le nom ;\n"
                + "- 2. affiche() est redéfinie : elle affiche le nom, puis appelle super.affiche(), la "
                + "version de Point, pour les coordonnées (cours p.210).");
        Ecran.texte("Avec « System.out.println(x) » dans PointNom, javac refuse :");
        Ecran.champ("javac", "erreur : x has private access in Point");
        Ecran.texte("Sans l'appel super(x, y), Java appelle un constructeur Point() sans paramètres, qui "
                + "n'existe pas :");
        Ecran.champ("javac", "erreur : constructor Point in class Point cannot be applied to given types;\n"
                + "  required: int,int\n"
                + "  found:    no arguments\n"
                + "  reason: actual and formal argument lists differ in length");
        Clavier.pause();

        // --- 3. Exécution de TestPointNom ---
        Ecran.section("Exécution de TestPointNom");
        TestPointNom.main(new String[0]);
        System.out.println();
        Ecran.texte("q est une référence de type Point, mais l'objet est un PointNom : q.affiche() exécute "
                + "la méthode de PointNom. C'est la liaison dynamique (cours p.218).");

        // --- 4. À retenir ---
        Ecran.section("À retenir");
        Ecran.texte("- Le mot-clé super sert à atteindre la classe mère : super(...) pour son constructeur, "
                + "super.affiche() pour sa méthode.\n"
                + "- Si x et y étaient protected (cours p.214-215), PointNom pourrait les afficher "
                + "elle-même. Ici l'énoncé les a mis private : on réutilise le code de Point au lieu de le "
                + "recopier.");
    }
}
