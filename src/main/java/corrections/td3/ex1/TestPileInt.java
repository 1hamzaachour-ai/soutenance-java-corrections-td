package corrections.td3.ex1;

import corrections.outils.Clavier;

// =====================================================================
//  CLASSE TestPileInt  -  Série 3, Exercice 1, question 2
//  Rôle : programme de test. Il crée une pile de N entiers saisis au
//  clavier, l'affiche, en sort X entiers, puis l'affiche de nouveau.
// =====================================================================

/**
 * Programme de test de la pile d'entiers (question 2 de l'énoncé).
 *
 * Il montre aussi les exceptions : si l'on demande de sortir plus d'entiers qu'il n'y en a
 * (X > N), depiler() lève une PileVideException que ce programme attrape.
 */
public class TestPileInt {

    /**
     * Point d'entrée : création et remplissage, affichage, sortie de X entiers, réaffichage.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // --- 1. Saisie de N, puis création d'une pile de N cases ---
        // Bornes 1 à 20 : une pile d'au moins une case, et pas trop grande pour l'affichage.
        int n = Clavier.lireEntier("Nombre d'entiers à empiler (N, de 1 à 20) : ", 1, 20);
        PileInt pile = new PileInt(n);

        // --- 2. Remplissage : les N entiers saisis sont empilés un par un ---
        /*
         * try / catch obligatoire : empiler() annonce throws PilePleineException (exception
         * contrôlée). Sans try / catch (ou sans throws sur main), javac refuserait de compiler.
         * Ici l'exception ne peut pas se produire (on empile exactement N entiers dans N cases),
         * mais javac exige quand même qu'on la traite.
         */
        try {
            /*
             * Boucle for : on veut exactement n entiers, le nombre de tours est connu.
             * i va de 1 à n inclus (<=) car i sert aussi de numéro affiché (« entier n°1 ») ; on ne
             * l'utilise pas comme indice de tableau, il n'y a donc pas de risque de débordement.
             */
            for (int i = 1; i <= n; i++) {
                pile.empiler(Clavier.lireEntier("  entier n°" + i + " : "));
            }
        } catch (PilePleineException e) {
            System.out.println("Erreur : " + e.message());
        }

        // --- 3. Affichage de la pile ---
        System.out.println();
        System.out.println("La pile après le remplissage :");
        pile.afficher();

        // --- 4. Sortie de X entiers (le premier sorti est le dernier entré) ---
        System.out.println();
        // X peut être plus grand que N (jusqu'à 99) : c'est voulu, pour montrer PileVideException.
        int x = Clavier.lireEntier("Nombre d'entiers à sortir (X) : ", 0, 99);
        int sortis = 0;     // compteur des sorties réussies, pour le message d'erreur et le titre final
        /*
         * try / catch autour de toute la boucle : dès que la pile est vide, depiler() lève
         * PileVideException ; on sort alors de la boucle et on saute dans le catch. Les entiers
         * déjà sortis restent sortis.
         */
        try {
            /*
             * Boucle for : on veut X sorties (nombre de tours connu). i va de 1 à x ; si x vaut 0,
             * aucun tour. La boucle peut aussi s'arrêter plus tôt, par l'exception, si X > N.
             */
            for (int i = 1; i <= x; i++) {
                System.out.println("  sortie de " + pile.depiler());
                sortis++;       // exécuté seulement si depiler() a réussi
            }
        } catch (PileVideException e) {
            System.out.println("Erreur après " + sortis + " sortie(s) :");
            System.out.println("  " + e.message());
        }

        // --- 5. Réaffichage de la pile ---
        System.out.println();
        System.out.println("La pile après la sortie de " + sortis + " entier(s) :");
        pile.afficher();
    }
}
