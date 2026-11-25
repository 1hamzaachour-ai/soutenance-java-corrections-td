package corrections.td3.ex1;

// =====================================================================
//  CLASSE PileVideException  -  Série 3, Exercice 1
//  Rôle : exception définie par le programmeur, levée par depiler() et
//  sommet() quand la pile ne contient aucun entier. Même principe que
//  l'exemple AEgalBException du cours (p.248-250).
// =====================================================================

/**
 * Exception levée quand on essaie de dépiler (ou de lire le sommet d') une pile vide.
 *
 * Même principe que PilePleineException : une classe à nous, fille de Exception, pour signaler
 * une erreur que Java ne connaît pas et la reconnaître par son type dans un catch.
 * Avoir deux classes différentes (pleine / vide) permet de traiter chaque cas à part.
 *
 * REMARQUE (préexistant, signalé par javac -Xlint) : pas de serialVersionUID, ce qui ne donne
 * qu'un avertissement (notion hors du cours, sans effet sur le programme).
 */
public class PileVideException extends Exception {

    /**
     * Message qui décrit l'erreur (méthode message(), comme dans l'exemple du cours p.250).
     *
     * @return le texte de l'erreur, affiché par le catch
     */
    public String message() {
        return "la pile est vide, impossible de dépiler ou de lire le sommet.";
    }
}
