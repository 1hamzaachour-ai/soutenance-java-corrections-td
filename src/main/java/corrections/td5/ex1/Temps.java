package corrections.td5.ex1;

// =====================================================================
//  CLASSE Temps  -  Série 5, Exercice 1, questions 1 et 2
//  Rôle : un temps (heures, minutes, secondes). Le constructeur vérifie
//  chaque valeur et lève une TempsException, qu'il ne traite pas
//  (throws) ; c'est main qui l'intercepte (try / catch).
//  La classe est public pour que le menu puisse lancer son main.
// =====================================================================

/**
 * Un temps de la journée : heures, minutes et secondes.
 *
 * Pourquoi lever une exception dans le constructeur : un constructeur ne peut pas renvoyer
 * « false » ou un code d'erreur. Si les valeurs ne forment pas un temps valide, le seul moyen
 * d'empêcher la création d'un objet incorrect est de lever une exception : l'objet n'est alors
 * jamais créé, et l'appelant est prévenu.
 *
 * Répartition des rôles (ce que demande l'énoncé) : le constructeur LÈVE l'exception (throw) et
 * l'ANNONCE (throws) sans la traiter ; c'est main, l'appelant, qui la TRAITE (try / catch).
 */
public class Temps {

    // =================================================================
    //  ATTRIBUTS
    // =================================================================
    // int : des valeurs entières. private : on ne peut pas mettre 99 minutes dans un Temps en
    // contournant les vérifications du constructeur.
    private int heures, minutes, secondes;
    // REMARQUE : la classe ne fournit aucun accesseur (getHeures...) : l'énoncé ne demande que
    // la vérification à la construction. Les valeurs rangées ne sont donc jamais relues.

    // =================================================================
    //  CONSTRUCTEUR (question 1)
    // =================================================================

    /**
     * Range h, m et s s'ils forment un temps valide ; sinon lève une TempsException, sans la traiter.
     *
     * Pas de public devant le constructeur (comme dans l'énoncé) : il n'est utilisable que dans le
     * paquetage ex1, où se trouvent main et TestTemps.
     *
     * @param h les heures (0 à 23)
     * @param m les minutes (0 à 59)
     * @param s les secondes (0 à 59)
     * @throws TempsException si une des trois valeurs sort de son intervalle
     */
    Temps(int h, int m, int s) throws TempsException {
        // --- 1. Vérifier les heures (0 à 23) ---
        /*
         * (h >= 0) && (h <= 23) : h est dans l'intervalle [0 ; 23]. && car les DEUX bornes doivent
         * être respectées. <= 23 (et non < 23) : 23 h est une heure valide, 24 h non.
         * Vrai : on range la valeur. Faux : throw lève l'exception et ARRÊTE le constructeur ; les
         * vérifications suivantes ne sont pas faites et l'objet n'est pas créé.
         * Si on avait écrit || au lieu de &&, toute valeur serait acceptée (n'importe quel nombre
         * est soit >= 0, soit <= 23).
         */
        if ((h >= 0) && (h <= 23)) {
            heures = h;
        } else {
            throw new TempsException();
        }

        // --- 2. Vérifier les minutes (0 à 59) ---
        // Même principe : 60 minutes est refusé (c'est déjà une heure de plus).
        if ((m >= 0) && (m <= 59)) {
            minutes = m;
        } else {
            throw new TempsException();
        }

        // --- 3. Vérifier les secondes (0 à 59) ---
        if ((s >= 0) && (s <= 59)) {
            secondes = s;
        } else {
            throw new TempsException();
        }
    }

    // =================================================================
    //  PROGRAMME PRINCIPAL (question 2)
    // =================================================================

    /**
     * Crée le temps de l'énoncé, 24 h 12 min 67 s, et traite l'exception en affichant « Temps invalide ».
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        /*
         * try / catch obligatoire : le constructeur annonce throws TempsException (exception
         * contrôlée). Sans try / catch, javac refuserait : « unreported exception TempsException;
         * must be caught or declared to be thrown ».
         * Ici les heures (24) sont déjà invalides : le constructeur lève l'exception dès sa première
         * vérification, la ligne « Temps valide » est sautée et l'exécution passe dans le catch.
         */
        try {
            // La variable t n'est pas utilisée ensuite : on garde l'instruction de l'énoncé, dont
            // le seul but est d'essayer de créer le temps.
            Temps t = new Temps(24, 12, 67);
            System.out.println("Temps valide");
        } catch (TempsException e) {
            System.out.println(e);              // println appelle e.toString() : « Temps invalide »
        }
    }
}
