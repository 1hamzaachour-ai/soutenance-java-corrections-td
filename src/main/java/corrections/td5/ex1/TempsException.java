package corrections.td5.ex1;

// =====================================================================
//  CLASSE TempsException  -  Série 5, Exercice 1
//  Rôle : exception définie par le programmeur, levée par le constructeur
//  de Temps quand l'heure, les minutes ou les secondes ne forment pas un
//  temps valide (cours p.248-250).
// =====================================================================

/**
 * Exception levée par le constructeur de Temps quand le temps n'est pas valide.
 *
 * extends Exception : c'est une exception contrôlée. javac oblige donc le constructeur de Temps à
 * l'annoncer (throws TempsException) et main à la traiter (try / catch) : on ne peut pas oublier
 * le cas d'erreur.
 *
 * Le cours affiche le message d'une exception avec une méthode message() (exemple AEgalBException,
 * p.250). La correction officielle de la série redéfinit plutôt toString() : ainsi
 * System.out.println(e) affiche directement « Temps invalide ».
 *
 * REMARQUE (préexistant, signalé par javac -Xlint) : pas de serialVersionUID ; ce n'est qu'un
 * avertissement, sans effet sur le programme (notion hors du cours).
 */
public class TempsException extends Exception {

    /**
     * Redéfinition de toString() : le texte affiché par println(e), « Temps invalide ».
     *
     * Sans cette redéfinition, println(e) afficherait le toString() hérité, du type
     * « corrections.td5.ex1.TempsException ».
     *
     * @return le message de l'erreur
     */
    public String toString() {
        return "Temps invalide";
    }
}
