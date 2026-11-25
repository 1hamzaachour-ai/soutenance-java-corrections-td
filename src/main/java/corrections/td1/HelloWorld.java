package corrections.td1;

// =====================================================================
//  CLASSE HelloWorld  -  Série 1, Exercice 7
//  Rôle : le programme HelloWorld de l'énoncé. L'accolade finale est
//  corrigée : l'énoncé se termine par « { » au lieu de « } ».
// =====================================================================

/**
 * Le programme HelloWorld de l'exercice 7, enregistré dans un fichier qui porte le nom de la
 * classe publique (HelloWorld.java), comme Java l'exige.
 *
 * Il a sa propre méthode main : on peut le lancer seul (Run File dans NetBeans), et TD1_Ex7
 * l'exécute depuis le menu avec HelloWorld.main(new String[0]).
 */
public class HelloWorld {

    /**
     * Point d'entrée : affiche « Hello World !! » puis « Good luck !! ».
     *
     * public : la JVM doit pouvoir l'appeler de l'extérieur ; static : elle est appelée sans créer
     * d'objet HelloWorld ; void : elle ne renvoie rien ; String[] args : les paramètres de la
     * ligne de commande (ici non utilisés).
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        System.out.print("Hello World");        // print : pas de retour à la ligne
        System.out.println(" !!");              // println : retour à la ligne après le texte
        System.out.println("Good luck !!");
    }
}
