package corrections.td3.ex3.q1;

// =====================================================================
//  CLASSE EnsembleStr (tableau de taille fixe)  -  Série 3, Exercice 3, question 1
//  Rôle : un ensemble de chaînes, rangé dans un tableau de taille fixe.
//  Chaque chaîne n'y figure qu'une fois ; l'ordre des éléments ne compte
//  pas. Seules les nb premières cases du tableau sont utilisées.
// =====================================================================

/**
 * Un ensemble de chaînes représenté par un tableau de taille fixe (question 1).
 *
 * Un ensemble, au sens des mathématiques : chaque élément n'y figure qu'une seule fois et l'ordre
 * n'a pas d'importance. Toutes les méthodes respectent ces deux règles : ajouter() refuse les
 * doublons, et retirer() peut changer l'ordre des éléments sans que cela pose problème.
 *
 * Organisation du tableau : les chaînes occupent toujours les cases 0 à nb - 1, sans trou. Le
 * compteur nb indique à la fois le nombre d'éléments et la première case libre.
 * Limite de cette version : au plus CAPACITE chaînes. La question 3 la supprime avec une ArrayList.
 */
public class EnsembleStr {

    // =================================================================
    //  CONSTANTE ET ATTRIBUTS
    // =================================================================

    // private static final : constante propre à la classe ; si on veut agrandir l'ensemble, on ne
    // change que cette valeur.

    /** Nombre de cases du tableau (constante de classe). */
    private static final int CAPACITE = 10;

    // Un tableau de String de taille fixe, comme le demande la question 1, et le compteur des
    // cases utilisées (un tableau Java ne sait pas lui-même combien de cases sont « remplies »).
    private String[] elements;                  // les chaînes de l'ensemble, dans les cases 0 à nb - 1
    private int nb;                             // nombre de chaînes rangées dans le tableau

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /** Constructeur non paramétré : un ensemble vide de CAPACITE places. */
    public EnsembleStr() {
        elements = new String[CAPACITE];        // CAPACITE cases, toutes à null au départ
        nb = 0;                                 // aucune case utilisée : l'ensemble est vide
    }

    // =================================================================
    //  MÉTHODES
    // =================================================================

    /**
     * Vide l'ensemble : il suffit de remettre le compteur à 0.
     * Les anciennes chaînes restent dans le tableau mais ne comptent plus (seules les cases de 0 à
     * nb - 1 font partie de l'ensemble) ; elles seront écrasées par les prochains ajouts.
     */
    public void vider() {
        nb = 0;
    }

    /** Affiche l'ensemble entre accolades, par exemple { java, c, python }. */
    public void afficher() {
        // --- 1. Ensemble vide ---
        // Cas particulier traité à part ; return quitte la méthode, la suite n'est pas exécutée.
        if (nb == 0) {
            System.out.println("{ }");
            return;
        }

        // --- 2. Les éléments séparés par des virgules ---
        StringBuffer sb = new StringBuffer("{ ");
        /*
         * Boucle for jusqu'à nb (et non elements.length) : seules les nb premières cases font partie
         * de l'ensemble ; les autres valent null ou contiennent d'anciennes valeurs.
         */
        for (int i = 0; i < nb; i++) {
            // Une virgule avant chaque élément sauf le premier (i > 0) : pas de virgule en trop.
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(elements[i]);
        }
        sb.append(" }");
        System.out.println(sb.toString());
    }

    /**
     * Position de ch dans l'ensemble si elle y appartient, -1 sinon.
     *
     * -1 est utilisé comme « introuvable » car ce n'est jamais un indice valide (comme pour indexOf
     * de String).
     *
     * @param ch la chaîne cherchée
     * @return son indice dans le tableau, ou -1
     */
    public int appartient(String ch) {
        /*
         * Recherche séquentielle : on examine les cases une par une, de 0 à nb - 1.
         * Boucle for, car au pire on parcourt les nb éléments ; mais on sort dès qu'on trouve la
         * chaîne, avec return i (inutile de continuer). Si la boucle se termine sans avoir trouvé,
         * on arrive au return -1 placé après elle.
         */
        for (int i = 0; i < nb; i++) {
            if (elements[i].equals(ch)) {       // equals : on compare le contenu des chaînes
                return i;
            }
        }
        return -1;
    }

    /**
     * Ajoute ch si elle n'y est pas encore et s'il reste une place ; renvoie true si ch a été ajoutée.
     *
     * Renvoyer un boolean (au lieu d'afficher un message) laisse l'appelant décider quoi faire.
     *
     * @param ch la chaîne à ajouter
     * @return true si l'ajout a eu lieu, false sinon
     */
    public boolean ajouter(String ch) {
        // --- 1. Déjà présente, ou tableau plein : l'ajout est refusé ---
        /*
         * Deux raisons de refus reliées par || : une seule suffit.
         * - appartient(ch) != -1 : ch est déjà dans l'ensemble (pas de doublon dans un ensemble) ;
         * - nb == elements.length : toutes les cases sont utilisées. Sans ce test, elements[nb]
         *   lèverait une ArrayIndexOutOfBoundsException au 11e ajout.
         */
        if (appartient(ch) != -1 || nb == elements.length) {
            return false;
        }

        // --- 2. Rangement dans la première case libre ---
        // La première case libre est la case nb (les cases 0 à nb - 1 sont occupées).
        elements[nb] = ch;
        nb++;
        return true;
    }

    /**
     * Retire ch si elle est présente ; renvoie true si ch a été retirée.
     *
     * @param ch la chaîne à retirer
     * @return true si le retrait a eu lieu, false si ch n'était pas dans l'ensemble
     */
    public boolean retirer(String ch) {
        // --- 1. Recherche de la position de ch ---
        int pos = appartient(ch);
        if (pos == -1) {
            return false;               // absente : rien à retirer
        }

        // --- 2. La dernière chaîne prend sa place : l'ordre ne compte pas dans un ensemble ---
        /*
         * Au lieu de décaler tous les éléments suivants d'une case vers la gauche (une boucle), on
         * recopie le dernier élément dans le trou, puis on réduit nb. Les éléments restent dans les
         * cases 0 à nb - 1, sans trou, avec une seule affectation.
         * Si ch était déjà le dernier élément, il se recopie sur lui-même : aucun problème.
         */
        elements[pos] = elements[nb - 1];
        nb--;
        return true;
    }

    /**
     * Égalité avec e : même nombre d'éléments, et chaque élément de l'ensemble courant appartient à e.
     *
     * Le nom sans accent (egalite) vient du fait qu'on évite les accents dans les noms Java.
     * Pourquoi ces deux tests suffisent : si les deux ensembles ont la même taille et que tous les
     * éléments du premier sont dans le second (sans doublon de part et d'autre), alors ils
     * contiennent exactement les mêmes éléments, quel que soit leur ordre.
     *
     * @param e l'autre ensemble
     * @return true si les deux ensembles contiennent les mêmes chaînes
     */
    public boolean egalite(EnsembleStr e) {
        // --- 1. Tailles différentes : les ensembles sont différents ---
        if (nb != e.nb) {                       // e.nb : accès permis, on est dans la classe EnsembleStr
            return false;
        }

        // --- 2. Chaque élément doit appartenir à e ---
        /*
         * Boucle for sur les nb éléments ; dès qu'un élément manque dans e, on renvoie false
         * (inutile de continuer). Si la boucle va jusqu'au bout, tous les éléments ont été trouvés.
         */
        for (int i = 0; i < nb; i++) {
            if (e.appartient(elements[i]) == -1) {
                return false;
            }
        }
        return true;
    }
}
