package corrections.td2.ex3.q1;

// =====================================================================
//  CLASSE TestForfait (version 1)  -  Série 2, Exercice 3, question 1
//  Rôle : créer et afficher un forfait à 25,5 dinars pour 30 Go.
// =====================================================================

/**
 * Programme de test de la première version de Forfait (question 1).
 */
public class TestForfait {

    /**
     * Point d'entrée : création puis affichage du forfait.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String args[]) {    // String args[] équivaut à String[] args
        // --- 1. Initialisation : création du forfait ---
        // 30 (un int) est accepté pour un paramètre float : la conversion int -> float est
        // élargissante, donc implicite. 25.5 sans suffixe serait un double, et double -> float
        // est refusée sans cast : d'où 25.5f.
        Forfait f1 = new Forfait(25.5f, 30);     // le f est obligatoire : 25.5 seul est un double

        // --- 2. Affichage (les calculs sont faits par coutAnnuel() et prixParGo()) ---
        f1.affiche();
    }
}
