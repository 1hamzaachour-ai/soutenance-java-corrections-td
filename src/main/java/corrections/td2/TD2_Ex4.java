package corrections.td2;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;
import corrections.td2.ex4.MotDePasse;
import corrections.td2.ex4.TestMotDePasse;

// =====================================================================
//  CLASSE TD2_Ex4  -  Série 2, Exercice 4
//  Rôle : présenter le vérificateur de mots de passe : énoncé, diagramme
//  UML, essai de toutes les méthodes, exécution de TestMotDePasse et
//  réponses aux questions. Le code demandé est dans le paquetage ex4.
// =====================================================================

/**
 * Présentation de l'exercice 4 de la série 2 (vérificateur de mots de passe) dans le menu.
 *
 * Le code demandé (MotDePasse et TestMotDePasse) est dans le paquetage ex4. Cette classe le
 * présente : énoncé, diagramme UML, essai de chaque méthode sur un exemple fixe, exécution du
 * programme de test, puis réponse à la question sur private.
 */
public class TD2_Ex4 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 2) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD2_Ex4() {
        super("Vérificateur de mots de passe (classe MotDePasse)",
                "Écrire la classe MotDePasse (chiffres, classement, force, masquage, cryptage) en protégeant sa valeur avec private.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, puis répond aux trois questions.
     *
     * L'essai de l'étape 3 utilise un exemple fixe (« Tunis2026 ») : on voit le résultat de chaque
     * méthode sans dépendre de ce que l'utilisateur tapera. Dans ces chaînes, \" affiche un
     * guillemet.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Écrire une classe MotDePasse ayant un attribut privé valeur (de type String).");

        // --- 2. Question 1 : la classe MotDePasse (diagramme UML) ---
        Ecran.question(1, "La classe MotDePasse offre : MotDePasse(String v) ; int nbr_chiffres() (nombre "
                + "de chiffres) ; void classer() (majuscules dans un tableau MAJ, minuscules dans MIN, "
                + "autres caractères dans AUTRES, puis affichage) ; boolean est_fort() (au moins 8 "
                + "caractères, une majuscule, une minuscule et un chiffre) ; String masquer() (seuls les "
                + "deux premiers caractères restent visibles, les autres sont remplacés par '*') ; String "
                + "cryptage() (code de César : chaque minuscule est remplacée par la lettre située 3 "
                + "positions plus loin, 'x' devient 'a', 'y' 'b', 'z' 'c') ; boolean changer(String "
                + "nouveau) (remplace le mot de passe seulement si nouveau est fort et différent de "
                + "l'ancien ; renvoie true si le changement a eu lieu).");
        String[] attributs = {"- valeur : String"};
        String[] methodes = {"+ MotDePasse(v : String)", "+ nbr_chiffres() : int", "+ classer() : void",
            "+ est_fort() : boolean", "+ masquer() : String", "+ cryptage() : String",
            "+ changer(nouveau : String) : boolean"};
        Ecran.uml("MotDePasse", attributs, methodes);
        // --- 3. Essai de toutes les méthodes sur l'exemple « Tunis2026 » ---
        System.out.println("Essai de toutes les méthodes avec l'exemple de l'énoncé :");
        System.out.println("MotDePasse mdp = new MotDePasse(\"Tunis2026\");");
        MotDePasse mdp = new MotDePasse("Tunis2026");
        System.out.println("  nbr_chiffres() : " + mdp.nbr_chiffres());
        System.out.println("  classer()      :");
        mdp.classer();
        System.out.println("  est_fort()     : " + mdp.est_fort());
        System.out.println("  masquer()      : " + mdp.masquer());
        System.out.println("  cryptage()     : " + mdp.cryptage());
        // « xyz » montre le retour au début de l'alphabet (le % 26 de cryptage) : résultat « abc ».
        System.out.println("  new MotDePasse(\"xyz\").cryptage() : " + new MotDePasse("xyz").cryptage());
        // Les trois appels de changer() montrent ses trois issues : refus (identique), refus (faible),
        // puis succès. L'ordre compte : le dernier modifie vraiment le mot de passe.
        System.out.println("  changer(\"Tunis2026\") : " + mdp.changer("Tunis2026") + "   (identique à l'ancien)");
        System.out.println("  changer(\"tunis\")     : " + mdp.changer("tunis") + "   (pas assez fort)");
        System.out.println("  changer(\"Sfax2027!\") : " + mdp.changer("Sfax2027!") + "    (fort et différent)");
        System.out.println("  masquer() après le changement : " + mdp.masquer());
        System.out.println();
        Ecran.texte("Remarque : « Tunis2026 » compte 9 caractères ; la version masquée garde la même "
                + "longueur, soit 7 étoiles : « Tu******* » (l'exemple de l'énoncé n'en montre que 6).\n"
                + "changer() réutilise est_fort() sur un objet temporaire, « new MotDePasse(nouveau) », "
                + "au lieu de recopier les règles.");
        Clavier.pause();

        // --- 4. Question 2 : exécution de TestMotDePasse ---
        Ecran.question(2, "Écrire une classe TestMotDePasse : l'utilisateur saisit un mot de passe ; tant "
                + "qu'il n'est pas fort, le programme affiche ce qui manque et redemande la saisie. Une "
                + "fois accepté, afficher sa version masquée et sa version cryptée, puis demander un "
                + "nouveau mot de passe et tenter de le changer.");
        Ecran.texte("(Essayez d'abord un mot de passe faible, par exemple « tunis », puis un mot de passe "
                + "fort comme « Tunis2026 ».)");
        TestMotDePasse.main(new String[0]);

        // --- 5. Question 3 : pourquoi private, et pourquoi pas de getValeur() ---
        Ecran.question(3, "Pourquoi l'attribut valeur est-il déclaré private ? Pourquoi n'est-il pas "
                + "souhaitable d'ajouter une méthode getValeur() à cette classe ?");
        Ecran.texte("- private : le mot de passe est une donnée sensible. Seules les méthodes de "
                + "MotDePasse peuvent le lire ou le modifier ; personne ne peut écrire "
                + "« mdp.valeur = \"123\"; » pour contourner les règles de changer() (mot de passe fort et "
                + "différent de l'ancien). C'est l'encapsulation : l'objet garantit lui-même sa cohérence.\n"
                + "- Pas de getValeur() : elle rendrait le mot de passe en clair à n'importe quel code "
                + "(affichage, fichier journal, autre classe...), ce qui annulerait l'intérêt de masquer() "
                + "et de cryptage(). Un mot de passe ne se relit pas, il se vérifie : on ajouterait "
                + "plutôt une méthode « boolean verifier(String essai) » qui répond seulement vrai ou faux. "
                + "De même, un setValeur() permettrait de changer le mot de passe sans passer par les "
                + "contrôles de changer().\n"
                + "Vérification : depuis une autre classe, « System.out.println(mdp.valeur); » ne "
                + "compile pas, javac répond :");
        Ecran.champ("javac", "erreur : valeur has private access in MotDePasse");
    }
}
