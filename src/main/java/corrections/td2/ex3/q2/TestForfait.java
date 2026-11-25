package corrections.td2.ex3.q2;

// =====================================================================
//  CLASSE TestForfait (version 2)  -  Série 2, Exercice 3, question 2
//  Rôle : créer deux forfaits (un avec chaque constructeur), les
//  afficher, puis dire lequel est le plus avantageux.
// =====================================================================

/**
 * Programme de test de la deuxième version de Forfait (question 2).
 */
public class TestForfait {

    /**
     * Point d'entrée : deux forfaits, puis le plus avantageux des deux.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String args[]) {
        // --- 1. Initialisation : un forfait par constructeur ---
        Forfait f1 = new Forfait(25.5f, 30);
        Forfait f2 = new Forfait();             // 10 dinars pour 5 Go

        // --- 2. Affichage des deux forfaits ---
        System.out.println("Forfait 1 :");
        f1.affiche();
        System.out.println("Forfait 2 :");
        f2.affiche();

        // --- 3. Comparaison et affichage du résultat ---
        /*
         * Trois cas exclusifs, donc if / else if / else : un seul message s'affiche.
         * - f1 moins cher au Go que f2 : le forfait 1 gagne ;
         * - sinon, f2 moins cher au Go que f1 : le forfait 2 gagne ;
         * - sinon, aucun n'est strictement moins cher : ils ont le même prix par Go.
         * Le troisième cas est nécessaire : avec un simple if / else, deux forfaits identiques
         * seraient déclarés « forfait 2 plus avantageux », ce qui serait faux.
         */
        if (f1.estMeilleurQue(f2)) {
            System.out.println("=> Le forfait 1 est le plus avantageux.");
        } else if (f2.estMeilleurQue(f1)) {
            System.out.println("=> Le forfait 2 est le plus avantageux.");
        } else {
            System.out.println("=> Les deux forfaits ont le même prix par Go.");
        }
    }
}
