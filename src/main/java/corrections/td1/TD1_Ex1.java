package corrections.td1;

import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD1_Ex1  -  Série 1, Exercice 1
//  Rôle : dire quelles affectations entre types primitifs sont correctes,
//  et quelles conversions sont mises en jeu.
//  Les affectations correctes sont réellement exécutées ; les incorrectes
//  sont exécutées dans leur version corrigée (avec un cast). La ligne
//  « javac » de chaque réponse reprend le message obtenu en compilant
//  l'instruction.
// =====================================================================

/**
 * Correction de l'exercice 1 de la série 1 : les conversions entre types primitifs.
 *
 * Pourquoi cette classe existe : chaque exercice du projet est une classe fille de Exercice.
 * Main la range dans le tableau EXERCICES_TD1 et l'exécute avec executer(), sans connaître son
 * contenu (polymorphisme).
 *
 * Responsabilités : afficher l'énoncé et un rappel de cours, puis, pour chacune des 11
 * expressions de l'énoncé, dire si elle est correcte, pourquoi, et montrer le résultat réel.
 * Pour les expressions incorrectes (qui ne compileraient pas), on exécute leur version corrigée
 * avec un cast, et on affiche le message que donne javac.
 *
 * extends Exercice : TD1_Ex1 hérite du titre, de l'objectif et de leurs accesseurs, et doit
 * écrire la méthode executer() (abstraite dans Exercice).
 */
public class TD1_Ex1 extends Exercice {

    // =================================================================
    //  CONSTANTE
    // =================================================================

    // Le même message de javac sert pour trois expressions (1, 3 et 7) : une constante évite de
    // recopier trois fois ce long texte (et d'introduire une faute dans une des copies).
    // private static final : réservée à cette classe, partagée (static), non modifiable (final).

    /** Message de javac pour une affectation int -> byte sans cast. */
    private static final String INT_VERS_BYTE = "erreur : incompatible types: possible lossy conversion from int to byte";

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 1) et son objectif (affiché avant l'exécution).
     *
     * super(titre, objectif) appelle le constructeur de la classe mère Exercice, qui range ces
     * deux textes dans ses attributs private. Il doit être la première instruction du
     * constructeur. Ce constructeur est public et sans paramètre : Main crée l'objet avec
     * new TD1_Ex1().
     */
    public TD1_Ex1() {
        super("Conversions entre types primitifs",
                "Repérer les affectations incorrectes entre types primitifs et nommer les conversions mises en jeu (implicites ou cast).");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, un rappel de cours, puis la correction des 11 expressions.
     *
     * Redéfinition de la méthode abstraite executer() de Exercice : c'est cette version que Java
     * exécute quand Main appelle exercice.executer() sur un objet TD1_Ex1 (liaison dynamique).
     * Le programme suit l'ordre de l'énoncé : chaque expression est d'abord exécutée (dans sa
     * version qui compile), puis la méthode expression(...) affiche la réponse avec le résultat.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Soit les déclarations : byte b ; short p ; int n ; long q ; float x ; double y ;\n"
                + "Parmi les expressions suivantes, lesquelles sont incorrectes et pourquoi ? Lorsque "
                + "l'expression est correcte, citer les conversions éventuellement mises en jeu.");

        // --- 2. Rappel de cours ---
        Ecran.section("Rappel de cours");
        Ecran.texte("- Conversion élargissante (implicite) : byte -> short -> int -> long -> float -> double.\n"
                + "- Conversion dégradante (vers un type plus petit) : interdite sans cast explicite, "
                + "sauf pour une constante entière dont la valeur tient dans le type de destination.\n"
                + "- Promotion numérique : dans un calcul, byte, short et char sont d'abord convertis en "
                + "int ; si l'un des opérandes est long, float ou double, l'autre est converti dans ce type.\n"
                + "- Littéraux : 25 est un int, 2.5 est un double, 2.5f est un float.");

        // --- 3. Correction ---
        Ecran.section("Correction");
        Ecran.texte("La ligne « javac » indique ce que répond le compilateur Java quand on compile "
                + "l'instruction avec les déclarations de l'énoncé.");

        // --- 4. Initialisation : les variables de l'énoncé, avec des valeurs pour les exécuter ---
        // Les six types de l'énoncé, du plus petit au plus grand : byte (8 bits, de -128 à 127),
        // short (16 bits), int (32 bits), long (64 bits), puis les réels float (32 bits) et
        // double (64 bits). b, p, x et y ne reçoivent pas de valeur ici : chaque expression
        // leur en donne une avant qu'on les lise (Java refuse de lire une variable locale
        // jamais initialisée).
        // n = 300 est choisi exprès : 300 ne tient pas dans un byte, ce qui montre l'effet du cast.
        // 123456789L : le suffixe L indique un littéral de type long.
        byte b;
        short p;
        int n = 300;
        long q = 123456789L;
        float x;
        double y;

        // --- 5. Calculs et affichage : les 11 expressions, l'une après l'autre ---
        // Pour chaque expression : on exécute l'instruction (corrigée avec un cast si
        // l'originale ne compile pas), puis on appelle expression(...) pour afficher le
        // verdict, l'explication, la valeur obtenue et la réponse de javac.
        // (byte) n est un cast : on force la conversion int -> byte. Java garde alors seulement
        // les 8 bits de poids faible, d'où 300 -> 44 : la valeur est faussée, c'est pour cela que
        // le compilateur exige qu'on écrive le cast volontairement.
        b = (byte) n;
        expression(1, "b = n ;", false,
                "n est un int (32 bits) et b un byte (8 bits) : conversion dégradante, refusée sans cast "
                + "car la valeur de n risque de ne pas tenir dans un byte.",
                "b = (byte) n ;   avec n = 300  =>  b = " + b
                + " (seuls les 8 bits de poids faible sont gardés : 300 - 256).",
                INT_VERS_BYTE);

        b = 25;
        expression(2, "b = 25 ;", true,
                "25 est une constante de type int, convertie implicitement en byte : c'est permis parce "
                + "que c'est une constante et que sa valeur tient dans un byte (de -128 à 127).",
                "b = " + b,
                "compile sans erreur");

        b = (byte) 500;
        expression(3, "b = 500 ;", false,
                "500 est bien une constante, mais elle ne tient pas dans un byte (127 au maximum) : la "
                + "conversion implicite est refusée.",
                "b = (byte) 500 ;   donne b = " + b + " (500 - 512) : la valeur est faussée, d'où l'interdiction.",
                INT_VERS_BYTE);

        x = 2 * q;
        expression(4, "x = 2*q ;", true,
                "2 (int) est promu en long pour être multiplié par q (promotion numérique) ; le résultat "
                + "long est ensuite converti en float à l'affectation : conversion élargissante, permise "
                + "même si elle peut perdre de la précision.",
                "q = " + q + "  =>  x = " + x + "  (la valeur exacte " + (2 * q)
                + " n'est pas représentable en float).",
                "compile sans erreur");

        b = 12;
        y = b * b;
        expression(5, "y = b*b ;", true,
                "les deux byte sont promus en int pour la multiplication ; le résultat int est converti "
                + "en double à l'affectation (conversion élargissante).",
                "b = 12  =>  y = " + y,
                "compile sans erreur");

        p = (short) (b * b);
        expression(6, "p = b*b ;", false,
                "b*b est un int (promotion des byte en int) et int -> short est une conversion dégradante. "
                + "Le compilateur ne connaît pas la valeur de b*b : ce n'est pas une constante.",
                "p = (short) (b*b) ;   avec b = 12  =>  p = " + p + ". Attention aux parenthèses : "
                + "« (short) b*b » ne compile toujours pas, car le cast ne porte que sur le premier b.",
                "erreur : incompatible types: possible lossy conversion from int to short");

        b = (byte) (b + 5);
        expression(7, "b = b+5 ;", false,
                "b+5 est un int (b est promu en int) : on retombe sur une affectation int -> byte, "
                + "refusée sans cast.",
                "b = (byte) (b+5) ;   avec b = 12  =>  b = " + b + ". Remarque : « b += 5; » compile, "
                + "car l'opérateur += contient un cast implicite.",
                INT_VERS_BYTE);

        x = 2.5f;
        expression(8, "x = 2.5f ;", true,
                "aucune conversion : 2.5f est un littéral de type float (suffixe f). Attention, le suffixe "
                + "doit être collé au nombre : écrit « 2.5 f » avec un espace, comme dans l'énoncé, c'est "
                + "une erreur de syntaxe (ligne « avec espace »).",
                "x = " + x,
                "compile sans erreur");
        Ecran.champ("avec espace", "erreur : ';' expected");

        x = (float) 2.5;
        expression(9, "x = 2.5 ;", false,
                "sans suffixe, 2.5 est un littéral double (64 bits) ; double -> float est une conversion "
                + "dégradante, refusée sans cast.",
                "x = 2.5f ;  ou  x = (float) 2.5 ;   =>  x = " + x,
                "erreur : incompatible types: possible lossy conversion from double to float");

        y = 2.5f;
        expression(10, "y = 2.5f ;", true,
                "le float 2.5f est converti en double à l'affectation (conversion élargissante, sans perte).",
                "y = " + y,
                "compile sans erreur");

        y = 2.5;
        expression(11, "y = 2.5 ;", true,
                "aucune conversion : 2.5 est déjà un double.",
                "y = " + y,
                "compile sans erreur");

        // --- 6. Bilan ---
        Ecran.section("Bilan");
        Ecran.texte("Incorrectes : 1, 3, 6, 7 et 9.    Correctes : 2, 4, 5, 8, 10 et 11.");
    }

    // =================================================================
    //  MÉTHODE UTILITAIRE (privée)
    // =================================================================

    /**
     * Affiche la réponse pour une expression : verdict, explication, exécution et message de javac.
     *
     * Pourquoi une méthode séparée : les 11 réponses ont exactement la même présentation. Sans
     * cette méthode, il faudrait recopier 11 fois les mêmes lignes d'affichage ; ici on ne passe
     * que ce qui change. private : elle ne sert qu'à cette classe. static : elle n'utilise aucun
     * attribut d'objet.
     *
     * @param numero      numéro de l'expression dans l'énoncé (1 à 11)
     * @param instruction l'instruction telle qu'écrite dans l'énoncé
     * @param correcte    true si l'instruction compile, false sinon
     * @param explication pourquoi elle est correcte (conversions) ou incorrecte
     * @param execution   le résultat obtenu en l'exécutant (ou sa version corrigée)
     * @param javac       ce que répond le compilateur
     */
    private static void expression(int numero, String instruction, boolean correcte,
            String explication, String execution, String javac) {
        // --- 1. Verdict ---
        String verdict;
        // correcte est déjà un boolean : on écrit if (correcte), inutile d'écrire == true.
        // Vrai : l'expression compile ; faux (else) : elle est refusée par javac.
        if (correcte) {
            verdict = "[CORRECTE]";
        } else {
            verdict = "[INCORRECTE]";
        }

        // --- 2. Numéro aligné à droite sur 4 caractères : "   1) ..." ou "  10) ..." ---
        // condition ? valeurSiVrai : valeurSiFaux est l'opérateur conditionnel : un if / else
        // écrit en une seule expression. Un nombre à un chiffre reçoit un espace de plus, pour
        // que les « ) » de 1 à 11 soient alignés.
        String debut = (numero < 10 ? "   " : "  ") + numero + ") " + instruction;

        // --- 3. Affichage de la réponse ---
        // completer remplit avec des espaces : le verdict tombe ainsi tout à droite de la ligne.
        // Les étiquettes changent selon le verdict (opérateur ? : à nouveau).
        System.out.println();
        System.out.println(Ecran.completer(debut, Ecran.LARGEUR - verdict.length()) + verdict);
        Ecran.champ(correcte ? "Conversions" : "Pourquoi", explication);
        Ecran.champ(correcte ? "Exécution" : "Correction", execution);
        Ecran.champ("javac", javac);
    }
}
