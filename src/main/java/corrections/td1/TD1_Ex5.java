package corrections.td1;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD1_Ex5  -  Série 1, Exercice 5
//  Rôle : calculatrice à trois paramètres (entier, opérateur, entier).
//  Le programme demandé est la méthode main : java corrections.td1.TD1_Ex5 12 + 5
//  Depuis le menu, les paramètres sont tapés au clavier puis transmis à main.
// =====================================================================

/**
 * Correction de l'exercice 5 de la série 1 : une calculatrice qui lit « entier opérateur entier »
 * sur la ligne de commande.
 *
 * Même organisation que TD1_Ex4 : la méthode main est le programme demandé (on peut la lancer
 * seule avec de vrais paramètres), et executer() la lance depuis le menu avec des paramètres
 * tapés au clavier. Le code de la correction n'est donc écrit qu'une fois.
 */
public class TD1_Ex5 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 1) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD1_Ex5() {
        super("Calculatrice : entier, opérateur, entier",
                "Écrire une calculatrice qui lit un entier, un opérateur et un entier sur la ligne de commande, sans jamais diviser par 0.");
    }

    // =================================================================
    //  LE PROGRAMME DE L'EXERCICE
    // =================================================================

    /**
     * Le programme de l'exercice : args = {entier, opérateur, entier}.
     *
     * @param args trois paramètres : un entier, un opérateur (+ - * /) et un entier
     */
    public static void main(String[] args) {
        // --- 1. Vérification : trois paramètres, l'opérateur sur un seul caractère ---
        /*
         * Deux erreurs possibles, reliées par || (OU) : il suffit d'UNE pour refuser.
         * - args.length != 3 : il n'y a pas exactement trois paramètres ;
         * - args[1].length() != 1 : l'opérateur fait plus d'un caractère (« ++ », « plus »).
         * L'ordre compte : || n'évalue pas la seconde condition si la première est déjà vraie.
         * Avec un seul paramètre, args[1].length() n'est donc jamais calculé, ce qui évite une
         * ArrayIndexOutOfBoundsException. Dans l'ordre inverse, le programme planterait.
         * Si l'une est vraie : message d'usage, puis return quitte main.
         */
        if (args.length != 3 || args[1].length() != 1) {
            System.out.println("Usage : java TD1_Ex5 <entier> <opérateur + - * /> <entier>   (exemple : java TD1_Ex5 12 + 5)");
            return;
        }

        // --- 2. Conversion du premier et du troisième paramètre en entiers ---
        // Même principe que TD1_Ex4 : a et b sont déclarés avant le try pour être utilisables
        // après ; parseInt lève une NumberFormatException si un paramètre n'est pas un entier,
        // et le return du catch arrête le programme proprement.
        int a, b;
        try {
            a = Integer.parseInt(args[0]);
            b = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Erreur : le premier et le troisième paramètre doivent être des entiers.");
            return;
        }

        // --- 3. L'opérateur : le premier (et seul) caractère du deuxième paramètre ---
        // char et non String : un switch sur un char compare directement des caractères ('+').
        // charAt(0) est sûr ici : on a vérifié plus haut que args[1] contient exactement 1 caractère.
        char operateur = args[1].charAt(0);

        // --- 4. Calcul et affichage selon l'opérateur (switch sur un char) ---
        /*
         * switch : un calcul différent pour chaque valeur possible de l'opérateur.
         * - Chaque case se termine par break, sinon l'exécution continuerait dans le case suivant.
         * - case '*', 'x' et 'X' sont regroupés (pas de break entre eux) : les trois mènent au
         *   même produit.
         * - default : tout autre caractère est un opérateur inconnu.
         */
        switch (operateur) {
            case '+':
                System.out.println(a + " + " + b + " = " + (a + b));
                break;
            case '-':
                System.out.println(a + " - " + b + " = " + (a - b));
                break;
            case '*':
            case 'x':       // accepté aussi : sur une vraie ligne de commande, * est remplacé par des noms de fichiers
            case 'X':
                System.out.println(a + " * " + b + " = " + (a * b));
                break;
            case '/':
                // On vérifie le diviseur AVANT de diviser (exigence de l'énoncé) : diviser un
                // int par 0 lèverait une ArithmeticException.
                if (b == 0) {
                    System.out.println("Erreur : division par zéro impossible !");
                } else {
                    // (double) a : division réelle (7 / 2 donne 3.5 et non 3).
                    System.out.println(a + " / " + b + " = " + ((double) a / b));
                }
                break;
            default:
                System.out.println("Erreur : opérateur « " + operateur + " » inconnu (utilisez + - * ou /).");
        }
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE DEPUIS LE MENU
    // =================================================================

    /**
     * Affiche l'énoncé et le principe, fait saisir les paramètres, puis lance main.
     * Les paramètres tapés au clavier sont découpés en tableau par Clavier.lireArguments.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Écrire un programme en Java qui lit trois paramètres : un nombre entier, un caractère "
                + "représentant une opération (+, -, * ou /) et un second nombre entier, puis qui effectue "
                + "le calcul ainsi indiqué et affiche le résultat (attention à éviter les divisions par 0).");

        // --- 2. Principe ---
        Ecran.section("Principe");
        Ecran.texte("Comme à l'exercice 4, les paramètres arrivent dans args : « java TD1_Ex5 12 + 5 » "
                + "donne « args = {\"12\", \"+\", \"5\"} ». Les entiers sont convertis avec Integer.parseInt(), "
                + "l'opérateur est le premier caractère de args[1] (args[1].charAt(0)) et un switch "
                + "choisit le calcul ; la division est faite en réel pour ne pas perdre la partie décimale.\n"
                + "Attention au caractère * sur une vraie ligne de commande : il est remplacé par la liste "
                + "des fichiers du dossier (par le shell sous Linux/macOS, par java.exe lui-même sous "
                + "Windows). Il faut l'écrire entre guillemets, \"*\" (ou '\"*\"' dans PowerShell) ; ce "
                + "programme accepte aussi x pour la multiplication.");

        // --- 3. Saisie utilisateur : les paramètres, puis exécution de main ---
        Ecran.section("Exécution");
        String[] args = Clavier.lireArguments("Paramètres (entier opérateur entier, ex : 12 + 5) : ");
        String commande = "java corrections.td1.TD1_Ex5";
        /*
         * Boucle for sur toutes les cases de args (nombre de tours connu : args.length) pour
         * reconstruire la commande équivalente. i < args.length arrête la boucle après la
         * dernière case ; un tableau vide donne zéro tour.
         */
        for (int i = 0; i < args.length; i++) {
            commande = commande + " " + args[i];
        }
        System.out.println("Commande équivalente : " + commande);
        System.out.println();
        main(args);

        // --- 4. Pour tester ---
        Ecran.section("Pour tester");
        Ecran.texte("Relancez l'exercice (R) avec « 7 / 0 », « 7 x 6 », « 7 % 2 » (opérateur inconnu) "
                + "ou « 7 / 2 ».");
    }
}
