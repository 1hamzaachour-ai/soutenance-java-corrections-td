package corrections.td1;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD1_Ex2  -  Série 1, Exercice 2
//  Rôle : réécrire la boucle for de l'énoncé (somme de 4 entiers) avec
//  while, puis avec do..while, et exécuter les deux versions.
// =====================================================================

/**
 * Correction de l'exercice 2 de la série 1 : une même boucle écrite avec for, while et do..while.
 *
 * Pourquoi cette classe existe : elle présente l'exercice dans le menu (titre, objectif) et
 * l'exécute (énoncé, deux réponses, exécution réelle). Les deux programmes demandés sont écrits
 * dans deux méthodes séparées, sommeAvecWhile() et sommeAvecDoWhile(), pour qu'on puisse les
 * lancer et les comparer.
 *
 * Responsabilités : afficher le programme de l'énoncé et ses deux réécritures, faire choisir la
 * version à exécuter, puis rappeler la différence entre while et do..while.
 */
public class TD1_Ex2 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 1) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD1_Ex2() {
        super("Boucle for réécrite avec while et do..while",
                "Réécrire une boucle for avec while, puis avec do..while, en gardant le même résultat.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé et les deux réponses, puis exécute la version choisie.
     *
     * Les programmes sont affichés à partir de tableaux de String (une case par ligne) passés
     * à Ecran.code : c'est du texte, il n'est pas exécuté. Dans ces chaînes, \" représente un
     * guillemet à l'intérieur du texte (sans le \, le guillemet terminerait la chaîne).
     */
    public void executer() {
        // --- 1. Énoncé : le programme avec for ---
        Ecran.section("Énoncé");
        Ecran.enonce("Le programme suivant lit 4 entiers au clavier et affiche leur somme. Écrire un "
                + "programme réalisant la même chose en employant à la place de l'instruction for : "
                + "1. une instruction while ; 2. une instruction do..while.");
        String[] versionFor = {
            "som = 0;",
            "for (i = 0; i < 4; i++) {",
            "    System.out.println(\"donnez un entier \");",
            "    n = sc.nextInt();",
            "    som += n;",
            "}",
            "System.out.println(\"Somme : \" + som);"
        };
        Ecran.code(versionFor);

        // --- 2. Réponse 1 : version while ---
        Ecran.section("1. Version while");
        String[] versionWhile = {
            "som = 0;",
            "i = 0;                     // initialisation du for",
            "while (i < 4) {            // condition du for, testée AVANT chaque tour",
            "    System.out.println(\"donnez un entier \");",
            "    n = sc.nextInt();",
            "    som += n;",
            "    i++;                   // incrémentation du for",
            "}",
            "System.out.println(\"Somme : \" + som);"
        };
        Ecran.code(versionWhile);

        // --- 3. Réponse 2 : version do..while ---
        Ecran.section("2. Version do..while");
        String[] versionDoWhile = {
            "som = 0;",
            "i = 0;",
            "do {",
            "    System.out.println(\"donnez un entier \");",
            "    n = sc.nextInt();",
            "    som += n;",
            "    i++;",
            "} while (i < 4);           // condition testée APRÈS chaque tour",
            "System.out.println(\"Somme : \" + som);"
        };
        Ecran.code(versionDoWhile);

        // --- 4. Saisie utilisateur : choix de la version, puis exécution ---
        Ecran.section("Exécution");
        // Bornes 1 et 3 : toute autre saisie est redemandée par lireEntier.
        int version = Clavier.lireEntier("Version à exécuter (1 = while, 2 = do..while, 3 = les deux) : ", 1, 3);
        /*
         * Deux if indépendants (et non if / else) : avec le choix 3, les DEUX blocs doivent
         * s'exécuter l'un après l'autre. Avec un else, le second bloc serait sauté.
         * version == 1 || version == 3 : « la version while est demandée seule OU avec l'autre ».
         * || (OU) suffit qu'une des deux égalités soit vraie. == compare deux entiers.
         */
        if (version == 1 || version == 3) {
            System.out.println();
            System.out.println(">> Version while");
            sommeAvecWhile();
        }
        // Même raisonnement pour do..while : demandée seule (2) ou avec l'autre (3).
        if (version == 2 || version == 3) {
            System.out.println();
            System.out.println(">> Version do..while");
            sommeAvecDoWhile();
        }

        // --- 5. À retenir ---
        Ecran.section("À retenir");
        Ecran.texte("- while teste la condition AVANT chaque tour : si elle est fausse dès le départ, le "
                + "corps n'est jamais exécuté.\n"
                + "- do..while teste la condition APRÈS chaque tour : le corps est exécuté au moins une fois.\n"
                + "- Ici la boucle fait toujours 4 tours : les trois versions sont équivalentes. Avec while "
                + "et do..while, il ne faut pas oublier d'initialiser i avant la boucle et de l'incrémenter "
                + "dans le corps, sinon la boucle ne s'arrête jamais.");
    }

    // =================================================================
    //  LES DEUX VERSIONS DU PROGRAMME
    // =================================================================

    /**
     * Version 1 : lit 4 entiers avec une boucle while et affiche leur somme.
     *
     * Méthode séparée (et public static) pour que la réponse soit un vrai programme, exécutable
     * et lisible seul, identique à ce qui est affiché dans la correction.
     */
    public static void sommeAvecWhile() {
        // --- 1. Initialisation ---
        // i compte les entiers déjà lus, n est l'entier qu'on vient de lire, som la somme.
        // int : on additionne des entiers. som = 0 est indispensable : une somme part de 0, et
        // Java refuse d'utiliser une variable locale qui n'a pas reçu de valeur.
        // i = 0 joue le rôle de l'initialisation « i = 0 » du for.
        int i, n, som;
        som = 0;
        i = 0;

        // --- 2. Saisie et calcul : la condition est testée AVANT chaque tour ---
        /*
         * Boucle while : c'est la réponse demandée par l'énoncé. Les trois parties du for sont
         * réparties : initialisation avant la boucle (i = 0), condition dans le while (i < 4),
         * incrémentation à la fin du corps (i++).
         * La condition i < 4 est testée AVANT chaque tour : avec i = 0, 1, 2, 3 elle est vraie
         * (4 tours) ; quand i vaut 4, elle est fausse et la boucle s'arrête.
         * Si on oubliait i++, i resterait à 0 et la boucle ne s'arrêterait jamais. Avec i <= 4,
         * on lirait 5 entiers au lieu de 4.
         */
        while (i < 4) {
            System.out.println("donnez un entier ");
            n = Clavier.lireEntier("");         // équivaut à n = sc.nextInt();
            som += n;                           // som += n veut dire som = som + n
            i++;                                // un entier de plus a été lu
        }

        // --- 3. Affichage ---
        System.out.println("Somme : " + som);
    }

    /**
     * Version 2 : lit 4 entiers avec une boucle do..while et affiche leur somme.
     */
    public static void sommeAvecDoWhile() {
        // --- 1. Initialisation ---
        // Mêmes variables et mêmes valeurs de départ que dans la version while.
        int i, n, som;
        som = 0;
        i = 0;

        // --- 2. Saisie et calcul : la condition est testée APRÈS chaque tour ---
        /*
         * Boucle do..while : le corps est exécuté une première fois SANS test, puis la condition
         * i < 4 est vérifiée à la fin de chaque tour. Ici le résultat est le même qu'avec while,
         * parce qu'on veut toujours au moins un tour (4 entiers à lire).
         * Différence importante : si on demandait 0 entier (i < 0), while ne ferait aucun tour
         * alors que do..while en ferait quand même un. do..while convient donc quand le corps
         * doit s'exécuter au moins une fois (par exemple afficher un menu avant de lire le choix).
         * Arrêt : après le 4e tour, i vaut 4 et la condition devient fausse.
         * Le point-virgule après while (i < 4) est obligatoire dans un do..while.
         */
        do {
            System.out.println("donnez un entier ");
            n = Clavier.lireEntier("");         // équivaut à n = sc.nextInt();
            som += n;
            i++;
        } while (i < 4);

        // --- 3. Affichage ---
        System.out.println("Somme : " + som);
    }
}
