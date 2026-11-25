package corrections.td2;

import corrections.outils.Ecran;
import corrections.outils.Exercice;
import corrections.td2.ex1.Morceau;
import corrections.td2.ex1.TestMorceau;

// =====================================================================
//  CLASSE TD2_Ex1  -  Série 2, Exercice 1
//  Rôle : présenter le lecteur de musique : énoncé, diagramme UML,
//  exécution de TestMorceau et réponses aux questions. Le code demandé
//  est dans le paquetage ex1 (classes Morceau et TestMorceau).
// =====================================================================

/**
 * Présentation de l'exercice 1 de la série 2 (lecteur de musique) dans le menu de la soutenance.
 *
 * Pourquoi la correction est séparée de cette classe : le code demandé par l'énoncé (Morceau et
 * TestMorceau) est rangé à part, dans le paquetage ex1, exactement comme on le rendrait. Cette
 * classe-ci ne fait que le présenter : énoncé, diagramme UML, exécution, réponses.
 * Classe fille de Exercice : Main la lance avec executer() comme tous les autres exercices.
 */
public class TD2_Ex1 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 2) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD2_Ex1() {
        super("Lecteur de musique (classe Morceau)",
                "Écrire la classe Morceau sans constructeur, la tester avec des données tapées au clavier et observer les valeurs par défaut.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, puis répond aux trois questions.
     *
     * Les diagrammes UML sont décrits par deux tableaux de String (attributs, méthodes) passés à
     * Ecran.uml : « - » signifie private et « + » public, comme en UML.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Un morceau, utilisé par une application de streaming musical, est composé des "
                + "attributs titre (String), minutes (int) et secondes (int). Il possède les méthodes "
                + "setTitre(String t), setMinutes(int m), setSecondes(int s), int dureeEnSecondes() "
                + "et void afficherMorceau(), qui affiche le morceau selon le modèle « --- Mon titre --- » "
                + "puis « 3min 42s ».");

        // --- 2. Question 1 : la classe Morceau (diagramme UML) ---
        Ecran.question(1, "Écrire la classe Morceau sans définir de constructeur.");
        String[] attributs = {"- titre : String", "- minutes : int", "- secondes : int"};
        String[] methodes = {"+ setTitre(t : String) : void", "+ setMinutes(m : int) : void",
            "+ setSecondes(s : int) : void", "+ dureeEnSecondes() : int", "+ afficherMorceau() : void"};
        Ecran.uml("Morceau", attributs, methodes);
        Ecran.texte("Code : corrections/td2/ex1/Morceau.java. Les attributs sont private et modifiés par "
                + "les méthodes set ; dureeEnSecondes() renvoie « minutes * 60 + secondes ». Aucun "
                + "constructeur n'est écrit.");

        // --- 3. Question 2 : exécution de TestMorceau ---
        Ecran.question(2, "Écrire la classe TestMorceau : dans main, créer un objet Morceau désigné par "
                + "une référence M, modifier son titre, ses minutes et ses secondes selon les données "
                + "introduites par l'utilisateur, puis afficher son nouveau contenu ainsi que sa durée "
                + "en secondes.");
        // On exécute réellement le programme de test, en appelant sa méthode main.
        // new String[0] : un tableau de paramètres vide, comme un lancement sans paramètre.
        TestMorceau.main(new String[0]);

        // --- 4. Question 3 : un morceau vide, affiché avant tout appel aux méthodes set ---
        Ecran.question(3, "Qu'affiche afficherMorceau() si on l'appelle juste après la création de M, "
                + "avant tout appel aux méthodes set ? Justifier.");
        System.out.println("Morceau M = new Morceau();");
        System.out.println("M.afficherMorceau();   affiche :");
        System.out.println();
        // Démonstration réelle : un morceau créé avec le constructeur par défaut et affiché
        // aussitôt montre les valeurs par défaut des attributs (null et 0).
        Morceau M = new Morceau();
        M.afficherMorceau();
        System.out.println();
        Ecran.texte("Justification : aucun constructeur n'étant écrit, le compilateur ajoute un "
                + "constructeur par défaut, sans paramètres, qui ne donne aucune valeur aux attributs. "
                + "Ceux-ci gardent donc leur valeur par défaut : null pour une référence (le String "
                + "titre) et 0 pour un int (minutes et secondes). Concaténer null avec une chaîne "
                + "produit simplement le texte « null », sans erreur.\n"
                + "Attention : ces valeurs par défaut ne concernent que les attributs. Une variable "
                + "locale utilisée sans avoir été initialisée provoque une erreur de compilation.");
    }
}
