package model;

/**
 * Une salle réservable. Elle hérite de Ressource (id, nom)
 * et ajoute ses attributs propres : la capacité et le bâtiment.
 *
 * Pourquoi extends Ressource : une salle EST une ressource réservable. Elle hérite de
 * l'identifiant, du nom et de leurs accesseurs, et n'ajoute que ce qui lui est propre.
 * Elle donne aussi sa catégorie (getCategorie), obligatoire car abstraite dans Ressource.
 */
public class Salle extends Ressource {

    // int : une capacité est un nombre entier de places. String : le bâtiment est un texte (« A »).
    // private : les autres classes passent par les getters et setters.
    private int capacite;
    private String batiment;

    /**
     * Crée une salle.
     *
     * @param id       l'identifiant (S1, S2...)
     * @param nom      le nom de la salle
     * @param capacite le nombre de places
     * @param batiment le bâtiment
     */
    public Salle(String id, String nom, int capacite, String batiment) {
        super(id, nom);   // la classe mère Ressource enregistre l'identifiant et le nom
        this.capacite = capacite;
        this.batiment = batiment;
    }

    /**
     * Redéfinition de la méthode abstraite de Ressource : une salle est de catégorie SALLE.
     *
     * @return la constante CATEGORIE_SALLE (héritée de Ressource, utilisable sans préfixe)
     */
    @Override
    public String getCategorie() {
        return CATEGORIE_SALLE;
    }

    /**
     * @return le nombre de places de la salle
     */
    public int getCapacite() {
        return capacite;
    }

    /**
     * @param capacite le nouveau nombre de places (la vérification > 0 est faite par GestionReservations)
     */
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    /**
     * @return le bâtiment de la salle
     */
    public String getBatiment() {
        return batiment;
    }

    /**
     * @param batiment le nouveau bâtiment
     */
    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    /**
     * Texte complet de la salle, par exemple « S1 - A101 (capacité : 30, bâtiment : A) ».
     * super.toString() réutilise le début écrit dans Ressource (« S1 - A101 ») au lieu de le recopier.
     *
     * @return la description de la salle
     */
    @Override
    public String toString() {
        return super.toString() + " (capacité : " + capacite + ", bâtiment : " + batiment + ")";
    }
}
