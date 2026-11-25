package corrections.td1;

import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD1_Ex7  -  Série 1, Exercice 7 (TP)
//  Rôle : compiler et exécuter HelloWorld, puis observer ce qui se passe
//  quand le nom du fichier source ne correspond plus à la classe.
//  Les réponses de javac affichées ont été obtenues en compilant ces fichiers.
// =====================================================================

/**
 * Correction de l'exercice 7 (TP) de la série 1 : compiler et exécuter HelloWorld, puis
 * comprendre la règle « une classe public doit être dans un fichier qui porte son nom ».
 *
 * Le programme HelloWorld lui-même est une vraie classe du projet (HelloWorld.java) : on
 * l'exécute réellement avec HelloWorld.main(...). Les erreurs de compilation, elles, ne peuvent
 * pas être produites pendant l'exécution du menu : on affiche le message exact obtenu en
 * compilant chaque fichier avec javac.
 */
public class TD1_Ex7 extends Exercice {

    // =================================================================
    //  CONSTANTES
    // =================================================================
    // Constantes de classe (private static final) : ces textes ne changent jamais et le message
    // MAUVAIS_NOM sert deux fois ; il est écrit une seule fois pour éviter une différence.

    /** Le programme de l'énoncé, accolade finale corrigée. */
    private static final String[] PROGRAMME = {
        "public class HelloWorld",
        "{",
        "    public static void main(String[] args)",
        "    {",
        "        System.out.print(\"Hello World\");",
        "        System.out.println(\" !!\");",
        "        System.out.println(\"Good luck !!\");",
        "    }",
        "}"
    };

    /** Réponse de javac quand la classe publique HelloWorld n'est pas dans HelloWorld.java. */
    private static final String MAUVAIS_NOM =
            "erreur : class HelloWorld is public, should be declared in a file named HelloWorld.java";

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 1) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD1_Ex7() {
        super("HelloWorld et nom du fichier source",
                "Compiler et exécuter HelloWorld, puis comprendre pourquoi le fichier source doit porter le nom de la classe publique.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /** Affiche l'énoncé, exécute HelloWorld, puis montre la réponse de javac pour chaque nom de fichier. */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("1. Recopier le programme HelloWorld, l'enregistrer sous le nom HelloWorld.java, le "
                + "compiler puis l'exécuter.\n"
                + "2. Remplacer le nom du fichier source par HelloWorld1.java, ensuite par "
                + "helloworld.java, et essayer de l'exécuter. Que se passe-t-il et pourquoi ?");

        // --- 2. Question 1 : le programme, son exécution, et la coquille de l'énoncé ---
        Ecran.question(1, "Compiler et exécuter HelloWorld.java");
        Ecran.code(PROGRAMME);
        System.out.println();
        Ecran.texte("Compilation : « javac HelloWorld.java » (crée HelloWorld.class), puis exécution : "
                + "« java HelloWorld ». Le programme affiche :");
        System.out.println();
        // Exécution réelle du programme : on appelle sa méthode main comme le ferait
        // « java HelloWorld ». new String[0] est un tableau vide (aucun paramètre).
        HelloWorld.main(new String[0]);
        System.out.println();
        Ecran.texte("print() n'ajoute pas de retour à la ligne, println() en ajoute un : « Hello World » "
                + "et « !! » s'affichent donc sur la même ligne.");
        System.out.println();
        Ecran.alerte("Coquille dans l'énoncé : le programme se termine par « { » au lieu de « } ». "
                + "Recopié tel quel, il ne compile pas :");
        compilation("HelloWorld.java", "erreur : reached end of file while parsing");

        // --- 3. Question 2 : réponse de javac pour chaque nom de fichier ---
        Ecran.question(2, "Renommer le fichier source en HelloWorld1.java, puis en helloworld.java");
        compilation("HelloWorld.java", "compile sans erreur");
        compilation("HelloWorld1.java", MAUVAIS_NOM);
        compilation("helloworld.java", MAUVAIS_NOM);
        System.out.println();
        // --- 4. Explication ---
        Ecran.texte("Avec HelloWorld1.java comme avec helloworld.java, la compilation échoue : une classe "
                + "déclarée public doit être enregistrée dans un fichier qui porte exactement son nom, "
                + "suivi de .java. Java distingue les majuscules des minuscules : HelloWorld et helloworld "
                + "sont deux noms différents, même sous Windows où le système de fichiers ne fait pas "
                + "la différence.\n"
                + "Comme la compilation échoue, aucun fichier .class n'est créé : « java HelloWorld1 » "
                + "répond « Error: Could not find or load main class HelloWorld1 ». Piège : si le "
                + "HelloWorld.class de la question 1 est encore là, « java HelloWorld » exécute cette "
                + "ancienne version.\n"
                + "Pour corriger : renommer le fichier en HelloWorld.java, ou renommer la classe comme le "
                + "fichier (« public class HelloWorld1 »), ou retirer le mot public. Une classe non "
                + "publique peut être placée dans un fichier de n'importe quel nom : « javac "
                + "HelloWorld1.java » crée alors HelloWorld.class, que l'on lance avec « java HelloWorld ».");
        System.out.println();
        // --- 5. Vérification : la classe non publique compile dans HelloWorld1.java ---
        Ecran.texte("Même programme sans le mot public (« class HelloWorld »), enregistré dans HelloWorld1.java :");
        compilation("HelloWorld1.java", "compile sans erreur");
    }

    // =================================================================
    //  MÉTHODE UTILITAIRE (privée)
    // =================================================================

    /**
     * Affiche la réponse de « javac nomFichier ».
     *
     * Méthode séparée car elle est appelée six fois avec la même présentation ; completer(..., 22)
     * aligne les résultats en colonne.
     *
     * @param nomFichier le nom du fichier compilé
     * @param resultat   la réponse de javac
     */
    private static void compilation(String nomFichier, String resultat) {
        Ecran.champ(Ecran.completer("javac " + nomFichier, 22), resultat);
    }
}
