package corrections.td2.ex3.q4;

// =====================================================================
//  CLASSE TestForfait (version 4)  -  Série 2, Exercice 3, question 4
//  Rôle : exécuter les deux instructions de l'énoncé avec une classe
//  Forfait qui n'a aucun constructeur écrit.
// =====================================================================

/**
 * Programme de test de la quatrième version de Forfait (question 4).
 */
public class TestForfait {

    /**
     * Point d'entrée : création avec le constructeur par défaut, puis affichage (prix par Go = NaN).
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String args[]) {
        // --- 1. Initialisation : constructeur par défaut, ajouté par le compilateur ---
        // Il compile car la classe Forfait (version 4) ne déclare aucun constructeur.
        Forfait f = new Forfait();              // constructeur par défaut, ajouté par le compilateur

        // --- 2. Affichage ---
        // Prix et data valent 0.0 : le coût annuel vaut 0.0 et le prix par Go NaN (0 / 0).
        f.affiche();
    }
}
