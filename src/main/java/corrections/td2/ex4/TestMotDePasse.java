package corrections.td2.ex4;

import corrections.outils.Clavier;

// =====================================================================
//  CLASSE TestMotDePasse  -  Série 2, Exercice 4, question 2
//  Rôle : faire saisir un mot de passe fort, l'afficher masqué et crypté,
//  puis tenter de le changer.
// =====================================================================

/**
 * Programme de test de la classe MotDePasse (question 2 de l'énoncé).
 *
 * Responsabilités : dialoguer avec l'utilisateur (saisies et messages). Toutes les règles sur le
 * mot de passe restent dans MotDePasse ; ce programme ne fait que les utiliser.
 */
public class TestMotDePasse {

    // =================================================================
    //  PROGRAMME PRINCIPAL
    // =================================================================

    /**
     * Point d'entrée : saisie d'un mot de passe fort, affichages, puis tentative de changement.
     *
     * @param arg paramètres de la ligne de commande (non utilisés ; le nom arg vient de l'énoncé)
     */
    public static void main(String[] arg) {
        // --- 1. Saisie utilisateur : mot de passe redemandé tant qu'il n'est pas fort ---
        String saisie = Clavier.lireTexte("Choisissez un mot de passe : ");
        MotDePasse mdp = new MotDePasse(saisie);
        /*
         * Boucle while : on ne sait pas combien d'essais il faudra ; on redemande TANT QUE le mot
         * de passe n'est pas fort (! inverse le résultat de est_fort()).
         * La première saisie est faite avant la boucle : si elle est déjà forte, aucun tour.
         * À chaque tour, on crée un nouvel objet MotDePasse avec la nouvelle saisie : c'est lui
         * que la condition teste au tour suivant. Si on oubliait de remplacer mdp, la condition
         * testerait toujours l'ancien mot de passe et la boucle ne s'arrêterait jamais.
         */
        while (!mdp.est_fort()) {
            System.out.println("Mot de passe trop faible. Il manque :");
            afficherCeQuiManque(saisie);
            saisie = Clavier.lireTexte("Nouvel essai : ");
            mdp = new MotDePasse(saisie);
        }
        System.out.println("Mot de passe accepté.");

        // --- 2. Affichage : version masquée et cryptée (plus les deux autres méthodes de la classe) ---
        System.out.println("Version masquée    : " + mdp.masquer());
        System.out.println("Version cryptée    : " + mdp.cryptage());
        System.out.println("Nombre de chiffres : " + mdp.nbr_chiffres());
        System.out.println("Classement des caractères :");
        mdp.classer();

        // --- 3. Tentative de changement du mot de passe ---
        System.out.println();
        String nouveau = Clavier.lireTexte("Nouveau mot de passe : ");
        /*
         * Trois issues possibles, donc if / else if / else (un seul message affiché) :
         * - changer() a réussi ;
         * - sinon, si le nouveau mot de passe n'est pas fort : on explique ce qui manque ;
         * - sinon, il était fort mais refusé : c'est forcément qu'il est identique à l'ancien
         *   (la seule autre raison de refus de changer(), nouveau ne pouvant pas être null ici).
         * Attention : changer() modifie le mot de passe s'il réussit ; il ne faut l'appeler
         * qu'une fois, d'où ce premier test.
         */
        if (mdp.changer(nouveau)) {
            System.out.println("Changement effectué. Nouvelle version masquée : " + mdp.masquer());
        } else if (!new MotDePasse(nouveau).est_fort()) {
            System.out.println("Changement refusé : le nouveau mot de passe n'est pas fort. Il manque :");
            afficherCeQuiManque(nouveau);
        } else {
            // fort mais refusé : c'est donc qu'il est identique à l'ancien
            System.out.println("Changement refusé : le nouveau mot de passe est identique à l'ancien.");
        }
    }

    // =================================================================
    //  MÉTHODE UTILITAIRE (privée)
    // =================================================================

    /**
     * Affiche les règles que le texte s ne respecte pas.
     *
     * Méthode séparée car elle sert à deux endroits (saisie et changement). Elle donne un message
     * précis (ce qui manque) au lieu d'un simple « mot de passe faible ».
     *
     * @param s le mot de passe refusé
     */
    private static void afficherCeQuiManque(String s) {
        // --- 1. Recherche d'une majuscule et d'une minuscule ---
        // Même principe que est_fort() : deux indicateurs passés à true dès qu'on trouve le bon type.
        boolean majuscule = false;
        boolean minuscule = false;
        /*
         * Boucle for sur tous les caractères de s (nombre de tours connu : s.length()).
         */
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                majuscule = true;
            } else if (c >= 'a' && c <= 'z') {
                minuscule = true;
            }
        }

        // --- 2. Affichage de chaque règle non respectée ---
        // Quatre if indépendants (et non else if) : plusieurs règles peuvent manquer en même temps,
        // et chacune doit être affichée.
        if (s.length() < 8) {
            System.out.println("  - au moins 8 caractères (il y en a " + s.length() + ")");
        }
        if (!majuscule) {
            System.out.println("  - au moins une lettre majuscule");
        }
        if (!minuscule) {
            System.out.println("  - au moins une lettre minuscule");
        }
        // On réutilise nbr_chiffres() de MotDePasse au lieu de recompter les chiffres ici.
        if (new MotDePasse(s).nbr_chiffres() == 0) {
            System.out.println("  - au moins un chiffre");
        }
    }
}
