package exceptions;

/**
 * Exception personnalisée : l'élément recherché n'existe pas.
 *
 * Elle est lancée quand un identifiant ne correspond à aucune salle, aucun équipement,
 * aucun utilisateur ou aucune réservation (on prend "ressource" au sens large :
 * tout ce qui est retrouvé grâce à son identifiant).
 *
 * Pourquoi une exception : les méthodes de recherche (trouverSalle, trouverUtilisateur...) doivent
 * renvoyer un objet. Si l'identifiant est inconnu, renvoyer null obligerait chaque appelant à
 * tester null (et un oubli provoquerait une NullPointerException plus loin). Avec cette exception,
 * l'appelant est obligé de traiter le cas, et le message dit précisément ce qui manque.
 */
public class RessourceIntrouvableException extends Exception {

    // Numéro de version : les exceptions sont "Serializable", Java demande donc de le préciser
    private static final long serialVersionUID = 1L;

    /**
     * Crée l'exception avec un message qui nomme l'identifiant introuvable.
     *
     * @param message l'explication (par exemple « aucune salle n'a l'identifiant S9 »)
     */
    public RessourceIntrouvableException(String message) {
        super(message);
    }
}
