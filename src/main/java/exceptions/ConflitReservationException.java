package exceptions;

/**
 * Exception personnalisée : la réservation demandée est impossible.
 *
 * Elle est lancée quand le créneau est déjà pris (cas d'une salle) ou quand toutes les
 * unités de l'équipement sont déjà réservées (disponibilité insuffisante).
 *
 * Pourquoi une exception (et pas un simple boolean) : GestionReservations, qui détecte le conflit,
 * ne fait aucun affichage. Elle lève cette exception avec un message précis (« la salle S1 est
 * occupée le ... de ... à ... »), et c'est Main qui l'attrape dans un catch et affiche le message.
 * extends Exception : exception contrôlée, javac oblige les appelants à la traiter ou à l'annoncer.
 */
public class ConflitReservationException extends Exception {

    // Numéro de version : les exceptions sont "Serializable", Java demande donc de le préciser
    // static final long : une constante de classe ; 1L est un littéral de type long (suffixe L).
    private static final long serialVersionUID = 1L;

    /**
     * Crée l'exception avec un message qui explique le conflit.
     *
     * super(message) transmet le texte au constructeur de Exception, qui le garde ; Main le
     * récupère ensuite avec e.getMessage().
     *
     * @param message l'explication du conflit
     */
    public ConflitReservationException(String message) {
        super(message);
    }
}
