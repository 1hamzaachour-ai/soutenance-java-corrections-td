package corrections.td5.ex2;

// =====================================================================
//  CLASSE NombreNegatifException  -  Série 5, Exercice 2
//  Rôle : exception définie par le programmeur, levée par TestFact quand
//  le paramètre est négatif. Elle n'a besoin d'aucun contenu : son type
//  suffit pour que le bon catch la reconnaisse (cours p.248-250).
// =====================================================================

/**
 * Exception levée par TestFact quand le paramètre est négatif.
 *
 * Pourquoi une classe vide suffit : ce qui compte, c'est son TYPE. Le bloc
 * catch (NombreNegatifException e) de TestFact ne s'exécute que pour cette exception-là, et
 * affiche lui-même le message « Le paramètre est négatif ». Elle hérite de Exception tout ce
 * dont une exception a besoin.
 *
 * REMARQUE (préexistant, signalé par javac -Xlint) : pas de serialVersionUID ; ce n'est qu'un
 * avertissement, sans effet sur le programme (notion hors du cours).
 */
public class NombreNegatifException extends Exception {
}
