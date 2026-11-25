package corrections.td2.ex2.q1;

import corrections.outils.Clavier;

// =====================================================================
//  CLASSE TestCarte (première version)  -  Série 2, Exercice 2, questions 2 et 3
//  Rôle : utiliser la première version de Carte (question 2), puis
//  montrer la triche permise par des attributs non private (question 3).
// =====================================================================

/**
 * Programme de test de la première version de Carte.
 *
 * Responsabilités : main répond à la question 2 (créer, afficher, modifier, réafficher une
 * carte) ; tricher() montre la réponse à la question 3 (modifier les attributs sans passer par
 * la méthode modifier, ce que permet un attribut non private).
 */
public class TestCarte {

    /**
     * Question 2 : créer une carte, l'afficher, la modifier (valeurs de l'utilisateur), l'afficher de nouveau.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // --- 1. Initialisation : création de la carte et premier affichage ---
        // Le premier affichage montre les valeurs par défaut : [null — 0 pts].
        Carte c = new Carte();                  // constructeur par défaut : nom = null, valeur = 0
        System.out.print("Carte créée    : ");  // print : afficher() écrira la carte sur la même ligne
        c.afficher();

        // --- 2. Saisie utilisateur ---
        // lireEntier sans bornes : la question 3 veut justement montrer qu'aucune valeur n'est
        // refusée par cette version de Carte.
        String nom = Clavier.lireTexte("Nouveau nom    : ");
        int valeur = Clavier.lireEntier("Nouvelle valeur : ");

        // --- 3. Modification de la carte ---
        c.modifier(nom, valeur);

        // --- 4. Affichage ---
        System.out.print("Carte modifiée : ");
        c.afficher();
    }

    /**
     * Question 3 : les attributs n'étant pas private, on peut les changer sans passer par modifier().
     *
     * Méthode séparée de main : c'est une démonstration distincte, appelée par TD2_Ex2.
     */
    public static void tricher() {
        // --- 1. Une carte normale, modifiée avec la méthode modifier() ---
        Carte c = new Carte();
        c.modifier("Gobelin", 15);
        System.out.print("Carte normale            : ");
        c.afficher();

        // --- 2. Triche : on change l'attribut directement ---
        // Possible seulement parce que valeur n'est pas private et que TestCarte est dans le
        // même paquetage que Carte. Avec private (version q4), cette ligne ne compilerait pas.
        c.valeur = 9999;                        // accès direct à l'attribut : triche
        System.out.print("Après c.valeur = 9999;   : ");
        c.afficher();

        // --- 3. Valeur négative : rien ne l'empêche ---
        c.valeur = -50;                         // rien n'empêche non plus une valeur négative
        System.out.print("Après c.valeur = -50;    : ");
        c.afficher();
    }
}
