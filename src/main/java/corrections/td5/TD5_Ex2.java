package corrections.td5;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;
import corrections.td5.ex2.TestFact;

// =====================================================================
//  CLASSE TD5_Ex2  -  Série 5, Exercice 2
//  Rôle : présenter la factorielle avec gestion des exceptions : énoncé,
//  explications, essais automatiques des quatre cas, puis essai avec vos
//  propres paramètres. Le code est dans le paquetage ex2.
// =====================================================================
/**
 * Présentation de l'exercice 2 de la série 5 (factorielle et exceptions, classe TestFact).
 *
 * Le code est dans le paquetage ex2 : TestFact (le programme corrigé) et NombreNegatifException.
 * Cette classe explique la correction, lance TestFact automatiquement sur les cas d'erreur et sur
 * des cas corrects, puis avec les paramètres tapés par l'utilisateur.
 */
public class TD5_Ex2 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 5) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD5_Ex2() {
        super("Factorielle et exceptions (classe TestFact)",
                "Améliorer TestFact en attrapant une exception par cas d'erreur : paramètre absent, non entier ou négatif.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé et la correction, lance les essais automatiques, puis un essai au clavier.
     *
     * Les essais automatiques couvrent les quatre cas de l'énoncé (pas de paramètre, pas un entier,
     * négatif, correct) et un cas de dépassement (13!), sans que l'utilisateur ait à les taper.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Le programme TestFact calcule la factorielle d'un entier passé en paramètre sur la ligne "
                + "de commande. Il faut l'améliorer en attrapant les différentes exceptions, pour préciser la "
                + "difficulté à l'utilisateur quand le calcul est impossible : il n'y a pas de paramètre sur "
                + "la ligne de commande ; le paramètre n'est pas entier ; le paramètre est négatif.");

        // --- 2. Correction : un catch par cas d'erreur ---
        Ecran.section("Correction");
        String[] code = {"try {", "    int n = Integer.parseInt(args[0]);",
            "    if (n < 0) throw new NombreNegatifException();", "    ... calcul et affichage de n! ...",
            "} catch (ArrayIndexOutOfBoundsException e) {", "    // aucun paramètre",
            "} catch (NumberFormatException e) {", "    // le paramètre n'est pas un entier",
            "} catch (NombreNegatifException e) {", "    // le paramètre est négatif", "}"};
        Ecran.code(code);
        Ecran.texte("Code : corrections/td5/ex2/TestFact.java. Les trois erreurs n'ont pas la même origine :\n"
                + "- aucun paramètre : args est vide, donc args[0] lève une ArrayIndexOutOfBoundsException "
                + "(indice hors du tableau) ;\n"
                + "- paramètre qui n'est pas un entier : Integer.parseInt lève une NumberFormatException ;\n"
                + "- paramètre négatif : Java ne voit aucune erreur, c'est nous qui levons "
                + "NombreNegatifException avec throw. Cette classe hérite de Exception et n'a besoin d'aucun "
                + "contenu : son type suffit pour que le bon catch la reconnaisse.\n"
                + "Chaque catch affiche un message précis (cours p.245 : un catch par exception).");
        Clavier.pause();

        // --- 3. Essais automatiques des quatre cas ---
        Ecran.section("Essais automatiques");
        // Un tableau par essai, comme le tableau args que Java remplirait. new String[0] crée un
        // tableau vide : c'est le cas « aucun paramètre sur la ligne de commande ».
        String[] sansParametre = new String[0];
        String[] pasEntier = {"abc"};
        String[] negatif = {"-3"};
        String[] correct = {"5"};
        String[] trop = {"13"};
        essayer(sansParametre);
        essayer(pasEntier);
        essayer(negatif);
        essayer(correct);
        essayer(trop);
        System.out.println();
        Ecran.texte("Attention au dernier essai : 13! vaut 6 227 020 800, ce qui dépasse le plus grand int "
                + "(2 147 483 647). Le calcul déborde sans lever d'exception et le résultat est faux. Le "
                + "programme est juste jusqu'à 12! = 479 001 600 ; avec un long, il irait jusqu'à 20!.");

        // --- 4. Saisie utilisateur : vos propres paramètres ---
        Ecran.section("À vous");
        String[] args = Clavier.lireArguments("Paramètres de la ligne de commande (ex : 6, abc, -2, ou rien) : ");
        String commande = "java TestFact";
        /*
         * Boucle for sur toutes les cases de args (nombre de tours connu : args.length) pour
         * reconstruire la commande équivalente ; si l'utilisateur n'a rien tapé, aucun tour.
         */
        for (int i = 0; i < args.length; i++) {
            commande = commande + " " + args[i];
        }
        System.out.println(commande);
        TestFact.main(args);

        // --- 5. Remarques sur l'énoncé ---
        Ecran.section("Remarques");
        Ecran.texte("- L'énoncé écrit « lnteger.parselnt » avec des l minuscules au lieu de I majuscules, et "
                + "« prinln » au lieu de « println » : ce sont des fautes de frappe, ce code ne compilerait "
                + "pas.\n"
                + "- On aurait pu tester args.length == 0 avec un if au lieu d'attraper "
                + "ArrayIndexOutOfBoundsException ; l'exercice demande justement de passer par les exceptions.");
    }

    // =================================================================
    //  MÉTHODE UTILITAIRE (privée)
    // =================================================================

    /**
     * Affiche la commande équivalente, puis lance TestFact avec ces paramètres.
     *
     * Méthode séparée car elle est appelée cinq fois (un appel par essai) avec la même
     * présentation : completer(commande, 20) aligne les flèches « -> » en colonne.
     *
     * @param args les paramètres à transmettre à TestFact.main
     */
    private static void essayer(String[] args) {
        String commande = "java TestFact";
        // Même boucle for que dans executer() : on ajoute chaque paramètre à la commande affichée.
        for (int i = 0; i < args.length; i++) {
            commande = commande + " " + args[i];
        }
        System.out.print(Ecran.completer(commande, 20) + "->");
        TestFact.main(args);
    }
}
