package corrections;

// Clavier : lit ce que l'utilisateur tape au clavier dans la console (une ligne, un texte,
// un entier, un entier entre deux bornes) et redemande une saisie invalide. Elle contient le
// seul Scanner du programme : les autres classes n'ont pas à manipuler Scanner elles-mêmes.
import corrections.outils.Clavier;
// Ecran : affiche le texte et les résultats dans la console, de façon simple et mise en forme
// (bandeaux, titres, paragraphes coupés à 78 colonnes, extraits de code, diagrammes UML) :
// une surcouche de System.out.println.
import corrections.outils.Ecran;
// Exercice : modèle commun de tous les exercices (classe abstraite). Chaque exercice a un
// titre, affiché dans les menus, un objectif, affiché avant son exécution, et écrit sa propre
// méthode executer() : énoncé, correction et réponses aux questions.
import corrections.outils.Exercice;
// TD : une série de travaux dirigés, avec son numéro, son intitulé, le tableau de ses objectifs
// et le tableau de ses exercices. Les menus de Main s'en servent pour afficher les exercices,
// les objectifs, et lancer le bon exercice.
import corrections.outils.TD;
// Les exercices de chaque série : un objet de chaque classe est créé dans les tableaux plus bas.
import corrections.td1.TD1_Ex1;
import corrections.td1.TD1_Ex2;
import corrections.td1.TD1_Ex3;
import corrections.td1.TD1_Ex4;
import corrections.td1.TD1_Ex5;
import corrections.td1.TD1_Ex6;
import corrections.td1.TD1_Ex7;
import corrections.td2.TD2_Ex1;
import corrections.td2.TD2_Ex2;
import corrections.td2.TD2_Ex3;
import corrections.td2.TD2_Ex4;
import corrections.td3.TD3_Ex1;
import corrections.td3.TD3_Ex2;
import corrections.td3.TD3_Ex3;
import corrections.td4.TD4_Ex1;
import corrections.td4.TD4_Ex2;
import corrections.td4.TD4_Ex3;
import corrections.td5.TD5_Ex1;
import corrections.td5.TD5_Ex2;

// =====================================================================
//  CLASSE Main  -  point d'entrée de l'application de soutenance
//  Rôle : afficher le menu principal de la soutenance (les 5 séries de
//  TD, le projet Java et les objectifs des séries), lancer le choix de
//  l'utilisateur, puis réafficher le menu jusqu'à ce qu'il quitte.
// =====================================================================

/**
 * Classe principale de l'application de soutenance : c'est elle que NetBeans lance (F6).
 *
 * Pourquoi cette classe existe : l'énoncé de la soutenance demande un programme console unique
 * qui regroupe les 5 séries de TD et le projet, avec un menu principal (1 à 5 : une série,
 * 6 : le projet, 7 : les objectifs d'une série, 0 : quitter).
 *
 * Responsabilités :
 * - garder les données des menus : les exercices, les objectifs et les intitulés des séries,
 *   rangés dans des tableaux (exigence de l'énoncé : ne pas répéter les instructions d'affichage) ;
 * - afficher les menus et traiter les choix (boucle while + switch, exigence de l'énoncé) ;
 * - lancer un exercice ou le projet, puis revenir au bon menu.
 *
 * Ce que Main ne fait PAS : le contenu des exercices. Chaque exercice est écrit dans sa propre
 * classe (TD1_Ex1...) ; Main appelle seulement leur méthode executer(). Ajouter ou corriger un
 * exercice ne demande donc aucune modification des menus.
 *
 * Toutes les méthodes et données sont static : on n'a besoin que d'un seul menu, on ne crée donc
 * jamais d'objet Main. main() étant static, elle ne peut appeler directement que des méthodes
 * static de sa classe.
 */
public class Main {

    // =================================================================
    //  DONNÉES : LES EXERCICES DE CHAQUE SÉRIE
    // =================================================================

    // Tableaux polymorphes : des références de type Exercice vers des objets TD1_Ex1, TD1_Ex2...
    // Chaque objet connaît son titre et son objectif (attributs de la classe Exercice).
    //
    // private : seules les méthodes de Main utilisent ces tableaux.
    // static : les tableaux appartiennent à la classe (une seule copie), comme les méthodes qui
    //          les utilisent ; ils sont créés une seule fois, au chargement de la classe.
    // final : la variable désigne toujours le même tableau (on ne peut pas la réaffecter).
    // Pourquoi des tableaux et non des ArrayList : le nombre d'exercices de chaque série est fixé
    // par l'énoncé et ne change jamais pendant l'exécution ; un tableau suffit.
    // La forme { a, b, c } (création et remplissage en une fois) n'est permise que dans la
    // déclaration du tableau (cours p.175).
    // Pourquoi un tableau par série : chaque série a un nombre différent d'exercices (7, 4, 3,
    // 3, 2) ; un tableau par série garde exactement le bon nombre de cases.
    private static final Exercice[] EXERCICES_TD1 = {
        new TD1_Ex1(), new TD1_Ex2(), new TD1_Ex3(), new TD1_Ex4(),
        new TD1_Ex5(), new TD1_Ex6(), new TD1_Ex7()
    };
    private static final Exercice[] EXERCICES_TD2 = {
        new TD2_Ex1(), new TD2_Ex2(), new TD2_Ex3(), new TD2_Ex4()
    };
    private static final Exercice[] EXERCICES_TD3 = {
        new TD3_Ex1(), new TD3_Ex2(), new TD3_Ex3()
    };
    private static final Exercice[] EXERCICES_TD4 = {
        new TD4_Ex1(), new TD4_Ex2(), new TD4_Ex3()
    };
    private static final Exercice[] EXERCICES_TD5 = {
        new TD5_Ex1(), new TD5_Ex2()
    };

    // =================================================================
    //  DONNÉES : LES OBJECTIFS DE CHAQUE SÉRIE (option 7 du menu)
    // =================================================================
    // Un tableau de String par série. L'affichage se fait avec UNE seule boucle (dans
    // afficherObjectifsSerie) pour toutes les séries, au lieu d'écrire un println par objectif :
    // c'est ce que demande l'énoncé (« stocker les objectifs dans des tableaux »).
    // Le dernier objectif de la série 5 est écrit sur deux lignes et collé avec + : c'est
    // une seule chaîne, coupée seulement pour la lisibilité du code.

    private static final String[] OBJECTIFS_TD1 = {
        "Distinguer les types primitifs et leurs conversions (implicites ou avec un cast).",
        "Écrire des boucles (for, while, do..while) et des choix multiples avec switch.",
        "Utiliser les paramètres de main (tableau args) et les convertir avec Integer.parseInt.",
        "Lire la documentation du JDK, compiler avec javac et exécuter avec java."
    };
    private static final String[] OBJECTIFS_TD2 = {
        "Écrire des classes avec leurs attributs et leurs méthodes, puis créer des objets.",
        "Comprendre le constructeur par défaut et écrire plusieurs constructeurs (surcharge).",
        "Protéger les données par l'encapsulation : attributs private, accesseurs et contrôles.",
        "Redéfinir toString et equals, et manipuler les chaînes avec String et StringBuffer."
    };
    private static final String[] OBJECTIFS_TD3 = {
        "Représenter une structure de données (une pile d'entiers) avec un tableau.",
        "Signaler les erreurs avec des exceptions écrites pour le programme.",
        "Relier des objets entre eux et les ranger dans un Vector.",
        "Comparer un tableau de taille fixe et une ArrayList pour gérer un ensemble."
    };
    private static final String[] OBJECTIFS_TD4 = {
        "Dériver une classe avec extends et appeler le constructeur parent avec super(...).",
        "Redéfinir une méthode et réutiliser la version de la classe mère avec super.",
        "Prévoir l'effet de la liaison dynamique, y compris dans un tableau polymorphe.",
        "Écrire une classe abstraite et ses classes filles."
    };
    private static final String[] OBJECTIFS_TD5 = {
        "Lever une exception avec throw et l'annoncer avec throws.",
        "Définir ses propres exceptions en héritant de la classe Exception.",
        "Traiter les exceptions avec try / catch, un bloc catch par cas d'erreur.",
        "Distinguer les exceptions levées par Java (indice hors du tableau, nombre mal écrit) "
                + "de celles du programmeur."
    };

    // SERIES regroupe tout : un objet TD par série, construit avec son numéro, son intitulé,
    // ses objectifs et ses exercices. L'ordre des déclarations compte : SERIES utilise les
    // tableaux ci-dessus, il doit donc être déclaré APRÈS eux, sinon javac refuse
    // (« illegal forward reference »).
    // Le choix N du menu principal correspond à la case N - 1 de ce tableau.

    /** Les 5 séries de TD corrigées (options 1 à 5 du menu principal). */
    private static final TD[] SERIES = {
        new TD(1, "Série N°1 : Programmation avancée Java", OBJECTIFS_TD1, EXERCICES_TD1),
        new TD(2, "Série N°2 : Programmation orientée objet Java", OBJECTIFS_TD2, EXERCICES_TD2),
        new TD(3, "Série N°3 : Programmation avancée Java", OBJECTIFS_TD3, EXERCICES_TD3),
        new TD(4, "Série N°4 : Programmation avancée Java", OBJECTIFS_TD4, EXERCICES_TD4),
        new TD(5, "Série N°5 : Programmation avancée Java", OBJECTIFS_TD5, EXERCICES_TD5)
    };

    // Constante : l'intitulé est utilisé à deux endroits (menu principal et titre du projet) ;
    // l'écrire une seule fois évite d'avoir deux textes différents si on le modifie.

    /** Intitulé du projet, affiché dans le menu principal et en titre quand on le lance (option 6). */
    private static final String PROJET = "Projet Java : réservation de salles et d'équipements";

    // =================================================================
    //  PROGRAMME PRINCIPAL
    // =================================================================

    /**
     * Point d'entrée : titre, menu principal jusqu'au choix « 0. Quitter », puis message de fin.
     *
     * main est volontairement très courte : elle délègue le menu à menuPrincipal(). Chaque
     * méthode a ainsi un seul rôle, comme le demande l'énoncé (« une méthode pour le menu
     * principal »).
     *
     * @param args paramètres de la ligne de commande (non utilisés par le menu)
     */
    public static void main(String[] args) {
        // --- 1. Titre du programme ---
        Ecran.bandeau("SOUTENANCE JAVA - SÉRIES DE TD ET PROJET (2ème BI, FSEGT)");
        // Rappel utile : dans NetBeans, les saisies ne sont prises en compte que si l'on a
        // cliqué dans la fenêtre Output.
        System.out.println("  Sous NetBeans, cliquez dans la fenêtre Output avant de taper au clavier.");

        // --- 2. Menu principal ---
        // Cet appel ne revient ici que lorsque l'utilisateur a choisi de quitter.
        menuPrincipal();

        // --- 3. Fin du programme ---
        // Après ce message, main se termine et le programme s'arrête normalement
        // (pas besoin de System.exit).
        System.out.println();
        System.out.println("Au revoir et bon courage pour la soutenance !");
    }

    // =================================================================
    //  MENU PRINCIPAL
    // =================================================================

    /**
     * Affiche le menu principal et traite le choix, en boucle, jusqu'à « 0. Quitter ».
     *
     * Fonctionnement : afficher le menu, lire un choix entre 0 et 7, exécuter l'action
     * correspondante avec un switch, puis recommencer.
     */
    private static void menuPrincipal() {
        // Variable de contrôle de la boucle : true tant que l'utilisateur n'a pas demandé à quitter.
        // boolean car elle n'a que deux états possibles (continuer ou non).
        boolean continuer = true;
        /*
         * Boucle while : le menu doit se répéter un nombre de fois inconnu, jusqu'à ce que
         * l'utilisateur tape 0 (ou Q à la fin d'un exercice). Une boucle for, faite pour un nombre
         * de tours connu, ne convient pas.
         * Un do..while aurait aussi fonctionné (le menu s'affiche au moins une fois) ; avec
         * continuer initialisé à true, le while fait de toute façon un premier tour.
         * Évolution : continuer passe à false avec le choix 0, ou quand menuTD renvoie false
         * (Q tapé après un exercice). La boucle s'arrête alors au test suivant.
         * Si continuer n'était jamais mis à false, le programme ne pourrait jamais se terminer.
         */
        while (continuer) {
            // --- 1. Affichage du menu et choix (lireEntier redemande tout ce qui n'est pas 0 à 7) ---
            afficherMenuPrincipal();
            // \n saute une ligne avant la question. Grâce aux bornes 0 et 7, choix est forcément
            // un des cas du switch : un texte ou un nombre hors liste ne fait pas planter le programme.
            int choix = Clavier.lireEntier("\nVotre choix : ", 0, 7);

            // --- 2. Traitement du choix ---
            /*
             * switch : on compare une même variable (choix) à plusieurs valeurs fixes ; c'est plus
             * lisible qu'une suite de if / else if, et c'est la structure demandée par l'énoncé.
             * - case 1 à case 5 sont regroupés : sans instruction ni break entre eux, l'exécution
             *   « tombe » jusqu'au code commun. Les cinq séries sont traitées par la même ligne.
             * - break : sort du switch. Sans break, l'exécution continuerait dans le case suivant
             *   (le choix 6 lancerait le projet PUIS afficherait les objectifs).
             * - default : tous les autres cas. lireEntier n'accepte que 0 à 7, donc ici seul 0
             *   peut arriver : c'est le choix « Quitter ».
             */
            switch (choix) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:                                  // ouvrir le menu de la série choisie
                    // SERIES[choix - 1] : le choix 1 correspond à la case 0 du tableau.
                    // menuTD renvoie false si l'utilisateur a tapé Q pour quitter tout le
                    // programme, true s'il revient simplement au menu principal.
                    continuer = menuTD(SERIES[choix - 1]);   // l'indice commence à 0
                    break;
                case 6:                                  // lancer le projet
                    lancerProjet();
                    break;
                case 7:                                  // afficher les objectifs d'une série
                    afficherObjectifsSerie();
                    break;
                default:                                 // 0 : quitter le programme
                    continuer = false;
                    break;
            }
        }
        // REMARQUE : les valeurs 5, 6 et 7 sont écrites en dur (case 6, case 7, borne 7). C'est
        // correct pour les 5 séries de l'énoncé ; si l'on ajoutait une 6e série, il faudrait
        // aussi décaler les numéros du projet et des objectifs.
    }

    // =================================================================
    //  MENU D'UNE SÉRIE ET LANCEMENT D'UN EXERCICE
    // =================================================================

    /**
     * Menu d'une série ; renvoie false si l'utilisateur veut quitter le programme.
     *
     * Une seule méthode sert pour les cinq séries : elle reçoit la série en paramètre et lit son
     * nombre d'exercices avec td.getNombreExercices(). Le menu s'adapte donc tout seul à 7 ou à 2
     * exercices, sans recopier le même code pour chaque série (exigence de l'énoncé).
     *
     * @param td la série choisie dans le menu principal
     * @return true pour revenir au menu principal, false pour quitter tout le programme
     */
    private static boolean menuTD(TD td) {
        /*
         * Boucle while (true) : le menu de la série se réaffiche après chaque exercice, un nombre
         * de fois inconnu. On n'utilise pas de variable de contrôle : on sort de la boucle (et de
         * la méthode) directement avec return, dès que la réponse est connue (0 ou Q).
         * Sans ces return, la boucle ne s'arrêterait jamais.
         */
        while (true) {
            // --- 1. Affichage du menu et choix de l'exercice ---
            afficherMenuTD(td);
            // La borne haute dépend de la série : 7 pour la série 1, 2 pour la série 5.
            int numero = Clavier.lireEntier("\nVotre choix : ", 0, td.getNombreExercices());

            // --- 2. Retour au menu principal, ou lancement de l'exercice choisi ---
            /*
             * switch sur le numéro : 0 est le seul cas fixe (retour) ; tous les autres numéros
             * (1 à n, déjà vérifiés par lireEntier) lancent l'exercice correspondant : c'est le
             * default. On ne peut pas écrire un case par exercice, car leur nombre change d'une
             * série à l'autre.
             * Ici « return true » remplace le break : il quitte à la fois le switch, la boucle et
             * la méthode.
             */
            switch (numero) {
                case 0:
                    return true;                         // retour au menu principal
                default:                                 // 1 à n : n dépend de la série
                    // lancer renvoie false si l'utilisateur a tapé Q : on transmet ce « quitter »
                    // au menu principal en renvoyant false à notre tour.
                    // ! (NON) inverse le résultat : on entre dans le if quand lancer renvoie false.
                    if (!lancer(td, numero)) {
                        return false;                    // Q a été choisi après l'exercice
                    }
                    // Sinon (Entrée), break sort du switch et la boucle réaffiche le menu de la série.
                    break;
            }
        }
    }

    /**
     * Exécute un exercice, autant de fois que demandé ; renvoie false si l'utilisateur veut quitter.
     *
     * Après l'exercice, l'utilisateur choisit : Entrée (revenir au menu de la série), R (relancer
     * le même exercice) ou Q (quitter le programme). Cette méthode est séparée de menuTD car elle
     * a sa propre boucle (la relance) et son propre traitement des erreurs.
     *
     * @param td     la série de l'exercice
     * @param numero le numéro de l'exercice dans la série (1 à n)
     * @return true pour revenir au menu de la série, false pour quitter le programme
     */
    private static boolean lancer(TD td, int numero) {
        // Le type de la variable est Exercice (la classe mère), l'objet réel est un TD3_Ex2, etc.
        Exercice exercice = td.getExercice(numero);
        /*
         * Boucle while (true) : l'exercice peut être relancé autant de fois que l'utilisateur tape
         * R. On sort avec return quand il tape Q (return false) ou autre chose (return true).
         */
        while (true) {
            // --- 1. Titre et objectif de l'exercice ---
            Ecran.bandeau("Série " + td.getNumero() + " - Exercice " + numero + " : " + exercice.getTitre());
            Ecran.texte("Objectif de l'exercice " + numero + " : " + exercice.getObjectif());

            // --- 2. Exécution (une erreur imprévue est affichée sans arrêter le programme) ---
            /*
             * try / catch : si une erreur imprévue (RuntimeException : indice hors du tableau,
             * division par zéro, référence null...) se produisait dans un exercice, elle serait
             * attrapée ici. On affiche le message et l'utilisateur retrouve le menu, au lieu de
             * voir tout le programme s'arrêter devant le jury.
             * Les exceptions prévues (NumberFormatException d'une saisie...) sont déjà traitées
             * à l'intérieur des exercices ; ce catch est un filet de sécurité.
             */
            try {
                exercice.executer();            // liaison dynamique : la méthode de la classe de l'objet
            } catch (RuntimeException e) {
                // "..." + e : Java appelle e.toString(), qui donne le nom de l'exception et son message.
                Ecran.alerte("Erreur inattendue pendant l'exercice : " + e);
            }

            // --- 3. Suite : menu de la série (Entrée), relancer (R) ou quitter (Q) ---
            System.out.println();
            Ecran.trait();
            // trim() retire les espaces tapés par erreur ; toLowerCase() met en minuscules :
            // « Q », « q » et « q  » donnent tous « q ».
            String suite = Clavier.lireLigne("[Entrée] menu de la série " + td.getNumero()
                    + "    [R] relancer l'exercice    [Q] quitter : ").trim().toLowerCase();
            /*
             * equals compare le CONTENU des chaînes. == comparerait les références (les adresses
             * des objets) : une chaîne lue au clavier est un nouvel objet, donc suite == "q"
             * serait faux même quand on tape q.
             */
            if (suite.equals("q")) {
                return false;                   // quitter tout le programme
            }
            // Tout ce qui n'est pas « r » (Entrée, ou une autre lettre) ramène au menu de la série.
            // Seul « r » laisse la boucle continuer : l'exercice est alors relancé.
            if (!suite.equals("r")) {
                return true;
            }
        }
    }

    // =================================================================
    //  PROJET ET OBJECTIFS DES SÉRIES (options 6 et 7)
    // =================================================================

    /**
     * Option 6 : lance le vrai projet (sa propre méthode main) ; son « 0. Quitter » ramène ici.
     *
     * On réutilise le projet tel qu'il a été écrit, sans le réécrire : on appelle simplement sa
     * méthode main. Quand l'utilisateur quitte le projet, sa méthode main se termine et
     * l'exécution reprend ici, puis revient au menu principal.
     */
    private static void lancerProjet() {
        // --- 1. Titre ---
        Ecran.bandeau(PROJET);

        // --- 2. Exécution du projet (une erreur imprévue est affichée sans arrêter le programme) ---
        // Même filet de sécurité que pour les exercices (voir lancer).
        try {
            // reservation.Main : nom complet (paquetage + classe). On ne peut pas l'importer, car
            // la classe où nous sommes s'appelle déjà Main ; « Main » seul désignerait celle-ci.
            // new String[0] : un tableau de paramètres vide, comme si on lançait le projet sans
            // paramètre sur la ligne de commande.
            reservation.Main.main(new String[0]);   // nom complet : cette classe-ci s'appelle aussi Main
        } catch (RuntimeException e) {
            Ecran.alerte("Erreur inattendue dans le projet : " + e);
        }

        // --- 3. Retour au menu principal ---
        System.out.println();
        System.out.println("Fin du projet : retour au menu principal de la soutenance.");
    }

    /**
     * Option 7 : demande un numéro de série, puis affiche son intitulé et la liste de ses objectifs.
     *
     * Méthode séparée, comme l'exige l'énoncé (« une méthode pour l'affichage des objectifs »).
     */
    private static void afficherObjectifsSerie() {
        // --- 1. Numéro de la série : un numéro qui n'existe pas est refusé et redemandé ---
        // lireEntier sans bornes : il refuse déjà les textes qui ne sont pas des nombres ;
        // les bornes sont vérifiées juste après pour afficher un message plus précis
        // (« la série 9 n'existe pas »), comme le demande l'énoncé.
        int numero = Clavier.lireEntier("\nNuméro de la série (1 à " + SERIES.length + ") : ");
        /*
         * Boucle while : on redemande tant que le numéro est invalide, un nombre de fois inconnu ;
         * si le premier numéro est correct, la boucle ne fait aucun tour.
         * Condition numero < 1 || numero > SERIES.length : trop petit OU trop grand. || suffit
         * qu'une des deux erreurs soit vraie. SERIES.length (5) plutôt que 5 écrit en dur : la borne
         * suit automatiquement la taille du tableau.
         * Sans cette vérification, SERIES[numero - 1] lèverait une ArrayIndexOutOfBoundsException
         * pour 0 ou 6.
         */
        while (numero < 1 || numero > SERIES.length) {
            numero = Clavier.lireEntier("  Erreur : la série " + numero + " n'existe pas. Tapez un numéro de 1 à "
                    + SERIES.length + " : ");
        }
        TD td = SERIES[numero - 1];     // numéro 1 = case 0 du tableau

        // --- 2. Intitulé de la série, puis ses objectifs ---
        Ecran.section("Objectifs de la série " + td.getNumero());
        System.out.println("  " + td.getIntitule());
        /*
         * Boucle for : le nombre d'objectifs est connu (td.getNombreObjectifs()).
         * i va de 1 à getNombreObjectifs() inclus (<=) car getObjectif(i) attend un numéro qui
         * commence à 1 ; c'est TD qui fait la conversion vers l'indice i - 1. Avec i < ..., on
         * oublierait le dernier objectif ; en commençant à 0, getObjectif(0) sortirait du tableau.
         * Une seule instruction d'affichage sert pour tous les objectifs de toutes les séries.
         */
        for (int i = 1; i <= td.getNombreObjectifs(); i++) {
            Ecran.texte("- " + td.getObjectif(i));     // « - » au début : Ecran en fait une puce
        }
    }

    // =================================================================
    //  AFFICHAGE DES MENUS
    // =================================================================
    // L'affichage est séparé du traitement des choix : pour changer la présentation des menus,
    // on ne touche qu'à ces deux méthodes, sans risquer de casser la logique des boucles.

    /** Affiche les séries (1 à 5), le projet (6), les objectifs (7), puis le choix « 0. Quitter ». */
    private static void afficherMenuPrincipal() {
        Ecran.section("SOUTENANCE JAVA - Menu principal");
        /*
         * Boucle for sur le tableau SERIES : le nombre de séries est connu (SERIES.length).
         * i est l'indice dans le tableau (0 à 4) ; on affiche i + 1 car l'utilisateur numérote à
         * partir de 1. C'est ce même numéro que menuPrincipal retransforme en indice (choix - 1).
         * i < SERIES.length : on s'arrête après la dernière case (avec <=, on sortirait du tableau).
         */
        for (int i = 0; i < SERIES.length; i++) {
            TD td = SERIES[i];
            // (i + 1) entre parenthèses : sans elles, "  " + i + 1 collerait les textes « 0 » et
            // « 1 » (on obtiendrait « 01 ») au lieu de calculer la somme.
            System.out.println("  " + (i + 1) + ". " + td.getIntitule()
                    + " (" + td.getNombreExercices() + " exercices)");
        }
        System.out.println("  6. " + PROJET);
        System.out.println("  7. Afficher les objectifs d'une série");
        System.out.println("  0. Quitter");
    }

    /**
     * Affiche les exercices de la série, numérotés à partir de 1, puis le choix « 0. Retour ».
     *
     * @param td la série dont on affiche le menu
     */
    private static void afficherMenuTD(TD td) {
        Ecran.section(td.getIntitule());
        /*
         * Boucle for de 1 à getNombreExercices() inclus : ici le compteur i est directement le
         * numéro affiché ET le numéro attendu par getExercice(i) (qui fait lui-même i - 1).
         * Le nombre de tours dépend de la série : la même boucle affiche 7 ou 2 exercices.
         */
        for (int i = 1; i <= td.getNombreExercices(); i++) {
            System.out.println("  " + i + ". " + td.getExercice(i).getTitre());
        }
        System.out.println("  0. Retour au menu principal");
    }
}
