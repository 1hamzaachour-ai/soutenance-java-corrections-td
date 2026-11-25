package corrections.outils;

// ArrayList : liste dont la taille augmente au fur et à mesure (utilisée dans lireArguments,
// car on ne sait pas à l'avance combien de mots l'utilisateur va taper).
import java.util.ArrayList;
// Scanner : la classe du cours (chapitre 4) qui lit ce que l'on tape au clavier.
import java.util.Scanner;

// =====================================================================
//  CLASSE Clavier
//  Rôle : toutes les lectures au clavier du projet.
//
//  Un seul Scanner est créé sur System.in (variable de classe, static) et
//  partagé par tous les exercices : plusieurs Scanner sur System.in se
//  « volent » les caractères déjà lus. Chaque saisie lit une ligne entière
//  avec nextLine(), puis la convertit : une saisie invalide est redemandée
//  au lieu d'arrêter le programme.
// =====================================================================

/**
 * Toutes les saisies au clavier du programme (menus, exercices et projet de réservation).
 *
 * Pourquoi cette classe existe :
 * - Sans elle, chaque exercice créerait son propre Scanner. Or un Scanner lit le clavier par
 *   paquets et garde en réserve ce qu'il a lu en trop : un deuxième Scanner sur System.in ne
 *   verrait pas ces caractères, et des saisies seraient « perdues ». Un seul Scanner, partagé,
 *   évite ce problème.
 * - Elle regroupe la vérification des saisies : un texte qui n'est pas un nombre, ou un nombre
 *   hors des bornes, est redemandé au lieu de faire planter le programme. Ce contrôle est écrit
 *   une seule fois ici au lieu d'être recopié dans chaque menu et chaque exercice.
 *
 * Responsabilités : lire une ligne, un texte non vide, un entier (avec ou sans bornes), une liste
 * de paramètres « comme sur la ligne de commande », et faire une pause.
 *
 * Toutes les méthodes sont static : on les appelle avec le nom de la classe (Clavier.lireEntier),
 * sans créer d'objet Clavier, puisqu'il n'y a qu'un seul clavier à gérer.
 */
public class Clavier {

    // =================================================================
    //  ATTRIBUT DE CLASSE
    // =================================================================

    // static : une seule variable pour toute la classe (et non une par objet). Elle est créée
    // une fois, au premier usage de Clavier, puis partagée par tout le programme.
    // private : aucune autre classe ne peut remplacer ce Scanner par un autre.
    // new Scanner(System.in) : System.in est le flux des caractères tapés au clavier.

    /** Le Scanner unique, partagé par tout le programme. */
    private static Scanner sc = new Scanner(System.in);

    // =================================================================
    //  ACCÈS AU SCANNER
    // =================================================================

    /**
     * Renvoie le Scanner unique.
     *
     * Pourquoi cette méthode : le projet de réservation (classe Saisie, paquetage util) a ses
     * propres méthodes de saisie. Au lieu de créer un second Scanner sur System.in, ce qui
     * provoquerait la perte de saisies expliquée plus haut, il récupère celui-ci.
     *
     * @return le Scanner partagé
     */
    public static Scanner getScanner() {
        return sc;
    }

    // =================================================================
    //  LECTURE DE TEXTE
    // =================================================================

    /**
     * Affiche le message puis renvoie la ligne tapée (éventuellement vide).
     *
     * C'est la seule méthode qui lit vraiment le clavier : toutes les autres passent par elle.
     * print (et non println) laisse le curseur sur la même ligne que la question.
     * nextLine() lit toute la ligne jusqu'à Entrée. On n'utilise pas nextInt() : il laisse le
     * retour à la ligne dans le tampon (la lecture suivante recevrait une ligne vide) et il
     * lève une exception sans consommer la saisie si l'on tape des lettres.
     *
     * @param message la question affichée avant la saisie
     * @return la ligne tapée, sans le retour à la ligne
     */
    public static String lireLigne(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    /**
     * Lit un texte non vide (les espaces au début et à la fin sont retirés avec trim).
     *
     * @param message la question affichée
     * @return un texte d'au moins un caractère, sans espaces au début ni à la fin
     */
    public static String lireTexte(String message) {
        // --- 1. Saisie ---
        // trim() enlève les espaces en trop : « Ali » et «   Ali  » donnent le même texte,
        // et une saisie faite seulement d'espaces devient vide (donc refusée juste après).
        String texte = lireLigne(message).trim();

        // --- 2. Tant que la saisie est vide, on redemande ---
        /*
         * Boucle while : on ne sait pas combien de fois l'utilisateur se trompera (0, 1, 5 fois),
         * le nombre de tours n'est pas connu à l'avance, donc pas de for.
         * Pas de do..while non plus : la première saisie est déjà faite au-dessus, et si elle est
         * correcte la boucle ne doit faire aucun tour (while teste AVANT chaque tour).
         * Condition : texte.length() == 0 signifie « texte vide ». On utilise == car on compare
         * deux nombres (la longueur et 0). La variable qui fait évoluer la condition est texte,
         * relue à chaque tour.
         * Arrêt : dès qu'un texte non vide est tapé. Si on avait écrit != 0 par erreur, la boucle
         * redemanderait justement les bonnes saisies et accepterait les vides.
         */
        while (texte.length() == 0) {
            texte = lireLigne("  Saisie vide, recommencez : ").trim();
        }
        return texte;
    }

    // =================================================================
    //  LECTURE D'ENTIERS
    // =================================================================

    /**
     * Lit un entier (comme sc.nextInt()) et redemande tant que la saisie n'en est pas un :
     * Integer.parseInt lève une NumberFormatException, que l'on intercepte.
     *
     * @param message la question affichée
     * @return l'entier tapé (positif, nul ou négatif)
     */
    public static int lireEntier(String message) {
        // --- 1. Saisie ---
        String saisie = lireLigne(message).trim();

        // --- 2. Conversion en int, redemandée tant qu'elle échoue ---
        /*
         * Boucle while (true) : une boucle « sans fin » dont on sort par le return, dès que la
         * conversion réussit. On ne peut pas écrire une condition simple du type « tant que la
         * saisie n'est pas un entier » : la seule façon de le savoir est d'essayer de convertir
         * (try) et de voir si une exception se produit (catch).
         * - Si Integer.parseInt réussit : return renvoie le nombre et quitte la méthode, donc
         *   aussi la boucle.
         * - Si la saisie n'est pas un entier (« abc », « 3.5 », vide) : parseInt lève une
         *   NumberFormatException ; le catch l'attrape, le programme ne s'arrête pas, on affiche
         *   un message et on relit une saisie, puis la boucle recommence.
         * Sans le try / catch, la première faute de frappe arrêterait tout le programme.
         */
        while (true) {
            try {
                return Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
                saisie = lireLigne("  « " + saisie + " » n'est pas un entier valide, recommencez : ").trim();
            }
        }
    }

    /**
     * Lit un entier compris entre min et max (bornes incluses) ; surcharge de la méthode précédente.
     *
     * Surcharge : même nom lireEntier, mais paramètres différents. Java choisit la bonne version
     * d'après les arguments écrits dans l'appel. C'est cette version qui protège tous les menus :
     * un choix hors de la liste est redemandé, il ne peut donc jamais produire un indice de
     * tableau invalide.
     *
     * @param message la question affichée
     * @param min     plus petite valeur acceptée
     * @param max     plus grande valeur acceptée
     * @return un entier n tel que min <= n <= max
     */
    public static int lireEntier(String message, int min, int max) {
        // --- 1. Saisie d'un entier ---
        // On réutilise la version sans bornes : elle s'occupe déjà des saisies non numériques.
        int n = lireEntier(message);

        // --- 2. Tant qu'il n'est pas entre min et max, on redemande ---
        /*
         * Boucle while : nombre de tours inconnu, et aucun tour si la première valeur est bonne.
         * Condition n < min || n > max : « n est trop petit OU trop grand ». On utilise || (OU)
         * car il suffit qu'UNE des deux erreurs soit présente pour refuser. Avec && (ET), la
         * condition ne serait jamais vraie (un nombre ne peut pas être à la fois plus petit que
         * min et plus grand que max) et toutes les valeurs seraient acceptées.
         * < et > (et non <= et >=) : min et max eux-mêmes sont des valeurs valides.
         * Arrêt : dès que min <= n <= max.
         */
        while (n < min || n > max) {
            n = lireEntier("  La valeur doit être comprise entre " + min + " et " + max + ", recommencez : ");
        }
        return n;
    }

    // =================================================================
    //  LECTURE DE PARAMÈTRES (exercices 4 et 5 du TD 1)
    // =================================================================

    /**
     * Lit une ligne et la découpe comme une ligne de commande : un paramètre par mot.
     *
     * Pourquoi : certains exercices (TD1 Ex4 et Ex5, TD5 Ex2) travaillent sur le tableau args de
     * main. Depuis le menu, on ne peut pas taper une vraie ligne de commande ; on tape donc les
     * paramètres au clavier et cette méthode fabrique le même tableau que Java aurait créé.
     * On découpe à la main avec indexOf et substring (méthodes de String vues en cours) au lieu
     * d'utiliser split, qui n'est pas au programme.
     *
     * @param message la question affichée
     * @return un tableau avec un mot par case (vide si la ligne ne contient que des espaces)
     */
    public static String[] lireArguments(String message) {
        // --- 1. Saisie de la ligne ---
        String ligne = lireLigne(message);

        // --- 2. Découpage en mots, aux espaces ---
        // ArrayList et non tableau : on ne connaît pas encore le nombre de mots.
        ArrayList<String> mots = new ArrayList<String>();
        int i = 0;   // position du caractère examiné dans la ligne
        /*
         * Boucle while : i n'avance pas toujours de 1 (il avance de 1 sur un espace, mais saute
         * d'un coup à la fin du mot sinon). Une boucle for, faite pour un pas régulier i++, serait
         * moins claire ici.
         * Condition i < ligne.length() : on s'arrête quand toute la ligne a été parcourue. Avec
         * <= on lirait ligne.charAt(ligne.length()), qui n'existe pas
         * (StringIndexOutOfBoundsException).
         * La boucle se termine toujours : à chaque tour, i augmente d'au moins 1.
         */
        while (i < ligne.length()) {
            // Condition : le caractère courant est-il un espace ? == compare deux char
            // (les guillemets simples ' ' désignent un caractère, pas une chaîne).
            if (ligne.charAt(i) == ' ') {
                i++;                            // espace entre deux paramètres
            } else {
                // Sinon, un mot commence en i : on cherche l'espace qui le termine.
                int fin = ligne.indexOf(' ', i);
                // indexOf renvoie -1 s'il n'y a plus d'espace : le mot va alors jusqu'au
                // bout de la ligne. Sans ce test, substring(i, -1) lèverait une exception.
                if (fin == -1) {
                    fin = ligne.length();
                }
                mots.add(ligne.substring(i, fin));   // le mot = caractères de i à fin - 1
                i = fin;                             // on repart juste après le mot
            }
        }

        // --- 3. Recopie de la liste dans un tableau de chaînes, comme le tableau args de main ---
        // new String[mots.size()] : maintenant la taille est connue, on peut créer le tableau.
        String[] args = new String[mots.size()];
        /*
         * Boucle for : le nombre de tours est connu (la taille de la liste) et l'indice k sert à
         * la fois pour lire la liste (get(k)) et pour écrire dans le tableau (args[k]).
         * k va de 0 à mots.size() - 1 : la condition k < mots.size() empêche de sortir de la
         * liste et du tableau, qui ont la même taille.
         */
        for (int k = 0; k < mots.size(); k++) {
            args[k] = mots.get(k);
        }
        return args;
    }

    // =================================================================
    //  PAUSE
    // =================================================================

    /**
     * Attend que l'utilisateur appuie sur Entrée.
     *
     * Les exercices affichent beaucoup de texte ; la pause laisse le temps de lire une question
     * avant de passer à la suite. La ligne tapée est lue puis ignorée : on n'a pas besoin de sa
     * valeur, seulement d'attendre. « \n » au début du message saute une ligne avant la phrase.
     */
    public static void pause() {
        lireLigne("\n  (Appuyez sur Entrée pour continuer...)");
    }
}
