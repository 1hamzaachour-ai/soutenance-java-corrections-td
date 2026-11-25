package corrections.outils;

// =====================================================================
//  CLASSE ABSTRAITE Exercice
//  Rôle : modèle commun à tous les exercices du projet (TD1_Ex1, ...).
//  Chaque exercice a un titre, affiché dans les menus, un objectif,
//  affiché avant de le lancer, et une méthode executer() qui affiche
//  l'énoncé, exécute la correction et répond aux questions. executer()
//  est abstraite : chaque exercice écrit la sienne.
// =====================================================================

/**
 * Classe mère de tous les exercices des cinq séries (TD1_Ex1 à TD5_Ex2).
 *
 * Pourquoi cette classe existe : le menu de Main doit pouvoir afficher et lancer n'importe quel
 * exercice SANS connaître sa classe exacte. On regroupe donc ici ce que tous les exercices ont en
 * commun (un titre, un objectif, une méthode executer()) ; Main manipule seulement des références
 * de type Exercice (tableaux EXERCICES_TD1...), c'est le polymorphisme (cours, chapitre 6).
 *
 * Responsabilités :
 * - garder le titre et l'objectif de l'exercice (attributs private, lus avec des accesseurs) ;
 * - obliger chaque classe fille à écrire sa propre méthode executer() (méthode abstraite).
 *
 * Pourquoi « abstract » : un « exercice en général » n'a pas de contenu, on ne doit donc pas pouvoir
 * écrire new Exercice(...). Si on l'essayait, javac refuserait : « Exercice is abstract; cannot be
 * instantiated ». Seules les classes filles concrètes (TD1_Ex1...) créent des objets.
 */
public abstract class Exercice {

    // =================================================================
    //  ATTRIBUTS
    // =================================================================

    // private : seules les méthodes de cette classe peuvent lire ou modifier ces attributs
    // (encapsulation). Les autres classes passent par getTitre() et getObjectif().
    // final : la valeur est donnée une seule fois, dans le constructeur, puis ne change plus ;
    // un titre ou un objectif n'a aucune raison d'être modifié pendant l'exécution.
    // String : un titre et un objectif sont des textes.

    /** Titre de l'exercice, affiché dans le menu de la série. */
    private final String titre;

    /** Objectif de l'exercice, affiché juste avant son exécution. */
    private final String objectif;

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Initialise le titre et l'objectif de l'exercice.
     *
     * Il n'est jamais appelé directement : chaque classe fille l'appelle en première ligne de son
     * propre constructeur avec super("titre", "objectif") (cours p.209 : super(...) doit être la
     * première instruction).
     *
     * Pourquoi protected : un constructeur public ne servirait à rien (la classe est abstraite) ;
     * protected le réserve aux classes filles, ce qui exprime exactement son usage.
     *
     * @param titre    texte affiché dans le menu de la série (par exemple « Pile d'entiers »)
     * @param objectif phrase affichée avant l'exécution (« Objectif de l'exercice n : ... »)
     */
    protected Exercice(String titre, String objectif) {
        // this.titre désigne l'attribut de l'objet ; titre seul désigne le paramètre.
        // Sans « this. », on recopierait le paramètre dans lui-même et l'attribut resterait null.
        this.titre = titre;
        this.objectif = objectif;
    }

    // =================================================================
    //  MÉTHODES
    // =================================================================

    /**
     * Accesseur du titre : seul moyen, pour les autres classes, de lire l'attribut private titre.
     * Main l'appelle pour afficher la liste des exercices d'une série.
     *
     * @return le titre de l'exercice
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Accesseur de l'objectif : Main l'affiche juste avant de lancer l'exercice.
     * Il n'y a pas de setObjectif() : l'attribut est final et ne doit pas changer.
     *
     * @return l'objectif de l'exercice
     */
    public String getObjectif() {
        return objectif;
    }

    /**
     * Affiche l'énoncé puis la correction complète de l'exercice (écrite dans chaque classe fille).
     *
     * Méthode abstraite : pas de corps ici, seulement la signature. Chaque classe fille est
     * obligée de la redéfinir, sinon javac refuse de compiler (« ... is not abstract and does not
     * override abstract method executer() »). Quand Main écrit exercice.executer(), Java choisit
     * à l'exécution la version de la classe réelle de l'objet : c'est la liaison dynamique
     * (cours p.218).
     */
    public abstract void executer();
}
