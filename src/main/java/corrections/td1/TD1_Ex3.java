package corrections.td1;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;

// =====================================================================
//  CLASSE TD1_Ex3  -  Série 1, Exercice 3
//  Rôle : afficher en toutes lettres un chiffre de 0 à 9 tapé par
//  l'utilisateur, avec la structure switch.
// =====================================================================

/**
 * Correction de l'exercice 3 de la série 1 : écrire un chiffre en toutes lettres avec switch.
 *
 * Pourquoi cette classe existe : elle présente l'exercice dans le menu et l'exécute. Le
 * programme demandé par l'énoncé est dans la méthode afficherEnLettres(int), séparée de
 * l'affichage de l'énoncé pour qu'on la reconnaisse facilement.
 *
 * Responsabilités : afficher l'énoncé, lire un chiffre, l'afficher en lettres, rappeler le rôle
 * de break et de default.
 */
public class TD1_Ex3 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 1) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD1_Ex3() {
        super("Chiffre en toutes lettres (switch)",
                "Afficher en toutes lettres un chiffre de 0 à 9 tapé au clavier, avec la structure switch.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /** Affiche l'énoncé, fait saisir un chiffre, l'affiche en lettres, puis rappelle l'essentiel. */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Écrire un programme en Java qui permet d'afficher en toutes lettres un chiffre "
                + "(de 0 à 9) introduit par l'utilisateur. Utiliser la structure switch.");

        // --- 2. Saisie utilisateur, puis affichage en lettres ---
        Ecran.section("Exécution");
        // lireEntier SANS bornes, volontairement : on doit pouvoir taper 12 ou -3 pour voir le
        // cas default du switch. Avec des bornes 0 et 9, ce cas ne pourrait jamais être montré.
        int chiffre = Clavier.lireEntier("Donnez un chiffre (de 0 à 9) : ");
        afficherEnLettres(chiffre);

        // --- 3. À retenir ---
        Ecran.section("À retenir");
        Ecran.texte("- Chaque case se termine par break : sans lui, l'exécution continuerait dans les "
                + "case suivants.\n"
                + "- default traite toutes les autres valeurs, ici un nombre qui n'est pas un chiffre.\n"
                + "- Relancez l'exercice (R) avec 12 ou -3 pour voir le cas default.");
    }

    // =================================================================
    //  LE PROGRAMME DE L'EXERCICE
    // =================================================================

    /**
     * Affiche le chiffre c en toutes lettres (un case par chiffre, default pour le reste).
     *
     * @param c le nombre tapé par l'utilisateur
     */
    public static void afficherEnLettres(int c) {
        /*
         * switch : on compare la même variable c à dix valeurs fixes (0 à 9). C'est plus lisible
         * qu'une suite de dix if / else if, et c'est la structure imposée par l'énoncé.
         * Chaque case se termine par break : il fait sortir du switch. Sans break, l'exécution
         * « tomberait » dans les case suivants : pour 7, on afficherait sept, huit, neuf, puis le
         * message d'erreur.
         * default : traite toutes les valeurs qui ne correspondent à aucun case (12, -3...).
         * Comme c'est le dernier bloc, il n'a pas besoin de break.
         */
        switch (c) {
            case 0:
                System.out.println("zéro");
                break;
            case 1:
                System.out.println("un");
                break;
            case 2:
                System.out.println("deux");
                break;
            case 3:
                System.out.println("trois");
                break;
            case 4:
                System.out.println("quatre");
                break;
            case 5:
                System.out.println("cinq");
                break;
            case 6:
                System.out.println("six");
                break;
            case 7:
                System.out.println("sept");
                break;
            case 8:
                System.out.println("huit");
                break;
            case 9:
                System.out.println("neuf");
                break;
            default:
                System.out.println("Erreur : " + c + " n'est pas un chiffre (de 0 à 9).");
        }
    }
}
