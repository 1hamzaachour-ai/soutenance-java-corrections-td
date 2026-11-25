package model;

/**
 * Classe abstraite : une ressource réservable est soit une Salle, soit un Equipement.
 *
 * Elle regroupe ce qui est commun aux deux (l'identifiant et le nom).
 * On ne peut pas écrire "new Ressource(...)" : il faut créer une Salle ou un Equipement.
 *
 * Pourquoi cette classe existe : une réservation peut porter sur une salle OU sur un équipement.
 * Grâce à cette classe mère commune, GestionReservations peut chercher « la ressource S1 ou E2 »,
 * afficher son nom ou connaître sa catégorie sans savoir s'il s'agit d'une salle ou d'un
 * équipement : c'est le polymorphisme. Le code commun (id, nom, toString) n'est écrit qu'une fois.
 *
 * Responsabilités : garder l'identifiant et le nom, et obliger chaque sous-classe à donner sa
 * catégorie (méthode abstraite getCategorie()).
 */
public abstract class Ressource {

    // Les deux catégories possibles (ce sont les valeurs écrites dans reservations.txt)
    // public static final : des constantes partagées par tout le programme. Écrire
    // Ressource.CATEGORIE_SALLE au lieu de "SALLE" à la main évite les fautes de frappe : une
    // erreur dans le nom de la constante est détectée par javac, pas une erreur dans un texte.
    public static final String CATEGORIE_SALLE = "SALLE";
    public static final String CATEGORIE_EQUIPEMENT = "EQUIPEMENT";

    // Attributs privés (encapsulation) : on y accède uniquement avec les getters et les setters
    // String pour l'identifiant (« S1 », « E2 » : des lettres et des chiffres) et pour le nom.
    private String id;
    private String nom;

    /**
     * Constructeur appelé par les sous-classes avec super(id, nom).
     *
     * Une classe abstraite peut avoir un constructeur : il initialise la partie commune de chaque
     * Salle ou Equipement créé.
     *
     * @param id  l'identifiant (S1, E2...)
     * @param nom le nom de la ressource
     */
    public Ressource(String id, String nom) {
        // this.id : l'attribut ; id : le paramètre de même nom.
        this.id = id;
        this.nom = nom;
    }

    /**
     * Chaque sous-classe donne sa catégorie : "SALLE" ou "EQUIPEMENT".
     *
     * Méthode abstraite (sans corps) : Salle et Equipement sont obligées de l'écrire. Elle permet
     * de savoir à quelle catégorie appartient une ressource sans tester sa classe.
     *
     * @return la catégorie de la ressource
     */
    public abstract String getCategorie();

    // Accesseurs (get) et modificateurs (set) : le seul moyen d'accéder aux attributs private.

    /**
     * @return l'identifiant de la ressource
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
     * @return le nom de la ressource
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom le nouveau nom (utilisé quand on modifie une salle ou un équipement)
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Texte de la ressource, par exemple « S1 - A101 ».
     *
     * @Override : annotation qui demande à javac de vérifier qu'on redéfinit bien une méthode de
     * la classe mère (ici toString() de Object). En cas de faute de frappe dans le nom, javac
     * refuserait de compiler au lieu de créer une nouvelle méthode sans rapport.
     *
     * @return l'identifiant et le nom
     */
    @Override
    public String toString() {
        return id + " - " + nom;
    }
}
