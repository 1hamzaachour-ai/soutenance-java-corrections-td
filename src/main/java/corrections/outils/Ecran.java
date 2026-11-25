package corrections.outils;

// ArrayList : liste de taille variable, utilisée pour ranger les mots d'un texte
// (on ne sait pas à l'avance combien de mots il contient).
import java.util.ArrayList;

// =====================================================================
//  CLASSE Ecran
//  Rôle : mise en forme de l'affichage console : titres, paragraphes
//  coupés à la largeur de l'écran, extraits de code et petits diagrammes
//  de classe UML. Toutes les méthodes sont static : on les appelle avec
//  le nom de la classe, par exemple Ecran.section("Énoncé").
// =====================================================================

/**
 * Tout l'affichage mis en forme du programme : bandeaux, titres de section, paragraphes,
 * extraits de code, messages d'alerte et diagrammes UML dessinés avec des caractères.
 *
 * Pourquoi cette classe existe :
 * - La console affiche le texte sur une seule ligne tant qu'il n'y a pas de retour à la ligne :
 *   un long paragraphe serait coupé n'importe où par la fenêtre, parfois au milieu d'un mot.
 *   Ecran coupe donc elle-même les textes à LARGEUR caractères, entre deux mots.
 * - Les 19 exercices affichent tous le même genre d'éléments. En écrivant la mise en forme une
 *   seule fois ici, tous les exercices ont la même présentation, et une modification (par
 *   exemple la largeur) se fait à un seul endroit.
 *
 * Responsabilités : afficher (méthodes public) et calculer la coupure des lignes (méthodes
 * private, utilisées seulement à l'intérieur de la classe).
 *
 * Toutes les méthodes sont static : la classe ne garde aucune donnée propre à un objet, il n'y a
 * donc aucune raison de créer un objet Ecran.
 */
public class Ecran {

    // =================================================================
    //  CONSTANTE
    // =================================================================

    // public static final = constante de classe : une seule valeur pour tout le programme (static),
    // lisible depuis les autres classes (public), impossible à modifier (final).
    // 78 caractères : une fenêtre de console standard en affiche 80 ; on garde une petite marge.

    /** Nombre maximal de caractères par ligne (constante de classe). */
    public static final int LARGEUR = 78;

    // =================================================================
    //  TITRES
    // =================================================================

    /**
     * Grand titre encadré de deux lignes de « = » (titre du programme, titre d'un exercice).
     *
     * @param titre le texte du titre
     */
    public static void bandeau(String titre) {
        System.out.println();                          // ligne vide pour aérer avant le bandeau
        System.out.println(repeter('=', LARGEUR));     // ligne de « = » sur toute la largeur
        System.out.println("  " + titre);
        System.out.println(repeter('=', LARGEUR));
    }

    /**
     * Titre de partie, par exemple « --- Énoncé ----------- ».
     *
     * Le nombre de tirets est calculé pour que la ligne fasse exactement LARGEUR caractères :
     * on retire la longueur du titre et les 5 caractères déjà écrits (« --- » plus deux espaces).
     *
     * @param titre le nom de la partie
     */
    public static void section(String titre) {
        System.out.println();
        System.out.println("--- " + titre + " " + repeter('-', LARGEUR - titre.length() - 5));
    }

    /**
     * Titre d'une question suivi de son énoncé.
     *
     * Méthode séparée car toutes les questions de tous les exercices s'affichent de la même façon ;
     * elle réutilise section() et enonce() au lieu de répéter leur code.
     *
     * @param numero numéro de la question
     * @param enonce texte de la question
     */
    public static void question(int numero, String enonce) {
        section("Question " + numero);    // + entre une chaîne et un int : le nombre est converti en texte
        enonce(enonce);
        System.out.println();
    }

    // =================================================================
    //  TEXTES
    // =================================================================

    /**
     * Texte tiré de l'énoncé, précédé de « > » pour le distinguer de la correction.
     *
     * @param texte le texte de l'énoncé (« \n » commence un nouveau paragraphe)
     */
    public static void enonce(String texte) {
        afficher(texte, "  > ", "  > ");
    }

    /**
     * Paragraphe de correction ; une ligne qui commence par « - » devient une puce.
     *
     * enonce, texte, champ et alerte ne diffèrent que par les débuts de ligne : elles délèguent
     * toutes le travail à afficher(), écrite une seule fois.
     *
     * @param texte le texte à afficher
     */
    public static void texte(String texte) {
        afficher(texte, "  ", "  ");
    }

    /**
     * Affiche « étiquette : valeur », la suite de la valeur étant alignée sous son début.
     * Sert notamment pour les messages de javac : « javac : erreur ... ».
     *
     * @param etiquette le nom du champ (complété à 11 caractères pour que les « : » s'alignent)
     * @param valeur    le texte du champ
     */
    public static void champ(String etiquette, String valeur) {
        String debut = "      " + completer(etiquette, 11) + " : ";
        // Les lignes suivantes commencent par autant d'espaces que le début de la première :
        // la valeur forme ainsi un bloc bien aligné.
        afficher(valeur, debut, repeter(' ', debut.length()));
    }

    // =================================================================
    //  CODE, MESSAGES, DIAGRAMMES UML ET SÉPARATEUR
    // =================================================================

    /**
     * Une ligne de code, affichée telle quelle.
     * Elle n'est pas coupée : couper du code le rendrait faux à la lecture.
     *
     * @param ligne la ligne de code
     */
    public static void ligneDeCode(String ligne) {
        System.out.println("    | " + ligne);
    }

    /**
     * Plusieurs lignes de code, rangées dans un tableau.
     *
     * @param lignes les lignes, dans l'ordre
     */
    public static void code(String[] lignes) {
        /*
         * Boucle for : le nombre de lignes est connu (lignes.length) et on a besoin de l'indice i
         * pour lire chaque case. i va de 0 à lignes.length - 1 ; la condition i < lignes.length
         * s'arrête juste après la dernière case. Avec <=, on lirait lignes[lignes.length], qui
         * n'existe pas : ArrayIndexOutOfBoundsException.
         */
        for (int i = 0; i < lignes.length; i++) {
            ligneDeCode(lignes[i]);
        }
    }

    /**
     * Message mis en évidence (saisie refusée, remarque importante...).
     *
     * Dans une chaîne Java, « \\ » représente UN seul caractère « \ » : "  /!\\ " s'affiche
     * donc « /!\ ». Un seul « \ » serait lu comme le début d'une séquence spéciale.
     *
     * @param message le texte du message
     */
    public static void alerte(String message) {
        afficher(message, "  /!\\ ", "      ");
    }

    /**
     * Petit diagramme de classe UML dessiné avec des caractères : un cadre avec le nom de la
     * classe, puis les attributs, puis les méthodes.
     *
     * @param classe    nom de la classe (centré en haut du cadre)
     * @param attributs une ligne par attribut, par exemple « - nom : String »
     * @param methodes  une ligne par méthode, par exemple « + getNom() : String »
     */
    public static void uml(String classe, String[] attributs, String[] methodes) {
        // --- 1. Calcul : largeur du cadre = longueur de la plus longue ligne du diagramme ---
        // Recherche du maximum : on part de la longueur du nom de la classe, puis on garde
        // la plus grande longueur rencontrée.
        int largeur = classe.length();
        /*
         * Deux boucles for, une par tableau : le nombre de cases est connu et l'indice i sert à
         * lire chaque ligne. i < attributs.length (puis i < methodes.length) arrête la boucle
         * après la dernière case. Si un tableau est vide (classe sans attribut), la boucle ne
         * fait aucun tour, sans erreur.
         */
        for (int i = 0; i < attributs.length; i++) {
            // > strictement : une ligne de même longueur que le maximum ne change rien.
            if (attributs[i].length() > largeur) {
                largeur = attributs[i].length();
            }
        }
        for (int i = 0; i < methodes.length; i++) {
            if (methodes[i].length() > largeur) {
                largeur = methodes[i].length();
            }
        }

        // --- 2. Préparation du bord du cadre et du nom de la classe ---
        // largeur + 2 : un espace de chaque côté du texte, entre le texte et le bord « | ».
        String bord = "    +" + repeter('-', largeur + 2) + "+";
        // Centrage : la moitié de la place libre est mise avant le nom. / entre deux int donne
        // une division entière (5 / 2 vaut 2) : c'est voulu, on ne peut pas écrire un demi-espace.
        String titre = repeter(' ', (largeur - classe.length()) / 2) + classe;     // nom centré

        // --- 3. Affichage : nom de la classe, puis attributs, puis méthodes ---
        // completer(..., largeur) ajoute des espaces à droite : tous les « | » de droite
        // tombent dans la même colonne.
        System.out.println(bord);
        System.out.println("    | " + completer(titre, largeur) + " |");
        System.out.println(bord);
        // Même principe de boucle for que plus haut : une ligne affichée par case du tableau.
        for (int i = 0; i < attributs.length; i++) {
            System.out.println("    | " + completer(attributs[i], largeur) + " |");
        }
        System.out.println(bord);
        for (int i = 0; i < methodes.length; i++) {
            System.out.println("    | " + completer(methodes[i], largeur) + " |");
        }
        System.out.println(bord);
    }

    /** Ligne de séparation sur toute la largeur (avant la question « Entrée / R / Q » de Main). */
    public static void trait() {
        System.out.println(repeter('-', LARGEUR));
    }

    // =================================================================
    //  OUTILS SUR LES CHAÎNES
    // =================================================================

    /**
     * Chaîne formée de n fois le caractère c (construite avec un StringBuffer, modifiable).
     *
     * Pourquoi un StringBuffer : un String ne peut pas être modifié. Écrire s = s + c dans une
     * boucle créerait un nouvel objet String à chaque tour. Le StringBuffer s'allonge sur place
     * avec append, puis toString() donne le String final (cours, chapitre 4).
     *
     * @param c le caractère à répéter
     * @param n le nombre de répétitions (0 donne une chaîne vide)
     * @return la chaîne de n caractères c
     */
    public static String repeter(char c, int n) {
        StringBuffer sb = new StringBuffer();
        /*
         * Boucle for : on sait exactement combien de tours faire (n). i compte les caractères
         * déjà ajoutés : il vaut 0, 1, ..., n - 1, et la boucle s'arrête quand i atteint n,
         * après exactement n tours. Si n vaut 0 ou est négatif, la condition est fausse dès le
         * départ : aucun tour, chaîne vide (aucune erreur).
         */
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Complète s avec des espaces à droite jusqu'à n caractères (pour aligner des colonnes).
     *
     * @param s le texte de départ
     * @param n la longueur voulue
     * @return s s'il fait déjà au moins n caractères, sinon s suivi des espaces manquants
     */
    public static String completer(String s, int n) {
        // Si le texte est déjà assez long, on le renvoie tel quel : on ne coupe jamais un texte.
        // >= : un texte de longueur exactement n n'a besoin d'aucun espace.
        // return quitte la méthode tout de suite : la ligne suivante n'est alors pas exécutée.
        if (s.length() >= n) {
            return s;
        }
        // Sinon il manque n - s.length() caractères : on ajoute autant d'espaces.
        return s + repeter(' ', n - s.length());
    }

    // =================================================================
    //  DÉCOUPAGE DES TEXTES EN LIGNES (méthodes privées)
    // =================================================================
    // Ces méthodes sont private : ce sont des étapes internes du découpage. Les autres classes
    // n'ont pas à les appeler ; elles utilisent enonce(), texte(), champ() ou alerte().

    /**
     * Affiche un texte en coupant les lignes trop longues. Chaque « \n » du texte
     * commence un nouveau paragraphe ; on les repère avec indexOf.
     *
     * @param texte          le texte complet, éventuellement sur plusieurs paragraphes
     * @param premierPrefixe début de la toute première ligne (par exemple « > » pour un énoncé)
     * @param prefixeSuite   début de toutes les autres lignes
     */
    private static void afficher(String texte, String premierPrefixe, String prefixeSuite) {
        // --- 1. Initialisation : on part du début du texte ---
        String prefixe = premierPrefixe;
        int debut = 0;                      // début du paragraphe en cours
        int fin = texte.indexOf('\n');      // position du premier retour à la ligne, ou -1 s'il n'y en a pas

        // --- 2. Chaque paragraphe terminé par un retour à la ligne est affiché à part ---
        /*
         * Boucle while : le nombre de paragraphes n'est pas connu à l'avance, on avance de
         * « \n » en « \n » avec indexOf.
         * Condition fin != -1 : il reste un retour à la ligne après debut. indexOf renvoie -1
         * quand il n'en trouve plus : on sort alors de la boucle.
         * Évolution : debut passe juste après le « \n » trouvé (fin + 1), puis on cherche le « \n »
         * suivant À PARTIR de debut. Si on oubliait ce deuxième paramètre de indexOf, on
         * retrouverait toujours le même « \n » et la boucle ne s'arrêterait jamais.
         */
        while (fin != -1) {
            afficherParagraphe(texte.substring(debut, fin), prefixe, prefixeSuite);
            prefixe = prefixeSuite;         // seule la toute première ligne utilise premierPrefixe
            debut = fin + 1;
            fin = texte.indexOf('\n', debut);
        }

        // --- 3. Affichage du dernier paragraphe ---
        // Le texte après le dernier « \n » (ou tout le texte s'il n'y en avait aucun) n'est pas
        // traité par la boucle : il faut l'afficher ici. substring(debut) va jusqu'à la fin.
        afficherParagraphe(texte.substring(debut), prefixe, prefixeSuite);
    }

    /**
     * Affiche un paragraphe sur des lignes de LARGEUR caractères au plus.
     *
     * Principe : on remplit une ligne mot après mot. Quand le mot suivant ne tient plus, on
     * affiche la ligne et on en commence une nouvelle. Ainsi on ne coupe jamais au milieu d'un mot.
     *
     * @param paragraphe    le texte du paragraphe (sans « \n »)
     * @param prefixe       début de la première ligne du paragraphe
     * @param prefixeSuite  début des lignes suivantes
     */
    private static void afficherParagraphe(String paragraphe, String prefixe, String prefixeSuite) {
        // --- 1. Préparation des débuts de ligne ---
        String contenu = paragraphe.trim();
        String debut = prefixe;
        String suite = prefixeSuite;
        // un paragraphe qui commence par « - » est une puce : ses lignes suivantes sont décalées
        /*
         * Trois conditions reliées par && (ET) : toutes doivent être vraies.
         * - contenu.length() >= 2 est testé EN PREMIER : && s'arrête dès qu'une condition est
         *   fausse, donc charAt(0) et charAt(1) ne sont jamais appelés sur un texte trop court
         *   (sinon : StringIndexOutOfBoundsException sur un paragraphe vide).
         * - charAt(0) == '-' et charAt(1) == ' ' : le paragraphe commence par « - ». Exiger
         *   l'espace évite de prendre pour une puce un texte comme « -1 est négatif ».
         * Si c'est vrai : on met « - » au début de la première ligne et deux espaces au début
         * des suivantes, pour que la suite du texte s'aligne sous le premier mot.
         */
        if (contenu.length() >= 2 && contenu.charAt(0) == '-' && contenu.charAt(1) == ' ') {
            debut = prefixe + "- ";
            suite = prefixeSuite + "  ";
            contenu = contenu.substring(2);        // on retire « - » du texte : il est dans debut
        }

        // --- 2. Découpage du paragraphe en mots ---
        ArrayList<String> mots = decouper(contenu);

        // --- 3. Remplissage des lignes, mot par mot ---
        // StringBuffer : la ligne est allongée mot après mot (append), sans recréer un String.
        StringBuffer ligne = new StringBuffer(debut);
        int longueur = debut.length();          // nombre de caractères déjà dans la ligne
        boolean ligneVide = true;               // vrai tant qu'aucun mot n'est sur la ligne
        /*
         * Boucle for : on parcourt tous les mots de la liste, du premier (indice 0) au dernier
         * (mots.size() - 1). Le nombre de tours est connu : la taille de la liste. On a besoin de
         * l'indice i pour lire chaque mot avec get(i) (un for-each serait possible, mais le for
         * avec indice est la forme vue en cours pour les ArrayList).
         * Arrêt : quand i atteint mots.size(). Si le paragraphe est vide, aucun tour.
         */
        for (int i = 0; i < mots.size(); i++) {
            String mot = mots.get(i);
            /*
             * La ligne est-elle pleine ? Il faut que les DEUX conditions soient vraies (&&) :
             * - !ligneVide : la ligne contient déjà au moins un mot. Sans ce test, un mot trop
             *   long pour la ligne ferait d'abord afficher une ligne vide (le préfixe seul), puis
             *   le mot quand même : on accepte donc directement un mot trop long seul sur sa ligne.
             * - longueur + 1 + mot.length() > LARGEUR : avec un espace puis le mot, on dépasserait
             *   la largeur permise. > (et non >=) : une ligne d'exactement LARGEUR caractères est
             *   autorisée.
             * Si c'est vrai : on affiche la ligne terminée et on en prépare une nouvelle, qui
             * commence par le préfixe des lignes suivantes.
             */
            if (!ligneVide && longueur + 1 + mot.length() > LARGEUR) {
                System.out.println(ligne.toString());       // ligne pleine : on l'affiche
                ligne = new StringBuffer(suite);
                longueur = suite.length();
                ligneVide = true;
            }
            // Un espace sépare deux mots, mais on n'en met pas avant le premier mot d'une ligne.
            if (!ligneVide) {
                ligne.append(' ');
                longueur = longueur + 1;
            }
            ligne.append(mot);
            longueur = longueur + mot.length();      // on tient le compte à jour pour le test suivant
            ligneVide = false;
        }

        // --- 4. Affichage de la dernière ligne ---
        // La dernière ligne commencée n'est jamais « pleine » : la boucle ne l'a pas affichée.
        System.out.println(ligne.toString());
    }

    /**
     * Découpe un texte en mots, aux espaces. Pour garder l'affichage lisible :
     * un passage entre « » et un appel comme f(a, b) restent chacun un seul mot,
     * et un signe « : ; ! ? » précédé d'une espace reste collé au mot d'avant.
     *
     * Pourquoi une méthode séparée : le découpage en mots et le remplissage des lignes sont deux
     * problèmes différents ; les séparer rend chacun plus court et plus facile à vérifier.
     *
     * @param texte le paragraphe à découper
     * @return la liste des « mots », dans l'ordre
     */
    private static ArrayList<String> decouper(String texte) {
        // --- 1. Initialisation : liste vide, position au début du texte ---
        ArrayList<String> mots = new ArrayList<String>();
        int i = 0;

        // --- 2. Parcours du texte : on saute un espace ou on lit un mot entier ---
        /*
         * Boucle while (et non for) : i n'avance pas d'un pas régulier ; il avance de 1 sur un
         * espace, ou saute directement à la fin du mot lu.
         * Condition i < texte.length() : il reste des caractères à examiner.
         * La boucle se termine toujours, car i augmente d'au moins 1 à chaque tour (un mot
         * contient au moins un caractère, donc fin > i).
         */
        while (i < texte.length()) {
            if (texte.charAt(i) == ' ') {
                i++;                            // espace entre deux mots
            } else {
                int fin = finDuMot(texte, i);
                /*
                 * Boucle while : tant que le mot est suivi d'une espace PUIS d'un signe de
                 * ponctuation (« : », « ; », « ! », « ? », « » »), on rallonge le mot jusqu'au
                 * prochain espace. En français, on écrit « mot : » avec une espace avant les deux-
                 * points ; sans cette boucle, « : » pourrait se retrouver seul au début d'une ligne.
                 * Les trois conditions sont reliées par && :
                 * - fin + 1 < texte.length() est testé d'abord, pour que charAt(fin + 1) existe ;
                 * - charAt(fin) == ' ' : le mot est bien suivi d'une espace ;
                 * - estPonctuation(...) : le caractère d'après est un signe à garder collé.
                 * C'est une boucle (et pas un simple if) car plusieurs signes peuvent se suivre.
                 */
                while (fin + 1 < texte.length() && texte.charAt(fin) == ' '
                        && estPonctuation(texte.charAt(fin + 1))) {
                    fin = prochainEspace(texte, fin + 1);
                }
                mots.add(texte.substring(i, fin));
                i = fin;                        // on reprend la lecture juste après le mot
            }
        }
        return mots;
    }

    /**
     * Position juste après le mot qui commence à la position debut du texte.
     *
     * @param texte le paragraphe
     * @param debut la position du premier caractère du mot
     * @return la position qui suit le mot (un espace, ou la longueur du texte)
     */
    private static int finDuMot(String texte, int debut) {
        // --- 1. Fin normale du mot : le prochain espace ---
        int fin = prochainEspace(texte, debut);

        // --- 2. Un passage entre « et » reste entier ---
        int ouvrant = texte.indexOf('«', debut);
        /*
         * ouvrant != -1 : il y a bien un « dans la suite du texte (-1 = introuvable).
         * ouvrant < fin : ce « fait partie du mot en cours (il est avant la fin normale du mot).
         * Les deux sont nécessaires (&&) : sinon on regrouperait un passage situé plus loin.
         */
        if (ouvrant != -1 && ouvrant < fin) {
            int fermant = texte.indexOf('»', ouvrant);
            // Si le » fermant est absent (texte mal écrit), on garde la coupure normale.
            if (fermant != -1) {
                fin = garderEnsemble(texte, debut, fin, fermant);
            }
        }

        // --- 3. Un appel de méthode comme f(a, b) reste entier ---
        int parenthese = texte.indexOf('(', debut);
        /*
         * parenthese > debut : la parenthèse n'est pas le premier caractère du mot ;
         * parenthese < fin : elle fait partie du mot en cours ;
         * estLettreOuChiffre(caractère avant) : la parenthèse suit un nom (f(, println(), et
         * non une simple parenthèse de texte comme « (voir plus bas) ».
         * Le premier test permet aussi de lire charAt(parenthese - 1) sans sortir du texte.
         */
        if (parenthese > debut && parenthese < fin && estLettreOuChiffre(texte.charAt(parenthese - 1))) {
            int fermante = texte.indexOf(')', parenthese);
            if (fermante != -1) {
                fin = garderEnsemble(texte, debut, fin, fermante);
            }
        }
        return fin;
    }

    /**
     * Prolonge le mot jusqu'au caractère en position dernier, sauf si le mot dépasserait 60 caractères.
     *
     * La limite de 60 évite qu'un très long passage entre guillemets devienne un « mot » plus
     * large que la ligne : dans ce cas on garde la coupure normale.
     *
     * @param texte   le paragraphe
     * @param debut   début du mot
     * @param fin     fin normale du mot
     * @param dernier position du » ou de la parenthèse fermante
     * @return la nouvelle fin du mot, ou l'ancienne si le regroupement est impossible
     */
    private static int garderEnsemble(String texte, int debut, int fin, int dernier) {
        int nouvelleFin = prochainEspace(texte, dernier);
        // nouvelleFin > fin : le regroupement allonge vraiment le mot ;
        // nouvelleFin - debut <= 60 : le mot regroupé reste assez court pour tenir sur une ligne.
        if (nouvelleFin > fin && nouvelleFin - debut <= 60) {
            return nouvelleFin;
        }
        return fin;
    }

    /**
     * Position du premier espace à partir de la position i, ou longueur du texte s'il n'y en a plus.
     *
     * indexOf renvoie -1 quand il ne trouve rien ; on le remplace par la longueur du texte pour
     * que l'appelant puisse toujours écrire substring(debut, fin) sans cas particulier.
     *
     * @param texte le texte
     * @param i     position de départ de la recherche
     * @return la position de l'espace trouvé, ou texte.length()
     */
    private static int prochainEspace(String texte, int i) {
        int espace = texte.indexOf(' ', i);
        if (espace == -1) {
            return texte.length();
        }
        return espace;
    }

    /**
     * Vrai si c est un signe qui ne doit pas commencer une ligne : « : ; ! ? » ou le guillemet fermant.
     *
     * || (OU) : il suffit que c soit égal à UN de ces signes. == compare deux caractères (char).
     *
     * @param c le caractère à tester
     * @return true si c est l'un de ces signes
     */
    private static boolean estPonctuation(char c) {
        return c == ':' || c == ';' || c == '!' || c == '?' || c == '»';
    }

    /**
     * Vrai si c est une lettre sans accent ou un chiffre (comparaison dans l'ordre des caractères).
     *
     * Les caractères ont un code numérique, et 'a' à 'z', 'A' à 'Z', '0' à '9' se suivent dans
     * cet ordre : c >= 'a' && c <= 'z' veut dire « c est entre a et z ». Chaque intervalle demande
     * && (les deux bornes), et les trois intervalles sont reliés par || (un seul suffit).
     * On écrit ce test soi-même car Character.isLetterOrDigit n'est pas au programme du cours.
     *
     * @param c le caractère à tester
     * @return true si c est une lettre non accentuée ou un chiffre
     */
    private static boolean estLettreOuChiffre(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }
}
