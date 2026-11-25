package corrections.td4.ex2;

// =====================================================================
//  FICHIER Test.java  -  Série 4, Exercice 2 (programme de l'énoncé)
//  Rôle : les six classes A à F, liées par héritage, et le programme Test.
//  Un fichier peut contenir plusieurs classes, mais une seule est public
//  (Test), et le fichier porte son nom (cours p.14). Les classes A à F ne
//  sont utilisées que dans ce fichier, d'où le complément placé dans Test.
// =====================================================================
// Hiérarchie : A est la classe de départ ; B et C héritent de A ; D et F héritent de C ;
// E hérite de B. Une classe qui ne redéfinit pas affiche() utilise celle de sa classe mère la
// plus proche qui la définit.
// Les six classes n'ont pas de public : elles restent dans ce fichier (une seule classe public
// par fichier, celle qui donne son nom au fichier).

/** A : la classe de départ ; sa méthode affiche() écrit « Je suis un A ». */
class A {
    public void affiche() {
        System.out.println("Je suis un A");
    }
}

/** B hérite de A sans rien redéfinir : elle garde la méthode affiche() de A. */
class B extends A {
}

/** C hérite de A et redéfinit affiche(). */
class C extends A {
    // Redéfinition : même signature que affiche() de A ; elle remplace celle de A pour C.
    public void affiche() {
        System.out.println("Je suis un C");
    }
}

/** D hérite de C et redéfinit affiche() à son tour. */
class D extends C {
    public void affiche() {
        System.out.println("Je suis un D");
    }
}

/** E hérite de B, donc aussi de A, sans rien redéfinir. */
class E extends B {
    // Ni E ni B ne redéfinissent affiche() : Java remonte jusqu'à A, d'où « Je suis un A ».
}

/** F hérite de C sans rien redéfinir : elle garde la méthode affiche() de C. */
class F extends C {
    // Java remonte jusqu'à C, la classe mère la plus proche qui définit affiche() : « Je suis un C ».
}

// =====================================================================
//  CLASSE Test : le programme principal de l'énoncé, puis un complément
// =====================================================================

/**
 * Le programme de l'énoncé (main) et un complément qui montre la même chose avec un tableau
 * polymorphe (tableauPolymorphe).
 *
 * Résultat de main : A, A, C, D, A, C. Seules C et D redéfinissent affiche() ; les autres
 * classes utilisent la méthode héritée de la classe mère la plus proche qui la définit.
 */
public class Test {

    /**
     * Le programme de l'énoncé : crée un objet de chaque classe et appelle sa méthode affiche().
     *
     * Le code est gardé tel que dans l'énoncé (deux instructions par ligne). Chaque variable a le
     * type de son objet : ici, le résultat ne dépend que de la classe de chaque objet.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        A a = new A(); a.affiche();
        B b = new B(); b.affiche();
        C c = new C(); c.affiche();
        D d = new D(); d.affiche();
        E e = new E(); e.affiche();
        F f = new F(); f.affiche();
    }

    /**
     * Complément (hors énoncé) : le même programme avec un tableau polymorphe de type A[] (cours p.220).
     *
     * Toutes les classes héritent de A : un tableau de type A[] peut donc contenir un objet de
     * chacune. L'appel t[i].affiche() est écrit une seule fois, et Java exécute la version de la
     * classe réelle de chaque objet (liaison dynamique) : on obtient le même résultat que main.
     */
    public static void tableauPolymorphe() {
        // --- 1. Initialisation : un tableau de références de type A ---
        // Le second tableau, classes, sert seulement à écrire le nom de la classe de chaque objet ;
        // il a la même longueur et le même ordre que t.
        A[] t = { new A(), new B(), new C(), new D(), new E(), new F() };
        String[] classes = { "A", "B", "C", "D", "E", "F" };

        // --- 2. Affichage : le même appel t[i].affiche() pour chaque case ---
        /*
         * Boucle for avec indice : on a besoin de i pour lire la même case dans les deux tableaux
         * (t[i] et classes[i]). Le nombre de tours est connu : t.length, soit 6.
         * i < t.length s'arrête après la dernière case (i = 5) ; avec <=, t[6] n'existerait pas.
         */
        for (int i = 0; i < t.length; i++) {
            System.out.print("t[" + i + "] désigne un objet " + classes[i] + " : ");
            t[i].affiche();
        }
    }
}
