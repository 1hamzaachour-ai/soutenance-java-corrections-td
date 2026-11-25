package corrections.td3;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;
import corrections.td3.ex1.PileInt;
import corrections.td3.ex1.PileVideException;
import corrections.td3.ex1.TestPileInt;

// =====================================================================
//  CLASSE TD3_Ex1  -  Série 3, Exercice 1
//  Rôle : présenter la pile d'entiers : énoncé, diagramme UML, exécution
//  de TestPileInt et réponses aux questions. Le code demandé est dans le
//  paquetage ex1 (PileInt, ses deux exceptions et TestPileInt).
// =====================================================================
/**
 * Présentation de l'exercice 1 de la série 3 (pile d'entiers) dans le menu de la soutenance.
 *
 * Le code demandé (PileInt, ses deux exceptions et TestPileInt) est rangé dans le paquetage ex1,
 * comme on le rendrait. Cette classe le présente : énoncé, diagramme UML, démonstration d'une
 * exception, exécution du programme de test et points à retenir.
 * Classe fille de Exercice : Main la lance avec executer(), comme tous les exercices.
 */
public class TD3_Ex1 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 3) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD3_Ex1() {
        super("Pile d'entiers (classe PileInt)",
                "Représenter une pile d'entiers avec un tableau (empiler, dépiler, sommet) et signaler pile pleine ou vide par des exceptions.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, puis répond aux deux questions.
     * TestPileInt.main(new String[0]) exécute réellement le programme de test, avec un tableau de
     * paramètres vide ; la pause laisse le temps de lire la question 1 avant la saisie.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("On voudrait écrire un programme de gestion d'une pile d'entiers sur laquelle on "
                + "effectue les opérations classiques suivantes : Initialiser, Afficher, Empiler, Dépiler "
                + "et donner le Sommet de la pile.");

        // --- 2. Question 1 : la classe PileInt (diagramme UML) ---
        Ecran.question(1, "Donner une définition de la classe PileInt, que l'on représentera à l'aide d'un "
                + "tableau d'entiers.");
        String[] attributs = {"- tab : int[]", "- indiceSommet : int"};
        String[] methodes = {"+ PileInt(capacite : int)", "+ initialiser() : void", "+ estVide() : boolean",
            "+ estPleine() : boolean", "+ taille() : int", "+ empiler(x : int) : void", "+ depiler() : int",
            "+ sommet() : int", "+ afficher() : void"};
        Ecran.uml("PileInt", attributs, methodes);
        Ecran.texte("Code : corrections/td3/ex1/PileInt.java. Points clés :\n"
                + "- une pile suit la règle « dernier entré, premier sorti » : on ne touche qu'à la case "
                + "du haut, le sommet ;\n"
                + "- indiceSommet est l'indice de cette case : il vaut -1 quand la pile est vide, et "
                + "initialiser() le remet à -1 ;\n"
                + "- empiler(x) fait monter le sommet d'une case, puis y range x ; depiler() renvoie "
                + "tab[indiceSommet], puis fait descendre le sommet ; sommet() renvoie la même valeur sans "
                + "la retirer ;\n"
                + "- empiler() sur une pile pleine, depiler() et sommet() sur une pile vide lèvent une "
                + "exception écrite pour l'exercice, PilePleineException ou PileVideException, sur le modèle "
                + "de l'exemple AEgalBException du cours (p.248-250) : aucune valeur inventée n'est renvoyée.");
        Ecran.texte("Démonstration : depiler() sur une pile vide.");
        demonstrationPileVide();
        Clavier.pause();

        // --- 3. Question 2 : exécution de TestPileInt ---
        Ecran.question(2, "Écrire un programme java permettant : la création et le remplissage d'une pile de "
                + "type PileInt par N entiers, N étant donné par l'utilisateur ; l'affichage de la pile ; la "
                + "sortie de X entiers de la pile, X étant donné par l'utilisateur ; le réaffichage de la pile.");
        Ecran.texte("(Essayez par exemple N = 4 avec 10, 20, 30, 40, puis X = 2. Relancez avec R et choisissez "
                + "X plus grand que N : l'exception PileVideException est levée, puis interceptée.)");
        TestPileInt.main(new String[0]);

        // --- 4. À retenir ---
        Ecran.section("À retenir");
        Ecran.texte("- Les entiers sortent dans l'ordre inverse de leur entrée : le dernier empilé sort le "
                + "premier.\n"
                + "- depiler() n'efface pas la case : il fait seulement descendre le sommet ; la valeur "
                + "restée dans le tableau sera écrasée au prochain empiler().\n"
                + "- empiler() et depiler() annoncent leur exception avec throws : le compilateur oblige "
                + "l'appelant à l'intercepter, d'où les deux blocs try / catch de TestPileInt (cours p.245-247).");
    }

    // =================================================================
    //  MÉTHODE UTILITAIRE (privée)
    // =================================================================

    /**
     * Essaie de dépiler une pile vide et affiche le message de l'exception interceptée.
     *
     * Méthode séparée : c'est une petite démonstration indépendante, qui montre l'exception
     * avant même le programme de test.
     */
    private static void demonstrationPileVide() {
        // --- 1. Une pile de 3 cases, encore vide ---
        PileInt p = new PileInt(3);
        System.out.println("    PileInt p = new PileInt(3);");
        System.out.println("    p.depiler();");

        // --- 2. depiler() lève PileVideException, interceptée par le catch ---
        /*
         * try / catch obligatoire : depiler() annonce throws PileVideException (exception
         * contrôlée). Ici l'exception se produit à coup sûr : la ligne « (pas d'erreur) » n'est
         * jamais affichée, car throw fait sauter l'exécution directement dans le catch.
         */
        try {
            p.depiler();
            System.out.println("    (pas d'erreur)");
        } catch (PileVideException e) {
            System.out.println("    -> PileVideException interceptée :");
            System.out.println("       " + e.message());
        }
    }
}
