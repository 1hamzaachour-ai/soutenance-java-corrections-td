package corrections.td3.ex2;

// Vector : liste d'objets dont la taille augmente au fur et à mesure (cours p.187-188),
// demandée par l'énoncé pour ranger les employés d'un service.
import java.util.Vector;

// =====================================================================
//  CLASSE Service  -  Série 3, Exercice 2, question 1
//  Rôle : un service (nom, téléphone), son chef et tous ses employés,
//  rangés dans un Vector. Chaque affectation met aussi à jour le service
//  de l'employé : les deux côtés du lien restent d'accord.
// =====================================================================

/**
 * Un service de l'entreprise : son nom, son téléphone, son chef et ses employés.
 *
 * Pourquoi un Vector : le nombre d'employés d'un service change (on en affecte, on en retire).
 * Un tableau a une taille fixe ; un Vector s'agrandit et se réduit tout seul, avec des méthodes
 * prêtes à l'emploi (addElement, remove, contains, size, elementAt).
 *
 * Responsabilités : gérer la liste des employés (affecter, éliminer), le chef, et garder le lien
 * cohérent dans les deux sens : quand un employé entre ou sort, son attribut service est mis à
 * jour en même temps (e.setService(...)).
 */
public class Service {

    // =================================================================
    //  ATTRIBUTS (private : encapsulation)
    // =================================================================
    // int pour le numéro de téléphone (8 chiffres, comme l'impose l'énoncé).
    // chef est une référence vers un des employés du service (ou null).
    // Vector<Employe> : le <Employe> indique que le vecteur ne contient que des Employe ; on peut
    // donc lire un élément sans cast (comme ArrayList<Employe> dans le cours, p.197).
    private String nom;
    private int numTel;
    private Employe chef;                       // null tant qu'aucun chef n'est nommé
    private Vector<Employe> employes;           // tous les employés du service, chef compris

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Crée le service, sans chef et sans employé.
     *
     * @param nom    le nom du service
     * @param numTel son numéro de téléphone
     */
    public Service(String nom, int numTel) {
        this.nom = nom;
        this.numTel = numTel;
        // Le vecteur doit être créé (new) : sans cela, employes vaudrait null et le premier
        // addElement lèverait une NullPointerException.
        employes = new Vector<Employe>();       // vecteur dynamique : sa taille augmente à chaque ajout
    }

    // =================================================================
    //  ACCESSEURS ET MODIFICATEURS
    // =================================================================

    /**
     * @return le nom du service
     */
    public String getNom() {
        return nom;
    }

    /**
     * Nombre d'employés du service, chef compris.
     *
     * @return la taille du vecteur (size() pour un Vector, comme pour une ArrayList)
     */
    public int getNombreEmploye() {
        return employes.size();
    }

    /**
     * Change le numéro de téléphone du service.
     *
     * @param numTel le nouveau numéro
     */
    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    /**
     * @return le numéro de téléphone du service
     */
    public int getNumTel() {
        return numTel;
    }

    /**
     * @return le chef du service, ou null s'il n'y en a pas
     */
    public Employe getChef() {
        return chef;
    }

    /**
     * Nomme e chef du service ; s'il n'y travaille pas encore, il y est d'abord affecté.
     *
     * Ainsi, le chef fait toujours partie des employés du service (règle de cohérence).
     *
     * @param e l'employé nommé chef
     */
    public void setChef(Employe e) {
        // contains cherche e dans le vecteur (en comparant avec equals ; Employe ne le redéfinit
        // pas, donc ce sont les références qui sont comparées). ! inverse : « e n'y est pas ».
        // Vrai : on l'affecte d'abord. Faux : il y est déjà, rien à faire avant de le nommer chef.
        if (!employes.contains(e)) {
            affecterEmploye(e);
        }
        chef = e;
    }

    // =================================================================
    //  AFFECTATION ET ÉLIMINATION DES EMPLOYÉS
    // =================================================================

    /**
     * Ajoute e aux employés du service (une seule fois) et lui indique son nouveau service.
     *
     * @param e l'employé à affecter
     */
    public void affecterEmploye(Employe e) {
        // Ce test empêche d'ajouter deux fois le même employé (il serait compté deux fois et
        // cité deux fois par toString).
        if (!employes.contains(e)) {
            employes.addElement(e);
        }
        // On met à jour l'autre côté du lien : l'employé sait maintenant dans quel service il est.
        e.setService(this);                     // this : le service courant
    }
    // REMARQUE : affecterEmploye ne retire pas l'employé de son ANCIEN service. Pour le déplacer,
    // il faut d'abord appeler eliminerEmploye sur l'ancien service, comme le fait Test lors de
    // l'échange de E2 et E4. Sinon il apparaîtrait dans les deux vecteurs.

    /**
     * Retire e du service ; s'il en était le chef, le service n'a plus de chef.
     *
     * @param e l'employé à retirer
     */
    public void eliminerEmploye(Employe e) {
        /*
         * remove(e) retire e du vecteur et renvoie true s'il y était, false sinon. On l'utilise
         * directement comme condition : les mises à jour ne sont faites que si e travaillait
         * vraiment dans ce service (sinon on effacerait le service d'un employé d'un autre service).
         */
        if (employes.remove(e)) {               // remove renvoie true si e était dans le vecteur
            e.setService(null);                 // l'employé n'a plus de service
            // == compare les références : e est-il exactement l'objet chef ?
            // Si oui, le service perd son chef : on remet chef à null.
            if (chef == e) {
                chef = null;
            }
        }
    }

    // =================================================================
    //  REDÉFINITION DE toString() (méthode héritée de la classe Object)
    // =================================================================

    /**
     * Description : nom, téléphone, nom du chef, puis noms des autres employés (le chef n'est cité qu'une fois).
     *
     * @return la description du service, sur trois lignes
     */
    public String toString() {
        // --- 1. Nom, téléphone et chef ---
        // StringBuffer : le texte est construit morceau par morceau avec append.
        StringBuffer sb = new StringBuffer();
        sb.append("Service " + nom + " (tél. " + numTel + ")\n");
        // chef == null ? "aucun" : chef.getNom() : on ne peut pas appeler getNom() sur null
        // (NullPointerException) ; l'opérateur conditionnel traite ce cas.
        sb.append("  chef     : " + (chef == null ? "aucun" : chef.getNom()) + "\n");
        sb.append("  employés : ");

        // --- 2. Les autres employés : on saute le chef, déjà cité ---
        int cites = 0;      // nombre de noms déjà écrits, pour savoir où mettre les virgules
        /*
         * Boucle for avec un indice sur le Vector : elementAt(i) lit l'élément d'indice i,
         * de 0 à size() - 1. Le nombre de tours est connu (size()).
         */
        for (int i = 0; i < employes.size(); i++) {
            Employe e = employes.elementAt(i);
            // e != chef : on n'écrit pas le chef une deuxième fois (il est sur la ligne « chef »).
            // != compare les références (est-ce un autre objet que le chef ?).
            if (e != chef) {
                // Une virgule avant chaque nom sauf le premier : pas de virgule en trop.
                if (cites > 0) {
                    sb.append(", ");
                }
                sb.append(e.getNom());
                cites++;
            }
        }
        // Aucun nom écrit : le service n'a que son chef, ou personne.
        if (cites == 0) {
            sb.append("aucun autre employé");
        }
        return sb.toString();
    }
}
