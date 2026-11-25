package corrections.td4;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;
import corrections.td4.ex2.Test;

// =====================================================================
//  CLASSE TD4_Ex2  -  Série 4, Exercice 2
//  Rôle : prévoir puis vérifier ce qu'affiche le programme de l'énoncé :
//  hiérarchie des classes A à F, réponse expliquée, exécution réelle et
//  version avec un tableau polymorphe. Le code est dans le paquetage ex2.
// =====================================================================
/**
 * Présentation de l'exercice 2 de la série 4 (héritage et redéfinition, classes A à F) dans le menu.
 *
 * L'énoncé demande de PRÉVOIR ce qu'affiche un programme. Cette classe donne d'abord la réponse
 * expliquée (hiérarchie des classes, méthode utilisée par chacune), puis exécute réellement le
 * programme (Test.main) pour vérifier, et enfin le même programme écrit avec un tableau polymorphe.
 */
public class TD4_Ex2 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 4) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD4_Ex2() {
        super("Héritage et redéfinition : résultats du programme",
                "Prévoir ce qu'affiche un programme utilisant les classes A à F : héritage, redéfinition de affiche() et liaison dynamique.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, la hiérarchie, la réponse expliquée, puis les deux exécutions.
     * Le programme de l'énoncé et les classes A à F sont dans corrections/td4/ex2/Test.java.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Quels résultats fournit le programme suivant ?");
        String[] programme = {
            "class A {",
            "    public void affiche() { System.out.println(\"Je suis un A\"); }",
            "}",
            "class B extends A {}",
            "class C extends A {",
            "    public void affiche() { System.out.println(\"Je suis un C\"); }",
            "}",
            "class D extends C {",
            "    public void affiche() { System.out.println(\"Je suis un D\"); }",
            "}",
            "class E extends B {}",
            "class F extends C {}",
            "public class Test {",
            "    public static void main(String[] args) {",
            "        A a = new A(); a.affiche();",
            "        B b = new B(); b.affiche();",
            "        C c = new C(); c.affiche();",
            "        D d = new D(); d.affiche();",
            "        E e = new E(); e.affiche();",
            "        F f = new F(); f.affiche();",
            "    }",
            "}"};
        Ecran.code(programme);

        // --- 2. La hiérarchie des classes ---
        Ecran.section("La hiérarchie des classes");
        String[] arbre = {
            "          A        affiche() : « Je suis un A »",
            "        /   \\",
            "       B     C     C redéfinit affiche() : « Je suis un C »",
            "       |    / \\",
            "       E   D   F   D redéfinit affiche() : « Je suis un D »"};
        Ecran.code(arbre);

        // --- 3. Réponse expliquée ---
        Ecran.section("Réponse");
        Ecran.texte("Pour chaque appel, Java cherche affiche() dans la classe de l'objet ; si elle n'y est "
                + "pas, il remonte vers la classe mère, jusqu'à la première définition trouvée :\n"
                + "- a.affiche() : « Je suis un A », la méthode de A ;\n"
                + "- b.affiche() : « Je suis un A », car B ne redéfinit rien : elle hérite de celle de A ;\n"
                + "- c.affiche() : « Je suis un C », car C redéfinit affiche() ;\n"
                + "- d.affiche() : « Je suis un D », car D redéfinit à son tour celle de C ;\n"
                + "- e.affiche() : « Je suis un A », car E hérite de B, qui hérite de A ;\n"
                + "- f.affiche() : « Je suis un C », car F hérite de C.");
        Clavier.pause();

        // --- 4. Exécution du programme de l'énoncé ---
        Ecran.section("Exécution de Test (corrections/td4/ex2/Test.java)");
        // Exécution réelle du programme de l'énoncé : elle confirme la réponse prévue ci-dessus.
        Test.main(new String[0]);
        System.out.println();
        Ecran.texte("Les six lignes sont bien celles prévues.");

        // --- 5. Complément : le même programme avec un tableau polymorphe ---
        Ecran.section("Complément : le même programme avec un tableau A[]");
        // Même résultat avec un tableau de type A[] et une seule instruction t[i].affiche().
        Test.tableauPolymorphe();
        System.out.println();
        Ecran.texte("Toutes les cases sont de type A, mais chaque objet exécute la version de affiche() de "
                + "sa propre classe : c'est la liaison dynamique (cours p.218-220). C'est aussi le principe "
                + "du tableau Exercice[] qui construit les menus de ce programme.");
    }
}
