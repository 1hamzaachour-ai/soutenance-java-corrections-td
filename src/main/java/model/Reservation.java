package model;

// LocalDate et LocalTime : les classes de Java (depuis Java 8) pour une date et une heure.
// Elles savent comparer deux dates, vérifier qu'une date existe, et s'écrire dans un format choisi.
import java.time.LocalDate;
import java.time.LocalTime;
// DateTimeFormatter : décrit un format d'écriture, par exemple « 07/12/2026 » ou « 09:00 ».
import java.time.format.DateTimeFormatter;

/**
 * Une réservation : un utilisateur réserve une ressource (salle ou équipement)
 * à une date, de heureDebut à heureFin.
 *
 * La réservation ne garde que l'identifiant de la ressource et celui de l'utilisateur
 * (comme dans le fichier reservations.txt) ; la catégorie vaut "SALLE" ou "EQUIPEMENT".
 *
 * Elle implémente Comparable pour pouvoir être triée dans l'ordre chronologique
 * (Collections.sort utilise la méthode compareTo).
 *
 * Pourquoi garder des identifiants plutôt que des références vers les objets : c'est exactement
 * ce qui est écrit dans le fichier, la sauvegarde et le chargement sont donc directs. Pour afficher
 * le nom de la salle ou de l'utilisateur, GestionReservations le retrouve à partir de l'identifiant.
 * La catégorie est gardée car S1 et E1 ne se trouvent pas dans la même liste.
 *
 * Pourquoi LocalDate et LocalTime plutôt que des String : on peut comparer deux dates ou deux
 * heures (isBefore, compareTo) ; « 9:00 » et « 10:00 » comparés comme du texte donneraient un
 * mauvais ordre (le caractère '9' est plus grand que '1').
 */
public class Reservation implements Comparable<Reservation> {

    // Formats d'AFFICHAGE à l'écran (le format des fichiers est géré par GestionFichiers)
    // public static final : constantes partagées, utilisées aussi par Main pour afficher une date.
    // dd/MM/yyyy : jour, mois (MM majuscules ; mm minuscules voudrait dire minutes), année.
    public static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMAT_HEURE = DateTimeFormatter.ofPattern("HH:mm");

    // Attributs privés (encapsulation) ; les identifiants sont des textes (R1, S1, U1...).
    private String id;
    private String idRessource;
    private String categorie;
    private String idUtilisateur;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    /**
     * Crée une réservation. Aucune vérification ici : les règles (date passée, conflit, ressource
     * existante...) sont contrôlées par GestionReservations AVANT de créer l'objet. Cette classe
     * reste ainsi un simple « conteneur » de données, facile à charger depuis le fichier.
     *
     * @param id            l'identifiant de la réservation (R1, R2...)
     * @param idRessource   l'identifiant de la salle ou de l'équipement
     * @param categorie     SALLE ou EQUIPEMENT
     * @param idUtilisateur l'identifiant de la personne qui réserve
     * @param date          le jour
     * @param heureDebut    l'heure de début
     * @param heureFin      l'heure de fin
     */
    public Reservation(String id, String idRessource, String categorie, String idUtilisateur,
                       LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        this.id = id;
        this.idRessource = idRessource;
        this.categorie = categorie;
        this.idUtilisateur = idUtilisateur;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    // Accesseurs (get) et modificateurs (set). Les set servent à la modification d'une
    // réservation (menu 5) : GestionReservations les appelle après avoir vérifié les nouvelles valeurs.

    /** @return l'identifiant de la réservation */
    public String getId() {
        return id;
    }

    /** @param id le nouvel identifiant */
    public void setId(String id) {
        this.id = id;
    }

    /** @return l'identifiant de la ressource réservée */
    public String getIdRessource() {
        return idRessource;
    }

    /** @param idRessource le nouvel identifiant de ressource */
    public void setIdRessource(String idRessource) {
        this.idRessource = idRessource;
    }

    /** @return SALLE ou EQUIPEMENT */
    public String getCategorie() {
        return categorie;
    }

    /** @param categorie la nouvelle catégorie */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /** @return l'identifiant de l'utilisateur */
    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    /** @param idUtilisateur le nouvel identifiant d'utilisateur */
    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    /** @return la date de la réservation */
    public LocalDate getDate() {
        return date;
    }

    /** @param date la nouvelle date */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /** @return l'heure de début */
    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    /** @param heureDebut la nouvelle heure de début */
    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    /** @return l'heure de fin */
    public LocalTime getHeureFin() {
        return heureFin;
    }

    /** @param heureFin la nouvelle heure de fin */
    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    // Les trois méthodes suivantes évitent de répéter date.format(FORMAT_DATE) partout dans Main.

    /**
     * La date au format de la console : 23/11/2026.
     *
     * @return la date écrite jj/mm/aaaa
     */
    public String getDateTexte() {
        return date.format(FORMAT_DATE);
    }

    /**
     * L'heure de début au format HH:mm.
     *
     * @return l'heure de début écrite hh:mm
     */
    public String getHeureDebutTexte() {
        return heureDebut.format(FORMAT_HEURE);
    }

    /**
     * L'heure de fin au format HH:mm.
     *
     * @return l'heure de fin écrite hh:mm
     */
    public String getHeureFinTexte() {
        return heureFin.format(FORMAT_HEURE);
    }

    /**
     * Ordre chronologique : d'abord la date, puis l'heure de début, puis l'heure de fin.
     * Si deux réservations ont exactement le même créneau, on les classe par identifiant
     * (le plus court d'abord, pour que R2 passe avant R10).
     *
     * Règle de compareTo : renvoyer un nombre négatif si cette réservation vient AVANT l'autre,
     * 0 si elles sont au même rang, un nombre positif si elle vient APRÈS. C'est ce que demande
     * l'interface Comparable, utilisée par Collections.sort pour trier les plannings.
     *
     * @param autre la réservation à comparer
     * @return négatif, 0 ou positif selon l'ordre
     */
    @Override
    public int compareTo(Reservation autre) {
        // On compare critère par critère : on ne passe au critère suivant que si le précédent est
        // à égalité (resultat == 0). Trois if successifs (et non else if), car chaque test dépend
        // du résultat du test précédent.
        int resultat = date.compareTo(autre.date);
        if (resultat == 0) {
            resultat = heureDebut.compareTo(autre.heureDebut);
        }
        if (resultat == 0) {
            resultat = heureFin.compareTo(autre.heureFin);
        }
        if (resultat == 0) {
            /*
             * Dernier critère : l'identifiant. Comparés comme du texte, « R10 » viendrait avant
             * « R2 » (le caractère '1' est avant '2'). On compare donc d'abord les longueurs :
             * un identifiant plus court a un numéro plus petit. À longueur égale, l'ordre du
             * texte donne le bon ordre (R2 avant R3, R10 avant R11).
             */
            if (id.length() != autre.id.length()) {
                resultat = id.length() - autre.id.length();
            } else {
                resultat = id.compareTo(autre.id);
            }
        }
        return resultat;
    }

    /**
     * Texte compact d'une réservation, sur une ligne (utile pour les messages et le débogage).
     *
     * @return par exemple « R1 | SALLE S1 | U1 | 07/12/2026 | 09:00 - 11:00 »
     */
    @Override
    public String toString() {
        return id + " | " + categorie + " " + idRessource + " | " + idUtilisateur + " | "
                + getDateTexte() + " | " + getHeureDebutTexte() + " - " + getHeureFinTexte();
    }
}
