package corrections.td4.ex1;

// =====================================================================
//  CLASSE TestPointNom  -  Série 4, Exercice 1
//  Rôle : programme de test. Il affiche un Point, un PointNom, puis un
//  PointNom désigné par une référence de type Point.
// =====================================================================

/**
 * Programme de test de PointNom.
 *
 * Il montre trois cas : un Point (méthode de Point), un PointNom (méthode redéfinie), et un
 * PointNom désigné par une référence de type Point, qui exécute quand même la méthode de
 * PointNom : c'est la liaison dynamique.
 */
public class TestPointNom {

    /**
     * Point d'entrée : trois points et leurs affichages.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // --- 1. Un point simple : la méthode affiche() de Point ---
        // Chaque instruction est d'abord écrite à l'écran, puis exécutée : on voit le code et son effet.
        System.out.println("Point p = new Point(3, 5);  p.affiche();");
        Point p = new Point(3, 5);
        p.affiche();

        // --- 2. Un point nommé : la méthode affiche() redéfinie dans PointNom ---
        System.out.println();
        System.out.println("PointNom pn = new PointNom(2, 7, 'A');  pn.affiche();");
        // 'A' entre guillemets simples : un caractère (char), et non une chaîne (String).
        PointNom pn = new PointNom(2, 7, 'A');
        pn.affiche();

        // --- 3. Une référence de type Point vers un PointNom : liaison dynamique ---
        // Permis, car un PointNom est un Point (héritage). Le type de la variable (Point) décide
        // des méthodes qu'on a le droit d'appeler ; le type réel de l'objet (PointNom) décide de
        // la version exécutée : on voit donc « Point B » puis les coordonnées.
        System.out.println();
        System.out.println("Point q = new PointNom(-1, 4, 'B');  q.affiche();");
        Point q = new PointNom(-1, 4, 'B');
        q.affiche();
    }
}
