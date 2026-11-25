package util;

// Clavier (menu de la soutenance) : on lui emprunte son Scanner, pour n'en avoir qu'un seul.
import corrections.outils.Clavier;

// java.time : les classes de date (LocalDate) et d'heure (LocalTime) de Java, avec leur format
// d'écriture (DateTimeFormatter), l'exception levée quand le texte ne correspond pas
// (DateTimeParseException) et le mode de vérification STRICT (ResolverStyle).
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/**
 * Saisies sécurisées au clavier : chaque méthode REDEMANDE tant que la valeur est invalide,
 * et affiche un message d'erreur clair. Le programme ne plante donc jamais sur une faute de frappe.
 *
 * Deux règles importantes :
 *  - un SEUL Scanner pour toute l'application (plusieurs Scanner sur System.in se volent les données) ;
 *  - on lit toujours une ligne entière avec nextLine() (jamais nextInt()), ce qui évite le piège
 *    du retour à la ligne oublié dans le tampon.
 *
 * Quand le clavier est fermé (Ctrl+D / Ctrl+Z), nextLine() lance NoSuchElementException :
 * on ne l'attrape pas ici, c'est Main qui ferme alors le programme proprement.
 *
 * Pourquoi cette classe existe : le projet pose beaucoup de questions (nom, capacité, date,
 * heure, identifiant, oui / non). Toutes les vérifications de saisie sont regroupées ici, une seule
 * fois ; Main peut alors écrire simplement Saisie.lireDate("Date : ") et recevoir une date valide.
 *
 * Construction de toutes les méthodes : une boucle while (true) qui lit, vérifie, et sort avec
 * return dès que la saisie est correcte ; sinon elle affiche l'erreur et recommence. On ne peut
 * pas savoir à l'avance combien d'essais il faudra, d'où while et non for.
 *
 * Plusieurs méthodes existent en deux versions de même nom (surcharge) : lireTexte(message) et
 * lireTexte(message, valeurActuelle). La seconde sert aux modifications : Entrée seule garde
 * l'ancienne valeur. Les deux appellent une méthode private commune (...OuDefaut), pour ne pas
 * écrire deux fois la même boucle.
 */
public class Saisie {

    // Intégration à la soutenance : on reprend le Scanner de Clavier (menus et exercices des séries)
    // au lieu d'en créer un deuxième sur System.in, qui volerait des lignes à celui de Clavier.
    private static final Scanner SCANNER = Clavier.getScanner();

    // Mode STRICT : refuse les dates qui n'existent pas (31/02/2026) et les heures comme 24:00.
    // En mode STRICT, l'année s'écrit "uuuu" (et non "yyyy").
    // "d/M/uuuu" accepte aussi bien 05/12/2026 que 5/12/2026 ; "H:mm" accepte 09:30 comme 9:30.
    private static final DateTimeFormatter FORMAT_DATE =
            DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter FORMAT_HEURE =
            DateTimeFormatter.ofPattern("H:mm").withResolverStyle(ResolverStyle.STRICT);

    // ===================== Lecture de base =====================

    /**
     * Affiche le message puis lit une ligne (sans les espaces au début et à la fin).
     *
     * Toutes les autres méthodes passent par celle-ci : c'est le seul endroit qui lit le clavier.
     * flush() force l'affichage immédiat de la question avant d'attendre la réponse.
     *
     * @param message la question affichée
     * @return la ligne tapée, sans espaces au début ni à la fin (trim)
     */
    public static String lireLigne(String message) {
        System.out.print(message);
        System.out.flush();
        return SCANNER.nextLine().trim();
    }

    // ===================== Texte =====================

    /**
     * Lit un texte : il ne doit pas être vide ni contenir de point-virgule (séparateur des fichiers).
     *
     * @param message la question affichée
     * @return un texte valide
     */
    public static String lireTexte(String message) {
        // null = « pas de valeur actuelle » : une saisie vide sera refusée.
        return lireTexteOuDefaut(message, null);
    }

    /**
     * Comme lireTexte, mais si l'utilisateur appuie seulement sur Entrée, on garde la valeur actuelle.
     *
     * @param message        la question affichée
     * @param valeurActuelle la valeur renvoyée si l'utilisateur ne tape rien
     * @return le nouveau texte, ou la valeur actuelle
     */
    public static String lireTexte(String message, String valeurActuelle) {
        return lireTexteOuDefaut(message, valeurActuelle);
    }

    /**
     * Boucle de saisie commune aux deux versions de lireTexte.
     *
     * @param message la question affichée
     * @param defaut  valeur renvoyée sur une saisie vide, ou null si une saisie vide est interdite
     * @return un texte non vide, sans point-virgule (ou la valeur par défaut)
     */
    private static String lireTexteOuDefaut(String message, String defaut) {
        // Boucle while (true) : on redemande jusqu'à une saisie valide ; la sortie se fait par return.
        while (true) {
            String texte = lireLigne(message);
            // Entrée seule ET une valeur par défaut existe : on garde l'ancienne valeur (modification).
            // && : les deux conditions sont nécessaires ; sans valeur par défaut, le vide est une erreur.
            if (texte.isEmpty() && defaut != null) {
                return defaut;
            }
            /*
             * Trois cas exclusifs (if / else if / else) :
             * - texte vide : refusé ;
             * - texte avec « ; » : refusé, car « ; » sépare les champs dans les fichiers texte ;
             *   un nom comme « Salle;A » couperait la ligne en trop de champs au rechargement ;
             * - sinon : le texte est accepté et renvoyé (ce qui termine la boucle).
             */
            if (texte.isEmpty()) {
                System.out.println("Erreur : cette saisie ne peut pas être vide.");
            } else if (texte.contains(";")) {
                System.out.println("Erreur : le caractère point-virgule (;) est interdit.");
            } else {
                return texte;
            }
        }
    }

    // ===================== Nombres =====================

    /**
     * Lit un nombre entier (positif, nul ou négatif).
     *
     * @param message la question affichée
     * @return l'entier tapé
     */
    public static int lireEntier(String message) {
        while (true) {
            String texte = lireLigne(message);
            // parseInt lève NumberFormatException si le texte n'est pas un entier : le catch
            // l'attrape, affiche l'erreur, et la boucle redemande. Si la conversion réussit,
            // return renvoie le nombre et termine la méthode.
            try {
                return Integer.parseInt(texte);
            } catch (NumberFormatException e) {
                System.out.println("Erreur : veuillez saisir un nombre valide.");
            }
        }
    }

    /**
     * Lit un nombre entier strictement positif (1, 2, 3...).
     *
     * @param message la question affichée
     * @return un entier supérieur à 0
     */
    public static int lireEntierPositif(String message) {
        // 0 = « pas de valeur par défaut » (voir lireEntierPositifOuDefaut).
        return lireEntierPositifOuDefaut(message, 0);
    }

    /**
     * Comme lireEntierPositif, mais Entrée seule garde la valeur actuelle.
     *
     * @param message        la question affichée
     * @param valeurActuelle la valeur renvoyée si l'utilisateur ne tape rien
     * @return le nouvel entier, ou la valeur actuelle
     */
    public static int lireEntierPositif(String message, int valeurActuelle) {
        return lireEntierPositifOuDefaut(message, valeurActuelle);
    }

    // defaut = 0 signifie "pas de valeur par défaut" (0 n'est jamais une réponse valide ici)
    /**
     * Boucle de saisie commune aux deux versions de lireEntierPositif.
     *
     * @param message la question affichée
     * @param defaut  valeur renvoyée sur une saisie vide, ou 0 si une saisie vide est interdite
     * @return un entier strictement positif
     */
    private static int lireEntierPositifOuDefaut(String message, int defaut) {
        while (true) {
            String texte = lireLigne(message);
            // Entrée seule avec une vraie valeur par défaut (> 0) : on la garde.
            if (texte.isEmpty() && defaut > 0) {
                return defaut;
            }
            try {
                int nombre = Integer.parseInt(texte);
                // > 0 : une capacité ou une quantité de 0 (ou négative) n'a pas de sens.
                if (nombre > 0) {
                    return nombre;
                }
                System.out.println("Erreur : le nombre doit être supérieur à 0.");
            } catch (NumberFormatException e) {
                // Texte vide (sans valeur par défaut) ou lettres : parseInt a échoué.
                System.out.println("Erreur : veuillez saisir un nombre valide.");
            }
        }
    }

    /**
     * Lit un choix de menu compris entre min et max (inclus).
     *
     * @param min le plus petit choix accepté (0 : Quitter ou Retour)
     * @param max le plus grand choix accepté
     * @return un choix valide
     */
    public static int lireChoixMenu(int min, int max) {
        while (true) {
            // lireEntier s'occupe déjà des saisies qui ne sont pas des nombres.
            int choix = lireEntier("Votre choix : ");
            // choix >= min && choix <= max : dans l'intervalle, bornes comprises. && car les deux
            // bornes doivent être respectées. Grâce à ce test, le switch de Main ne reçoit jamais
            // un numéro inexistant.
            if (choix >= min && choix <= max) {
                return choix;
            }
            System.out.println("Erreur : choix invalide. Entrez un nombre entre " + min + " et " + max + ".");
        }
    }

    // ===================== Identifiant =====================

    /**
     * Lit un identifiant (S1, E2, U3, R4...). Il est mis en majuscules : "s1" devient "S1".
     *
     * Les identifiants sont rangés en majuscules : sans toUpperCase(), « s1 » ne serait pas
     * reconnu (equals distingue les majuscules des minuscules).
     *
     * @param message la question affichée
     * @return un identifiant non vide, en majuscules
     */
    public static String lireIdentifiant(String message) {
        while (true) {
            String id = lireLigne(message).toUpperCase();
            // ! inverse : on accepte dès que l'identifiant n'est PAS vide. Le contrôle « existe-t-il ? »
            // est fait ensuite par GestionReservations (exception RessourceIntrouvableException).
            if (!id.isEmpty()) {
                return id;
            }
            System.out.println("Erreur : l'identifiant ne peut pas être vide.");
        }
    }

    // ===================== Date et heure =====================

    /**
     * Lit une date au format JJ/MM/AAAA.
     *
     * @param message la question affichée
     * @return une date qui existe vraiment
     */
    public static LocalDate lireDate(String message) {
        return lireDateOuDefaut(message, null);
    }

    /**
     * Comme lireDate, mais Entrée seule garde la date actuelle.
     *
     * @param message      la question affichée
     * @param dateActuelle la date renvoyée si l'utilisateur ne tape rien
     * @return la nouvelle date, ou la date actuelle
     */
    public static LocalDate lireDate(String message, LocalDate dateActuelle) {
        return lireDateOuDefaut(message, dateActuelle);
    }

    /**
     * Boucle de saisie commune aux deux versions de lireDate.
     *
     * @param message la question affichée
     * @param defaut  date renvoyée sur une saisie vide, ou null si une saisie vide est interdite
     * @return une date valide
     */
    private static LocalDate lireDateOuDefaut(String message, LocalDate defaut) {
        while (true) {
            String texte = lireLigne(message);
            if (texte.isEmpty() && defaut != null) {
                return defaut;
            }
            // LocalDate.parse lève DateTimeParseException si le texte ne suit pas le format ou si la
            // date n'existe pas (31/02/2026, grâce au mode STRICT). On redemande alors.
            try {
                return LocalDate.parse(texte, FORMAT_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Erreur : format de date incorrect. Utilisez JJ/MM/AAAA.");
            }
        }
    }

    /**
     * Lit une heure au format HH:mm.
     *
     * @param message la question affichée
     * @return une heure valide
     */
    public static LocalTime lireHeure(String message) {
        return lireHeureOuDefaut(message, null);
    }

    /**
     * Comme lireHeure, mais Entrée seule garde l'heure actuelle.
     *
     * @param message       la question affichée
     * @param heureActuelle l'heure renvoyée si l'utilisateur ne tape rien
     * @return la nouvelle heure, ou l'heure actuelle
     */
    public static LocalTime lireHeure(String message, LocalTime heureActuelle) {
        return lireHeureOuDefaut(message, heureActuelle);
    }

    /**
     * Boucle de saisie commune aux deux versions de lireHeure (même principe que pour la date).
     *
     * @param message la question affichée
     * @param defaut  heure renvoyée sur une saisie vide, ou null si une saisie vide est interdite
     * @return une heure valide (24:00 ou 9h30 sont refusés)
     */
    private static LocalTime lireHeureOuDefaut(String message, LocalTime defaut) {
        while (true) {
            String texte = lireLigne(message);
            if (texte.isEmpty() && defaut != null) {
                return defaut;
            }
            try {
                return LocalTime.parse(texte, FORMAT_HEURE);
            } catch (DateTimeParseException e) {
                System.out.println("Erreur : heure invalide. Utilisez HH:mm.");
            }
        }
    }

    // ===================== Confirmation =====================

    /**
     * Pose une question fermée : true pour O / OUI, false pour N / NON.
     *
     * Utilisée avant une suppression ou une annulation : une action définitive demande une
     * confirmation explicite.
     *
     * @param message la question affichée
     * @return true pour oui, false pour non
     */
    public static boolean lireConfirmation(String message) {
        while (true) {
            // toUpperCase : « o », « oui », « Oui » sont tous acceptés.
            String reponse = lireLigne(message).toUpperCase();
            // equals compare le contenu des chaînes (== comparerait les références et échouerait).
            // || : une des deux formes suffit (O ou OUI).
            if (reponse.equals("O") || reponse.equals("OUI")) {
                return true;
            }
            if (reponse.equals("N") || reponse.equals("NON")) {
                return false;
            }
            // Ni oui ni non : on ne devine pas, on redemande (la boucle recommence).
            System.out.println("Erreur : répondez par O (oui) ou N (non).");
        }
    }
}
