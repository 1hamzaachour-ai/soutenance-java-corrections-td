package corrections.td2.ex2.q4;

import corrections.outils.Clavier;

// =====================================================================
//  CLASSE TestCarte (version encapsulée)  -  Série 2, Exercice 2, questions 5 à 7
//  Rôle : tester la carte encapsulée. main enchaîne les trois questions ;
//  le menu les appelle une par une pour intercaler les explications.
// =====================================================================

/**
 * Programme de test de la carte encapsulée (questions 5, 6 et 7).
 *
 * Pourquoi une méthode par question : TD2_Ex2 (le menu) affiche une explication entre chaque
 * question ; il appelle donc question5(), question6() et question7() séparément. main les
 * enchaîne pour qu'on puisse aussi lancer ce test seul (Run File).
 */
public class TestCarte {

    // =================================================================
    //  ATTRIBUTS DE CLASSE : les deux cartes partagées par les questions
    // =================================================================
    // static : les trois méthodes sont static et doivent partager les mêmes cartes (la question 6
    // compare les cartes créées à la question 5). Des variables locales à question5() seraient
    // perdues à la fin de cette méthode. private : seules les méthodes de TestCarte les utilisent.
    private static Carte c1;
    private static Carte c2;

    // =================================================================
    //  PROGRAMME PRINCIPAL
    // =================================================================

    /**
     * Point d'entrée : exécute les questions 5, 6 et 7 l'une après l'autre.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // L'ordre est obligatoire : question6() utilise c1 et c2, créées par question5().
        question5();
        question6();
        question7();
    }

    // =================================================================
    //  LES TROIS QUESTIONS
    // =================================================================

    /** Question 5 : c1 par le constructeur paramétré, c2 par le constructeur sans paramètres, puis c2 copie c1. */
    public static void question5() {
        // --- 1. Saisie utilisateur ---
        // lireEntier sans bornes : on peut taper une valeur négative pour voir le constructeur la
        // ramener à 0.
        String nom = Clavier.lireTexte("Nom de la carte 1    : ");
        int valeur = Clavier.lireEntier("Valeur de la carte 1 : ");

        // --- 2. Initialisation : création des deux cartes ---
        // Deux constructeurs différents (surcharge) : Java choisit d'après les arguments.
        c1 = new Carte(nom, valeur);
        c2 = new Carte();
        // "..." + c2 : Java appelle automatiquement c2.toString().
        System.out.println("c2 à sa création : " + c2);

        // --- 3. c2 récupère le nom et la valeur de c1 ---
        // On ne peut pas lire c1.nom (private) depuis TestCarte : on passe par les accesseurs.
        c2.modifier(c1.getNom(), c1.getValeur());       // c2 devient identique à c1

        // --- 4. Affichage des deux cartes, de deux façons ---
        System.out.println();
        System.out.println("1re façon, avec la méthode afficher() :");
        c1.afficher();
        c2.afficher();
        System.out.println("2e façon, avec toString(), appelée automatiquement par println :");
        System.out.println("c1 = " + c1);
        System.out.println("c2 = " + c2);
    }

    /** Question 6 : comparaison des références (==), puis du contenu (equals). */
    public static void question6() {
        // --- 1. Deux objets distincts : == et equals ---
        // c1 et c2 ont le même contenu mais sont deux objets différents (deux new) :
        // c1 == c2 compare les références, donc false ; c1.equals(c2) compare le contenu, donc true.
        // Les parenthèses autour de c1 == c2 sont nécessaires : sans elles, + serait appliqué en
        // premier et on comparerait une chaîne à c2.
        System.out.println("c1 == c2      : " + (c1 == c2));
        System.out.println("c1.equals(c2) : " + c1.equals(c2));

        // --- 2. Deux références vers le même objet ---
        // Carte c4 = c1 ne crée PAS de nouvelle carte (pas de new) : c4 et c1 désignent le même
        // objet, donc c1 == c4 vaut true.
        Carte c4 = c1;                                  // c4 désigne le même objet que c1
        System.out.println("Avec Carte c4 = c1;  c1 == c4 : " + (c1 == c4));
    }

    /** Question 7 : une troisième carte, puis recherche de la carte qui a le plus de valeur. */
    public static void question7() {
        // --- 1. Saisie et création de la troisième carte ---
        String nom = Clavier.lireTexte("Nom de la carte 3    : ");
        int valeur = Clavier.lireEntier("Valeur de la carte 3 : ");
        Carte c3 = new Carte(nom, valeur);
        System.out.println("c3 = " + c3);

        // --- 2. Recherche de la plus forte : on part de c1 et on garde la meilleure ---
        /*
         * Recherche du maximum « à la main » : plusForte retient la meilleure carte vue jusqu'ici.
         * Deux if successifs (et non if / else) : chaque carte doit être comparée à la meilleure
         * ACTUELLE, qui a pu changer au if précédent.
         * estPlusForteQue utilise > strict : en cas d'égalité, on garde la carte déjà retenue
         * (la première rencontrée).
         */
        Carte plusForte = c1;
        if (c2.estPlusForteQue(plusForte)) {
            plusForte = c2;
        }
        if (c3.estPlusForteQue(plusForte)) {
            plusForte = c3;
        }

        // --- 3. Affichage du résultat ---
        System.out.println("La carte qui a le plus de valeur : " + plusForte);
    }
}
