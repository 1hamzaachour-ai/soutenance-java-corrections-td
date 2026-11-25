package gestion;

import model.Equipement;
import model.Reservation;
import model.Salle;
import model.Utilisateur;

// java.io : lecture et écriture de fichiers (File = un chemin ; les Reader / Writer lisent et
// écrivent du texte ; IOException = erreur d'entrée / sortie, par exemple un fichier verrouillé).
// java.nio.charset : les encodages de caractères (UTF-8, Windows-1252) et leurs erreurs.
// java.nio.file : copie d'un fichier en une instruction (Files.copy), pour les copies .bak.
// java.time : dates et heures, comme dans Reservation.
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

/**
 * Lecture et écriture des fichiers texte du dossier data/.
 *
 * Un enregistrement par ligne, champs séparés par un point-virgule, encodage UTF-8 :
 *   salles.txt         id;nom;capacite;batiment
 *   equipements.txt    id;nom;type;quantite
 *   utilisateurs.txt   id;nom;prenom;role
 *   reservations.txt   id;idRessource;categorie;idUtilisateur;date;debut;fin
 *
 * En plus des quatre fichiers du cahier des charges, un petit fichier technique compteurs.txt
 * garde le dernier numéro attribué à chaque type d'identifiant (S, E, U, R) afin de ne JAMAIS
 * réutiliser un identifiant, même après la suppression du plus grand et un redémarrage.
 *
 * Cette classe ne vérifie que la SYNTAXE des lignes (nombre de champs, nombres, dates, heures).
 * Les règles métier (capacité positive, ressource existante, conflits...) sont contrôlées
 * ensuite par GestionReservations. Une ligne incorrecte est ignorée et signalée dans la liste
 * des avertissements : elle ne fait jamais planter le programme.
 *
 * Pourquoi cette classe existe : séparer le « stockage » (comment les données sont écrites sur
 * le disque) de la « logique » (les règles de réservation, dans GestionReservations). Si l'on
 * voulait un jour enregistrer dans une base de données, on ne changerait que cette classe.
 *
 * Organisation : une méthode charger... et une méthode sauvegarder... par fichier, plus des
 * outils private communs (lireLignes, ecrireLignes, decouper, lireEntier, lireDate, lireHeure).
 */
public class GestionFichiers {

    // Le point-virgule sépare les champs d'une ligne. Une constante : si on voulait changer de
    // séparateur, on ne modifierait qu'ici. (C'est pour cela que Saisie interdit « ; » dans les textes.)
    private static final String SEPARATEUR = ";";

    // Heure écrite/lue dans les fichiers : HH:mm (STRICT : refuse 24:00 ou 9h30)
    private static final DateTimeFormatter FORMAT_HEURE =
            DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

    // Les chemins du dossier et des cinq fichiers, calculés une seule fois par le constructeur.
    // Un objet File représente un chemin (le fichier n'est pas ouvert pour autant).
    private File dossier;
    private File fichierSalles;
    private File fichierEquipements;
    private File fichierUtilisateurs;
    private File fichierReservations;
    private File fichierCompteurs;

    // Messages sur les lignes ignorées pendant le chargement
    // ArrayList : on ne sait pas d'avance combien de lignes seront mal formées (souvent aucune).
    private ArrayList<String> avertissements = new ArrayList<String>();

    /**
     * Prépare les chemins du dossier de données et de ses cinq fichiers.
     *
     * new File(dossier, "salles.txt") construit le chemin « dossier/salles.txt » sans avoir à écrire
     * le séparateur (\ sous Windows, / ailleurs) : le programme marche sur tous les systèmes.
     *
     * @param cheminDossier le dossier des données, « data » (relatif au dossier du projet)
     */
    public GestionFichiers(String cheminDossier) {
        dossier = new File(cheminDossier);
        fichierSalles = new File(dossier, "salles.txt");
        fichierEquipements = new File(dossier, "equipements.txt");
        fichierUtilisateurs = new File(dossier, "utilisateurs.txt");
        fichierReservations = new File(dossier, "reservations.txt");
        fichierCompteurs = new File(dossier, "compteurs.txt");
    }

    /** Chemin complet du dossier de données (affiché au démarrage). */
    public String getCheminDossier() {
        return dossier.getAbsolutePath();
    }

    /** Les messages sur les lignes ignorées lors des chargements. */
    public ArrayList<String> getAvertissements() {
        // On renvoie une COPIE de la liste : l'appelant peut la lire ou la modifier sans toucher à
        // la liste interne (encapsulation). Renvoyer avertissements lui-même lui donnerait accès
        // à l'attribut private.
        return new ArrayList<String>(avertissements);
    }

    // =====================================================================
    //  Préparation
    // =====================================================================

    /**
     * Crée le dossier data/ et les fichiers qui n'existent pas encore (vides).
     * Un fichier qui existe déjà n'est JAMAIS modifié ni écrasé ici.
     */
    public void preparerFichiers() throws IOException {
        /*
         * Deux cas d'erreur, chacun lève une IOException (que Main affichera) :
         * - le nom « data » existe mais c'est un fichier, pas un dossier : && car il faut à la fois
         *   qu'il existe ET qu'il ne soit pas un dossier ;
         * - le dossier n'existe pas ET sa création échoue (mkdirs renvoie false). Grâce à &&, mkdirs()
         *   n'est appelé que si le dossier n'existe pas encore.
         */
        if (dossier.exists() && !dossier.isDirectory()) {
            throw new IOException(dossier.getAbsolutePath() + " existe mais n'est pas un dossier");
        }
        if (!dossier.exists() && !dossier.mkdirs()) {
            throw new IOException("impossible de créer le dossier " + dossier.getAbsolutePath());
        }
        creerSiAbsent(fichierSalles);
        creerSiAbsent(fichierEquipements);
        creerSiAbsent(fichierUtilisateurs);
        creerSiAbsent(fichierReservations);
        creerSiAbsent(fichierCompteurs);
    }

    /**
     * Crée un fichier vide s'il n'existe pas encore ; un fichier existant n'est jamais touché.
     *
     * @param fichier le fichier à créer au besoin
     * @throws IOException si la création échoue
     */
    private void creerSiAbsent(File fichier) throws IOException {
        // Le test exists() protège les données : on ne crée (donc on ne vide) jamais un fichier existant.
        if (!fichier.exists()) {
            fichier.createNewFile();
        }
    }

    /**
     * Copie les quatre fichiers de données tels qu'ils sont (salles.txt.bak, etc.).
     * Main l'appelle quand des lignes ont été ignorées au chargement : ces lignes disparaîtront
     * du fichier à la prochaine sauvegarde, la copie permet de les récupérer.
     */
    public void copierFichiersOriginaux() throws IOException {
        File[] fichiers = {fichierSalles, fichierEquipements, fichierUtilisateurs, fichierReservations};
        /*
         * Boucle for-each (« pour chaque fichier du tableau ») : on veut traiter TOUS les éléments
         * dans l'ordre et on n'a pas besoin de leur indice ; for-each est alors plus court et
         * évite toute erreur d'indice. Elle s'arrête d'elle-même après le dernier élément.
         * REPLACE_EXISTING : une ancienne copie .bak est remplacée par la nouvelle.
         */
        for (File fichier : fichiers) {
            File copie = new File(dossier, fichier.getName() + ".bak");
            Files.copy(fichier.toPath(), copie.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    // =====================================================================
    //  Chargement
    // =====================================================================

    /** Lit salles.txt. Les lignes mal formées sont ignorées et signalées. */
    public ArrayList<Salle> chargerSalles() throws IOException {
        ArrayList<Salle> salles = new ArrayList<Salle>();
        ArrayList<String> lignes = lireLignes(fichierSalles);
        /*
         * Boucle for avec indice (et non for-each) : on a besoin de i pour donner le numéro de la
         * ligne fautive dans l'avertissement (i + 1, car les lignes d'un fichier se comptent à
         * partir de 1). La boucle parcourt toutes les lignes : i < lignes.size().
         */
        for (int i = 0; i < lignes.size(); i++) {
            String ligne = lignes.get(i);
            // continue : passe directement au tour suivant de la boucle, sans exécuter la suite.
            // Une ligne vide (souvent la dernière d'un fichier) est simplement sautée.
            if (ligne.trim().isEmpty()) {
                continue;   // une ligne vide n'est pas une erreur
            }
            /*
             * try / catch DANS la boucle : si une ligne est mal formée, decouper ou lireEntier
             * lèvent une IllegalArgumentException ; on la note (avertir) et on passe à la ligne
             * suivante. Une seule ligne abîmée n'empêche donc pas de charger toutes les autres.
             */
            try {
                // 4 champs attendus : id;nom;capacite;batiment
                String[] champs = decouper(ligne, 4);
                int capacite = lireEntier(champs[2], "la capacité");
                salles.add(new Salle(champs[0], champs[1], capacite, champs[3]));
            } catch (IllegalArgumentException e) {
                avertir(fichierSalles, i + 1, ligne, e);
            }
        }
        return salles;
    }

    /** Lit equipements.txt. Les lignes mal formées sont ignorées et signalées. */
    public ArrayList<Equipement> chargerEquipements() throws IOException {
        ArrayList<Equipement> equipements = new ArrayList<Equipement>();
        ArrayList<String> lignes = lireLignes(fichierEquipements);
        // Même boucle que chargerSalles : indice pour le numéro de ligne, continue sur les lignes
        // vides, try / catch par ligne pour ignorer seulement les lignes mal formées.
        for (int i = 0; i < lignes.size(); i++) {
            String ligne = lignes.get(i);
            if (ligne.trim().isEmpty()) {
                continue;
            }
            try {
                String[] champs = decouper(ligne, 4);
                int quantite = lireEntier(champs[3], "la quantité");
                equipements.add(new Equipement(champs[0], champs[1], champs[2], quantite));
            } catch (IllegalArgumentException e) {
                avertir(fichierEquipements, i + 1, ligne, e);
            }
        }
        return equipements;
    }

    /** Lit utilisateurs.txt. Les lignes mal formées sont ignorées et signalées. */
    public ArrayList<Utilisateur> chargerUtilisateurs() throws IOException {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        ArrayList<String> lignes = lireLignes(fichierUtilisateurs);
        // Même boucle que chargerSalles (numéro de ligne, lignes vides sautées, erreurs notées).
        for (int i = 0; i < lignes.size(); i++) {
            String ligne = lignes.get(i);
            if (ligne.trim().isEmpty()) {
                continue;
            }
            try {
                String[] champs = decouper(ligne, 4);
                utilisateurs.add(new Utilisateur(champs[0], champs[1], champs[2], champs[3]));
            } catch (IllegalArgumentException e) {
                avertir(fichierUtilisateurs, i + 1, ligne, e);
            }
        }
        return utilisateurs;
    }

    /** Lit reservations.txt. Les lignes mal formées sont ignorées et signalées. */
    public ArrayList<Reservation> chargerReservations() throws IOException {
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        ArrayList<String> lignes = lireLignes(fichierReservations);
        // Même boucle que chargerSalles ; ici 7 champs, dont une date et deux heures à convertir.
        for (int i = 0; i < lignes.size(); i++) {
            String ligne = lignes.get(i);
            if (ligne.trim().isEmpty()) {
                continue;
            }
            try {
                String[] champs = decouper(ligne, 7);
                LocalDate date = lireDate(champs[4]);
                LocalTime debut = lireHeure(champs[5]);
                LocalTime fin = lireHeure(champs[6]);
                reservations.add(new Reservation(champs[0], champs[1], champs[2], champs[3], date, debut, fin));
            } catch (IllegalArgumentException e) {
                avertir(fichierReservations, i + 1, ligne, e);
            }
        }
        return reservations;
    }

    /**
     * Lit compteurs.txt (lignes "S;4", "E;3", "U;5", "R;12").
     * Renvoie {dernier n° de salle, d'équipement, d'utilisateur, de réservation} ; 0 si absent.
     */
    public int[] chargerCompteurs() throws IOException {
        int[] compteurs = new int[4];   // quatre cases initialisées à 0
        ArrayList<String> lignes = lireLignes(fichierCompteurs);
        for (int i = 0; i < lignes.size(); i++) {
            String ligne = lignes.get(i);
            if (ligne.trim().isEmpty()) {
                continue;
            }
            try {
                String[] champs = decouper(ligne, 2);
                /*
                 * La lettre (S, E, U ou R) donne la case du tableau : sa position dans le texte
                 * "SEUR" (S -> 0, E -> 1, U -> 2, R -> 3). indexOf renvoie -1 pour une lettre
                 * inconnue. On vérifie d'abord que le champ fait UN caractère (opérateur ? :),
                 * sinon « SE » serait trouvé dans "SEUR" et pris pour la case 0.
                 */
                int position = champs[0].length() == 1 ? "SEUR".indexOf(champs[0]) : -1;
                if (position < 0) {
                    throw new IllegalArgumentException("lettre inconnue : \"" + champs[0] + "\" (S, E, U ou R attendu)");
                }
                int valeur = lireEntier(champs[1], "le numéro");
                if (valeur < 0) {
                    throw new IllegalArgumentException("le numéro ne peut pas être négatif");
                }
                compteurs[position] = valeur;
            } catch (IllegalArgumentException e) {
                avertir(fichierCompteurs, i + 1, ligne, e);
            }
        }
        return compteurs;
    }

    // =====================================================================
    //  Sauvegarde (le fichier est entièrement réécrit à chaque fois)
    // =====================================================================

    /**
     * Réécrit salles.txt avec toutes les salles (une ligne par salle).
     *
     * Le fichier est entièrement réécrit (et non complété) : c'est le moyen le plus simple pour
     * qu'une modification ou une suppression soit aussi enregistrée.
     *
     * @param salles toutes les salles, dans l'ordre
     * @throws IOException si l'écriture échoue
     */
    public void sauvegarderSalles(ArrayList<Salle> salles) throws IOException {
        ArrayList<String> lignes = new ArrayList<String>();
        /*
         * Boucle for-each : on transforme chaque salle en une ligne de texte, dans l'ordre, sans
         * avoir besoin d'indice. Elle s'arrête après la dernière salle (aucun tour si la liste est vide).
         */
        for (Salle salle : salles) {
            lignes.add(salle.getId() + SEPARATEUR + salle.getNom() + SEPARATEUR
                    + salle.getCapacite() + SEPARATEUR + salle.getBatiment());
        }
        ecrireLignes(fichierSalles, lignes);
    }

    // Les trois méthodes suivantes suivent le même modèle que sauvegarderSalles : une boucle
    // for-each qui fabrique une ligne par objet, puis une écriture complète du fichier.

    /**
     * Réécrit equipements.txt.
     *
     * @param equipements tous les équipements
     * @throws IOException si l'écriture échoue
     */
    public void sauvegarderEquipements(ArrayList<Equipement> equipements) throws IOException {
        ArrayList<String> lignes = new ArrayList<String>();
        for (Equipement equipement : equipements) {
            lignes.add(equipement.getId() + SEPARATEUR + equipement.getNom() + SEPARATEUR
                    + equipement.getType() + SEPARATEUR + equipement.getQuantiteDisponible());
        }
        ecrireLignes(fichierEquipements, lignes);
    }

    /**
     * Réécrit utilisateurs.txt.
     *
     * @param utilisateurs tous les utilisateurs
     * @throws IOException si l'écriture échoue
     */
    public void sauvegarderUtilisateurs(ArrayList<Utilisateur> utilisateurs) throws IOException {
        ArrayList<String> lignes = new ArrayList<String>();
        for (Utilisateur utilisateur : utilisateurs) {
            lignes.add(utilisateur.getId() + SEPARATEUR + utilisateur.getNom() + SEPARATEUR
                    + utilisateur.getPrenom() + SEPARATEUR + utilisateur.getRole());
        }
        ecrireLignes(fichierUtilisateurs, lignes);
    }

    /**
     * Réécrit reservations.txt. La date est écrite au format ISO (aaaa-mm-jj, ce que donne
     * LocalDate quand on la colle à un texte) et relue avec LocalDate.parse sans format.
     *
     * @param reservations toutes les réservations
     * @throws IOException si l'écriture échoue
     */
    public void sauvegarderReservations(ArrayList<Reservation> reservations) throws IOException {
        ArrayList<String> lignes = new ArrayList<String>();
        for (Reservation r : reservations) {
            lignes.add(r.getId() + SEPARATEUR + r.getIdRessource() + SEPARATEUR + r.getCategorie()
                    + SEPARATEUR + r.getIdUtilisateur() + SEPARATEUR + r.getDate()   // date : aaaa-mm-jj
                    + SEPARATEUR + r.getHeureDebut().format(FORMAT_HEURE)             // heure : HH:mm
                    + SEPARATEUR + r.getHeureFin().format(FORMAT_HEURE));
        }
        ecrireLignes(fichierReservations, lignes);
    }

    /**
     * Réécrit compteurs.txt avec le dernier numéro attribué à chaque type d'identifiant.
     *
     * @param salles       dernier numéro de salle (S)
     * @param equipements  dernier numéro d'équipement (E)
     * @param utilisateurs dernier numéro d'utilisateur (U)
     * @param reservations dernier numéro de réservation (R)
     * @throws IOException si l'écriture échoue
     */
    public void sauvegarderCompteurs(int salles, int equipements, int utilisateurs, int reservations)
            throws IOException {
        ArrayList<String> lignes = new ArrayList<String>();
        lignes.add("S" + SEPARATEUR + salles);
        lignes.add("E" + SEPARATEUR + equipements);
        lignes.add("U" + SEPARATEUR + utilisateurs);
        lignes.add("R" + SEPARATEUR + reservations);
        ecrireLignes(fichierCompteurs, lignes);
    }

    // =====================================================================
    //  Outils de lecture / écriture
    // =====================================================================

    /**
     * Lit toutes les lignes d'un fichier en UTF-8.
     * FileReader utilise l'encodage par défaut de la machine (Windows-1252 avant Java 18), ce qui
     * abîme les accents : on précise donc UTF-8 avec InputStreamReader.
     * Si le fichier n'est pas en UTF-8 (cas du Bloc-notes en mode "ANSI"), on le relit en
     * Windows-1252 pour ne pas perdre les accents ; il sera réenregistré en UTF-8.
     */
    private ArrayList<String> lireLignes(File fichier) throws IOException {
        ArrayList<String> lignes;
        /*
         * try / catch : on essaie d'abord de lire en UTF-8 « strict ». Si le fichier contient des
         * octets impossibles en UTF-8, le décodeur lève une CharacterCodingException ; le catch
         * relit alors le fichier en Windows-1252 (l'encodage du Bloc-notes « ANSI ») et ajoute un
         * avertissement. Ainsi un fichier modifié à la main ne fait pas perdre ses accents.
         */
        try {
            lignes = lireLignes(fichier, StandardCharsets.UTF_8.newDecoder());   // strict : signale les octets invalides
        } catch (CharacterCodingException e) {
            CharsetDecoder ansi = Charset.forName("windows-1252").newDecoder()
                    .onMalformedInput(CodingErrorAction.REPLACE)
                    .onUnmappableCharacter(CodingErrorAction.REPLACE);
            lignes = lireLignes(fichier, ansi);
            avertissements.add(fichier.getName() + " n'est pas encodé en UTF-8 : il a été relu en Windows-1252 "
                    + "et sera réenregistré en UTF-8.");
        }
        // !lignes.isEmpty() est testé d'abord (&&) : sur un fichier vide, get(0) n'existe pas.
        // REMARQUE : entre les guillemets de startsWith ci-dessous se trouve le caractère invisible
        // lui-même (U+FEFF, la marque BOM), et non une chaîne vide. Le code est correct, mais il se
        // lit mal ; la notation d'échappement de Java (barre oblique inverse, u, FEFF) le rendrait
        // visible sans rien changer au fonctionnement.
        // Un fichier UTF-8 enregistré "avec BOM" commence par un caractère invisible : on l'enlève
        if (!lignes.isEmpty() && lignes.get(0).startsWith("﻿")) {
            lignes.set(0, lignes.get(0).substring(1));
        }
        return lignes;
    }

    /**
     * Lit toutes les lignes d'un fichier avec le décodeur donné (UTF-8 ou Windows-1252).
     *
     * @param fichier  le fichier à lire
     * @param decodeur l'encodage à utiliser
     * @return les lignes du fichier, dans l'ordre
     * @throws IOException si la lecture échoue (dont CharacterCodingException en UTF-8 strict)
     */
    private ArrayList<String> lireLignes(File fichier, CharsetDecoder decodeur) throws IOException {
        ArrayList<String> lignes = new ArrayList<String>();
        // try-with-resources : le fichier est fermé automatiquement, même en cas d'erreur
        try (BufferedReader lecteur = new BufferedReader(new InputStreamReader(new FileInputStream(fichier), decodeur))) {
            /*
             * Boucle while de lecture : on ne connaît pas le nombre de lignes du fichier.
             * readLine() renvoie la ligne suivante, ou null quand le fichier est terminé : la
             * condition ligne != null arrête donc la boucle à la fin du fichier.
             * La première ligne est lue avant la boucle, les suivantes à la fin de chaque tour.
             * Si on oubliait la relecture dans la boucle, ligne ne changerait jamais et la boucle
             * ajouterait la même ligne sans fin.
             */
            String ligne = lecteur.readLine();
            while (ligne != null) {
                lignes.add(ligne);
                ligne = lecteur.readLine();
            }
        }
        return lignes;
    }

    /**
     * Réécrit entièrement le fichier en UTF-8, une ligne par enregistrement.
     *
     * try-with-resources (le try avec des parenthèses) ferme le fichier automatiquement, même en
     * cas d'erreur : sans fermeture, les dernières lignes pourraient ne jamais être écrites.
     * Le catch relance une IOException avec un message qui nomme le fichier (plus clair pour
     * l'utilisateur) ; l'erreur d'origine (e) est transmise en second paramètre.
     *
     * @param fichier le fichier à écrire
     * @param lignes  les lignes à écrire, dans l'ordre
     * @throws IOException si l'écriture échoue
     */
    private void ecrireLignes(File fichier, ArrayList<String> lignes) throws IOException {
        try (BufferedWriter ecrivain = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fichier), StandardCharsets.UTF_8))) {
            // for-each : chaque ligne est écrite dans l'ordre, suivie d'un retour à la ligne
            // (newLine écrit le retour à la ligne du système).
            for (String ligne : lignes) {
                ecrivain.write(ligne);
                ecrivain.newLine();
            }
        } catch (IOException e) {
            throw new IOException("écriture impossible dans " + fichier.getName() + " (" + e.getMessage() + ")", e);
        }
    }

    // =====================================================================
    //  Outils d'analyse des lignes
    // =====================================================================

    /**
     * Découpe une ligne sur les points-virgules ; il faut exactement nombreChamps champs.
     *
     * @param ligne        la ligne du fichier
     * @param nombreChamps le nombre de champs attendu (4 ou 7 selon le fichier)
     * @return les champs, sans espaces au début ni à la fin
     * @throws IllegalArgumentException si le nombre de champs est faux (la ligne sera ignorée)
     */
    private String[] decouper(String ligne, int nombreChamps) {
        String[] champs = ligne.split(SEPARATEUR, -1);   // -1 : garde les champs vides en fin de ligne
        // != : trop ou pas assez de champs. On lève une exception au lieu de deviner : la ligne
        // sera ignorée et signalée, au lieu d'être chargée avec des valeurs fausses.
        if (champs.length != nombreChamps) {
            throw new IllegalArgumentException(nombreChamps + " champs attendus, " + champs.length + " trouvé(s)");
        }
        // Boucle for avec indice : on REMPLACE chaque case du tableau par sa version sans espaces ;
        // un for-each ne permettrait pas d'écrire dans le tableau.
        for (int i = 0; i < champs.length; i++) {
            champs[i] = champs[i].trim();
        }
        return champs;
    }

    // Les trois méthodes suivantes convertissent un champ (texte) en nombre, date ou heure.
    // Elles transforment l'exception propre à chaque conversion (NumberFormatException,
    // DateTimeParseException) en une seule sorte d'exception, IllegalArgumentException, avec un
    // message clair : les boucles de chargement n'ont alors qu'un seul catch à écrire.

    /**
     * @param texte    le champ à convertir
     * @param nomChamp son nom, pour le message d'erreur
     * @return l'entier lu
     */
    private int lireEntier(String texte, String nomChamp) {
        try {
            return Integer.parseInt(texte);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(nomChamp + " n'est pas un nombre entier : \"" + texte + "\"");
        }
    }

    private LocalDate lireDate(String texte) {
        try {
            return LocalDate.parse(texte);   // format ISO : aaaa-mm-jj
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("date invalide (format aaaa-mm-jj attendu) : \"" + texte + "\"");
        }
    }

    private LocalTime lireHeure(String texte) {
        try {
            return LocalTime.parse(texte, FORMAT_HEURE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("heure invalide (format HH:mm attendu) : \"" + texte + "\"");
        }
    }

    /**
     * Note une ligne ignorée dans la liste des avertissements (Main les affiche au démarrage).
     *
     * @param fichier     le fichier concerné
     * @param numeroLigne le numéro de la ligne (à partir de 1)
     * @param ligne       le texte de la ligne
     * @param e           l'exception qui explique le problème (e.getMessage() donne le message)
     */
    private void avertir(File fichier, int numeroLigne, String ligne, IllegalArgumentException e) {
        avertissements.add(fichier.getName() + ", ligne " + numeroLigne + " ignorée (" + e.getMessage() + ") : " + ligne);
    }
}
