package model;

/**
 * Un équipement réservable (vidéoprojecteur, PC portable, caméra...).
 * Il hérite de Ressource (id, nom) et ajoute son type et le nombre d'exemplaires possédés.
 *
 * Chaque réservation d'un équipement correspond à UNE unité.
 *
 * Pourquoi extends Ressource : un équipement EST une ressource réservable, comme une salle.
 * La différence importante : une salle ne peut être réservée qu'une fois sur un créneau, alors
 * qu'un équipement peut l'être autant de fois qu'il y a d'unités (quantiteDisponible).
 */
public class Equipement extends Ressource {

    // String pour le type (texte libre, rangé en majuscules : VIDEOPROJECTEUR...).
    // int pour la quantité : un nombre entier d'exemplaires.
    private String type;
    private int quantiteDisponible;

    /**
     * Crée un équipement.
     *
     * @param id                 l'identifiant (E1, E2...)
     * @param nom                le nom de l'équipement
     * @param type               son type (VIDEOPROJECTEUR, PC PORTABLE...)
     * @param quantiteDisponible le nombre d'exemplaires possédés
     */
    public Equipement(String id, String nom, String type, int quantiteDisponible) {
        super(id, nom);   // la classe mère Ressource enregistre l'identifiant et le nom
        this.type = type;
        this.quantiteDisponible = quantiteDisponible;
    }

    /**
     * Redéfinition de la méthode abstraite de Ressource : un équipement est de catégorie EQUIPEMENT.
     *
     * @return la constante CATEGORIE_EQUIPEMENT
     */
    @Override
    public String getCategorie() {
        return CATEGORIE_EQUIPEMENT;
    }

    /**
     * @return le type de l'équipement
     */
    public String getType() {
        return type;
    }

    /**
     * @param type le nouveau type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return le nombre d'exemplaires possédés (le stock total, pas ce qui reste libre)
     */
    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }

    /**
     * @param quantiteDisponible le nouveau nombre d'exemplaires (GestionReservations refuse une
     *                           quantité inférieure aux unités déjà réservées en même temps)
     */
    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    /**
     * Texte complet de l'équipement ; super.toString() réutilise le début écrit dans Ressource.
     *
     * @return la description de l'équipement
     */
    @Override
    public String toString() {
        return super.toString() + " (type : " + type + ", quantité : " + quantiteDisponible + ")";
    }
}
