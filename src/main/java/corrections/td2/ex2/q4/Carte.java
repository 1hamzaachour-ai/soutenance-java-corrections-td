package corrections.td2.ex2.q4;

// =====================================================================
//  CLASSE Carte (version encapsulée)  -  Série 2, Exercice 2, question 4
//  Rôle : la même carte, protégée contre la triche. Les attributs sont
//  private : on ne peut plus les modifier qu'à travers les méthodes de la
//  classe, qui refusent les valeurs négatives.
// =====================================================================

/**
 * Version encapsulée de la carte à collectionner (questions 4 à 7).
 *
 * Pourquoi cette nouvelle version : la question 3 a montré qu'avec des attributs non private, un
 * programme peut donner 9999 points ou une valeur négative à une carte sans passer par ses
 * méthodes. Ici les attributs sont private : toute modification passe par le constructeur ou par
 * modifier(), qui appliquent la même règle (pas de valeur négative). C'est l'encapsulation.
 *
 * Responsabilités : créer une carte valide (deux constructeurs), donner son nom et sa valeur
 * (accesseurs en lecture seule), se modifier sans tricher, se comparer à une autre carte, et
 * redéfinir toString() et equals() hérités de la classe Object.
 */
public class Carte {

    // =================================================================
    //  ATTRIBUTS (private : encapsulation)
    // =================================================================
    // private : TestCarte ne peut plus écrire c.valeur = 9999 (javac répondrait « valeur has
    // private access in Carte »). La seule façon de changer la valeur est modifier(), qui la
    // contrôle.
    private String nom;
    private int valeur;

    // =================================================================
    //  CONSTRUCTEURS (surcharge : deux constructeurs)
    // =================================================================
    // Deux constructeurs de même nom mais avec des paramètres différents : c'est la surcharge.
    // Java choisit le bon d'après les arguments : new Carte() ou new Carte("Dragon", 80).

    /** Constructeur sans paramètres : la carte « Inconnue » valant 10 points. */
    public Carte() {
        // this(...) appelle l'autre constructeur de la même classe. Avantage : la règle « pas de
        // valeur négative » n'est écrite qu'une fois, dans le constructeur paramétré.
        // this(...) doit être la première instruction du constructeur.
        this("Inconnue", 10);                   // appelle l'autre constructeur (première instruction)
    }

    /**
     * Constructeur paramétré : une valeur négative est remplacée par 0.
     *
     * @param nom    le nom de la carte
     * @param valeur sa valeur en points (ramenée à 0 si elle est négative)
     */
    public Carte(String nom, int valeur) {
        // Les paramètres ont le même nom que les attributs : this.nom désigne l'attribut,
        // nom seul désigne le paramètre. Sans « this. », l'attribut ne serait jamais rempli.
        this.nom = nom;
        // Opérateur conditionnel : si valeur < 0 est vrai, on range 0, sinon on range valeur.
        // < (strictement) : 0 est une valeur permise, seules les valeurs négatives sont corrigées.
        this.valeur = valeur < 0 ? 0 : valeur;
    }

    // =================================================================
    //  ACCESSEURS (lecture seule)
    // =================================================================
    // Des getters sans setters : on peut LIRE le nom et la valeur, mais pas les changer
    // librement ; la seule modification possible passe par modifier(), qui contrôle la valeur.

    /**
     * @return le nom de la carte
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return la valeur de la carte, en points
     */
    public int getValeur() {
        return valeur;
    }

    // =================================================================
    //  MÉTHODES
    // =================================================================

    /**
     * Affiche la carte sous la forme [Dragon de feu — 80 pts].
     * Elle réutilise toString() : le format de la carte n'est écrit qu'à un seul endroit.
     */
    public void afficher() {
        System.out.println(toString());
    }

    /**
     * Change le nom et la valeur, avec la même règle que le constructeur (pas de valeur négative).
     *
     * @param n le nouveau nom
     * @param v la nouvelle valeur (ramenée à 0 si elle est négative)
     */
    public void modifier(String n, int v) {
        nom = n;
        valeur = v < 0 ? 0 : v;
    }

    /**
     * Vrai si la carte courante vaut plus de points que c.
     *
     * c.valeur est permis même si valeur est private : private protège la CLASSE, pas l'objet.
     * Une méthode de Carte peut donc lire les attributs d'une autre Carte.
     *
     * @param c la carte à laquelle on compare la carte courante
     * @return true si cette carte a strictement plus de points que c
     */
    public boolean estPlusForteQue(Carte c) {
        // > strict : à égalité de points, la carte courante n'est PAS « plus forte ».
        return valeur > c.valeur;
    }

    // =================================================================
    //  REDÉFINITIONS DE MÉTHODES HÉRITÉES DE LA CLASSE Object
    // =================================================================
    // Toute classe hérite de Object. Sans redéfinition, toString() afficherait un texte du type
    // « corrections.td2.ex2.q4.Carte@1b6d3586 », et equals() comparerait les références comme ==.

    /**
     * Redéfinition de toString() de la classe Object : le texte de la carte.
     *
     * Java l'appelle automatiquement quand on écrit "c1 = " + c1 ou println(c1).
     *
     * @return la carte sous la forme [nom — valeur pts]
     */
    public String toString() {
        return "[" + nom + " — " + valeur + " pts]";
    }

    /**
     * Redéfinition de equals() de la classe Object : même nom et même valeur.
     *
     * Le paramètre est de type Object (et non Carte) : c'est la signature de la méthode de Object.
     * Avec equals(Carte c), on écrirait une NOUVELLE méthode (une surcharge) au lieu de redéfinir
     * celle de Object.
     *
     * @param o l'objet à comparer avec cette carte
     * @return true si o est une carte de même nom et de même valeur
     */
    public boolean equals(Object o) {
        // --- 1. Même référence : c'est le même objet ---
        // == compare les références : si this et o désignent le même objet, ils sont égaux.
        if (this == o) {
            return true;                        // même référence : c'est le même objet
        }

        // --- 2. o n'est pas une carte : elles ne peuvent pas être égales ---
        // instanceof vérifie le type réel de l'objet. Le ! inverse le test : on entre dans le if
        // quand o n'est PAS une Carte (ou quand o vaut null). Sans ce test, le cast de l'étape 3
        // lèverait une ClassCastException pour un objet d'un autre type.
        if (!(o instanceof Carte)) {
            return false;                       // o n'est pas une carte
        }

        // --- 3. Comparaison du contenu, après un cast vers Carte ---
        // Le cast est nécessaire : le type déclaré de o est Object, qui n'a pas d'attribut valeur.
        Carte c = (Carte) o;                    // cast : on sait maintenant que o est une Carte
        // && : les deux conditions doivent être vraies. valeur est un int, comparé avec == ;
        // nom est un String, comparé avec equals (== comparerait les adresses des chaînes).
        return valeur == c.valeur && nom.equals(c.nom);
    }
    // REMARQUE (préexistant, signalé par javac -Xlint) : quand on redéfinit equals, la règle Java
    // est de redéfinir aussi hashCode(), pour que deux cartes égales aient le même code. Ce n'est
    // pas utile dans cet exercice (aucune HashMap ni HashSet) et hashCode n'est pas au programme
    // du cours ; javac affiche seulement un avertissement.
    // REMARQUE : nom.equals(c.nom) lèverait une NullPointerException si nom valait null ; ici
    // nom ne vaut jamais null tant qu'on ne passe pas null au constructeur ou à modifier().
}
