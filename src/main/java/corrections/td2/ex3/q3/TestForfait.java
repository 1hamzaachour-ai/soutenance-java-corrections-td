package corrections.td2.ex3.q3;

// =====================================================================
//  CLASSE TestForfait (version 3)  -  Série 2, Exercice 3, question 3
//  Rôle : obtenir un forfait à 45 dinars pour 100 Go alors que le
//  constructeur avec paramètres n'existe plus.
// =====================================================================

/**
 * Programme de test de la troisième version de Forfait (question 3).
 */
public class TestForfait {

    /**
     * Point d'entrée : création avec Forfait(), modification, puis affichage.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String args[]) {
        // --- 1. Initialisation : création avec le seul constructeur restant ---
        // new Forfait(45, 100) ne compile plus : ce constructeur a été enlevé.
        // javac répondrait : « constructor Forfait in class Forfait cannot be applied to given types ».
        Forfait f3 = new Forfait();             // 10 dinars pour 5 Go...

        // --- 2. Modification du forfait ---
        // 45 et 100 sont des int, convertis implicitement en float (conversion élargissante).
        f3.modifierForfait(45, 100);            // ... puis 45 dinars pour 100 Go

        // --- 3. Affichage ---
        f3.affiche();
    }
}
