package model;

/**
 * Une personne qui peut réserver : enseignant, étudiant, etc.
 *
 * Pourquoi une classe à part (et non une sous-classe de Ressource) : un utilisateur n'est pas une
 * chose réservable, c'est celui qui réserve. Il n'a donc rien en commun avec Salle et Equipement
 * à part le fait d'avoir un identifiant.
 *
 * Responsabilités : garder l'identité de la personne et la présenter (nom complet, toString).
 */
public class Utilisateur {

    // Quatre textes (String). private : on y accède par les getters et setters.
    // Le rôle est du texte libre (ENSEIGNANT, ETUDIANT...), rangé en majuscules par GestionReservations.
    private String id;
    private String nom;
    private String prenom;
    private String role;

    /**
     * Crée un utilisateur.
     *
     * @param id     l'identifiant (U1, U2...)
     * @param nom    le nom de famille
     * @param prenom le prénom
     * @param role   le rôle (ENSEIGNANT, ETUDIANT...)
     */
    public Utilisateur(String id, String nom, String prenom, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    /**
     * @return l'identifiant de l'utilisateur
     */
    public String getId() {
        return id;
    }

    /**
     * @param id le nouvel identifiant
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return le nom de famille
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom le nouveau nom de famille
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return le prénom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom le nouveau prénom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return le rôle de l'utilisateur
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role le nouveau rôle
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Le nom complet, dans l'ordre habituel : "Sara Benali".
     *
     * Méthode à part car le nom complet est affiché à plusieurs endroits (plannings, récapitulatif
     * d'une réservation) : il n'est construit qu'ici.
     *
     * @return le prénom suivi du nom
     */
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    /**
     * Texte de l'utilisateur, par exemple « U1 - Sara Benali (ENSEIGNANT) ».
     * @Override : javac vérifie qu'on redéfinit bien toString() de Object.
     *
     * @return la description de l'utilisateur
     */
    @Override
    public String toString() {
        return id + " - " + getNomComplet() + " (" + role + ")";
    }
}
