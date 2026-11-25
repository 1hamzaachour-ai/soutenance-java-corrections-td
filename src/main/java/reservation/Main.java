package reservation;

import exceptions.ConflitReservationException;
import exceptions.RessourceIntrouvableException;
import gestion.GestionFichiers;
import gestion.GestionReservations;
import model.Equipement;
import model.Reservation;
import model.Ressource;
import model.Salle;
import model.Utilisateur;
import util.Saisie;

// IOException : erreur de lecture / écriture des fichiers. LocalDate / LocalTime : dates et
// heures saisies. ArrayList : listes renvoyées par GestionReservations. NoSuchElementException :
// levée par le Scanner quand le clavier est fermé (fin de saisie).
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Point d'entrée de l'application : le menu principal et les sous-menus.
 *
 * Main ne contient aucune règle métier : il pose les questions (avec Saisie), appelle
 * GestionReservations, puis affiche le résultat ou le message de l'exception.
 * Après chaque opération qui modifie les données, il demande la sauvegarde des fichiers.
 *
 * Intégration à la soutenance : ce projet est lancé par l'option 6 du menu de corrections.Main,
 * qui appelle reservation.Main.main(...). Seule différence avec le projet d'origine : cette
 * classe est rangée dans le paquetage reservation, car une classe du paquetage par défaut ne
 * peut pas être utilisée depuis un autre paquetage. « 0. Quitter » ferme le projet et ramène
 * au menu de la soutenance.
 *
 * Organisation : une méthode par menu (menuSalles, menuEquipements...) et une méthode par action
 * (ajouterSalle, reserver...). Chaque menu est une boucle do..while avec un switch, et chaque
 * action suit le même schéma : poser les questions, appeler GestionReservations, afficher le
 * résultat, sauvegarder. Les erreurs (exceptions) sont attrapées dans les menus, ce qui évite de
 * répéter un try / catch dans chaque action.
 */
public class Main {

    // L'objet qui contient toute la logique métier et les données en mémoire
    // static : toutes les méthodes de Main sont static (on ne crée jamais d'objet Main) ; elles
    // partagent ce même gestionnaire, créé une fois au début de main.
    private static GestionReservations gestion;

    // Le texte des menus (le numéro est ajouté automatiquement ; 0 est toujours Quitter ou Retour)
    // Des tableaux de String (et non un println par option) : une seule méthode, afficherMenu,
    // affiche n'importe quel menu avec une boucle. private static final : constantes de la classe.
    private static final String[] MENU_PRINCIPAL = {
        "Gérer les salles",
        "Gérer les équipements",
        "Gérer les utilisateurs",
        "Réserver une ressource",
        "Annuler / modifier une réservation",
        "Consulter le planning",
        "Rechercher une ressource disponible"
    };
    private static final String[] MENU_SALLES = {
        "Ajouter une salle", "Modifier une salle", "Supprimer une salle", "Afficher toutes les salles"
    };
    private static final String[] MENU_EQUIPEMENTS = {
        "Ajouter un équipement", "Modifier un équipement", "Supprimer un équipement", "Afficher tous les équipements"
    };
    private static final String[] MENU_UTILISATEURS = {
        "Ajouter un utilisateur", "Modifier un utilisateur", "Supprimer un utilisateur", "Afficher tous les utilisateurs"
    };
    private static final String[] MENU_RESERVER = {
        "Réserver une salle", "Réserver un équipement"
    };
    private static final String[] MENU_RESERVATIONS = {
        "Lister les réservations", "Rechercher par identifiant", "Annuler une réservation", "Modifier une réservation"
    };
    private static final String[] MENU_PLANNING = {
        "Planning par salle", "Planning par équipement", "Planning par date", "Planning par utilisateur"
    };

    // =====================================================================
    //  Programme principal
    // =====================================================================

    /**
     * Programme principal du projet : chargement des fichiers, menu principal en boucle, sauvegarde
     * à la fermeture.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        GestionFichiers fichiers = new GestionFichiers("data");   // dossier data/ à la racine du projet
        gestion = new GestionReservations(fichiers);

        // 1. Chargement des données au démarrage
        /*
         * try / catch : si les fichiers ne peuvent pas être lus (IOException), on affiche l'erreur
         * et return arrête main tout de suite. On ne continue surtout pas avec des listes vides :
         * la première sauvegarde écraserait les vrais fichiers et effacerait les données.
         */
        try {
            ArrayList<String> problemes = gestion.chargerDonnees();
            System.out.println("Données chargées depuis : " + fichiers.getCheminDossier());
            afficherProblemes(problemes);
        } catch (IOException e) {
            System.out.println("ERREUR : impossible de lire les fichiers de données (" + e.getMessage() + ").");
            System.out.println("Le programme s'arrête pour ne pas écraser vos fichiers.");
            return;
        }

        // 2. Boucle du menu principal : on réaffiche le menu après chaque action, jusqu'à "Quitter"
        /*
         * Boucle while : le menu se répète un nombre de fois inconnu, jusqu'au choix 0 (ou jusqu'à
         * la fermeture du clavier). La variable boolean continuer contrôle la boucle : elle passe
         * à false dans ces deux cas, et la boucle s'arrête au test suivant.
         * Le try / catch est À L'INTÉRIEUR de la boucle : après une erreur, on affiche le message
         * et le menu se réaffiche, au lieu d'arrêter le programme.
         */
        boolean continuer = true;
        while (continuer) {
            afficherMenu("RESERVATION DE SALLES ET D'EQUIPEMENTS", MENU_PRINCIPAL, "Quitter");
            try {
                // lireChoixMenu n'accepte que 0 à 7 : le switch ne reçoit jamais d'autre valeur.
                int choix = Saisie.lireChoixMenu(0, 7);
                /*
                 * switch : une action par numéro de menu. Chaque case se termine par break, qui sort
                 * du switch (sans lui, on exécuterait aussi les case suivants). default ne peut
                 * recevoir que 0 ici : c'est « Quitter ».
                 */
                switch (choix) {
                    case 1:
                        menuSalles();
                        break;
                    case 2:
                        menuEquipements();
                        break;
                    case 3:
                        menuUtilisateurs();
                        break;
                    case 4:
                        menuReserver();
                        break;
                    case 5:
                        menuReservations();
                        break;
                    case 6:
                        menuPlanning();
                        break;
                    case 7:
                        rechercherDisponibilites();
                        break;
                    default:   // 0 : quitter
                        continuer = false;
                        break;
                }
            /*
             * Trois catch, du plus précis au plus général (l'ordre est obligatoire : Java prend le
             * premier catch qui correspond, et refuse un catch qui ne pourrait jamais être atteint) :
             * - NoSuchElementException : le clavier est fermé, on sort de la boucle ;
             * - IllegalArgumentException ou IllegalStateException (multi-catch avec |) : une règle
             *   de gestion a été refusée (date passée, suppression impossible...) : on affiche le
             *   message préparé par GestionReservations ;
             * - RuntimeException : toute autre erreur imprévue, affichée sans fermer le programme.
             * getMessage() renvoie le texte donné à l'exception quand elle a été créée.
             */
            } catch (NoSuchElementException e) {
                // Le clavier est fermé (Ctrl+D / Ctrl+Z) : on ne peut plus rien lire, on quitte proprement
                System.out.println();
                System.out.println("Fin de la saisie : fermeture du programme.");
                continuer = false;
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erreur : " + e.getMessage());
            } catch (RuntimeException e) {
                // Filet de sécurité : une erreur imprévue ne doit jamais fermer le programme
                System.out.println("Erreur inattendue : " + e);
            }
        }

        // 3. Sauvegarde à la fermeture
        sauvegarderALaFermeture();
        System.out.println("Au revoir !");
    }

    // =====================================================================
    //  Menus
    // =====================================================================

    /** Affiche un menu : le titre, les options numérotées à partir de 1, puis l'option 0. */
    private static void afficherMenu(String titre, String[] options, String texteZero) {
        System.out.println();
        System.out.println("=== " + titre + " ===");
        /*
         * Boucle for avec indice : il faut i pour écrire le numéro de chaque option. L'indice va de
         * 0 à options.length - 1, et on affiche i + 1 car les menus commencent à 1 (0 est réservé
         * à Quitter / Retour). Le même code affiche les sept menus, quelle que soit leur taille.
         */
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.println("0. " + texteZero);
    }

    /**
     * Sous-menu « Gérer les salles » ; il se répète jusqu'au choix 0 (retour au menu principal).
     */
    private static void menuSalles() {
        // choix est déclaré AVANT la boucle : la condition du do..while (après l'accolade) doit
        // pouvoir le lire ; déclaré dans la boucle, il n'existerait plus à cet endroit.
        int choix;
        /*
         * Boucle do..while : le menu doit s'afficher AU MOINS une fois, avant même de connaître le
         * choix ; c'est exactement ce que fait do..while (la condition est testée APRÈS le tour).
         * Condition choix != 0 : on recommence tant que l'utilisateur ne choisit pas 0 (Retour).
         * Le try / catch est dans la boucle : une erreur (salle introuvable...) affiche son message
         * et le sous-menu se réaffiche.
         * Les cinq autres sous-menus (équipements, utilisateurs, réserver, réservations, planning)
         * sont construits exactement de la même façon.
         */
        do {
            afficherMenu("GESTION DES SALLES", MENU_SALLES, "Retour");
            choix = Saisie.lireChoixMenu(0, 4);
            try {
                switch (choix) {
                    case 1:
                        ajouterSalle();
                        break;
                    case 2:
                        modifierSalle();
                        break;
                    case 3:
                        supprimerSalle();
                        break;
                    case 4:
                        afficherSalles();
                        break;
                    default:   // 0 : retour au menu principal
                        break;
                }
            } catch (RessourceIntrouvableException | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        } while (choix != 0);
    }

    /** Sous-menu « Gérer les équipements » (même structure que menuSalles). */
    private static void menuEquipements() {
        int choix;
        do {
            afficherMenu("GESTION DES EQUIPEMENTS", MENU_EQUIPEMENTS, "Retour");
            choix = Saisie.lireChoixMenu(0, 4);
            try {
                switch (choix) {
                    case 1:
                        ajouterEquipement();
                        break;
                    case 2:
                        modifierEquipement();
                        break;
                    case 3:
                        supprimerEquipement();
                        break;
                    case 4:
                        afficherEquipements();
                        break;
                    default:
                        break;
                }
            } catch (RessourceIntrouvableException | ConflitReservationException
                    | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        } while (choix != 0);
    }

    /** Sous-menu « Gérer les utilisateurs » (même structure que menuSalles). */
    private static void menuUtilisateurs() {
        int choix;
        do {
            afficherMenu("GESTION DES UTILISATEURS", MENU_UTILISATEURS, "Retour");
            choix = Saisie.lireChoixMenu(0, 4);
            try {
                switch (choix) {
                    case 1:
                        ajouterUtilisateur();
                        break;
                    case 2:
                        modifierUtilisateur();
                        break;
                    case 3:
                        supprimerUtilisateur();
                        break;
                    case 4:
                        afficherUtilisateurs();
                        break;
                    default:
                        break;
                }
            } catch (RessourceIntrouvableException | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        } while (choix != 0);
    }

    /**
     * Sous-menu « Réserver une ressource » (même structure que menuSalles). Les deux choix
     * appellent la même méthode reserver, avec la catégorie en paramètre : le code de réservation
     * n'est pas écrit deux fois.
     */
    private static void menuReserver() {
        int choix;
        do {
            afficherMenu("RESERVER UNE RESSOURCE", MENU_RESERVER, "Retour");
            choix = Saisie.lireChoixMenu(0, 2);
            try {
                switch (choix) {
                    case 1:
                        reserver(Ressource.CATEGORIE_SALLE);
                        break;
                    case 2:
                        reserver(Ressource.CATEGORIE_EQUIPEMENT);
                        break;
                    default:
                        break;
                }
            } catch (RessourceIntrouvableException | ConflitReservationException
                    | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        } while (choix != 0);
    }

    /** Sous-menu « Annuler / modifier une réservation » (même structure que menuSalles). */
    private static void menuReservations() {
        int choix;
        do {
            afficherMenu("ANNULER / MODIFIER UNE RESERVATION", MENU_RESERVATIONS, "Retour");
            choix = Saisie.lireChoixMenu(0, 4);
            try {
                switch (choix) {
                    case 1:
                        listerReservations();
                        break;
                    case 2:
                        rechercherReservation();
                        break;
                    case 3:
                        annulerReservation();
                        break;
                    case 4:
                        modifierReservation();
                        break;
                    default:
                        break;
                }
            } catch (RessourceIntrouvableException | ConflitReservationException
                    | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        } while (choix != 0);
    }

    /** Sous-menu « Consulter le planning » (même structure que menuSalles). */
    private static void menuPlanning() {
        int choix;
        do {
            afficherMenu("CONSULTER LE PLANNING", MENU_PLANNING, "Retour");
            choix = Saisie.lireChoixMenu(0, 4);
            try {
                switch (choix) {
                    case 1:
                        planningParSalle();
                        break;
                    case 2:
                        planningParEquipement();
                        break;
                    case 3:
                        planningParDate();
                        break;
                    case 4:
                        planningParUtilisateur();
                        break;
                    default:
                        break;
                }
            } catch (RessourceIntrouvableException | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        } while (choix != 0);
    }

    // =====================================================================
    //  Salles
    // =====================================================================

    /**
     * Ajoute une salle : questions, appel de GestionReservations, affichage, sauvegarde.
     * Les erreurs de règle (capacité, texte vide...) sont levées par GestionReservations et
     * attrapées par le catch de menuSalles.
     */
    private static void ajouterSalle() {
        System.out.println("--- Ajouter une salle ---");
        String nom = Saisie.lireTexte("Nom de la salle : ");
        int capacite = Saisie.lireEntierPositif("Capacité (nombre de places) : ");
        String batiment = Saisie.lireTexte("Bâtiment : ");

        Salle salle = gestion.ajouterSalle(nom, capacite, batiment);
        System.out.println("Salle ajoutée : " + salle);
        sauvegarder();
    }

    /**
     * Modifie une salle. Chaque question affiche la valeur actuelle entre crochets ; grâce aux
     * versions « avec valeur actuelle » de Saisie, Entrée seule garde cette valeur.
     * throws : l'exception « salle introuvable » est transmise à menuSalles, qui l'affiche.
     */
    private static void modifierSalle() throws RessourceIntrouvableException {
        System.out.println("--- Modifier une salle ---");
        afficherSalles();
        String id = Saisie.lireIdentifiant("Identifiant de la salle à modifier (ex : S1) : ");
        Salle salle = gestion.trouverSalle(id);

        System.out.println("Salle actuelle : " + salle);
        System.out.println("(Appuyez sur Entrée pour conserver la valeur actuelle.)");
        String nom = Saisie.lireTexte("Nom [" + salle.getNom() + "] : ", salle.getNom());
        int capacite = Saisie.lireEntierPositif("Capacité [" + salle.getCapacite() + "] : ", salle.getCapacite());
        String batiment = Saisie.lireTexte("Bâtiment [" + salle.getBatiment() + "] : ", salle.getBatiment());

        gestion.modifierSalle(id, nom, capacite, batiment);
        System.out.println("Salle modifiée : " + salle);
        sauvegarder();
    }

    private static void supprimerSalle() throws RessourceIntrouvableException {
        System.out.println("--- Supprimer une salle ---");
        afficherSalles();
        String id = Saisie.lireIdentifiant("Identifiant de la salle à supprimer (ex : S1) : ");
        Salle salle = gestion.trouverSalle(id);
        gestion.verifierSuppressionPossible(id);   // on refuse tout de suite si elle a des réservations
        // Confirmation avant une action définitive : true (O) supprime, false (N) abandonne (else).
        // "..." + salle appelle salle.toString() pour décrire la salle dans la question.
        if (Saisie.lireConfirmation("Supprimer définitivement " + salle + " ? (O/N) : ")) {
            gestion.supprimerSalle(id);
            System.out.println("Salle supprimée : " + salle);
            sauvegarder();
        } else {
            System.out.println("Suppression abandonnée : la salle est conservée.");
        }
    }

    /** Affiche toutes les salles sous forme de tableau (ou un message si la liste est vide). */
    private static void afficherSalles() {
        ArrayList<Salle> salles = gestion.getSalles();
        // Liste vide : message, puis return quitte la méthode (on n'affiche pas un tableau vide).
        if (salles.isEmpty()) {
            System.out.println("Aucune salle enregistrée.");
            return;
        }
        /*
         * String.format construit une ligne en colonnes : %-6s veut dire « un texte, aligné à
         * gauche, sur 6 caractères » (complété par des espaces). Ainsi l'en-tête et chaque salle
         * ont exactement les mêmes largeurs de colonnes. Les mêmes formats servent aux autres tableaux.
         * Boucle for-each : une ligne par salle, dans l'ordre de la liste, sans besoin d'indice.
         */
        System.out.println(String.format("%-6s %-22s %-10s %s", "ID", "Nom", "Capacité", "Bâtiment"));
        for (Salle salle : salles) {
            System.out.println(String.format("%-6s %-22s %-10s %s",
                    salle.getId(), salle.getNom(), salle.getCapacite(), salle.getBatiment()));
        }
        System.out.println(salles.size() + " salle(s).");
    }

    // =====================================================================
    //  Équipements
    // =====================================================================

    // Les méthodes des équipements et des utilisateurs suivent le même schéma que celles des
    // salles : ajouter, modifier (Entrée garde la valeur), supprimer (avec confirmation), afficher.

    /** Ajoute un équipement (même schéma que ajouterSalle). */
    private static void ajouterEquipement() {
        System.out.println("--- Ajouter un équipement ---");
        String nom = Saisie.lireTexte("Nom de l'équipement : ");
        String type = Saisie.lireTexte("Type (ex : VIDEOPROJECTEUR, PC PORTABLE, CAMERA) : ");
        int quantite = Saisie.lireEntierPositif("Quantité disponible : ");

        Equipement equipement = gestion.ajouterEquipement(nom, type, quantite);
        System.out.println("Équipement ajouté : " + equipement);
        sauvegarder();
    }

    private static void modifierEquipement() throws RessourceIntrouvableException, ConflitReservationException {
        System.out.println("--- Modifier un équipement ---");
        afficherEquipements();
        String id = Saisie.lireIdentifiant("Identifiant de l'équipement à modifier (ex : E1) : ");
        Equipement equipement = gestion.trouverEquipement(id);

        System.out.println("Équipement actuel : " + equipement);
        System.out.println("(Appuyez sur Entrée pour conserver la valeur actuelle.)");
        String nom = Saisie.lireTexte("Nom [" + equipement.getNom() + "] : ", equipement.getNom());
        String type = Saisie.lireTexte("Type [" + equipement.getType() + "] : ", equipement.getType());
        int quantite = Saisie.lireEntierPositif("Quantité [" + equipement.getQuantiteDisponible() + "] : ",
                equipement.getQuantiteDisponible());

        // Peut lever ConflitReservationException (quantité plus petite que les unités déjà
        // réservées en même temps) : annoncée par throws, attrapée par menuEquipements.
        gestion.modifierEquipement(id, nom, type, quantite);   // refusé si la quantité est trop basse
        System.out.println("Équipement modifié : " + equipement);
        sauvegarder();
    }

    private static void supprimerEquipement() throws RessourceIntrouvableException {
        System.out.println("--- Supprimer un équipement ---");
        afficherEquipements();
        String id = Saisie.lireIdentifiant("Identifiant de l'équipement à supprimer (ex : E1) : ");
        Equipement equipement = gestion.trouverEquipement(id);
        gestion.verifierSuppressionPossible(id);
        if (Saisie.lireConfirmation("Supprimer définitivement " + equipement + " ? (O/N) : ")) {
            gestion.supprimerEquipement(id);
            System.out.println("Équipement supprimé : " + equipement);
            sauvegarder();
        } else {
            System.out.println("Suppression abandonnée : l'équipement est conservé.");
        }
    }

    private static void afficherEquipements() {
        ArrayList<Equipement> equipements = gestion.getEquipements();
        if (equipements.isEmpty()) {
            System.out.println("Aucun équipement enregistré.");
            return;
        }
        System.out.println(String.format("%-6s %-26s %-18s %s", "ID", "Nom", "Type", "Quantité"));
        for (Equipement equipement : equipements) {
            System.out.println(String.format("%-6s %-26s %-18s %s",
                    equipement.getId(), equipement.getNom(), equipement.getType(), equipement.getQuantiteDisponible()));
        }
        System.out.println(equipements.size() + " équipement(s).");
    }

    // =====================================================================
    //  Utilisateurs
    // =====================================================================

    private static void ajouterUtilisateur() {
        System.out.println("--- Ajouter un utilisateur ---");
        String nom = Saisie.lireTexte("Nom : ");
        String prenom = Saisie.lireTexte("Prénom : ");
        String role = Saisie.lireTexte("Rôle (ex : ENSEIGNANT, ETUDIANT) : ");

        Utilisateur utilisateur = gestion.ajouterUtilisateur(nom, prenom, role);
        System.out.println("Utilisateur ajouté : " + utilisateur);
        sauvegarder();
    }

    private static void modifierUtilisateur() throws RessourceIntrouvableException {
        System.out.println("--- Modifier un utilisateur ---");
        afficherUtilisateurs();
        String id = Saisie.lireIdentifiant("Identifiant de l'utilisateur à modifier (ex : U1) : ");
        Utilisateur utilisateur = gestion.trouverUtilisateur(id);

        System.out.println("Utilisateur actuel : " + utilisateur);
        System.out.println("(Appuyez sur Entrée pour conserver la valeur actuelle.)");
        String nom = Saisie.lireTexte("Nom [" + utilisateur.getNom() + "] : ", utilisateur.getNom());
        String prenom = Saisie.lireTexte("Prénom [" + utilisateur.getPrenom() + "] : ", utilisateur.getPrenom());
        String role = Saisie.lireTexte("Rôle [" + utilisateur.getRole() + "] : ", utilisateur.getRole());

        gestion.modifierUtilisateur(id, nom, prenom, role);
        System.out.println("Utilisateur modifié : " + utilisateur);
        sauvegarder();
    }

    private static void supprimerUtilisateur() throws RessourceIntrouvableException {
        System.out.println("--- Supprimer un utilisateur ---");
        afficherUtilisateurs();
        String id = Saisie.lireIdentifiant("Identifiant de l'utilisateur à supprimer (ex : U1) : ");
        Utilisateur utilisateur = gestion.trouverUtilisateur(id);
        gestion.verifierSuppressionPossible(id);
        if (Saisie.lireConfirmation("Supprimer définitivement " + utilisateur + " ? (O/N) : ")) {
            gestion.supprimerUtilisateur(id);
            System.out.println("Utilisateur supprimé : " + utilisateur);
            sauvegarder();
        } else {
            System.out.println("Suppression abandonnée : l'utilisateur est conservé.");
        }
    }

    private static void afficherUtilisateurs() {
        ArrayList<Utilisateur> utilisateurs = gestion.getUtilisateurs();
        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré.");
            return;
        }
        System.out.println(String.format("%-6s %-18s %-18s %s", "ID", "Nom", "Prénom", "Rôle"));
        for (Utilisateur utilisateur : utilisateurs) {
            System.out.println(String.format("%-6s %-18s %-18s %s",
                    utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getRole()));
        }
        System.out.println(utilisateurs.size() + " utilisateur(s).");
    }

    // =====================================================================
    //  Réservations
    // =====================================================================

    /** Réserve une salle ou un équipement (selon la catégorie) : on pose les questions puis on réserve. */
    private static void reserver(String categorie) throws RessourceIntrouvableException, ConflitReservationException {
        // boolean calculé une fois, puis réutilisé : il évite de refaire la comparaison de chaînes.
        boolean estUneSalle = categorie.equals(Ressource.CATEGORIE_SALLE);
        // On montre la liste correspondante pour que l'utilisateur voie les identifiants possibles.
        if (estUneSalle) {
            System.out.println("--- Réserver une salle ---");
            afficherSalles();
        } else {
            System.out.println("--- Réserver un équipement ---");
            afficherEquipements();
        }

        // Opérateur conditionnel ? : : la question change selon la catégorie, en une seule instruction.
        String idRessource = Saisie.lireIdentifiant(estUneSalle
                ? "Identifiant de la salle (ex : S1) : "
                : "Identifiant de l'équipement (ex : E1) : ");
        // Les identifiants sont vérifiés dès qu'ils sont tapés : inutile de demander la date et les
        // heures si la ressource ou l'utilisateur n'existe pas (l'exception arrête la méthode).
        gestion.trouverRessource(idRessource, categorie);   // on s'arrête tout de suite si elle n'existe pas
        String idUtilisateur = Saisie.lireIdentifiant("Identifiant de l'utilisateur (ex : U1) : ");
        gestion.trouverUtilisateur(idUtilisateur);
        LocalDate date = Saisie.lireDate("Date (JJ/MM/AAAA) : ");
        LocalTime heureDebut = Saisie.lireHeure("Heure de début (HH:mm) : ");
        LocalTime heureFin = Saisie.lireHeure("Heure de fin (HH:mm) : ");

        // Toutes les règles de gestion sont vérifiées ici ; une exception est lancée si quelque chose cloche
        Reservation reservation = gestion.reserver(categorie, idRessource, idUtilisateur, date, heureDebut, heureFin);

        System.out.println();
        System.out.println("=== RESERVATION CONFIRMEE ===");
        System.out.println();
        afficherDetailsReservation(reservation);
        sauvegarder();
    }

    private static void listerReservations() {
        afficherReservations(gestion.getToutesLesReservations(),
                "Toutes les réservations", "Aucune réservation enregistrée.");
    }

    private static void rechercherReservation() throws RessourceIntrouvableException {
        String id = Saisie.lireIdentifiant("Identifiant de la réservation (ex : R1) : ");
        Reservation reservation = gestion.trouverReservation(id);
        System.out.println();
        afficherDetailsReservation(reservation);
    }

    private static void annulerReservation() throws RessourceIntrouvableException {
        System.out.println("--- Annuler une réservation ---");
        String id = Saisie.lireIdentifiant("Identifiant de la réservation à annuler (ex : R1) : ");
        Reservation reservation = gestion.trouverReservation(id);
        System.out.println();
        afficherDetailsReservation(reservation);
        // On affiche d'abord la réservation, puis on demande confirmation : l'utilisateur sait
        // exactement ce qu'il annule. O : annulation et sauvegarde ; N : rien ne change.
        if (Saisie.lireConfirmation("Voulez-vous vraiment annuler cette réservation ? (O/N) : ")) {
            gestion.annulerReservation(id);
            System.out.println("Réservation " + id + " annulée : le créneau est de nouveau libre.");
            sauvegarder();
        } else {
            System.out.println("Annulation abandonnée : la réservation est conservée.");
        }
    }

    private static void modifierReservation() throws RessourceIntrouvableException, ConflitReservationException {
        System.out.println("--- Modifier une réservation ---");
        String id = Saisie.lireIdentifiant("Identifiant de la réservation à modifier (ex : R1) : ");
        Reservation reservation = gestion.trouverReservation(id);
        System.out.println();
        afficherDetailsReservation(reservation);

        System.out.println();
        System.out.println("(Appuyez sur Entrée pour conserver la valeur actuelle.)");
        // lireTexte avec valeur actuelle (Entrée la garde), puis toUpperCase : « s2 » devient « S2 ».
        // Toutes les nouvelles valeurs sont lues AVANT d'appeler modifierReservation, qui vérifie
        // tout d'un coup : si une vérification échoue, l'ancienne réservation reste intacte.
        String idRessource = Saisie.lireTexte("Identifiant de la ressource [" + reservation.getIdRessource() + "] : ",
                reservation.getIdRessource()).toUpperCase();
        String idUtilisateur = Saisie.lireTexte("Identifiant de l'utilisateur [" + reservation.getIdUtilisateur() + "] : ",
                reservation.getIdUtilisateur()).toUpperCase();
        LocalDate date = Saisie.lireDate("Date [" + reservation.getDateTexte() + "] (JJ/MM/AAAA) : ",
                reservation.getDate());
        LocalTime heureDebut = Saisie.lireHeure("Heure de début [" + reservation.getHeureDebutTexte() + "] (HH:mm) : ",
                reservation.getHeureDebut());
        LocalTime heureFin = Saisie.lireHeure("Heure de fin [" + reservation.getHeureFinTexte() + "] (HH:mm) : ",
                reservation.getHeureFin());

        // Si une vérification échoue (conflit...), l'exception est lancée et la réservation reste intacte
        gestion.modifierReservation(id, idRessource, idUtilisateur, date, heureDebut, heureFin);

        System.out.println();
        System.out.println("=== RESERVATION MODIFIEE ===");
        System.out.println();
        afficherDetailsReservation(reservation);
        sauvegarder();
    }

    /** Affiche une réservation dans le format du récapitulatif demandé. */
    private static void afficherDetailsReservation(Reservation reservation) {
        System.out.println("Identifiant : " + reservation.getId());
        System.out.println("Ressource : " + reservation.getIdRessource() + " (" + gestion.getNomRessource(reservation) + ")");
        System.out.println("Utilisateur : " + reservation.getIdUtilisateur() + " (" + gestion.getNomUtilisateur(reservation) + ")");
        System.out.println("Date : " + reservation.getDateTexte());
        System.out.println("Début : " + reservation.getHeureDebutTexte());
        System.out.println("Fin : " + reservation.getHeureFinTexte());
    }

    /** Affiche une liste de réservations sous forme de tableau (ou le message adapté si elle est vide). */
    private static void afficherReservations(ArrayList<Reservation> liste, String titre, String messageSiVide) {
        // Une seule méthode d'affichage pour la liste complète et les quatre plannings : seuls la
        // liste, le titre et le message « vide » changent, ils sont donc passés en paramètres.
        System.out.println();
        System.out.println("--- " + titre + " ---");
        if (liste.isEmpty()) {
            System.out.println(messageSiVide);
            return;
        }
        System.out.println(String.format("%-5s %-11s %-6s %-6s %-26s %s",
                "ID", "Date", "Début", "Fin", "Ressource", "Utilisateur"));
        // for-each : une ligne par réservation, dans l'ordre de la liste (déjà triée chronologiquement).
        for (Reservation r : liste) {
            String ressource = r.getIdRessource() + " " + gestion.getNomRessource(r);
            String utilisateur = r.getIdUtilisateur() + " " + gestion.getNomUtilisateur(r);
            System.out.println(String.format("%-5s %-11s %-6s %-6s %-26s %s",
                    r.getId(), r.getDateTexte(), r.getHeureDebutTexte(), r.getHeureFinTexte(), ressource, utilisateur));
        }
        System.out.println(liste.size() + " réservation(s).");
    }

    // =====================================================================
    //  Planning
    // =====================================================================

    // Les quatre plannings : on montre la liste des choix possibles, on lit l'identifiant (ou la
    // date), puis on affiche la liste triée renvoyée par GestionReservations avec afficherReservations.

    /** Planning d'une salle choisie par son identifiant. */
    private static void planningParSalle() throws RessourceIntrouvableException {
        afficherSalles();
        String id = Saisie.lireIdentifiant("Identifiant de la salle (ex : S1) : ");
        Salle salle = gestion.trouverSalle(id);
        afficherReservations(gestion.planningParSalle(id), "Planning de la salle " + salle.getId() + " - " + salle.getNom(),
                "Aucune réservation pour la salle " + salle.getId() + " - " + salle.getNom() + ".");
    }

    private static void planningParEquipement() throws RessourceIntrouvableException {
        afficherEquipements();
        String id = Saisie.lireIdentifiant("Identifiant de l'équipement (ex : E1) : ");
        Equipement equipement = gestion.trouverEquipement(id);
        afficherReservations(gestion.planningParEquipement(id),
                "Planning de l'équipement " + equipement.getId() + " - " + equipement.getNom(),
                "Aucune réservation pour l'équipement " + equipement.getId() + " - " + equipement.getNom() + ".");
    }

    private static void planningParDate() {
        LocalDate date = Saisie.lireDate("Date (JJ/MM/AAAA) : ");
        String texteDate = date.format(Reservation.FORMAT_DATE);
        afficherReservations(gestion.planningParDate(date), "Planning du " + texteDate,
                "Aucune réservation le " + texteDate + ".");
    }

    private static void planningParUtilisateur() throws RessourceIntrouvableException {
        afficherUtilisateurs();
        String id = Saisie.lireIdentifiant("Identifiant de l'utilisateur (ex : U1) : ");
        Utilisateur utilisateur = gestion.trouverUtilisateur(id);
        afficherReservations(gestion.planningParUtilisateur(id),
                "Planning de " + utilisateur.getNomComplet() + " (" + utilisateur.getId() + ")",
                "Aucune réservation pour " + utilisateur.getNomComplet() + " (" + utilisateur.getId() + ").");
    }

    // =====================================================================
    //  Recherche de ressources disponibles
    // =====================================================================

    private static void rechercherDisponibilites() {
        System.out.println("--- Rechercher une ressource disponible ---");
        LocalDate date = Saisie.lireDate("Date (JJ/MM/AAAA) : ");
        LocalTime heureDebut = Saisie.lireHeure("Heure de début (HH:mm) : ");
        LocalTime heureFin = Saisie.lireHeure("Heure de fin (HH:mm) : ");

        // Cette méthode n'a pas de throws : seules des exceptions non contrôlées peuvent sortir
        // (IllegalArgumentException), attrapées par le catch de la boucle principale de main.
        // Lance IllegalArgumentException si le créneau est invalide (début >= fin, date passée)
        ArrayList<Salle> sallesLibres = gestion.sallesDisponibles(date, heureDebut, heureFin);
        ArrayList<Equipement> equipementsLibres = gestion.equipementsDisponibles(date, heureDebut, heureFin);

        System.out.println();
        System.out.println("Disponibilités le " + date.format(Reservation.FORMAT_DATE)
                + " de " + heureDebut.format(Reservation.FORMAT_HEURE)
                + " à " + heureFin.format(Reservation.FORMAT_HEURE) + " :");

        System.out.println();
        System.out.println("Salles disponibles :");
        // if / else : soit un message (liste vide), soit l'en-tête et une ligne par salle libre.
        if (sallesLibres.isEmpty()) {
            System.out.println("  Aucune salle disponible sur ce créneau.");
        } else {
            System.out.println(String.format("  %-6s %-22s %-10s %s", "ID", "Nom", "Capacité", "Bâtiment"));
            for (Salle salle : sallesLibres) {
                System.out.println(String.format("  %-6s %-22s %-10s %s",
                        salle.getId(), salle.getNom(), salle.getCapacite(), salle.getBatiment()));
            }
        }

        System.out.println();
        System.out.println("Équipements disponibles :");
        if (equipementsLibres.isEmpty()) {
            System.out.println("  Aucun équipement disponible sur ce créneau.");
        } else {
            System.out.println(String.format("  %-6s %-26s %-18s %-16s %s",
                    "ID", "Nom", "Type", "Quantité totale", "Unités disponibles"));
            // Pour chaque équipement libre, on calcule aussi combien d'unités restent sur ce créneau.
            for (Equipement equipement : equipementsLibres) {
                int libres = gestion.unitesDisponibles(equipement, date, heureDebut, heureFin);
                System.out.println(String.format("  %-6s %-26s %-18s %-16s %s",
                        equipement.getId(), equipement.getNom(), equipement.getType(),
                        equipement.getQuantiteDisponible(), libres));
            }
        }
    }

    // =====================================================================
    //  Sauvegarde et messages communs
    // =====================================================================

    /** Demande la sauvegarde des fichiers. On n'affiche "sauvegardées" QUE si l'écriture a réussi. */
    private static void sauvegarder() {
        // try / catch : une sauvegarde ratée (fichier verrouillé...) ne doit pas arrêter le
        // programme. On prévient l'utilisateur ; les données restent en mémoire et une nouvelle
        // tentative aura lieu à la prochaine modification.
        try {
            gestion.sauvegarderTout();
            System.out.println("Données sauvegardées.");
        } catch (IOException e) {
            System.out.println("ERREUR DE SAUVEGARDE : " + e.getMessage());
            System.out.println("Les modifications sont gardées en mémoire mais ne sont PAS enregistrées dans les fichiers.");
            System.out.println("Une nouvelle tentative aura lieu à la prochaine modification et à la fermeture.");
        }
    }

    /** Dernière sauvegarde à la fermeture ; si elle échoue, on propose de réessayer. */
    private static void sauvegarderALaFermeture() {
        /*
         * Boucle while : on réessaie la sauvegarde tant que l'utilisateur le demande. termine
         * passe à true quand la sauvegarde réussit, ou quand l'utilisateur répond N (ou que le
         * clavier est fermé) ; !termine veut dire « pas encore terminé ».
         * Un try / catch imbriqué protège la question elle-même : si le clavier est fermé, on ne
         * peut pas poser la question, on arrête donc sans boucler à l'infini.
         */
        boolean termine = false;
        while (!termine) {
            try {
                gestion.sauvegarderTout();
                System.out.println("Données sauvegardées.");
                termine = true;
            } catch (IOException e) {
                System.out.println("ERREUR DE SAUVEGARDE : " + e.getMessage());
                try {
                    termine = !Saisie.lireConfirmation("Réessayer la sauvegarde ? (O = réessayer, N = quitter sans sauvegarder) : ");
                } catch (NoSuchElementException fin) {
                    termine = true;   // plus de clavier : impossible de poser la question
                }
                if (termine) {
                    System.out.println("ATTENTION : les dernières modifications n'ont PAS été enregistrées.");
                }
            }
        }
    }

    /**
     * Affiche les lignes ignorées au chargement, puis fait une copie de secours (*.bak) des fichiers.
     *
     * @param problemes les messages renvoyés par chargerDonnees (liste vide si tout est correct)
     */
    private static void afficherProblemes(ArrayList<String> problemes) {
        // Aucun problème : rien à afficher, on quitte la méthode tout de suite.
        if (problemes.isEmpty()) {
            return;
        }
        System.out.println();
        System.out.println("ATTENTION : " + problemes.size() + " problème(s) dans les fichiers de données :");
        for (String probleme : problemes) {
            System.out.println(" - " + probleme);
        }
        System.out.println("Ces lignes ne sont pas chargées et disparaîtront des fichiers à la prochaine sauvegarde.");
        try {
            gestion.copierFichiersOriginaux();
            System.out.println("Une copie des fichiers d'origine a été enregistrée (fichiers *.bak dans le dossier data).");
        } catch (IOException e) {
            System.out.println("La copie de secours (*.bak) n'a pas pu être créée : " + e.getMessage());
        }
    }
}
