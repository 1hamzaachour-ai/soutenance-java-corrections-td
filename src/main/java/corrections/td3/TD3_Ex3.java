package corrections.td3;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD3_Ex3  -  Série 3, Exercice 3
//  Rôle : présenter l'ensemble de chaînes : énoncé, diagrammes UML,
//  exécutions et réponses aux trois questions.
//  Paquetage ex3.q1 : version avec un tableau de taille fixe.
//  Paquetage ex3.q3 : version avec une ArrayList (même programme de test).
// =====================================================================
/**
 * Présentation de l'exercice 3 de la série 3 (ensemble de chaînes) dans le menu de la soutenance.
 *
 * Les deux versions de EnsembleStr (tableau fixe en q1, ArrayList en q3) et leurs programmes de
 * test portent les mêmes noms : on les appelle par leur nom complet (corrections.td3.ex3.q1...)
 * au lieu de les importer. Les deux diagrammes UML partagent le même tableau de méthodes
 * (methodes), puisque les méthodes publiques sont identiques.
 */
public class TD3_Ex3 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 3) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD3_Ex3() {
        super("Ensemble de chaînes (classe EnsembleStr)",
                "Gérer un ensemble de chaînes sans doublon avec un tableau de taille fixe, puis avec une ArrayList, sans changer le test.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, puis répond aux trois questions.
     * Les deux programmes de test sont réellement exécutés, l'un après l'autre, pour comparer leurs
     * résultats ; une pause sépare les questions.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Un ensemble contient une liste non ordonnée d'éléments, et chaque élément y est "
                + "unique : on ne doit pas trouver deux fois le même élément.");

        // --- 2. Question 1 : EnsembleStr avec un tableau de taille fixe (diagramme UML) ---
        Ecran.question(1, "Définir une classe EnsembleStr qui gère un ensemble de chaînes de caractères à "
                + "l'aide d'un tableau de taille fixe, avec : un constructeur non paramétré ; vider() ; "
                + "afficher() ; appartient(ch), qui renvoie la position de ch dans l'ensemble ou -1 ; "
                + "ajouter(ch) ; retirer(ch) ; égalité(E), qui teste l'égalité de l'ensemble courant avec "
                + "un autre ensemble E.");
        String[] attributs = {"- CAPACITE : int = 10   (static final)", "- elements : String[]", "- nb : int"};
        String[] methodes = {"+ EnsembleStr()", "+ vider() : void", "+ afficher() : void",
            "+ appartient(ch : String) : int", "+ ajouter(ch : String) : boolean",
            "+ retirer(ch : String) : boolean", "+ egalite(e : EnsembleStr) : boolean"};
        Ecran.uml("EnsembleStr", attributs, methodes);
        Ecran.texte("Code : corrections/td3/ex3/q1/EnsembleStr.java. Points clés :\n"
                + "- seules les nb premières cases du tableau sont utilisées : vider() remet simplement nb à 0 ;\n"
                + "- ajouter() appelle appartient() pour refuser un doublon, et refuse aussi l'ajout quand le "
                + "tableau est plein. ajouter() et retirer() renvoient true si l'ensemble a changé ;\n"
                + "- retirer() met la dernière chaîne à la place de celle qu'on enlève : l'ordre ne compte "
                + "pas dans un ensemble, et on évite de décaler toutes les cases ;\n"
                + "- egalite() compare d'abord les tailles, puis vérifie que chaque élément appartient à "
                + "l'autre ensemble : comme il n'y a pas de doublon, c'est suffisant. e.nb est accessible, car "
                + "private s'applique à la classe et non à l'objet ;\n"
                + "- la méthode s'appelle egalite, sans accents : Java les accepte dans un nom, mais on les évite.");
        Clavier.pause();

        // --- 3. Question 2 : programme de test ---
        Ecran.question(2, "Écrire un programme principal permettant de tester le bon fonctionnement de la "
                + "classe EnsembleStr.");
        System.out.println("Exécution de TestEnsembleStr (paquetage ex3.q1) :");
        System.out.println();
        corrections.td3.ex3.q1.TestEnsembleStr.main(new String[0]);
        System.out.println();
        Ecran.texte("Le doublon « java » est refusé, e1 et e2 sont égaux malgré l'ordre différent, et e3 "
                + "n'accepte que 10 chaînes sur 12 : le tableau a une taille fixe.");
        Clavier.pause();

        // --- 4. Question 3 : la version ArrayList (diagramme UML et exécution) ---
        Ecran.question(3, "Représenter un ensemble de chaînes de caractères à l'aide d'un tableau dynamique "
                + "de type ArrayList à la place d'un tableau statique.");
        // Seul l'attribut change ; on réutilise le tableau methodes de la question 1, car les
        // méthodes publiques sont exactement les mêmes.
        String[] attributs3 = {"- elements : ArrayList<String>"};
        Ecran.uml("EnsembleStr", attributs3, methodes);
        Ecran.texte("Code : corrections/td3/ex3/q3/EnsembleStr.java. Les méthodes de l'ArrayList (cours "
                + "p.191-192) remplacent le travail fait à la main :\n"
                + "- appartient(ch) devient simplement indexOf(ch), qui renvoie déjà la position ou -1, en "
                + "comparant avec equals ;\n"
                + "- ajouter() utilise add(), retirer() utilise remove(pos), vider() utilise clear() ;\n"
                + "- le compteur nb disparaît : size() donne le nombre d'éléments, et il n'y a plus de "
                + "taille maximale.");
        System.out.println("Exécution de TestEnsembleStr (paquetage ex3.q3), le même programme :");
        System.out.println();
        corrections.td3.ex3.q3.TestEnsembleStr.main(new String[0]);
        System.out.println();
        Ecran.texte("Les résultats sont identiques, sauf pour e3, qui accepte cette fois les 12 chaînes. Les "
                + "méthodes publiques n'ont pas changé, donc le programme de test non plus : c'est l'intérêt "
                + "de l'encapsulation, la façon de ranger les données peut changer sans toucher au code qui "
                + "utilise la classe.\n"
                + "Remarque : retirer() de la version tableau recopie une seule case, alors que remove(pos) "
                + "d'une ArrayList décale tous les éléments suivants, en moyenne en O(N) (cours p.190).");
    }
}
