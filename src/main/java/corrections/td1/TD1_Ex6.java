package corrections.td1;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD1_Ex6  -  Série 1, Exercice 6 (TP)
//  Rôle : réponses aux recherches dans la documentation du JDK
//  (Double.parseDouble, Integer.parseInt, Float.parseFloat, Math.random),
//  puis essais de ces méthodes.
// =====================================================================

/**
 * Correction de l'exercice 6 (TP) de la série 1 : trouver dans la documentation du JDK les
 * méthodes de conversion d'une chaîne en nombre, et la méthode qui tire un nombre au hasard.
 *
 * Responsabilités : afficher les réponses, puis les faire essayer : l'utilisateur tape ses
 * propres chaînes (convertir), et le programme tire 10 lancers de dé avec Math.random().
 */
public class TD1_Ex6 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 1) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD1_Ex6() {
        super("Documentation du JDK : parseXxx() et Math.random()",
                "Chercher dans la documentation du JDK parseDouble, parseInt, parseFloat et Math.random, puis les essayer.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé et les réponses, puis fait essayer les conversions et Math.random().
     * Dans les chaînes affichées, \" représente un guillemet (le \ empêche de fermer la chaîne).
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("À l'aide de la documentation du JDK (http://docs.oracle.com/javase/7/docs/api/) :\n"
                + "1. trouver comment convertir une chaîne de caractères en double à l'aide de la classe Double ;\n"
                + "2. en int à l'aide de la classe Integer ;\n"
                + "3. en float à l'aide de la classe Float ;\n"
                + "4. trouver dans la classe Math une méthode permettant de tirer des nombres au hasard.");

        // --- 2. Réponses ---
        Ecran.section("Réponses");
        Ecran.texte("1) Chaîne -> double : méthode statique Double.parseDouble(String s)");
        Ecran.ligneDeCode("double d = Double.parseDouble(\"3.14\");    // d vaut 3.14");
        Ecran.texte("2) Chaîne -> int : méthode statique Integer.parseInt(String s)");
        Ecran.ligneDeCode("int i = Integer.parseInt(\"42\");           // i vaut 42");
        Ecran.texte("3) Chaîne -> float : méthode statique Float.parseFloat(String s)");
        Ecran.ligneDeCode("float f = Float.parseFloat(\"2.5\");        // f vaut 2.5");
        Ecran.texte("4) Math.random() renvoie un double au hasard dans [0 ; 1[ (1 exclu).");
        String[] hasard = {
            "int de = 1 + (int) (Math.random() * 6);    // entier entre 1 et 6",
            "// entier entre a et b : a + (int) (Math.random() * (b - a + 1))"
        };
        Ecran.code(hasard);
        System.out.println();
        Ecran.texte("À savoir :\n"
                + "- ce sont des méthodes de classe (static) : on les appelle avec le nom de la classe, "
                + "sans créer d'objet ;\n"
                + "- si la chaîne ne représente pas un nombre valide, ces méthodes lèvent une "
                + "NumberFormatException, que l'on intercepte avec try / catch ;\n"
                + "- le séparateur décimal est le point : \"12,5\" est refusé ;\n"
                + "- le cast (int) tronque le réel, il ne l'arrondit pas : c'est ce qui donne un entier "
                + "entre 1 et 6 ;\n"
                + "- documentation de la version actuelle : https://docs.oracle.com/en/java/javase/25/docs/api/");

        // --- 3. Saisie utilisateur : conversion de chaînes, jusqu'à une ligne vide ---
        Ecran.section("Essai : convertir vos propres chaînes");
        Ecran.texte("Tapez une chaîne (ex : 42, 3.14, 12,5 ou abc), puis une ligne vide pour passer à la suite.");
        // Première lecture AVANT la boucle : la condition du while a besoin d'une valeur de s.
        String s = Clavier.lireLigne("Chaîne : ").trim();
        /*
         * Boucle while avec « valeur sentinelle » : on ne sait pas combien de chaînes
         * l'utilisateur veut essayer ; c'est lui qui décide d'arrêter en tapant une ligne vide.
         * Le nombre de tours est inconnu, donc pas de for.
         * Condition s.length() != 0 : la chaîne n'est pas vide. Si la première ligne est déjà
         * vide, aucun tour : c'est pourquoi on n'a pas pris un do..while (qui essaierait de
         * convertir la ligne vide).
         * Évolution : s est relue à la fin de chaque tour. Si on oubliait cette relecture, s ne
         * changerait jamais et la boucle tournerait sans fin.
         */
        while (s.length() != 0) {
            convertir(s);
            s = Clavier.lireLigne("Chaîne : ").trim();
        }

        // --- 4. Calculs et affichage : 10 lancers de dé et un réel au hasard ---
        Ecran.section("Essai : Math.random()");
        // print (sans « ln ») : les 10 résultats restent sur la même ligne.
        System.out.print("10 lancers de dé : ");
        /*
         * Boucle for : on veut exactement 10 lancers, le nombre de tours est connu à l'avance.
         * k compte les lancers déjà faits : 0, 1, ..., 9 ; quand k vaut 10, k < 10 est faux et la
         * boucle s'arrête (10 tours). k n'est pas utilisé dans le corps, il sert seulement à compter.
         */
        for (int k = 0; k < 10; k++) {
            // Math.random() donne un réel dans [0 ; 1[. Multiplié par 6 : dans [0 ; 6[. Le cast
            // (int) garde la partie entière (0 à 5), puis + 1 donne un entier de 1 à 6.
            int de = 1 + (int) (Math.random() * 6);
            System.out.print(de + " ");
        }
        System.out.println();
        System.out.println("Un réel au hasard dans [0 ; 1[ : " + Math.random());
    }

    // =================================================================
    //  MÉTHODE UTILITAIRE (privée)
    // =================================================================

    /**
     * Essaie les trois conversions sur la chaîne s et affiche le résultat ou l'erreur.
     *
     * Pourquoi trois blocs try / catch séparés (et non un seul) : avec un seul try, la première
     * conversion qui échoue ferait sauter les suivantes. Avec « 3.14 », parseInt échoue, mais on
     * veut quand même voir que parseDouble et parseFloat réussissent.
     *
     * @param s la chaîne tapée par l'utilisateur
     */
    private static void convertir(String s) {
        // --- 1. Conversion en double ---
        // Si parseDouble réussit, println affiche le nombre ; s'il lève une
        // NumberFormatException, l'exécution saute directement dans le catch.
        try {
            System.out.println("  Double.parseDouble(\"" + s + "\") = " + Double.parseDouble(s));
        } catch (NumberFormatException e) {
            System.out.println("  Double.parseDouble(\"" + s + "\") -> NumberFormatException");
        }

        // --- 2. Conversion en int ---
        try {
            System.out.println("  Integer.parseInt(\"" + s + "\")    = " + Integer.parseInt(s));
        } catch (NumberFormatException e) {
            System.out.println("  Integer.parseInt(\"" + s + "\")    -> NumberFormatException");
        }

        // --- 3. Conversion en float ---
        try {
            System.out.println("  Float.parseFloat(\"" + s + "\")    = " + Float.parseFloat(s));
        } catch (NumberFormatException e) {
            System.out.println("  Float.parseFloat(\"" + s + "\")    -> NumberFormatException");
        }
    }
}
