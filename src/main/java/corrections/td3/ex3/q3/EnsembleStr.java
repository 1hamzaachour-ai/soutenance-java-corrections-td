package corrections.td3.ex3.q3;

// ArrayList : tableau dynamique de la bibliothèque Java (cours p.191-199).
import java.util.ArrayList;

// =====================================================================
//  CLASSE EnsembleStr (ArrayList)  -  Série 3, Exercice 3, question 3
//  Rôle : le même ensemble de chaînes, rangé cette fois dans une ArrayList,
//  un tableau dynamique : plus de taille maximale ni de compteur à gérer.
//  Les méthodes publiques sont exactement les mêmes qu'à la question 1.
// =====================================================================

/**
 * Le même ensemble de chaînes, représenté cette fois par une ArrayList (question 3).
 *
 * Ce qui change par rapport à la question 1 : l'ArrayList s'agrandit toute seule (plus de
 * CAPACITE), connaît son propre nombre d'éléments (size(), plus de compteur nb) et fournit des
 * méthodes toutes prêtes (indexOf, add, remove, clear).
 * Ce qui ne change pas : les méthodes publiques, avec les mêmes noms et les mêmes paramètres.
 * Le programme de test est donc identique à celui de la question 1 : c'est l'intérêt de
 * l'encapsulation, on peut changer la représentation interne sans toucher aux programmes qui
 * utilisent la classe.
 */
public class EnsembleStr {

    // =================================================================
    //  ATTRIBUT
    // =================================================================
    // ArrayList<String> : liste qui ne contient que des String (on lit donc sans cast).
    // private : les autres classes ne savent même pas qu'il s'agit d'une ArrayList.
    private ArrayList<String> elements;         // sa taille suit le nombre de chaînes

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /** Constructeur non paramétré : un ensemble vide. */
    public EnsembleStr() {
        // Sans ce new, elements vaudrait null et le premier add lèverait une NullPointerException.
        elements = new ArrayList<String>();
    }

    // =================================================================
    //  MÉTHODES
    // =================================================================

    /** Vide l'ensemble avec clear() (méthode d'ArrayList qui retire tous les éléments). */
    public void vider() {
        elements.clear();
    }

    /** Affiche l'ensemble entre accolades, par exemple { java, c, python }. */
    public void afficher() {
        // --- 1. Ensemble vide ---
        // size() == 0 : la liste ne contient aucun élément (isEmpty() n'est pas au programme).
        if (elements.size() == 0) {
            System.out.println("{ }");
            return;
        }

        // --- 2. Les éléments séparés par des virgules ---
        StringBuffer sb = new StringBuffer("{ ");
        /*
         * Boucle for avec un indice de 0 à size() - 1 : get(i) lit l'élément d'indice i.
         * Ici size() suffit comme borne : une ArrayList ne contient que ses vrais éléments.
         */
        for (int i = 0; i < elements.size(); i++) {
            if (i > 0) {
                sb.append(", ");            // pas de virgule avant le premier élément
            }
            sb.append(elements.get(i));
        }
        sb.append(" }");
        System.out.println(sb.toString());
    }

    /**
     * Position de ch dans l'ensemble si elle y appartient, -1 sinon : c'est exactement indexOf.
     *
     * Plus besoin d'écrire la boucle de recherche de la question 1 : indexOf fait le même parcours.
     *
     * @param ch la chaîne cherchée
     * @return son indice dans la liste, ou -1
     */
    public int appartient(String ch) {
        return elements.indexOf(ch);            // indexOf compare avec equals
    }

    /**
     * Ajoute ch si elle n'y est pas encore ; renvoie true si ch a été ajoutée.
     *
     * Plus de test « tableau plein » : une ArrayList n'a pas de taille maximale.
     *
     * @param ch la chaîne à ajouter
     * @return true si l'ajout a eu lieu, false si ch était déjà présente
     */
    public boolean ajouter(String ch) {
        // Pas de doublon dans un ensemble : != -1 veut dire « déjà présente ».
        if (appartient(ch) != -1) {
            return false;
        }
        elements.add(ch);                       // la liste s'agrandit toute seule
        return true;
    }

    /**
     * Retire ch si elle est présente ; renvoie true si ch a été retirée.
     *
     * @param ch la chaîne à retirer
     * @return true si le retrait a eu lieu, false si ch était absente
     */
    public boolean retirer(String ch) {
        int pos = appartient(ch);
        if (pos == -1) {
            return false;
        }
        // remove(int) décale lui-même les éléments suivants : la liste reste sans trou.
        elements.remove(pos);                   // remove(int) : supprime l'élément d'indice pos
        return true;
    }

    /**
     * Égalité avec e : même nombre d'éléments, et chaque élément de l'ensemble courant appartient à e.
     * Même raisonnement qu'à la question 1, avec size() au lieu du compteur nb.
     *
     * @param e l'autre ensemble
     * @return true si les deux ensembles contiennent les mêmes chaînes
     */
    public boolean egalite(EnsembleStr e) {
        // --- 1. Tailles différentes : les ensembles sont différents ---
        // e.elements est accessible : private protège la classe, et nous sommes dans EnsembleStr.
        if (elements.size() != e.elements.size()) {
            return false;
        }

        // --- 2. Chaque élément doit appartenir à e ---
        /*
         * Boucle for sur tous les éléments ; on renvoie false dès qu'un élément manque dans e.
         * Si la boucle arrive au bout, tous ont été trouvés : les ensembles sont égaux.
         */
        for (int i = 0; i < elements.size(); i++) {
            if (e.appartient(elements.get(i)) == -1) {
                return false;
            }
        }
        return true;
    }
}
