package corrections.td3.ex3.q1;

// =====================================================================
//  CLASSE TestEnsembleStr (tableau de taille fixe)  -  Série 3, Exercice 3, question 2
//  Rôle : programme principal qui essaie toutes les méthodes de la classe
//  EnsembleStr. Le même programme sert à la version ArrayList (question 3).
// =====================================================================

/**
 * Programme principal qui teste toutes les méthodes de EnsembleStr (question 2).
 *
 * Chaque étape affiche l'appel et son résultat, ce qui permet de vérifier les règles d'un
 * ensemble : pas de doublon, retrait, égalité sans tenir compte de l'ordre, capacité limitée.
 * Le même programme, recopié dans le paquetage q3, teste la version ArrayList.
 */
public class TestEnsembleStr {

    /**
     * Point d'entrée : ajouts, recherche, retrait, égalité, capacité, puis vider().
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // --- 1. Ajouts : le doublon "java" est refusé ---
        // \" dans une chaîne affiche un guillemet. ajouter renvoie true ou false, affiché tel quel.
        EnsembleStr e1 = new EnsembleStr();
        System.out.println("e1.ajouter(\"java\")   : " + e1.ajouter("java"));
        System.out.println("e1.ajouter(\"python\") : " + e1.ajouter("python"));
        System.out.println("e1.ajouter(\"c\")      : " + e1.ajouter("c"));
        System.out.println("e1.ajouter(\"java\")   : " + e1.ajouter("java") + "   (déjà présente)");
        System.out.print("e1 = ");
        e1.afficher();

        // --- 2. Recherche : position, ou -1 ---
        System.out.println("e1.appartient(\"python\") : " + e1.appartient("python"));
        System.out.println("e1.appartient(\"php\")    : " + e1.appartient("php"));

        // --- 3. Retrait ---
        System.out.println("e1.retirer(\"python\") : " + e1.retirer("python"));
        System.out.println("e1.retirer(\"php\")    : " + e1.retirer("php") + "   (absente)");
        System.out.print("e1 = ");
        e1.afficher();

        // --- 4. Égalité : le même contenu dans un autre ordre, puis un élément de plus ---
        // e2 contient les mêmes chaînes que e1 mais ajoutées dans l'autre ordre : egalite doit
        // répondre true (l'ordre ne compte pas), puis false après l'ajout de "php".
        EnsembleStr e2 = new EnsembleStr();
        e2.ajouter("c");
        e2.ajouter("java");
        System.out.print("e2 = ");
        e2.afficher();
        System.out.println("e1.egalite(e2) : " + e1.egalite(e2));
        e2.ajouter("php");
        System.out.print("Après e2.ajouter(\"php\"), e2 = ");
        e2.afficher();
        System.out.println("e1.egalite(e2) : " + e1.egalite(e2));

        // --- 5. Capacité : on essaie de mettre 12 chaînes dans un ensemble vide ---
        EnsembleStr e3 = new EnsembleStr();
        int acceptes = 0;   // nombre d'ajouts réussis
        /*
         * Boucle for : exactement 12 essais (nombre de tours connu). i va de 1 à 12 et sert à
         * fabriquer des chaînes toutes différentes : "m1", "m2", ..., "m12" ("m" + i colle le
         * nombre au texte). On compte les ajouts réussis : avec le tableau de 10 cases, les deux
         * derniers sont refusés ; avec l'ArrayList (question 3), les 12 sont acceptés.
         */
        for (int i = 1; i <= 12; i++) {
            if (e3.ajouter("m" + i)) {
                acceptes++;
            }
        }
        System.out.println("12 ajouts dans e3 : " + acceptes + " acceptés");
        System.out.print("e3 = ");
        e3.afficher();

        // --- 6. vider() ---
        e1.vider();
        System.out.print("Après e1.vider(), e1 = ");
        e1.afficher();
    }
}
