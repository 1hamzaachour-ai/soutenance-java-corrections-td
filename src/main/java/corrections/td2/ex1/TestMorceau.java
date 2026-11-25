package corrections.td2.ex1;

import corrections.outils.Clavier;

// =====================================================================
//  CLASSE TestMorceau  -  Série 2, Exercice 1, question 2
//  Rôle : programme de test. Il crée un morceau M, le modifie avec les
//  données tapées par l'utilisateur, puis l'affiche avec sa durée.
// =====================================================================

/**
 * Programme de test de la classe Morceau (question 2 de l'énoncé).
 *
 * Pourquoi une classe séparée : la classe Morceau décrit un morceau ; le programme qui
 * l'essaie est un autre rôle. On le sépare pour que Morceau reste réutilisable dans d'autres
 * programmes, sans ce test à l'intérieur.
 */
public class TestMorceau {

    /**
     * Point d'entrée : création, saisie, modification puis affichage du morceau M.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // --- 1. Initialisation : création de l'objet avec le constructeur par défaut ---
        // new Morceau() réserve un nouvel objet en mémoire et appelle le constructeur par défaut ;
        // M est une référence vers cet objet (son « adresse »), pas l'objet lui-même.
        Morceau M = new Morceau();              // la référence s'appelle M, comme dans l'énoncé

        // --- 2. Saisie utilisateur ---
        // On passe par Clavier : une saisie invalide (texte vide, lettres au lieu d'un nombre,
        // secondes hors de 0 à 59) est redemandée au lieu de faire planter le programme.
        // Bornes des minutes : 0 à 999, assez large pour n'importe quel morceau.
        String titre = Clavier.lireTexte("Titre du morceau : ");
        int minutes = Clavier.lireEntier("Minutes : ", 0, 999);
        int secondes = Clavier.lireEntier("Secondes (0 à 59) : ", 0, 59);

        // --- 3. Modification du morceau avec les méthodes set ---
        // Les attributs de Morceau sont private : écrire M.titre = titre ne compilerait pas.
        // On passe donc par les méthodes set, comme le demande l'énoncé.
        M.setTitre(titre);
        M.setMinutes(minutes);
        M.setSecondes(secondes);

        // --- 4. Affichage (avec le calcul de la durée) ---
        System.out.println();
        M.afficherMorceau();
        // dureeEnSecondes() renvoie un int, que + convertit en texte pour l'affichage.
        System.out.println("Durée en secondes : " + M.dureeEnSecondes());
    }
}
