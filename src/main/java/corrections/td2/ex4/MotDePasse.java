package corrections.td2.ex4;

// =====================================================================
//  CLASSE MotDePasse  -  Série 2, Exercice 4, question 1
//  Rôle : un vérificateur de mots de passe. L'attribut valeur est private
//  et aucune méthode ne le renvoie en clair : on peut le compter, le
//  classer, le masquer, le crypter ou le changer, jamais le relire.
// =====================================================================

/**
 * Un mot de passe, avec les traitements demandés par l'énoncé : compter ses chiffres, classer ses
 * caractères, vérifier qu'il est fort, le masquer, le crypter, le changer.
 *
 * Pourquoi cette classe existe : elle regroupe la donnée sensible (le mot de passe) et tout ce
 * qu'on a le droit d'en faire. Comme l'attribut est private et qu'il n'y a pas de getValeur(),
 * aucun autre programme ne peut relire le mot de passe en clair : c'est la réponse à la
 * question « pourquoi ne pas ajouter getValeur() ? ».
 *
 * Toutes les méthodes travaillent caractère par caractère avec length() et charAt(i), les
 * méthodes de String vues en cours, et des comparaisons de caractères (c >= 'A' && c <= 'Z').
 */
public class MotDePasse {

    // =================================================================
    //  ATTRIBUT (private : le mot de passe est une donnée sensible)
    // =================================================================
    // String : un mot de passe est un texte. private : seules les méthodes de MotDePasse le lisent.
    private String valeur;

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Crée le mot de passe à partir du texte v.
     *
     * Le constructeur ne vérifie pas la force du mot de passe : c'est TestMotDePasse qui redemande
     * la saisie tant que est_fort() renvoie false. Ainsi on peut aussi créer un MotDePasse faible
     * pour l'analyser (c'est ce que fait afficherCeQuiManque).
     *
     * @param v le texte du mot de passe
     */
    public MotDePasse(String v) {
        valeur = v;
    }

    // =================================================================
    //  ANALYSE DU MOT DE PASSE
    // =================================================================

    /**
     * Nombre de chiffres contenus dans le mot de passe.
     *
     * Le nom avec un tiret bas (nbr_chiffres) est celui imposé par l'énoncé.
     *
     * @return le nombre de caractères compris entre '0' et '9'
     */
    public int nbr_chiffres() {
        // --- 1. Initialisation du compteur ---
        // Un compteur part de 0 et augmente de 1 à chaque chiffre trouvé.
        int nb = 0;

        // --- 2. Parcours des caractères : on compte les chiffres ---
        /*
         * Boucle for : il faut examiner TOUS les caractères, de l'indice 0 au dernier ; leur
         * nombre est connu (valeur.length()). L'indice i sert à lire chaque caractère avec charAt.
         * i < valeur.length() : le dernier indice valide est length() - 1. Avec <=, charAt
         * lèverait une StringIndexOutOfBoundsException au dernier tour.
         */
        for (int i = 0; i < valeur.length(); i++) {
            char c = valeur.charAt(i);
            // c >= '0' && c <= '9' : c est entre '0' et '9' (les DEUX bornes doivent être
            // respectées, d'où le &&). Les chiffres ont des codes qui se suivent : ce test suffit.
            if (c >= '0' && c <= '9') {         // les chiffres se suivent dans l'ordre des caractères
                nb++;
            }
        }
        return nb;
    }

    /**
     * Range les majuscules dans MAJ, les minuscules dans MIN et le reste dans AUTRES, puis affiche.
     *
     * Les noms de tableaux MAJ, MIN et AUTRES sont ceux de l'énoncé.
     */
    public void classer() {
        // --- 1. Initialisation : trois tableaux et leurs compteurs ---
        // On ne sait pas d'avance combien de majuscules il y aura. Un tableau a une taille fixe :
        // on prévoit le pire cas (tous les caractères dans le même tableau), soit n cases.
        // Le compteur de chaque tableau indique combien de cases sont réellement remplies, et
        // aussi l'indice de la prochaine case libre.
        int n = valeur.length();
        char[] MAJ = new char[n];               // tableaux dimensionnés au pire cas,
        char[] MIN = new char[n];               // un compteur indique la partie remplie
        char[] AUTRES = new char[n];
        int nbMaj = 0;
        int nbMin = 0;
        int nbAutres = 0;

        // --- 2. Rangement de chaque caractère dans le bon tableau ---
        /*
         * Boucle for sur les n caractères (nombre de tours connu). Chaque caractère va dans UN
         * seul tableau : d'où la chaîne if / else if / else, où une seule branche est exécutée.
         */
        for (int i = 0; i < n; i++) {
            char c = valeur.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                // Majuscule : rangée à la première case libre de MAJ, puis le compteur avance.
                MAJ[nbMaj] = c;
                nbMaj++;
            } else if (c >= 'a' && c <= 'z') {
                MIN[nbMin] = c;
                nbMin++;
            } else {
                // Tout le reste : chiffres, symboles, lettres accentuées (é n'est pas entre a et z).
                AUTRES[nbAutres] = c;
                nbAutres++;
            }
        }

        // --- 3. Affichage des trois tableaux ---
        // On transmet le compteur : seules les cases remplies doivent être affichées.
        afficherTableau("MAJ   ", MAJ, nbMaj);
        afficherTableau("MIN   ", MIN, nbMin);
        afficherTableau("AUTRES", AUTRES, nbAutres);
    }

    /**
     * Fort = au moins 8 caractères, au moins une majuscule, une minuscule et un chiffre.
     *
     * @return true si les quatre règles sont respectées
     */
    public boolean est_fort() {
        // --- 1. Recherche d'une majuscule et d'une minuscule ---
        // Deux indicateurs (boolean) : false au départ (« pas encore trouvé »), passés à true dès
        // qu'on rencontre un caractère du bon type. Ils ne repassent jamais à false.
        boolean majuscule = false;
        boolean minuscule = false;
        /*
         * Boucle for sur tous les caractères : on ne s'arrête pas au premier trouvé (pas de
         * break), c'est plus simple et le mot de passe est court.
         */
        for (int i = 0; i < valeur.length(); i++) {
            char c = valeur.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                majuscule = true;
            } else if (c >= 'a' && c <= 'z') {
                minuscule = true;
            }
        }

        // --- 2. Les quatre conditions doivent être vraies ---
        // && entre les quatre règles : il suffit qu'UNE soit fausse pour que le mot de passe soit
        // faible. >= 8 (et non > 8) : 8 caractères exactement sont acceptés. majuscule s'écrit
        // seul, sans « == true », car c'est déjà un boolean.
        return valeur.length() >= 8 && majuscule && minuscule && nbr_chiffres() > 0;
    }

    // =================================================================
    //  TRANSFORMATIONS (le résultat est une nouvelle chaîne)
    // =================================================================

    /**
     * Seuls les deux premiers caractères restent visibles, les autres sont remplacés par '*'.
     *
     * Le mot de passe lui-même n'est pas modifié : on travaille sur une copie.
     *
     * @return la version masquée, par exemple « Ab****** »
     */
    public String masquer() {
        // --- 1. Copie modifiable : une String n'est pas modifiable, un StringBuffer l'est ---
        StringBuffer resultat = new StringBuffer(valeur);

        // --- 2. Remplacement des caractères à partir du troisième (indice 2) ---
        /*
         * Boucle for qui commence à i = 2 (et non à 0) : les indices 0 et 1, les deux premiers
         * caractères, doivent rester visibles. Elle s'arrête après le dernier caractère
         * (i < valeur.length()). Si le mot de passe a 2 caractères ou moins, aucun tour.
         */
        for (int i = 2; i < valeur.length(); i++) {
            resultat.setCharAt(i, '*');         // setCharAt remplace le caractère d'indice i
        }
        return resultat.toString();
    }

    /**
     * Code de César : chaque minuscule avance de 3 lettres ('x' -> 'a', 'y' -> 'b', 'z' -> 'c').
     *
     * Seules les minuscules sont transformées : les majuscules, les chiffres et les symboles
     * restent identiques, comme le demande l'énoncé.
     *
     * @return la version cryptée du mot de passe
     */
    public String cryptage() {
        // --- 1. Copie modifiable du mot de passe ---
        StringBuffer resultat = new StringBuffer(valeur);

        // --- 2. Décalage de chaque minuscule de 3 lettres ---
        /*
         * Boucle for sur tous les caractères ; l'indice i sert à lire dans valeur et à écrire au
         * même endroit dans resultat (les deux ont la même longueur).
         */
        for (int i = 0; i < valeur.length(); i++) {
            char c = valeur.charAt(i);
            if (c >= 'a' && c <= 'z') {
                // Le calcul se fait en int (promotion numérique), d'où le cast en char ;
                // le modulo 26 fait revenir au début de l'alphabet après 'z'.
                // Détail : c - 'a' donne le rang de la lettre (0 pour a, 25 pour z) ; on ajoute 3 ;
                // % 26 ramène 26, 27, 28 à 0, 1, 2 ; + 'a' redonne une lettre.
                resultat.setCharAt(i, (char) ('a' + (c - 'a' + 3) % 26));
            }
        }
        return resultat.toString();
    }

    // =================================================================
    //  CHANGEMENT DU MOT DE PASSE
    // =================================================================

    /**
     * Remplace le mot de passe seulement si nouveau est fort et différent de l'ancien.
     *
     * Elle renvoie un boolean au lieu d'afficher un message : c'est l'appelant (TestMotDePasse)
     * qui décide quoi dire à l'utilisateur. La classe reste ainsi utilisable dans un autre programme.
     *
     * @param nouveau le mot de passe proposé
     * @return true si le changement est fait, false s'il est refusé
     */
    public boolean changer(String nouveau) {
        // --- 1. Refus : absent, identique à l'ancien, ou pas assez fort ---
        /*
         * Trois raisons de refuser, reliées par || : une seule suffit.
         * - nouveau == null : aucune chaîne n'a été donnée. Ce test est placé EN PREMIER : si
         *   nouveau vaut null, || s'arrête là et nouveau.equals(...) n'est jamais appelé, ce qui
         *   évite une NullPointerException.
         * - nouveau.equals(valeur) : même contenu que l'ancien (equals compare le texte, == les
         *   références).
         * - !new MotDePasse(nouveau).est_fort() : on crée un objet temporaire pour réutiliser
         *   est_fort() au lieu de recopier les quatre règles ; ! inverse : « n'est pas fort ».
         */
        if (nouveau == null || nouveau.equals(valeur) || !new MotDePasse(nouveau).est_fort()) {
            return false;
        }

        // --- 2. Changement accepté ---
        valeur = nouveau;
        return true;
    }

    // =================================================================
    //  MÉTHODE UTILITAIRE (privée)
    // =================================================================

    /**
     * Affiche les « taille » premières cases du tableau, séparées par des virgules.
     *
     * private static : elle n'est utile qu'à classer() et n'utilise aucun attribut de l'objet.
     *
     * @param nom     le nom affiché devant le tableau
     * @param tableau le tableau de caractères
     * @param taille  le nombre de cases remplies (les suivantes ne sont pas affichées)
     */
    private static void afficherTableau(String nom, char[] tableau, int taille) {
        // --- 1. Construction du texte « a, b, c » ---
        StringBuffer contenu = new StringBuffer();
        /*
         * Boucle for jusqu'à taille (et non tableau.length) : seules les cases remplies comptent ;
         * les autres contiennent le caractère de valeur 0, sans signification.
         */
        for (int i = 0; i < taille; i++) {
            // On met « , » AVANT chaque élément sauf le premier (i > 0) : ainsi il n'y a pas de
            // virgule en trop à la fin de la liste.
            if (i > 0) {
                contenu.append(", ");
            }
            contenu.append(tableau[i]);
        }

        // --- 2. Affichage ---
        System.out.println("  " + nom + " = [" + contenu.toString() + "]");
    }
}
