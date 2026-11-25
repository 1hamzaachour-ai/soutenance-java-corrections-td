package corrections.td5.ex2;

// =====================================================================
//  CLASSE TestFact  -  Série 5, Exercice 2
//  Rôle : calculer la factorielle de l'entier passé sur la ligne de
//  commande, en signalant précisément chaque erreur : aucun paramètre,
//  paramètre qui n'est pas un entier, paramètre négatif.
// =====================================================================

/**
 * Le programme TestFact de l'énoncé, amélioré avec des exceptions (correction officielle).
 *
 * Idée : toutes les instructions « normales » sont dans un seul bloc try ; chaque problème possible
 * produit une exception d'un type différent, et un catch par type affiche le message adapté.
 * Le code normal reste ainsi lisible, sans être coupé par des tests d'erreur.
 */
public class TestFact {

    /**
     * Point d'entrée : calcul de n!, avec un catch par cas d'erreur (cours p.245).
     *
     * @param args un seul paramètre attendu : l'entier dont on veut la factorielle
     */
    public static void main(String[] args) {
        try {
            // --- 1. Lecture du paramètre (args[0] absent, ou pas un entier : exception) ---
            // Deux exceptions possibles sur cette ligne, levées par Java lui-même :
            // - si args est vide, args[0] n'existe pas : ArrayIndexOutOfBoundsException ;
            // - si le texte n'est pas un entier : Integer.parseInt lève NumberFormatException.
            int n = Integer.parseInt(args[0]);
            // Un nombre négatif est un entier valide pour Java : aucune exception n'est levée.
            // C'est nous qui décidons que c'est une erreur, et qui la levons avec throw.
            if (n < 0) {
                throw new NombreNegatifException();
            }

            // --- 2. Calcul et affichage de la factorielle ---
            // 0! vaut 1 par définition : cas traité à part (correction officielle).
            if (n == 0) {
                System.out.println(" La factorielle de " + n + " est : 1");
            } else {
                // f part de 1 (élément neutre de la multiplication) : f = 1 × 2 × ... × n.
                int f = 1;
                /*
                 * Boucle for : on connaît le nombre de multiplications (de 2 à n). i commence à 2,
                 * car multiplier par 1 ne change rien. i <= n (et non < n) : n doit être compris
                 * dans le produit ; avec < n, on calculerait (n - 1)!.
                 * Pour n = 1, la condition 2 <= 1 est fausse dès le départ : aucun tour, f = 1.
                 */
                for (int i = 2; i <= n; i++) {
                    f = f * i;
                }
                System.out.println(" La factorielle de " + n + " est : " + f);
            }
            // REMARQUE : f est un int (plus grand int : 2 147 483 647). Dès n = 13, n! dépasse cette
            // valeur : le calcul « déborde » SANS exception et le résultat affiché est faux (13!
            // donne 1932053504 au lieu de 6227020800). Avec un long, le calcul resterait juste
            // jusqu'à 20!. Le code de la correction officielle est conservé tel quel.
        } catch (ArrayIndexOutOfBoundsException e) {
            // --- 3. Aucun paramètre : args est vide, args[0] n'existe pas ---
            System.out.println(" Il n'y a pas de paramètre sur la ligne de commande");
        } catch (NumberFormatException e) {
            // --- 4. Le paramètre n'est pas un entier : parseInt a échoué ---
            System.out.println(" Le paramètre n'est pas un entier");
        } catch (NombreNegatifException e) {
            // --- 5. Le paramètre est négatif : l'exception a été levée par throw ---
            System.out.println(" Le paramètre est négatif");
        }
        // Une seule de ces trois branches s'exécute : Java choisit le premier catch dont le type
        // correspond à l'exception levée. Si aucune exception n'est levée, aucun catch ne s'exécute.
    }
}
