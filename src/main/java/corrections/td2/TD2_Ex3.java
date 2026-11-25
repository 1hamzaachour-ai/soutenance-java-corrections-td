package corrections.td2;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD2_Ex3  -  Série 2, Exercice 3
//  Rôle : présenter le comparateur de forfaits mobiles : énoncé, diagramme
//  UML, exécutions et réponses aux cinq questions.
//  Chaque question modifie les constructeurs de Forfait : chaque version
//  est gardée dans son paquetage (ex3.q1 à ex3.q4) avec sa classe TestForfait.
// =====================================================================

/**
 * Présentation de l'exercice 3 de la série 2 (comparateur de forfaits mobiles) dans le menu.
 *
 * Pourquoi quatre paquetages q1 à q4 : chaque question change les constructeurs de Forfait, ce
 * qui change les instructions qui compilent. Garder chaque version permet d'exécuter réellement
 * chaque étape et de constater la différence. Toutes s'appellent Forfait et TestForfait : on les
 * désigne donc par leur nom complet (corrections.td2.ex3.q1.TestForfait...), sans import.
 */
public class TD2_Ex3 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 2) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD2_Ex3() {
        super("Comparateur de forfaits mobiles (classe Forfait)",
                "Définir la classe Forfait et ses calculs, puis voir ce que change l'ajout ou la suppression d'un constructeur.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, puis répond aux cinq questions en exécutant chaque version de TestForfait.
     * Une pause (Clavier.pause) sépare les questions pour laisser le temps de lire les résultats.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Un forfait mobile est décrit par deux attributs de type float : prixMensuel (en "
                + "dinars) et dataGo (volume d'internet mobile en Go).");

        // --- 2. Question 1 : la classe Forfait et un premier forfait ---
        Ecran.question(1, "Définir la classe Forfait avec : le constructeur Forfait(float p, float d) ; "
                + "float coutAnnuel() (coût sur 12 mois) ; float prixParGo() (prix d'un Go) ; boolean "
                + "estMeilleurQue(Forfait f) (vrai si le prix par Go du forfait courant est inférieur à "
                + "celui de f) ; void affiche() (prix mensuel, data, coût annuel et prix par Go). Dans "
                + "une classe à part TestForfait, main crée et affiche un forfait à 25,5 dinars pour 30 Go.");
        String[] attributs = {"- prixMensuel : float", "- dataGo : float"};
        String[] methodes = {"+ Forfait(p : float, d : float)", "+ coutAnnuel() : float",
            "+ prixParGo() : float", "+ estMeilleurQue(f : Forfait) : boolean", "+ affiche() : void"};
        Ecran.uml("Forfait", attributs, methodes);
        System.out.println("Exécution de TestForfait (paquetage ex3.q1) :");
        corrections.td2.ex3.q1.TestForfait.main(new String[0]);
        System.out.println();
        Ecran.texte("Résultat attendu : coût annuel « 25,5 x 12 = 306 DT » et prix par Go « 25,5 / 30 "
                + "= 0,85 DT ». Dans main on écrit new Forfait(25.5f, 30) : sans le suffixe f, 25.5 est "
                + "un double et l'appel ne compile pas (double -> float, voir « TD 1 Ex 1 ») ; 30 (int) "
                + "est converti automatiquement en float.");
        Clavier.pause();

        // --- 3. Question 2 : deuxième constructeur et comparaison de deux forfaits ---
        Ecran.question(2, "Ajouter un deuxième constructeur sans paramètres Forfait() qui initialise les "
                + "attributs par les valeurs 10 et 5. Compléter main par la création d'un second forfait "
                + "avec ce constructeur, puis afficher lequel des deux forfaits est le plus avantageux.");
        System.out.println("Exécution de TestForfait (paquetage ex3.q2) :");
        corrections.td2.ex3.q2.TestForfait.main(new String[0]);
        System.out.println();
        Ecran.texte("Le forfait 2 coûte 10 DT pour 5 Go, soit 2 DT le Go : le forfait 1 (0,85 DT le Go) "
                + "est plus avantageux. Deux constructeurs portent le même nom avec des paramètres "
                + "différents : c'est la surcharge. Le constructeur sans paramètres réutilise l'autre "
                + "grâce à this(10, 5).");
        Clavier.pause();

        // --- 4. Question 3 : plus de constructeur paramétré, mais modifierForfait() ---
        Ecran.question(3, "Enlever le premier constructeur et ajouter la méthode void modifierForfait(float "
                + "p, float d) qui change le prix et le volume de data du forfait. Créer ensuite dans "
                + "main un forfait à 45 dinars pour 100 Go.");
        System.out.println("Exécution de TestForfait (paquetage ex3.q3) :");
        corrections.td2.ex3.q3.TestForfait.main(new String[0]);
        System.out.println();
        Ecran.texte("Le constructeur Forfait(float, float) n'existant plus, on crée le forfait avec "
                + "Forfait() (10 DT pour 5 Go), puis on le modifie : modifierForfait(45, 100). Résultat "
                + "attendu : 540 DT par an et 0,45 DT le Go. Les instructions new Forfait(25.5f, 30) des "
                + "questions 1 et 2 ne compilent plus.");
        Clavier.pause();

        // --- 5. Question 4 : aucun constructeur écrit, prix par Go = NaN ---
        Ecran.question(4, "Si on enlève tous les constructeurs de la classe Forfait, qu'obtient-on à "
                + "l'exécution avec les instructions « Forfait f = new Forfait(); f.affiche(); » ? "
                + "(attention à la méthode prixParGo())");
        System.out.println("Exécution de TestForfait (paquetage ex3.q4) :");
        corrections.td2.ex3.q4.TestForfait.main(new String[0]);
        System.out.println();
        Ecran.texte("Le programme compile et s'exécute : quand une classe ne définit aucun constructeur, "
                + "le compilateur ajoute le constructeur par défaut Forfait(), sans paramètres. Les "
                + "attributs gardent leur valeur par défaut, 0.0 pour un float : prix 0.0, data 0.0 et "
                + "coût annuel 0.0. prixParGo() calcule alors « 0.0 / 0.0 », ce qui donne NaN (« Not a "
                + "Number ») : avec des réels (float, double), une division par zéro ne déclenche pas "
                + "d'erreur, elle donne NaN, Infinity ou -Infinity (norme IEEE 754). Avec des entiers, "
                + "au contraire, elle lève une exception :");
        divisionsParZero();
        Clavier.pause();

        // --- 6. Question 5 : pourquoi new Forfait() ne compile pas avec le seul constructeur paramétré ---
        Ecran.question(5, "Peut-on écrire les instructions précédentes dans main si on avait gardé "
                + "uniquement le premier constructeur Forfait(float p, float d) ? Justifier.");
        Ecran.texte("Non. Dès qu'une classe définit au moins un constructeur, le compilateur ne fournit "
                + "plus le constructeur par défaut. Avec seulement Forfait(float p, float d), l'appel "
                + "new Forfait() ne correspond à aucun constructeur : c'est une erreur de compilation, "
                + "le programme ne peut même pas être lancé. Il faudrait écrire par exemple "
                + "new Forfait(25.5f, 30), ou ajouter soi-même un constructeur sans paramètres (comme à "
                + "la question 2). Avec la classe de la question 1, javac répond :");
        Ecran.champ("javac", "erreur : constructor Forfait in class Forfait cannot be applied to given types;\n"
                + "  required: float,float\n"
                + "  found:    no arguments\n"
                + "  reason: actual and formal argument lists differ in length");
    }

    // =================================================================
    //  MÉTHODE UTILITAIRE (privée)
    // =================================================================

    /**
     * Compare une division par zéro entière et une division par zéro réelle.
     *
     * Elle complète la réponse à la question 4 : on montre réellement les deux comportements au
     * lieu de seulement les décrire.
     */
    private static void divisionsParZero() {
        // --- 1. Avec des entiers : une ArithmeticException est levée ---
        // On utilise des variables (et non « 0 / 0 » écrit directement) : le calcul se fait donc
        // pendant l'exécution, et c'est bien à ce moment que l'exception est levée.
        int a = 0;
        int b = 0;
        // try / catch : sans lui, l'ArithmeticException arrêterait l'exercice. Le catch l'attrape
        // et l'affiche ("..." + e appelle toString() de l'exception).
        try {
            System.out.println("  int   : 0 / 0     = " + (a / b));
        } catch (ArithmeticException e) {
            System.out.println("  int   : 0 / 0     -> " + e);
        }

        // --- 2. Avec des réels : pas d'exception, le résultat vaut NaN ou Infinity ---
        // Pas besoin de try ici : une division de float par 0 ne lève jamais d'exception.
        float zero = 0;
        System.out.println("  float : 0 / 0     = " + (zero / zero));
        System.out.println("  float : 25.5 / 0  = " + (25.5f / zero));
    }
}
