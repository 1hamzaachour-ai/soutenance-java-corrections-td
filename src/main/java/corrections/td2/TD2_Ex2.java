package corrections.td2;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD2_Ex2  -  Série 2, Exercice 2
//  Rôle : présenter les cartes à collectionner : énoncé, diagrammes UML,
//  exécutions et réponses aux sept questions.
//  Paquetage ex2.q1 : première version de Carte (questions 1 à 3).
//  Paquetage ex2.q4 : version encapsulée (questions 4 à 7).
// =====================================================================

/**
 * Présentation de l'exercice 2 de la série 2 (cartes à collectionner) dans le menu.
 *
 * Pourquoi deux paquetages (q1 et q4) : la question 4 réécrit la classe Carte. Pour montrer la
 * triche de la question 3 ET la version corrigée, il faut garder les deux versions ; deux classes
 * de même nom ne peuvent exister que dans deux paquetages différents.
 * Cette classe présente l'énoncé, les diagrammes UML, les exécutions et les explications.
 */
public class TD2_Ex2 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 2) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD2_Ex2() {
        super("Cartes à collectionner (classe Carte)",
                "Écrire la classe Carte, montrer qu'on peut tricher tant que ses attributs ne sont pas private, puis l'encapsuler.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, puis répond aux sept questions.
     *
     * Les deux versions de TestCarte ont le même nom de classe : on les appelle donc par leur nom
     * complet (corrections.td2.ex2.q1.TestCarte, corrections.td2.ex2.q4.TestCarte) au lieu de
     * les importer ; deux import du même nom de classe seraient refusés par javac.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Une carte d'un album de cartes à collectionner est caractérisée par son nom (String) "
                + "et sa valeur en points (int).");

        // --- 2. Question 1 : première version de Carte (diagramme UML) ---
        Ecran.question(1, "Écrire une classe Carte avec une méthode afficher() qui imprime la carte sous "
                + "la forme [Dragon de feu — 80 pts] et une méthode modifier() qui change le nom et la "
                + "valeur par les valeurs reçues en arguments. On utilisera le constructeur par défaut.");
        String[] attributsQ1 = {"~ nom : String", "~ valeur : int"};
        String[] methodesQ1 = {"+ afficher() : void", "+ modifier(n : String, v : int) : void"};
        Ecran.uml("Carte", attributsQ1, methodesQ1);
        Ecran.texte("Code : corrections/td2/ex2/q1/Carte.java. Les attributs n'ont pas de modificateur "
                + "d'accès (~ en UML : visibilité « paquetage »). Aucun constructeur n'est écrit : Java "
                + "fournit le constructeur par défaut Carte(), qui laisse nom à null et valeur à 0.");

        // --- 3. Question 2 : exécution de TestCarte (première version) ---
        Ecran.question(2, "Écrire une classe TestCarte qui crée une carte, l'affiche, la modifie (valeurs "
                + "données par l'utilisateur) et l'affiche de nouveau.");
        corrections.td2.ex2.q1.TestCarte.main(new String[0]);

        // --- 4. Question 3 : démonstration de la triche, puis discussion ---
        Ecran.question(3, "Un joueur malin peut-il, à partir de TestCarte, donner 9999 points à sa carte "
                + "sans appeler la méthode modifier() ? Peut-il lui donner une valeur négative ? Comment "
                + "la classe Carte peut-elle empêcher la triche ? Faites une discussion.");
        corrections.td2.ex2.q1.TestCarte.tricher();
        System.out.println();
        Ecran.texte("Oui aux deux questions. Les attributs nom et valeur n'ont pas de modificateur "
                + "d'accès : toute classe du même paquetage, dont TestCarte, peut les modifier "
                + "directement, sans passer par modifier(). Il suffit d'écrire « c.valeur = 9999; » et "
                + "rien n'interdit « c.valeur = -50; », ni même « c.modifier(\"X\", -50) », car modifier() "
                + "ne contrôle rien.\n"
                + "Pour empêcher la triche, on applique l'encapsulation :\n"
                + "- déclarer les attributs private : seules les méthodes de la classe Carte y ont accès ;\n"
                + "- ne modifier la carte qu'à travers des méthodes publiques qui contrôlent les valeurs "
                + "(constructeurs, modifier()) : une valeur négative est ramenée à 0, on pourrait aussi "
                + "plafonner la valeur ;\n"
                + "- offrir des accesseurs en lecture (getNom(), getValeur()) : on consulte la carte sans "
                + "pouvoir la modifier n'importe comment.\n"
                + "C'est la version de la question 4. Avec des attributs private, la triche ne compile "
                + "plus : « c.valeur = 9999; » dans TestCarte donne l'erreur de compilation suivante.");
        Ecran.champ("javac", "erreur : valeur has private access in Carte");
        // Pause : laisse le temps de lire la discussion avant la version encapsulée.
        Clavier.pause();

        // --- 5. Question 4 : la carte encapsulée (diagramme UML et points clés) ---
        Ecran.question(4, "Reprendre la classe Carte en déclarant ses attributs private et en ajoutant : un "
                + "constructeur sans paramètres qui crée la carte « Inconnue » valant 10 points ; un "
                + "constructeur recevant le nom et la valeur (une valeur négative est remplacée par 0) ; "
                + "getNom() ; getValeur() ; boolean estPlusForteQue(Carte c) ; toString() ; "
                + "boolean equals(Object o) (même nom et même valeur).");
        String[] attributsQ4 = {"- nom : String", "- valeur : int"};
        String[] methodesQ4 = {"+ Carte()", "+ Carte(nom : String, valeur : int)", "+ getNom() : String",
            "+ getValeur() : int", "+ afficher() : void", "+ modifier(n : String, v : int) : void",
            "+ estPlusForteQue(c : Carte) : boolean", "+ toString() : String",
            "+ equals(o : Object) : boolean"};
        Ecran.uml("Carte", attributsQ4, methodesQ4);
        Ecran.texte("Code : corrections/td2/ex2/q4/Carte.java. Points clés :\n"
                + "- le constructeur sans paramètres appelle l'autre avec this(\"Inconnue\", 10), qui doit "
                + "être sa première instruction ;\n"
                + "- la règle « valeur négative -> 0 » est appliquée dans le constructeur ET dans "
                + "modifier(), sinon on pourrait encore tricher par modifier() ;\n"
                + "- toString() et equals() existent déjà dans la classe Object, dont toute classe hérite : "
                + "on les redéfinit ;\n"
                + "- toString() est appelée automatiquement par println(c) et par une concaténation "
                + "« \"...\" + c » ;\n"
                + "- equals(Object o) vérifie d'abord que o est une Carte (instanceof), le convertit en "
                + "Carte par un cast « (Carte) o », puis compare les noms (des String : avec equals) et "
                + "les valeurs (des int : avec ==).");

        // --- 6. Question 5 : deux cartes, deux constructeurs, deux façons d'afficher ---
        Ecran.question(5, "Modifier TestCarte : créer une première carte avec le constructeur paramétré "
                + "(valeurs données par l'utilisateur) et une deuxième avec le constructeur sans "
                + "paramètres. La deuxième doit ensuite devenir identique à la première, en récupérant "
                + "le nom et la valeur de la première. Afficher les deux cartes de deux façons.");
        Ecran.texte("(Essayez une valeur négative : le constructeur la remplace par 0.)");
        corrections.td2.ex2.q4.TestCarte.question5();

        // --- 7. Question 6 : == et equals ---
        Ecran.question(6, "Comparer ces deux cartes avec l'opérateur == sur leurs références, puis avec "
                + "la méthode equals(). Expliquer les résultats.");
        corrections.td2.ex2.q4.TestCarte.question6();
        System.out.println();
        Ecran.texte("- c1 == c2 vaut false : == compare les références, c'est-à-dire les adresses des "
                + "objets en mémoire. c1 et c2 ont été créées par deux new : ce sont deux objets "
                + "distincts, même si leur contenu est identique.\n"
                + "- c1.equals(c2) vaut true : notre méthode equals compare le contenu (même nom, même valeur).\n"
                + "- c1 == c4 vaut true : « Carte c4 = c1; » ne crée pas d'objet, c4 désigne le même "
                + "objet que c1.\n"
                + "Sans redéfinition, la méthode equals héritée de Object compare les références, comme "
                + "==, et aurait renvoyé false. C'est pour la même raison qu'on compare des String avec "
                + "equals(), jamais avec ==.");

        // --- 8. Question 7 : la carte qui a le plus de valeur ---
        Ecran.question(7, "Créer une troisième carte et afficher, parmi les cartes créées, celle qui a "
                + "le plus de valeur.");
        corrections.td2.ex2.q4.TestCarte.question7();
        System.out.println();
        Ecran.texte("On suppose d'abord que c1 est la plus forte, puis on la remplace chaque fois qu'une "
                + "autre carte est plus forte (estPlusForteQue). En cas d'égalité, la première trouvée est "
                + "gardée.");
    }
}
