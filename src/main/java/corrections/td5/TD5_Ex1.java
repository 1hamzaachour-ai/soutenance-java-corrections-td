package corrections.td5;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;
import corrections.td5.ex1.Temps;
import corrections.td5.ex1.TestTemps;

// =====================================================================
//  CLASSE TD5_Ex1  -  Série 5, Exercice 1
//  Rôle : présenter la classe Temps et son exception TempsException :
//  énoncé, réponses aux deux questions, messages de javac et exécutions.
//  Le code est dans le paquetage ex1 (Temps, TempsException, TestTemps).
// =====================================================================
/**
 * Présentation de l'exercice 1 de la série 5 (classe Temps et exception TempsException).
 *
 * Le code est dans le paquetage ex1 : Temps (avec sa méthode main corrigée), TempsException et
 * TestTemps. Cette classe affiche l'énoncé, le code demandé pour chaque question, le message de
 * javac quand l'exception n'est pas annoncée, puis exécute réellement Temps.main et TestTemps.
 */
public class TD5_Ex1 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 5) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD5_Ex1() {
        super("Temps valide ou non (exception TempsException)",
                "Faire lever une TempsException par le constructeur de Temps (throw, throws) et la traiter dans main avec try / catch.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, répond aux deux questions, puis exécute main et les essais.
     *
     * Les extraits de code sont des tableaux de String affichés par Ecran.code : c'est du texte,
     * pas du code exécuté. Le vrai code exécuté est celui des classes Temps et TestTemps.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Soit la classe Temps suivante :");
        String[] temps = {"class Temps {", "    private int heures, minutes, secondes;",
            "    Temps(int h, int m, int s) {", "        heures = h; minutes = m; secondes = s;", "    }",
            "    public static void main(String[] args) {", "        Temps t = new Temps(24, 12, 67);", "    }", "}"};
        Ecran.code(temps);

        // --- 2. Question 1 : le constructeur lève TempsException sans la traiter ---
        Ecran.question(1, "Modifier le constructeur de cette classe de manière à ce qu'il lance une exception "
                + "de type TempsException (qu'il ne traite pas) si les heures, les minutes ou les secondes ne "
                + "correspondent pas à un temps valide.");
        String[] constructeur = {"Temps(int h, int m, int s) throws TempsException {",
            "    if ((h >= 0) && (h <= 23)) heures = h;", "    else throw new TempsException();",
            "    if ((m >= 0) && (m <= 59)) minutes = m;", "    else throw new TempsException();",
            "    if ((s >= 0) && (s <= 59)) secondes = s;", "    else throw new TempsException();", "}"};
        Ecran.code(constructeur);
        Ecran.texte("Code : corrections/td5/ex1/Temps.java et TempsException.java. Points clés :\n"
                + "- TempsException est une exception écrite pour l'exercice : elle hérite de Exception "
                + "(cours p.248-250) ;\n"
                + "- throw new TempsException() lève l'exception dès qu'une valeur sort de son intervalle : "
                + "heures de 0 à 23, minutes et secondes de 0 à 59 ;\n"
                + "- le constructeur ne la traite pas : il l'annonce avec throws TempsException (p.246-247), "
                + "et c'est l'appelant qui devra s'en occuper.");
        Ecran.texte("Sans throws, javac refuse, car TempsException est une exception contrôlée :");
        Ecran.champ("javac", "erreur : unreported exception TempsException; must be caught or declared "
                + "to be thrown");
        Clavier.pause();

        // --- 3. Question 2 : main traite l'exception ---
        Ecran.question(2, "Modifier le code de la méthode main de manière à ce que l'exception TempsException "
                + "soit traitée en affichant le message suivant : « Temps invalide ».");
        String[] main = {"public static void main(String[] args) {", "    try {",
            "        Temps t = new Temps(24, 12, 67);", "    } catch (TempsException e) {",
            "        System.out.println(e);       // affiche « Temps invalide »", "    }", "}"};
        Ecran.code(main);
        Ecran.texte("TempsException redéfinit toString() pour renvoyer « Temps invalide » ; println(e) "
                + "l'appelle automatiquement (cours p.128-130). Sans le try / catch, javac répondrait le "
                + "même message « unreported exception » sur la ligne new Temps(24, 12, 67).");
        System.out.println("Exécution de Temps.main :");
        // Exécution réelle de la méthode main corrigée : elle affiche « Temps invalide ».
        Temps.main(new String[0]);
        System.out.println();
        System.out.println("Essais de plusieurs temps (TestTemps) :");
        TestTemps.main(new String[0]);
        System.out.println();

        // --- 4. À retenir ---
        Ecran.section("À retenir");
        Ecran.texte("- 24 h 12 min 67 s est refusé dès les heures : le premier throw arrête le constructeur, "
                + "les minutes et les secondes ne sont même pas vérifiées.\n"
                + "- throw lève l'exception, throws l'annonce dans l'en-tête de la méthode, try / catch la "
                + "traite (cours p.245-250).\n"
                + "- Le cours affiche le message avec une méthode message() (p.250) ; la correction de la "
                + "série redéfinit toString(), ce qui permet d'écrire simplement println(e).");
    }
}
