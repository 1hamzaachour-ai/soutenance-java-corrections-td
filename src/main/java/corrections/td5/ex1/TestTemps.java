package corrections.td5.ex1;

// =====================================================================
//  CLASSE TestTemps  -  Série 5, Exercice 1 (complément)
//  Rôle : essayer le constructeur de Temps avec plusieurs valeurs, valides
//  ou non, pour voir quand la TempsException est levée.
// =====================================================================

/**
 * Programme de test complémentaire : il essaie plusieurs temps, valides et invalides.
 *
 * L'énoncé ne teste qu'un seul temps (24 h 12 min 67 s). Ce complément montre aussi des temps
 * acceptés et des cas limites (23:59:59 accepté, 60 minutes refusé, -1 heure refusé).
 */
public class TestTemps {

    /**
     * Point d'entrée : un essai par ligne du tableau.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // --- 1. Initialisation : les heures, minutes et secondes à essayer ---
        // Tableau à deux dimensions : chaque ligne est un essai {heures, minutes, secondes}.
        // essais.length = nombre de lignes (5) ; essais[i][0] = heures de l'essai i, etc.
        int[][] essais = { {10, 30, 0}, {23, 59, 59}, {24, 12, 67}, {8, 60, 0}, {-1, 0, 0} };

        // --- 2. Un essai par ligne : seul le catch dit si le temps est refusé ---
        /*
         * Boucle for sur les lignes du tableau (nombre de tours connu : essais.length = 5).
         * i est le numéro de la ligne ; i < essais.length s'arrête après la dernière ligne.
         * Le try / catch est À L'INTÉRIEUR de la boucle : si un essai lève l'exception, on passe
         * dans le catch, puis la boucle continue avec l'essai suivant. Avec le try autour de la
         * boucle, la première erreur arrêterait tous les essais restants.
         */
        for (int i = 0; i < essais.length; i++) {
            int h = essais[i][0];
            int m = essais[i][1];
            int s = essais[i][2];
            System.out.print("new Temps(" + h + ", " + m + ", " + s + ")   -> ");
            try {
                // Si le constructeur lève l'exception, la ligne « temps accepté » est sautée.
                // REMARQUE : t n'est pas relue ensuite (NetBeans le signale) ; seule compte la
                // création, qui réussit ou lève l'exception. Comme dans le main de l'énoncé.
                Temps t = new Temps(h, m, s);
                System.out.println("temps accepté");
            } catch (TempsException e) {
                System.out.println(e);          // « Temps invalide » (toString de TempsException)
            }
        }
    }
}
