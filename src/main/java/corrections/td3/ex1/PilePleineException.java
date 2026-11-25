package corrections.td3.ex1;

// =====================================================================
//  CLASSE PilePleineException  -  Série 3, Exercice 1
//  Rôle : exception définie par le programmeur, levée par empiler()
//  quand toutes les cases de la pile sont occupées. Même principe que
//  l'exemple AEgalBException du cours (p.248-250).
// =====================================================================

/**
 * Exception levée quand on essaie d'empiler dans une pile pleine.
 *
 * Pourquoi une classe d'exception : Java n'a pas d'exception « pile pleine ». En écrivant la
 * nôtre, le programme peut la lever avec throw new PilePleineException() et la reconnaître par
 * son type dans un catch (catch (PilePleineException e)).
 *
 * extends Exception : c'est une exception « contrôlée ». javac oblige toute méthode qui peut la
 * lever à l'annoncer (throws) et tout appelant à la traiter (try / catch) ou à l'annoncer à son tour.
 *
 * REMARQUE (préexistant, signalé par javac -Xlint) : Exception est « Serializable », Java
 * conseille donc d'ajouter un attribut serialVersionUID. Ce n'est pas au programme du cours et
 * cela ne change rien au fonctionnement : javac affiche seulement un avertissement.
 */
public class PilePleineException extends Exception {

    /**
     * Message qui décrit l'erreur.
     * On écrit une méthode message(), comme l'exemple AEgalBException du cours (p.250).
     *
     * @return le texte de l'erreur, affiché par le catch
     */
    public String message() {
        return "la pile est pleine, impossible d'empiler.";
    }
}
