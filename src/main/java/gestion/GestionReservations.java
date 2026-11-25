package gestion;

import exceptions.ConflitReservationException;
import exceptions.RessourceIntrouvableException;
import model.Equipement;
import model.Reservation;
import model.Ressource;
import model.Salle;
import model.Utilisateur;

// IOException : erreur de lecture / écriture des fichiers (transmise à Main par throws).
// LocalDate, LocalTime : dates et heures des réservations.
// ArrayList : les listes de données ; Collections : sa méthode sort trie une liste.
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Toute la logique métier de l'application :
 * gestion des salles, des équipements, des utilisateurs et des réservations,
 * détection des conflits de créneaux, plannings et recherche de disponibilités.
 *
 * Les données vivent en mémoire dans quatre ArrayList. GestionFichiers sait les lire
 * et les écrire dans les fichiers texte ; cette classe ne fait AUCUN affichage :
 * quand quelque chose ne va pas, elle lance une exception avec un message clair,
 * et c'est Main qui l'affiche à l'utilisateur.
 *
 * Pourquoi aucun affichage ici : la même règle (par exemple « pas de réservation dans le passé »)
 * doit s'appliquer quand on réserve au clavier ET quand on recharge le fichier au démarrage. En
 * plaçant toutes les règles ici, sans System.out, elles sont écrites une seule fois et peuvent
 * être utilisées par n'importe quelle interface (console aujourd'hui, fenêtre demain).
 *
 * Deux sortes de méthodes de recherche : chercherXxx (private) renvoie null si rien n'est trouvé,
 * pour les tests internes ; trouverXxx (public) lance RessourceIntrouvableException, pour que
 * l'appelant soit obligé de traiter le cas « identifiant inconnu ».
 */
public class GestionReservations {

    // ===================== Attributs =====================

    // Quatre ArrayList : le nombre de salles, d'équipements, d'utilisateurs et de réservations
    // change pendant l'exécution (ajouts, suppressions) ; un tableau de taille fixe ne conviendrait
    // pas. Le type entre < > garantit qu'une liste ne contient que des objets de ce type.
    // private : seules les méthodes de cette classe, qui vérifient les règles, modifient les listes.
    private ArrayList<Salle> salles = new ArrayList<Salle>();
    private ArrayList<Equipement> equipements = new ArrayList<Equipement>();
    private ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    // Dernier numéro attribué pour chaque type d'identifiant (S12 -> 12). Ils ne diminuent JAMAIS :
    // un identifiant supprimé n'est donc jamais réattribué, et on ne se base pas sur size().
    private int dernierNumeroSalle = 0;
    private int dernierNumeroEquipement = 0;
    private int dernierNumeroUtilisateur = 0;
    private int dernierNumeroReservation = 0;

    // L'objet qui lit et écrit les fichiers ; il est reçu par le constructeur (Main le crée).
    private GestionFichiers fichiers;

    /**
     * Crée le gestionnaire avec l'objet qui s'occupera des fichiers.
     * Les listes sont vides au départ ; chargerDonnees() les remplit.
     *
     * @param fichiers l'objet chargé de la lecture et de l'écriture des fichiers
     */
    public GestionReservations(GestionFichiers fichiers) {
        this.fichiers = fichiers;
    }

    // =====================================================================
    //  Chargement et sauvegarde
    // =====================================================================

    /**
     * Charge les quatre fichiers au démarrage (en les créant s'ils n'existent pas).
     * Chaque donnée est contrôlée avec les mêmes règles que lors d'un ajout au clavier.
     * Une ligne incorrecte est ignorée ; la liste renvoyée explique chaque problème trouvé.
     */
    public ArrayList<String> chargerDonnees() throws IOException {
        fichiers.preparerFichiers();
        ArrayList<String> problemes = new ArrayList<String>();

        // Les numéros mémorisés dans compteurs.txt : le prochain identifiant ne sera jamais plus petit
        int[] compteurs = fichiers.chargerCompteurs();
        dernierNumeroSalle = compteurs[0];
        dernierNumeroEquipement = compteurs[1];
        dernierNumeroUtilisateur = compteurs[2];
        dernierNumeroReservation = compteurs[3];

        // 1. Les salles
        /*
         * Boucle for-each : on parcourt toutes les salles lues dans le fichier, une par une, sans
         * besoin d'indice. Pour chacune :
         * - verifierIdentifiant contrôle sa forme (S suivi d'un nombre) et qu'il n'y a pas de doublon
         *   (chercherSalle(...) != null veut dire « une salle de même identifiant est déjà chargée ») ;
         * - controlerSalle applique les mêmes règles que pour un ajout au clavier ;
         * - si tout va bien, la salle est ajoutée et le compteur est mis à jour avec Math.max (le
         *   plus grand des deux nombres), pour que le prochain identifiant soit plus grand que tous.
         * Le try / catch est DANS la boucle : une salle incorrecte est seulement ignorée et notée,
         * le chargement continue avec la suivante.
         */
        for (Salle salle : fichiers.chargerSalles()) {
            try {
                verifierIdentifiant(salle.getId(), 'S', chercherSalle(salle.getId()) != null);
                controlerSalle(salle.getNom(), salle.getCapacite(), salle.getBatiment());
                salles.add(salle);
                dernierNumeroSalle = Math.max(dernierNumeroSalle, numero(salle.getId()));
            } catch (IllegalArgumentException e) {
                problemes.add("salles.txt : la salle \"" + salle.getId() + "\" est ignorée : " + e.getMessage());
            }
        }

        // 2. Les équipements
        // Même principe que pour les salles (contrôle, ajout, compteur, erreur notée).
        for (Equipement equipement : fichiers.chargerEquipements()) {
            try {
                verifierIdentifiant(equipement.getId(), 'E', chercherEquipement(equipement.getId()) != null);
                controlerEquipement(equipement.getNom(), equipement.getType(), equipement.getQuantiteDisponible());
                equipements.add(equipement);
                dernierNumeroEquipement = Math.max(dernierNumeroEquipement, numero(equipement.getId()));
            } catch (IllegalArgumentException e) {
                problemes.add("equipements.txt : l'équipement \"" + equipement.getId() + "\" est ignoré : " + e.getMessage());
            }
        }

        // 3. Les utilisateurs
        // Même principe que pour les salles.
        for (Utilisateur utilisateur : fichiers.chargerUtilisateurs()) {
            try {
                verifierIdentifiant(utilisateur.getId(), 'U', chercherUtilisateur(utilisateur.getId()) != null);
                controlerUtilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getRole());
                utilisateurs.add(utilisateur);
                dernierNumeroUtilisateur = Math.max(dernierNumeroUtilisateur, numero(utilisateur.getId()));
            } catch (IllegalArgumentException e) {
                problemes.add("utilisateurs.txt : l'utilisateur \"" + utilisateur.getId() + "\" est ignoré : " + e.getMessage());
            }
        }

        // 4. Les réservations (en dernier : elles s'appuient sur les ressources et utilisateurs chargés)
        for (Reservation reservation : fichiers.chargerReservations()) {
            try {
                verifierIdentifiant(reservation.getId(), 'R', chercherReservation(reservation.getId()) != null);
                controlerReservationChargee(reservation);
                reservations.add(reservation);
                dernierNumeroReservation = Math.max(dernierNumeroReservation, numero(reservation.getId()));
            // Multi-catch (Java 7 et plus) : un seul bloc catch pour trois types d'exceptions, séparés
            // par |, car le traitement est le même pour les trois (noter le problème et continuer).
            } catch (IllegalArgumentException | RessourceIntrouvableException | ConflitReservationException e) {
                problemes.add("reservations.txt : la réservation \"" + reservation.getId() + "\" est ignorée : " + e.getMessage());
            }
        }

        // D'abord les lignes mal formées repérées par GestionFichiers, puis nos propres contrôles
        ArrayList<String> tous = fichiers.getAvertissements();
        tous.addAll(problemes);
        return tous;
    }

    /** Copie les fichiers d'origine (*.bak) : à faire quand des lignes ont été ignorées au chargement. */
    public void copierFichiersOriginaux() throws IOException {
        fichiers.copierFichiersOriginaux();
    }

    /**
     * Réécrit tous les fichiers. On essaie les cinq fichiers même si l'un échoue,
     * puis on lance UNE exception qui liste ce qui n'a pas pu être écrit.
     */
    public void sauvegarderTout() throws IOException {
        /*
         * Cinq blocs try / catch séparés (et non un seul) : avec un seul try, la première erreur
         * (un fichier verrouillé par exemple) empêcherait d'écrire les fichiers suivants. Ici
         * chaque fichier est tenté ; les erreurs sont accumulées dans « erreurs », puis signalées
         * ensemble à la fin.
         * StringBuilder : comme StringBuffer, un texte modifiable qu'on allonge avec append.
         */
        StringBuilder erreurs = new StringBuilder();
        try {
            fichiers.sauvegarderSalles(salles);
        } catch (IOException e) {
            noter(erreurs, e);
        }
        try {
            fichiers.sauvegarderEquipements(equipements);
        } catch (IOException e) {
            noter(erreurs, e);
        }
        try {
            fichiers.sauvegarderUtilisateurs(utilisateurs);
        } catch (IOException e) {
            noter(erreurs, e);
        }
        try {
            fichiers.sauvegarderReservations(reservations);
        } catch (IOException e) {
            noter(erreurs, e);
        }
        try {
            fichiers.sauvegarderCompteurs(dernierNumeroSalle, dernierNumeroEquipement,
                    dernierNumeroUtilisateur, dernierNumeroReservation);
        } catch (IOException e) {
            noter(erreurs, e);
        }
        // Au moins une erreur a été notée (le texte n'est pas vide) : on prévient l'appelant.
        if (erreurs.length() > 0) {
            throw new IOException(erreurs.toString());
        }
    }

    /**
     * Ajoute le message de e à la liste des erreurs, avec « | » entre deux messages.
     *
     * @param erreurs le texte qui accumule les erreurs
     * @param e       l'erreur à ajouter
     */
    private static void noter(StringBuilder erreurs, IOException e) {
        if (erreurs.length() > 0) {
            erreurs.append(" | ");
        }
        erreurs.append(e.getMessage());
    }

    // =====================================================================
    //  Recherche par identifiant
    //  chercherXxx renvoie null si rien n'est trouvé ; trouverXxx lance RessourceIntrouvableException.
    // =====================================================================

    /**
     * Recherche séquentielle d'une salle par son identifiant.
     *
     * @param id l'identifiant cherché
     * @return la salle trouvée, ou null s'il n'y en a pas
     */
    private Salle chercherSalle(String id) {
        /*
         * Boucle for-each : on examine les salles une par une. Dès que l'identifiant correspond
         * (equals : on compare le contenu des chaînes), return renvoie la salle et arrête la
         * boucle et la méthode. Si la boucle va jusqu'au bout sans trouver, on renvoie null.
         * Les trois méthodes chercher... suivantes fonctionnent exactement de la même façon.
         */
        for (Salle salle : salles) {
            if (salle.getId().equals(id)) {
                return salle;
            }
        }
        return null;
    }

    private Equipement chercherEquipement(String id) {
        for (Equipement equipement : equipements) {
            if (equipement.getId().equals(id)) {
                return equipement;
            }
        }
        return null;
    }

    private Utilisateur chercherUtilisateur(String id) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getId().equals(id)) {
                return utilisateur;
            }
        }
        return null;
    }

    private Reservation chercherReservation(String id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }
        return null;
    }

    /**
     * Renvoie la salle de cet identifiant ; lance RessourceIntrouvableException si elle n'existe pas.
     *
     * Les quatre méthodes trouver... réutilisent chercher... et transforment le « null » en une
     * exception avec un message clair. throws l'annonce : l'appelant doit la traiter.
     *
     * @param id l'identifiant de la salle
     * @return la salle
     * @throws RessourceIntrouvableException si aucune salle n'a cet identifiant
     */
    public Salle trouverSalle(String id) throws RessourceIntrouvableException {
        Salle salle = chercherSalle(id);
        // == null : la recherche n'a rien trouvé. throw arrête la méthode : le return n'est pas atteint.
        if (salle == null) {
            throw new RessourceIntrouvableException("cette ressource n'existe pas (salle introuvable : " + id + ").");
        }
        return salle;
    }

    public Equipement trouverEquipement(String id) throws RessourceIntrouvableException {
        Equipement equipement = chercherEquipement(id);
        if (equipement == null) {
            throw new RessourceIntrouvableException("cette ressource n'existe pas (équipement introuvable : " + id + ").");
        }
        return equipement;
    }

    public Utilisateur trouverUtilisateur(String id) throws RessourceIntrouvableException {
        Utilisateur utilisateur = chercherUtilisateur(id);
        if (utilisateur == null) {
            throw new RessourceIntrouvableException("cet utilisateur n'existe pas (identifiant introuvable : " + id + ").");
        }
        return utilisateur;
    }

    public Reservation trouverReservation(String id) throws RessourceIntrouvableException {
        Reservation reservation = chercherReservation(id);
        if (reservation == null) {
            throw new RessourceIntrouvableException("cette réservation n'existe pas (identifiant introuvable : " + id + ").");
        }
        return reservation;
    }

    /** Renvoie la salle ou l'équipement selon la catégorie ("SALLE" ou "EQUIPEMENT"). */
    public Ressource trouverRessource(String id, String categorie) throws RessourceIntrouvableException {
        verifierCategorie(categorie);
        // Le type de retour est Ressource (la classe mère) : on peut renvoyer une Salle OU un
        // Equipement. Pas besoin de else : si la catégorie est SALLE, le return quitte la méthode.
        if (categorie.equals(Ressource.CATEGORIE_SALLE)) {
            return trouverSalle(id);
        }
        return trouverEquipement(id);
    }

    // =====================================================================
    //  Salles
    // =====================================================================

    /** Une copie de la liste : l'appelant ne peut pas modifier la vraie liste par accident. */
    public ArrayList<Salle> getSalles() {
        return new ArrayList<Salle>(salles);
    }

    /**
     * Ajoute une salle après avoir contrôlé ses informations ; son identifiant est attribué ici.
     *
     * @param nom      le nom de la salle
     * @param capacite le nombre de places (> 0)
     * @param batiment le bâtiment
     * @return la salle créée (Main l'affiche)
     */
    public Salle ajouterSalle(String nom, int capacite, String batiment) {
        // On contrôle AVANT de créer : en cas d'erreur, controlerSalle lève une exception et rien
        // n'est ajouté.
        controlerSalle(nom, capacite, batiment);
        // Nouvel identifiant : « S » + (dernier numéro + 1). Les parenthèses sont nécessaires :
        // sans elles, "S" + dernierNumeroSalle + 1 collerait « 4 » puis « 1 » (S41 au lieu de S5).
        Salle salle = new Salle("S" + (dernierNumeroSalle + 1), nom.trim(), capacite, batiment.trim());
        dernierNumeroSalle++;
        salles.add(salle);
        return salle;
    }

    /**
     * Modifie une salle existante (même identifiant).
     *
     * @param id       l'identifiant de la salle à modifier
     * @param nom      le nouveau nom
     * @param capacite la nouvelle capacité
     * @param batiment le nouveau bâtiment
     * @throws RessourceIntrouvableException si la salle n'existe pas
     */
    public void modifierSalle(String id, String nom, int capacite, String batiment)
            throws RessourceIntrouvableException {
        Salle salle = trouverSalle(id);
        controlerSalle(nom, capacite, batiment);   // on vérifie TOUT avant de changer quoi que ce soit
        salle.setNom(nom.trim());
        salle.setCapacite(capacite);
        salle.setBatiment(batiment.trim());
    }

    /**
     * Supprime une salle, seulement si elle n'a aucune réservation.
     *
     * @param id l'identifiant de la salle
     * @throws RessourceIntrouvableException si la salle n'existe pas
     */
    public void supprimerSalle(String id) throws RessourceIntrouvableException {
        // Ordre des étapes : la salle existe (sinon exception), elle n'a plus de réservation
        // (sinon IllegalStateException), et seulement alors on la retire de la liste.
        Salle salle = trouverSalle(id);
        verifierSuppressionPossible(id);
        salles.remove(salle);
    }

    // =====================================================================
    //  Équipements
    // =====================================================================

    public ArrayList<Equipement> getEquipements() {
        return new ArrayList<Equipement>(equipements);
    }

    /**
     * Ajoute un équipement (même principe que ajouterSalle) ; le type est rangé en majuscules
     * pour que « camera » et « CAMERA » soient écrits de la même façon.
     *
     * @param nom      le nom de l'équipement
     * @param type     son type
     * @param quantite le nombre d'exemplaires (> 0)
     * @return l'équipement créé
     */
    public Equipement ajouterEquipement(String nom, String type, int quantite) {
        controlerEquipement(nom, type, quantite);
        Equipement equipement = new Equipement("E" + (dernierNumeroEquipement + 1),
                nom.trim(), type.trim().toUpperCase(), quantite);
        dernierNumeroEquipement++;
        equipements.add(equipement);
        return equipement;
    }

    /**
     * Modifie un équipement. Si la nouvelle quantité est plus petite que le nombre d'unités
     * déjà réservées en même temps, la modification est refusée (sinon les réservations
     * existantes dépasseraient le stock).
     */
    public void modifierEquipement(String id, String nom, String type, int quantite)
            throws RessourceIntrouvableException, ConflitReservationException {
        Equipement equipement = trouverEquipement(id);
        controlerEquipement(nom, type, quantite);
        int dejaReserveesEnMemeTemps = utilisationMaximale(id);
        // < strict : descendre exactement au nombre d'unités déjà utilisées en même temps est
        // permis (le stock reste suffisant) ; descendre en dessous ne l'est pas.
        if (quantite < dejaReserveesEnMemeTemps) {
            throw new ConflitReservationException("quantité disponible insuffisante : " + dejaReserveesEnMemeTemps
                    + " unité(s) de " + id + " sont déjà réservées en même temps, "
                    + "impossible de descendre à " + quantite + ".");
        }
        equipement.setNom(nom.trim());
        equipement.setType(type.trim().toUpperCase());
        equipement.setQuantiteDisponible(quantite);
    }

    public void supprimerEquipement(String id) throws RessourceIntrouvableException {
        Equipement equipement = trouverEquipement(id);
        verifierSuppressionPossible(id);
        equipements.remove(equipement);
    }

    // =====================================================================
    //  Utilisateurs
    // =====================================================================

    public ArrayList<Utilisateur> getUtilisateurs() {
        return new ArrayList<Utilisateur>(utilisateurs);
    }

    // Les méthodes des équipements et des utilisateurs suivent le même modèle que celles des
    // salles : contrôle d'abord, puis modification ; suppression seulement sans réservation.

    /**
     * Ajoute un utilisateur ; le rôle est rangé en majuscules (ENSEIGNANT, ETUDIANT).
     *
     * @param nom    le nom de famille
     * @param prenom le prénom
     * @param role   le rôle
     * @return l'utilisateur créé
     */
    public Utilisateur ajouterUtilisateur(String nom, String prenom, String role) {
        controlerUtilisateur(nom, prenom, role);
        Utilisateur utilisateur = new Utilisateur("U" + (dernierNumeroUtilisateur + 1),
                nom.trim(), prenom.trim(), role.trim().toUpperCase());
        dernierNumeroUtilisateur++;
        utilisateurs.add(utilisateur);
        return utilisateur;
    }

    public void modifierUtilisateur(String id, String nom, String prenom, String role)
            throws RessourceIntrouvableException {
        Utilisateur utilisateur = trouverUtilisateur(id);
        controlerUtilisateur(nom, prenom, role);
        utilisateur.setNom(nom.trim());
        utilisateur.setPrenom(prenom.trim());
        utilisateur.setRole(role.trim().toUpperCase());
    }

    public void supprimerUtilisateur(String id) throws RessourceIntrouvableException {
        Utilisateur utilisateur = trouverUtilisateur(id);
        verifierSuppressionPossible(id);
        utilisateurs.remove(utilisateur);
    }

    /**
     * Règle de gestion : une salle, un équipement ou un utilisateur qui a encore des réservations
     * ne peut pas être supprimé. On ne supprime JAMAIS les réservations à sa place.
     * (Les identifiants S, E et U sont tous différents : un seul test suffit pour les trois cas.)
     */
    public void verifierSuppressionPossible(String id) {
        ArrayList<Reservation> liees = new ArrayList<Reservation>();
        /*
         * Boucle for-each sur toutes les réservations : on garde celles qui concernent id, soit
         * comme ressource réservée, soit comme personne qui réserve. || : une des deux suffit.
         */
        for (Reservation r : reservations) {
            if (r.getIdRessource().equals(id) || r.getIdUtilisateur().equals(id)) {
                liees.add(r);
            }
        }
        // Au moins une réservation liée : la suppression est refusée.
        if (!liees.isEmpty()) {
            String identifiants = "";
            /*
             * Boucle for avec indice (et non for-each) : on a besoin de i pour ne pas mettre de
             * virgule avant le premier identifiant (i > 0). Résultat : « R1, R3, R4 ».
             * += ajoute à la fin de la chaîne (identifiants = identifiants + ...).
             */
            for (int i = 0; i < liees.size(); i++) {
                if (i > 0) {
                    identifiants += ", ";
                }
                identifiants += liees.get(i).getId();
            }
            throw new IllegalStateException("suppression impossible : " + id + " a encore " + liees.size()
                    + " réservation(s) (" + identifiants + "). Annulez d'abord ces réservations.");
        }
    }

    // =====================================================================
    //  Réservations : réserver, modifier, annuler
    // =====================================================================

    /**
     * Réserve une ressource. Tout est vérifié AVANT d'enregistrer : si une seule condition
     * n'est pas respectée, une exception est lancée et rien n'est enregistré.
     */
    // Paramètres de reserver : la catégorie (SALLE ou EQUIPEMENT), les identifiants de la
    // ressource et de l'utilisateur, la date et les heures. Elle renvoie la réservation créée,
    // que Main affiche dans le récapitulatif « RESERVATION CONFIRMEE ».
    // Pourquoi tout vérifier avant de créer : si on créait la réservation puis qu'une vérification
    // échouait, il faudrait l'annuler ; ici rien n'est modifié tant que tout n'est pas valide.
    public Reservation reserver(String categorie, String idRessource, String idUtilisateur,
                                LocalDate date, LocalTime heureDebut, LocalTime heureFin)
            throws RessourceIntrouvableException, ConflitReservationException {
        // 1. La catégorie, la ressource et l'utilisateur existent
        verifierCategorie(categorie);
        Ressource ressource = trouverRessource(idRessource, categorie);
        trouverUtilisateur(idUtilisateur);
        // 2. La date et les heures sont valides (début < fin, pas dans le passé)
        verifierCreneau(date, heureDebut, heureFin);
        // 3. La ressource est réellement disponible
        // null en dernier paramètre : il n'y a aucune réservation à ignorer (c'est une nouvelle).
        verifierDisponibilite(ressource, date, heureDebut, heureFin, null);

        // Tout est bon : on crée et on enregistre
        Reservation reservation = new Reservation("R" + (dernierNumeroReservation + 1),
                idRessource, categorie, idUtilisateur, date, heureDebut, heureFin);
        dernierNumeroReservation++;
        reservations.add(reservation);
        return reservation;
    }

    /**
     * Modifie une réservation (elle garde son identifiant et sa catégorie).
     * La nouvelle ressource doit être de la même catégorie.
     * Pour les conflits, on ignore la réservation en cours de modification elle-même.
     * Si une vérification échoue, l'ancienne réservation reste intacte.
     */
    public Reservation modifierReservation(String id, String idRessource, String idUtilisateur,
                                           LocalDate date, LocalTime heureDebut, LocalTime heureFin)
            throws RessourceIntrouvableException, ConflitReservationException {
        Reservation reservation = trouverReservation(id);
        Ressource ressource = trouverRessource(idRessource, reservation.getCategorie());
        trouverUtilisateur(idUtilisateur);
        verifierCreneau(date, heureDebut, heureFin);
        // On passe id : la réservation modifiée ne doit pas être en conflit avec ELLE-MÊME
        // (déplacer R1 de 9h-11h à 10h-11h ne doit pas être refusé à cause de l'ancien R1).
        // trouverRessource utilise la catégorie de la réservation : on ne peut pas transformer
        // une réservation de salle en réservation d'équipement.
        verifierDisponibilite(ressource, date, heureDebut, heureFin, id);

        // Toutes les vérifications ont réussi : on applique les changements
        reservation.setIdRessource(idRessource);
        reservation.setIdUtilisateur(idUtilisateur);
        reservation.setDate(date);
        reservation.setHeureDebut(heureDebut);
        reservation.setHeureFin(heureFin);
        return reservation;
    }

    /**
     * Annule une réservation : elle est retirée de la liste et le créneau redevient libre.
     *
     * @param id l'identifiant de la réservation
     * @throws RessourceIntrouvableException si elle n'existe pas
     */
    public void annulerReservation(String id) throws RessourceIntrouvableException {
        Reservation reservation = trouverReservation(id);
        // remove(objet) retire cet objet précis de la liste (comparaison avec equals ; Reservation
        // ne la redéfinit pas, ce sont donc les références qui sont comparées : le bon objet).
        reservations.remove(reservation);
    }

    /**
     * Toutes les réservations, dans l'ordre chronologique.
     * On trie une COPIE : l'ordre de la vraie liste (l'ordre de création) n'est pas modifié.
     *
     * @return une nouvelle liste triée
     */
    public ArrayList<Reservation> getToutesLesReservations() {
        return trier(new ArrayList<Reservation>(reservations));
    }

    /** Le nom de la ressource réservée (ou "(introuvable)" si le fichier a été modifié à la main). */
    public String getNomRessource(Reservation reservation) {
        // On cherche dans la liste des salles ou des équipements selon la catégorie. On utilise
        // chercher... (qui renvoie null) et non trouver... : un nom manquant ne doit pas empêcher
        // d'afficher un planning, on écrit seulement « (introuvable) ».
        if (reservation.getCategorie().equals(Ressource.CATEGORIE_SALLE)) {
            Salle salle = chercherSalle(reservation.getIdRessource());
            return salle == null ? "(introuvable)" : salle.getNom();
        }
        Equipement equipement = chercherEquipement(reservation.getIdRessource());
        return equipement == null ? "(introuvable)" : equipement.getNom();
    }

    /** Le nom complet de l'utilisateur qui a réservé. */
    public String getNomUtilisateur(Reservation reservation) {
        Utilisateur utilisateur = chercherUtilisateur(reservation.getIdUtilisateur());
        return utilisateur == null ? "(introuvable)" : utilisateur.getNomComplet();
    }

    // =====================================================================
    //  Détection des conflits
    // =====================================================================

    /**
     * Règle du cahier des charges : deux créneaux se chevauchent s'ils ont lieu le MÊME JOUR
     * et si CHACUN COMMENCE AVANT LA FIN DE L'AUTRE.
     * La comparaison est stricte (isBefore, jamais <=) : 9h-11h et 11h-12h ne se chevauchent pas.
     */
    public static boolean creneauxSeChevauchent(LocalDate date1, LocalTime debut1, LocalTime fin1,
                                                LocalDate date2, LocalTime debut2, LocalTime fin2) {
        /*
         * Trois conditions, toutes nécessaires (&&) :
         * - date1.equals(date2) : même jour (equals compare le contenu de deux LocalDate) ;
         * - debut1.isBefore(fin2) : le créneau 1 commence avant la fin du créneau 2 ;
         * - debut2.isBefore(fin1) : le créneau 2 commence avant la fin du créneau 1.
         * Exemple : 9h-11h et 10h-12h : 9h < 12h et 10h < 11h, donc chevauchement.
         * 9h-11h et 11h-12h : 11h n'est pas avant 11h, donc pas de chevauchement (la salle peut
         * être réservée juste après). Avec « avant ou égal », ces deux réservations seraient
         * refusées à tort.
         * static : la méthode n'utilise aucun attribut, elle ne dépend que de ses paramètres.
         */
        return date1.equals(date2) && debut1.isBefore(fin2) && debut2.isBefore(fin1);
    }

    /**
     * Vérifie que la ressource est libre sur le créneau demandé, sinon lance ConflitReservationException.
     * idReservationIgnoree : réservation à ne pas compter (celle qu'on est en train de modifier), ou null.
     */
    private void verifierDisponibilite(Ressource ressource, LocalDate date, LocalTime debut, LocalTime fin,
                                       String idReservationIgnoree) throws ConflitReservationException {
        /*
         * instanceof teste la classe réelle de l'objet : la règle n'est pas la même pour une salle
         * (une seule réservation à la fois) et pour un équipement (autant que d'unités).
         * Vrai : c'est une salle. Faux (else) : c'est forcément un équipement (les deux seules
         * sous-classes de Ressource).
         */
        if (ressource instanceof Salle) {
            // Une salle : aucune autre réservation ne doit chevaucher le créneau
            Reservation conflit = chercherConflitSalle(ressource.getId(), date, debut, fin, idReservationIgnoree);
            if (conflit != null) {
                throw new ConflitReservationException("ce créneau est déjà réservé : la salle " + ressource.getId()
                        + " (" + ressource.getNom() + ") est occupée le " + conflit.getDateTexte()
                        + " de " + conflit.getHeureDebutTexte() + " à " + conflit.getHeureFinTexte()
                        + " (réservation " + conflit.getId() + ").");
            }
        } else {
            // Un équipement : il doit rester au moins une unité libre pendant tout le créneau
            // Cast (Equipement) : la variable est de type Ressource, qui n'a pas de
            // getQuantiteDisponible() ; on sait (else de instanceof Salle) que c'est un Equipement.
            Equipement equipement = (Equipement) ressource;
            int utilisees = unitesUtilisees(equipement.getId(), date, debut, fin, idReservationIgnoree);
            // >= : si toutes les unités sont déjà prises (utilisées == quantité), il n'en reste
            // aucune pour la nouvelle réservation. Avec > seulement, on accepterait une unité de trop.
            if (utilisees >= equipement.getQuantiteDisponible()) {
                throw new ConflitReservationException("quantité disponible insuffisante : les "
                        + equipement.getQuantiteDisponible() + " unité(s) de " + equipement.getId()
                        + " (" + equipement.getNom() + ") sont déjà réservées sur ce créneau.");
            }
        }
    }

    /** Cherche une réservation de cette salle qui chevauche le créneau ; renvoie null s'il n'y en a pas. */
    private Reservation chercherConflitSalle(String idSalle, LocalDate date, LocalTime debut, LocalTime fin,
                                             String idReservationIgnoree) {
        /*
         * Boucle for-each sur toutes les réservations ; on renvoie la première qui pose problème
         * (return arrête la boucle), ou null si aucune. Une réservation est en conflit si les
         * quatre conditions sont vraies (&&) : c'est une réservation de salle, de CETTE salle,
         * ce n'est pas celle qu'on modifie (! inverse le test d'égalité), et les créneaux se
         * chevauchent. Les tests les plus simples sont placés d'abord : && s'arrête au premier
         * faux, le calcul du chevauchement n'est donc fait que pour les réservations de la salle.
         * equals(null) renvoie false : avec idReservationIgnoree == null, aucune n'est ignorée.
         */
        for (Reservation r : reservations) {
            if (r.getCategorie().equals(Ressource.CATEGORIE_SALLE)
                    && r.getIdRessource().equals(idSalle)
                    && !r.getId().equals(idReservationIgnoree)
                    && creneauxSeChevauchent(date, debut, fin, r.getDate(), r.getHeureDebut(), r.getHeureFin())) {
                return r;
            }
        }
        return null;
    }

    /**
     * Nombre MAXIMAL d'unités de l'équipement utilisées EN MÊME TEMPS pendant le créneau [debut, fin[.
     *
     * Chaque réservation d'un équipement représente une unité. Il ne suffit pas de compter les
     * réservations qui touchent le créneau : 8h-10h et 10h-12h touchent toutes les deux 8h-12h,
     * mais n'utilisent jamais deux unités à la fois. Le maximum est atteint à un instant où une
     * réservation commence : on teste donc le début du créneau et le début de chaque réservation
     * voisine, et on compte à chaque fois les réservations en cours.
     */
    private int unitesUtilisees(String idEquipement, LocalDate date, LocalTime debut, LocalTime fin,
                                String idReservationIgnoree) {
        // Les réservations de cet équipement qui chevauchent le créneau
        // (même filtre à quatre conditions que chercherConflitSalle, pour un équipement)
        ArrayList<Reservation> voisines = new ArrayList<Reservation>();
        for (Reservation r : reservations) {
            if (r.getCategorie().equals(Ressource.CATEGORIE_EQUIPEMENT)
                    && r.getIdRessource().equals(idEquipement)
                    && !r.getId().equals(idReservationIgnoree)
                    && creneauxSeChevauchent(date, debut, fin, r.getDate(), r.getHeureDebut(), r.getHeureFin())) {
                voisines.add(r);
            }
        }

        // Les instants à tester : début du créneau + début de chaque voisine qui commence plus tard
        ArrayList<LocalTime> instants = new ArrayList<LocalTime>();
        instants.add(debut);
        // for-each sur les voisines : seules celles qui commencent APRÈS le début du créneau
        // ajoutent un nouvel instant (celles qui ont commencé avant sont déjà en cours au début).
        for (Reservation r : voisines) {
            if (r.getHeureDebut().isAfter(debut)) {
                instants.add(r.getHeureDebut());
            }
        }

        // À chaque instant, on compte les réservations en cours (commencées et pas encore terminées)
        int maximum = 0;
        /*
         * Deux boucles imbriquées : pour CHAQUE instant (boucle extérieure), on compte TOUTES les
         * voisines en cours à cet instant (boucle intérieure), puis on garde le plus grand compte.
         * Une réservation est « en cours » à un instant si elle a déjà commencé (son début n'est
         * pas après l'instant, d'où !isAfter, qui veut dire « avant ou égal ») ET n'est pas encore
         * finie (sa fin est après l'instant). && : les deux conditions sont nécessaires.
         * enCours est remis à 0 au début de chaque instant : sinon on additionnerait les comptes
         * de tous les instants.
         */
        for (LocalTime instant : instants) {
            int enCours = 0;
            for (Reservation r : voisines) {
                if (!r.getHeureDebut().isAfter(instant) && r.getHeureFin().isAfter(instant)) {
                    enCours++;
                }
            }
            if (enCours > maximum) {
                maximum = enCours;
            }
        }
        return maximum;
    }

    /** Plus grand nombre d'unités de cet équipement réservées en même temps, tous jours confondus. */
    private int utilisationMaximale(String idEquipement) {
        int maximum = 0;
        // Pour chaque réservation de cet équipement, on calcule le nombre d'unités utilisées en
        // même temps sur son créneau, et on garde le plus grand résultat (recherche du maximum).
        for (Reservation r : reservations) {
            if (r.getCategorie().equals(Ressource.CATEGORIE_EQUIPEMENT) && r.getIdRessource().equals(idEquipement)) {
                int utilisees = unitesUtilisees(idEquipement, r.getDate(), r.getHeureDebut(), r.getHeureFin(), null);
                if (utilisees > maximum) {
                    maximum = utilisees;
                }
            }
        }
        return maximum;
    }

    // =====================================================================
    //  Plannings (toujours triés dans l'ordre chronologique)
    // =====================================================================

    // Les quatre plannings ont la même forme : vérifier que l'élément existe (sauf pour une date),
    // parcourir toutes les réservations avec un for-each, garder celles qui correspondent dans
    // une nouvelle liste (filtrage), puis la renvoyer triée. Une liste vide est un résultat normal
    // (Main affiche alors « Aucune réservation ... »).

    /**
     * @param idSalle l'identifiant de la salle
     * @return les réservations de cette salle, dans l'ordre chronologique
     * @throws RessourceIntrouvableException si la salle n'existe pas
     */
    public ArrayList<Reservation> planningParSalle(String idSalle) throws RessourceIntrouvableException {
        trouverSalle(idSalle);   // lance une exception si la salle n'existe pas
        ArrayList<Reservation> resultat = new ArrayList<Reservation>();
        for (Reservation r : reservations) {
            if (r.getCategorie().equals(Ressource.CATEGORIE_SALLE) && r.getIdRessource().equals(idSalle)) {
                resultat.add(r);
            }
        }
        return trier(resultat);
    }

    public ArrayList<Reservation> planningParEquipement(String idEquipement) throws RessourceIntrouvableException {
        trouverEquipement(idEquipement);
        ArrayList<Reservation> resultat = new ArrayList<Reservation>();
        for (Reservation r : reservations) {
            if (r.getCategorie().equals(Ressource.CATEGORIE_EQUIPEMENT) && r.getIdRessource().equals(idEquipement)) {
                resultat.add(r);
            }
        }
        return trier(resultat);
    }

    public ArrayList<Reservation> planningParDate(LocalDate date) {
        ArrayList<Reservation> resultat = new ArrayList<Reservation>();
        for (Reservation r : reservations) {
            if (r.getDate().equals(date)) {
                resultat.add(r);
            }
        }
        return trier(resultat);
    }

    public ArrayList<Reservation> planningParUtilisateur(String idUtilisateur) throws RessourceIntrouvableException {
        trouverUtilisateur(idUtilisateur);
        ArrayList<Reservation> resultat = new ArrayList<Reservation>();
        for (Reservation r : reservations) {
            if (r.getIdUtilisateur().equals(idUtilisateur)) {
                resultat.add(r);
            }
        }
        return trier(resultat);
    }

    /** Trie la liste dans l'ordre défini par Reservation.compareTo (date, puis heure de début). */
    private ArrayList<Reservation> trier(ArrayList<Reservation> liste) {
        // Collections.sort trie la liste sur place en appelant compareTo de Reservation : c'est
        // pour cela que Reservation implémente Comparable.
        Collections.sort(liste);
        return liste;
    }

    // =====================================================================
    //  Recherche de disponibilités sur un créneau
    // =====================================================================

    /** Les salles qu'aucune réservation ne chevauche sur ce créneau. */
    public ArrayList<Salle> sallesDisponibles(LocalDate date, LocalTime debut, LocalTime fin) {
        verifierCreneau(date, debut, fin);
        ArrayList<Salle> libres = new ArrayList<Salle>();
        // Une salle est libre si aucune réservation n'est en conflit avec le créneau (== null).
        for (Salle salle : salles) {
            if (chercherConflitSalle(salle.getId(), date, debut, fin, null) == null) {
                libres.add(salle);
            }
        }
        return libres;
    }

    /** Les équipements dont il reste au moins une unité libre pendant tout le créneau. */
    public ArrayList<Equipement> equipementsDisponibles(LocalDate date, LocalTime debut, LocalTime fin) {
        verifierCreneau(date, debut, fin);
        ArrayList<Equipement> libres = new ArrayList<Equipement>();
        for (Equipement equipement : equipements) {
            if (unitesDisponibles(equipement, date, debut, fin) > 0) {
                libres.add(equipement);
            }
        }
        return libres;
    }

    /** Nombre d'unités encore libres sur ce créneau = quantité totale - unités utilisées en même temps. */
    public int unitesDisponibles(Equipement equipement, LocalDate date, LocalTime debut, LocalTime fin) {
        int libres = equipement.getQuantiteDisponible()
                - unitesUtilisees(equipement.getId(), date, debut, fin, null);
        // Math.max(libres, 0) : jamais de nombre négatif (cela pourrait arriver si le fichier
        // contenait plus de réservations que d'unités).
        return Math.max(libres, 0);
    }

    // =====================================================================
    //  Contrôles de validité (utilisés à la saisie ET au chargement des fichiers)
    //  Ils lancent IllegalArgumentException avec un message explicatif.
    // =====================================================================

    /** Un texte ne doit pas être vide ni contenir le séparateur des fichiers (le point-virgule). */
    private static void verifierTexte(String valeur, String champ) {
        // valeur == null est testé EN PREMIER : si c'est vrai, || s'arrête et valeur.trim() n'est
        // jamais appelé sur null (NullPointerException évitée).
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException(champ + " ne peut pas être vide.");
        }
        if (valeur.contains(";")) {
            throw new IllegalArgumentException(champ + " ne peut pas contenir de point-virgule (;).");
        }
    }

    /**
     * Une capacité ou une quantité doit être strictement positive (<= 0 est refusé).
     *
     * @param valeur le nombre à contrôler
     * @param champ  son nom, pour le message
     */
    private static void verifierPositif(int valeur, String champ) {
        if (valeur <= 0) {
            throw new IllegalArgumentException(champ + " doit être supérieure à 0.");
        }
    }

    // Les trois méthodes controler... regroupent les contrôles d'un type d'objet. Elles sont
    // appelées à l'ajout, à la modification ET au chargement : une seule définition des règles.
    private static void controlerSalle(String nom, int capacite, String batiment) {
        verifierTexte(nom, "le nom de la salle");
        verifierPositif(capacite, "la capacité");
        verifierTexte(batiment, "le bâtiment");
    }

    private static void controlerEquipement(String nom, String type, int quantite) {
        verifierTexte(nom, "le nom de l'équipement");
        verifierTexte(type, "le type");
        verifierPositif(quantite, "la quantité");
    }

    private static void controlerUtilisateur(String nom, String prenom, String role) {
        verifierTexte(nom, "le nom");
        verifierTexte(prenom, "le prénom");
        verifierTexte(role, "le rôle");
    }

    private static void verifierCategorie(String categorie) {
        // « ni SALLE ni EQUIPEMENT » : les deux tests inversés sont reliés par && (il faut que les
        // deux soient faux). On écrit CONSTANTE.equals(categorie) et non categorie.equals(...) :
        // si categorie vaut null, equals renvoie simplement false au lieu de lever une exception.
        if (!Ressource.CATEGORIE_SALLE.equals(categorie) && !Ressource.CATEGORIE_EQUIPEMENT.equals(categorie)) {
            throw new IllegalArgumentException("catégorie incorrecte : \"" + categorie
                    + "\" (SALLE ou EQUIPEMENT attendu).");
        }
    }

    /** L'heure de début doit être strictement antérieure à l'heure de fin. */
    private static void verifierHeures(LocalTime debut, LocalTime fin) {
        if (debut == null || fin == null) {
            throw new IllegalArgumentException("les heures de début et de fin sont obligatoires.");
        }
        // !debut.isBefore(fin) : le début n'est PAS avant la fin, c'est-à-dire début égal ou après
        // la fin (11h-11h ou 12h-10h) : créneau impossible.
        if (!debut.isBefore(fin)) {
            throw new IllegalArgumentException("l'heure de début doit être antérieure à l'heure de fin.");
        }
    }

    /** Créneau valide pour une NOUVELLE réservation : heures cohérentes et date qui n'est pas dans le passé. */
    private static void verifierCreneau(LocalDate date, LocalTime debut, LocalTime fin) {
        if (date == null) {
            throw new IllegalArgumentException("la date est obligatoire.");
        }
        verifierHeures(debut, fin);
        // LocalDate.now() : la date du jour. Seule la DATE est comparée : aujourd'hui est accepté.
        // REMARQUE : une réservation pour aujourd'hui à une heure déjà passée est donc acceptée ;
        // c'est un choix (règle « pas dans le passé » appliquée au jour), pas un oubli.
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("la date ne peut pas être dans le passé.");
        }
    }

    /**
     * Contrôle d'une réservation lue dans le fichier. Mêmes règles qu'une nouvelle réservation
     * SAUF la date : une réservation passée a le droit d'exister (elle était valide quand elle a été faite).
     */
    private void controlerReservationChargee(Reservation r)
            throws RessourceIntrouvableException, ConflitReservationException {
        verifierCategorie(r.getCategorie());
        Ressource ressource = trouverRessource(r.getIdRessource(), r.getCategorie());
        trouverUtilisateur(r.getIdUtilisateur());
        verifierHeures(r.getHeureDebut(), r.getHeureFin());
        verifierDisponibilite(ressource, r.getDate(), r.getHeureDebut(), r.getHeureFin(), null);
    }

    // =====================================================================
    //  Identifiants
    // =====================================================================

    /** Un identifiant est une lettre suivie d'un nombre sans zéro au début (ex : S12). */
    private static boolean identifiantValide(String id, char lettre) {
        /*
         * Cinq raisons de refuser, reliées par || (une seule suffit), dans un ordre qui protège les
         * suivantes : null d'abord ; puis la longueur (au moins 2 caractères, sinon charAt(1)
         * n'existerait pas ; au plus 10, pour que le nombre tienne dans un int) ; puis la première
         * lettre ; puis le premier chiffre (« S01 » refusé, pour qu'un numéro ait une seule écriture).
         */
        if (id == null || id.length() < 2 || id.length() > 10 || id.charAt(0) != lettre || id.charAt(1) == '0') {
            return false;
        }
        /*
         * Boucle for à partir de l'indice 1 (l'indice 0 est la lettre) jusqu'au dernier caractère :
         * chacun doit être un chiffre. Dès qu'un caractère n'en est pas un (avant '0' OU après '9'),
         * return false arrête la vérification. Si la boucle va au bout, l'identifiant est valide.
         */
        for (int i = 1; i < id.length(); i++) {
            if (id.charAt(i) < '0' || id.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Contrôle un identifiant lu dans un fichier : bonne forme et pas de doublon.
     *
     * @param id          l'identifiant lu
     * @param lettre      la lettre attendue (S, E, U ou R)
     * @param dejaUtilise true si un élément chargé avant porte déjà cet identifiant
     */
    private static void verifierIdentifiant(String id, char lettre, boolean dejaUtilise) {
        if (!identifiantValide(id, lettre)) {
            throw new IllegalArgumentException("identifiant invalide (une lettre " + lettre
                    + " suivie d'un nombre est attendue, par exemple " + lettre + "1).");
        }
        if (dejaUtilise) {
            throw new IllegalArgumentException("cet identifiant est déjà utilisé (doublon).");
        }
    }

    /** Le nombre contenu dans un identifiant valide : "S12" donne 12. */
    private static int numero(String id) {
        // substring(1) : tout sauf la première lettre (« 12 »), puis conversion en int. Appelée
        // seulement après identifiantValide : parseInt ne peut donc pas échouer.
        return Integer.parseInt(id.substring(1));
    }
}
