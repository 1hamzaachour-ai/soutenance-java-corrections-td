package corrections.td1;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD1_Ex4  -  Série 1, Exercice 4
//  Rôle : somme, différence, produit et quotient de deux entiers passés
//  en paramètres sur la ligne de commande.
//  Le programme demandé est la méthode main : java corrections.td1.TD1_Ex4 17 5
//  Depuis le menu, les paramètres sont tapés au clavier puis transmis à main.
// =====================================================================

/**
 * Correction de l'exercice 4 de la série 1 : calculs sur deux entiers reçus en paramètres de main.
 *
 * Pourquoi cette classe a deux rôles :
 * - sa méthode main EST le programme demandé : on peut la lancer seule, avec de vrais
 *   paramètres (java corrections.td1.TD1_Ex4 17 5) ;
 * - comme classe fille de Exercice, elle apparaît aussi dans le menu ; executer() demande alors
 *   les paramètres au clavier et appelle main avec le tableau obtenu.
 * Ainsi le code de la correction est écrit une seule fois et sert dans les deux cas.
 */
public class TD1_Ex4 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 1) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD1_Ex4() {
        super("Opérations sur deux entiers de la ligne de commande",
                "Lire deux entiers passés en paramètres de main (tableau args) et afficher leur somme, différence, produit et quotient.");
    }

    // =================================================================
    //  LE PROGRAMME DE L'EXERCICE
    // =================================================================

    /**
     * Le programme de l'exercice : les deux entiers arrivent dans args.
     *
     * @param args les paramètres de la ligne de commande ; il en faut deux, des entiers
     */
    public static void main(String[] args) {
        // --- 1. Vérification : il faut exactement deux paramètres ---
        /*
         * args.length est le nombre de paramètres reçus. != 2 : « différent de 2 », donc zéro,
         * un, ou trois paramètres et plus. Cette vérification passe AVANT toute lecture de
         * args[0] et args[1] : sans elle, avec un seul paramètre, args[1] lèverait une
         * ArrayIndexOutOfBoundsException et le programme s'arrêterait brutalement.
         * Si la condition est vraie : on explique comment lancer le programme, puis return
         * quitte main tout de suite (le reste n'est pas exécuté).
         */
        if (args.length != 2) {
            System.out.println("Usage : java TD1_Ex4 <entier1> <entier2>   (exemple : java TD1_Ex4 17 5)");
            return;
        }

        // --- 2. Conversion des deux paramètres (des chaînes) en entiers ---
        // a et b sont déclarés AVANT le try : une variable déclarée dans le bloc try n'existerait
        // que dans ce bloc, et on ne pourrait pas l'utiliser pour les calculs plus bas.
        int a, b;
        /*
         * try / catch : Integer.parseInt lève une NumberFormatException si la chaîne n'est pas un
         * entier (« abc », « 3.5 »). On l'attrape pour afficher un message clair au lieu d'arrêter
         * le programme avec une trace d'erreur. Le return du catch quitte main : sans lui, on
         * continuerait avec a et b sans valeur (javac le refuserait d'ailleurs).
         */
        try {
            a = Integer.parseInt(args[0]);      // les paramètres sont des chaînes : on les convertit
            b = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Erreur : les deux paramètres doivent être des entiers.");
            return;
        }

        // --- 3. Calculs et affichage (le quotient seulement si b n'est pas nul) ---
        // Les parenthèses autour de (a + b) sont indispensables : sans elles, "... = " + a + b
        // collerait les deux nombres comme du texte (17 et 5 donneraient « 175 »).
        System.out.println("Somme      : " + a + " + " + b + " = " + (a + b));
        System.out.println("Différence : " + a + " - " + b + " = " + (a - b));
        System.out.println("Produit    : " + a + " * " + b + " = " + (a * b));
        /*
         * Test du diviseur : b == 0 (== compare deux entiers). Une division entière par 0 lève une
         * ArithmeticException qui arrêterait le programme : on la prévient au lieu de la subir.
         * Vrai : on affiche que la division est impossible. Faux (else) : on peut diviser.
         */
        if (b == 0) {
            System.out.println("Quotient   : impossible, on ne peut pas diviser par 0 !");
        } else {
            // a / b entre deux int : division ENTIÈRE (17 / 5 vaut 3) ; % donne le reste (2).
            // (double) a / b : le cast convertit a en double AVANT la division, qui devient
            // réelle (3.4). Écrire (double) (a / b) ne servirait à rien : la division entière
            // serait déjà faite (3.0).
            System.out.println("Quotient   : " + a + " / " + b + " = " + (a / b)
                    + " (division entière, reste " + (a % b) + ")");
            System.out.println("             soit " + ((double) a / b) + " en division réelle");
        }
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE DEPUIS LE MENU
    // =================================================================

    /**
     * Affiche l'énoncé et le principe, fait saisir les paramètres, puis lance main.
     *
     * Depuis le menu, il n'y a pas de vraie ligne de commande : Clavier.lireArguments découpe la
     * ligne tapée en un tableau de chaînes, exactement comme Java remplit args.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Écrire un programme en Java qui lit deux entiers passés en paramètres sur la ligne "
                + "de commande et affiche leur somme, leur différence, leur produit et leur quotient. "
                + "Attention, on ne peut pas diviser par 0.");

        // --- 2. Principe ---
        Ecran.section("Principe");
        Ecran.texte("Les paramètres de la ligne de commande arrivent dans le tableau args de main : "
                + "« java TD1_Ex4 17 5 » donne « args[0] = \"17\" » et « args[1] = \"5\" ». Ce sont des "
                + "chaînes : on les convertit en int avec Integer.parseInt(). Avant de diviser, on vérifie "
                + "que le diviseur n'est pas nul (une division entière par 0 lève une ArithmeticException).\n"
                + "Dans NetBeans : clic droit sur le projet > Properties > Run, Main Class = "
                + "« corrections.td1.TD1_Ex4 » et Arguments = « 17 5 ». Ici, le menu vous demande les "
                + "paramètres.");

        // --- 3. Saisie utilisateur : les paramètres, puis exécution de main ---
        Ecran.section("Exécution");
        String[] args = Clavier.lireArguments("Paramètres (deux entiers séparés par un espace, ex : 17 5) : ");
        // On reconstruit la commande qu'il faudrait taper pour obtenir les mêmes paramètres.
        String commande = "java corrections.td1.TD1_Ex4";
        /*
         * Boucle for : on parcourt toutes les cases du tableau args, du début à la fin ; le
         * nombre de tours est connu (args.length). L'indice i va de 0 à args.length - 1 :
         * i < args.length s'arrête juste après la dernière case. Si l'utilisateur n'a rien tapé,
         * args est vide et la boucle ne fait aucun tour.
         * À chaque tour, on ajoute un espace puis le paramètre à la fin de la commande.
         */
        for (int i = 0; i < args.length; i++) {
            commande = commande + " " + args[i];
        }
        System.out.println("Commande équivalente : " + commande);
        System.out.println();
        // Appel de la méthode main de cette classe, comme si Java l'avait lancée avec ces paramètres.
        main(args);

        // --- 4. Pour tester ---
        Ecran.section("Pour tester");
        Ecran.texte("Relancez l'exercice (R) avec un diviseur nul (« 8 0 »), un seul paramètre (« 8 ») "
                + "ou un paramètre qui n'est pas un entier (« 8 abc »).");
    }
}
