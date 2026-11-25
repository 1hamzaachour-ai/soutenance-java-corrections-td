package corrections.td3.ex1;

// =====================================================================
//  CLASSE PileInt  -  Série 3, Exercice 1, question 1
//  Rôle : une pile d'entiers rangée dans un tableau. Le dernier entier
//  empilé est le premier dépilé (« dernier entré, premier sorti ») :
//  on ne travaille que sur la case du haut, le sommet.
// =====================================================================

/**
 * Une pile d'entiers représentée par un tableau, comme le demande l'énoncé.
 *
 * Principe d'une pile : comme une pile d'assiettes, on ajoute (empiler) et on retire (dépiler)
 * toujours par le haut. Le dernier entier posé est donc le premier retiré.
 *
 * Responsabilités : les opérations de l'énoncé (initialiser, afficher, empiler, dépiler, sommet)
 * et les tests utiles (vide, pleine, taille).
 *
 * Choix de conception : au lieu de renvoyer une fausse valeur (par exemple -1) quand on dépile une
 * pile vide, les méthodes lèvent une exception (PileVideException, PilePleineException). Une
 * valeur comme -1 pourrait être un vrai entier de la pile ; une exception, elle, ne peut pas être
 * confondue avec un résultat normal et oblige l'appelant à traiter le cas.
 */
public class PileInt {

    // =================================================================
    //  ATTRIBUTS (private : encapsulation)
    // =================================================================
    // Un tableau d'int, comme l'impose l'énoncé : sa taille (la capacité) est fixée à la création.
    // indiceSommet repère la case du haut. On choisit -1 pour « pile vide » : aucune case n'est
    // occupée, et le premier empiler fera indiceSommet++ pour arriver sur la case 0.
    // private : personne ne peut déplacer le sommet ou écrire dans le tableau sans passer par
    // empiler et depiler, qui vérifient que l'opération est possible.
    private int[] tab;                          // les cases de la pile ; tab.length = capacité
    private int indiceSommet;                   // indice de la case du sommet, -1 si la pile est vide

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Crée une pile vide qui peut contenir capacite entiers.
     *
     * On n'appelle pas initialiser() ici : appeler depuis un constructeur une méthode que des
     * classes filles pourraient redéfinir déclenche un avertissement de javac (« this-escape »).
     * L'affectation directe a exactement le même effet.
     *
     * @param capacite nombre maximal d'entiers (la taille du tableau)
     */
    public PileInt(int capacite) {
        tab = new int[capacite];                // new int[n] : n cases, toutes à 0 au départ
        indiceSommet = -1;                      // la pile est vide au départ (même effet que initialiser())
    }

    // =================================================================
    //  ÉTAT DE LA PILE
    // =================================================================

    /**
     * Initialiser : vide la pile en remettant l'indice du sommet à -1.
     *
     * Les anciennes valeurs restent dans le tableau, mais elles ne comptent plus : seules les cases
     * de 0 à indiceSommet font partie de la pile. Inutile de les effacer une par une.
     */
    public void initialiser() {
        indiceSommet = -1;
    }

    /**
     * @return true si la pile ne contient aucun entier (sommet à -1)
     */
    public boolean estVide() {
        // == compare deux entiers ; le résultat (true ou false) est renvoyé directement,
        // sans écrire if (...) return true; else return false;
        return indiceSommet == -1;
    }

    /**
     * @return true si toutes les cases du tableau sont occupées
     */
    public boolean estPleine() {
        // La dernière case a l'indice tab.length - 1 (les indices commencent à 0).
        // Si le sommet y est arrivé, il n'y a plus de place.
        return indiceSommet == tab.length - 1;
    }

    /**
     * @return le nombre d'entiers rangés (le sommet en case 2 veut dire 3 entiers : cases 0, 1, 2)
     */
    public int taille() {
        return indiceSommet + 1;
    }

    // =================================================================
    //  OPÉRATIONS DE LA PILE
    // =================================================================

    /**
     * Empiler : pose x au sommet de la pile ; lève PilePleineException si elle est pleine.
     *
     * throws PilePleineException : annonce que la méthode peut lever cette exception sans la
     * traiter elle-même. C'est l'appelant (TestPileInt) qui décidera quoi faire, avec un try / catch.
     *
     * @param x l'entier à empiler
     * @throws PilePleineException si toutes les cases sont déjà occupées
     */
    public void empiler(int x) throws PilePleineException {
        // --- 1. Plus de case libre : on lève l'exception ---
        // Sans ce test, tab[indiceSommet] avec indiceSommet == tab.length lèverait une
        // ArrayIndexOutOfBoundsException, une erreur bien moins claire.
        // throw arrête la méthode tout de suite : la suite (étape 2) n'est pas exécutée.
        if (estPleine()) {
            throw new PilePleineException();
        }

        // --- 2. Le sommet monte d'une case, puis x y est rangé ---
        // L'ordre compte : on avance d'abord le sommet (de -1 à 0 pour le premier entier), puis on
        // écrit. Dans l'ordre inverse, on écrirait dans tab[-1] (exception) ou on écraserait
        // l'ancien sommet.
        indiceSommet++;
        tab[indiceSommet] = x;
    }

    /**
     * Dépiler : retire l'entier du sommet et le renvoie ; lève PileVideException si la pile est vide.
     *
     * @return l'entier qui était au sommet
     * @throws PileVideException si la pile ne contient aucun entier
     */
    public int depiler() throws PileVideException {
        // --- 1. Rien à retirer : on lève l'exception ---
        if (estVide()) {
            throw new PileVideException();
        }

        // --- 2. On garde la valeur du sommet, puis le sommet descend d'une case ---
        // On lit la valeur AVANT de faire descendre le sommet, sinon on renverrait l'entier d'en dessous.
        int x = tab[indiceSommet];
        indiceSommet--;                         // la case n'est pas effacée : le prochain empiler la réécrira
        return x;
    }

    /**
     * Sommet : renvoie l'entier du haut sans le retirer ; lève PileVideException si la pile est vide.
     *
     * Différence avec depiler() : indiceSommet ne change pas, la pile reste identique.
     *
     * @return l'entier situé au sommet
     * @throws PileVideException si la pile ne contient aucun entier
     */
    public int sommet() throws PileVideException {
        if (estVide()) {
            throw new PileVideException();
        }
        return tab[indiceSommet];
    }

    // =================================================================
    //  AFFICHAGE
    // =================================================================

    /** Afficher : dessine la pile, du sommet (en haut) jusqu'à la base. */
    public void afficher() {
        // --- 1. Pile vide : rien à dessiner ---
        // return dans une méthode void : on quitte la méthode tout de suite, sans dessiner.
        if (estVide()) {
            System.out.println("  (la pile est vide)");
            return;
        }

        // --- 2. Largeur des cases : celle de l'entier le plus long ---
        // Recherche du maximum : toutes les cases auront la largeur du plus long nombre, pour que
        // les bords « | » soient alignés.
        int largeur = 1;
        /*
         * Boucle for sur les cases OCCUPÉES seulement : de 0 à indiceSommet INCLUS (d'où <=).
         * Les cases au-dessus du sommet contiennent d'anciennes valeurs qui ne font plus partie de
         * la pile : on ne les regarde pas (on ne va donc pas jusqu'à tab.length).
         */
        for (int i = 0; i <= indiceSommet; i++) {
            // String.valueOf transforme le nombre en texte pour compter ses caractères
            // (-150 en compte 4, signe compris).
            int l = String.valueOf(tab[i]).length();
            if (l > largeur) {
                largeur = l;
            }
        }

        // --- 3. Affichage des cases, du sommet vers la base, puis du fond de la pile ---
        /*
         * Boucle for qui DESCEND (i--) : on dessine une pile, le sommet doit apparaître en haut de
         * l'écran ; on part donc du sommet (i = indiceSommet) jusqu'à la case 0 (i >= 0).
         * >= 0 et non > 0 : la case 0, la base de la pile, doit être affichée aussi.
         */
        for (int i = indiceSommet; i >= 0; i--) {
            // Opérateur conditionnel : la flèche n'est ajoutée que sur la ligne du sommet.
            String repere = (i == indiceSommet) ? "   <- sommet" : "";
            System.out.println("  | " + aDroite(tab[i], largeur) + " |" + repere);
        }
        // Le fond de la pile : « +----+ », avec autant de tirets que la case (largeur + 2 espaces).
        StringBuffer fond = new StringBuffer("  +");
        /*
         * Boucle for qui ajoute exactement largeur + 2 tirets (nombre de tours connu).
         */
        for (int i = 0; i < largeur + 2; i++) {
            fond.append('-');
        }
        fond.append('+');
        System.out.println(fond.toString());
    }

    /**
     * Écrit x en le complétant par des espaces à gauche, pour aligner les nombres à droite.
     *
     * @param x       le nombre à écrire
     * @param largeur la largeur voulue, en caractères
     * @return le nombre précédé des espaces nécessaires (par exemple « _7 » pour largeur 2)
     */
    private static String aDroite(int x, int largeur) {
        String s = String.valueOf(x);
        StringBuffer sb = new StringBuffer();
        /*
         * Boucle for qui ajoute largeur - s.length() espaces : i part de la longueur actuelle du
         * nombre et monte jusqu'à la largeur voulue. Si le nombre est déjà assez large, la
         * condition est fausse dès le départ : aucun espace.
         */
        for (int i = s.length(); i < largeur; i++) {
            sb.append(' ');
        }
        sb.append(s);
        return sb.toString();
    }
}
