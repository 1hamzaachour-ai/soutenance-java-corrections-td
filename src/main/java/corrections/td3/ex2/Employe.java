package corrections.td3.ex2;

// =====================================================================
//  CLASSE Employe  -  Série 3, Exercice 2, question 1
//  Rôle : un employé (nom, grade, salaire) et le service auquel il est
//  affecté. Le nom est fixé à la création : il n'y a pas de setNom().
// =====================================================================

/**
 * Un employé de l'entreprise, relié au service où il travaille.
 *
 * Pourquoi cette classe existe : l'énoncé décrit deux sortes d'objets liés entre eux, les employés
 * et les services. Chaque employé connaît son service (attribut service) et chaque service connaît
 * ses employés (un Vector dans Service) : c'est une association dans les deux sens.
 *
 * Responsabilités : garder les informations de l'employé, avec les accesseurs et modificateurs
 * demandés par l'énoncé, et se décrire par toString().
 */
public class Employe {

    // =================================================================
    //  ATTRIBUTS (private : encapsulation)
    // =================================================================
    // String pour le nom et le grade (des textes) ; float pour le salaire (montant avec décimales,
    // type imposé par l'énoncé) ; service est une RÉFÉRENCE vers un objet Service, pas une copie :
    // si le service change (nouveau chef...), l'employé voit le changement.
    private String nom;
    private String grade;
    private float salaire;
    private Service service;                    // null tant que l'employé n'est affecté à aucun service

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Crée l'employé ; il n'appartient encore à aucun service.
     *
     * L'attribut service n'est pas initialisé ici : un attribut objet non initialisé vaut null.
     * C'est Service.affecterEmploye() qui le remplira.
     *
     * @param nom     le nom de l'employé
     * @param grade   son grade
     * @param salaire son salaire en dinars
     */
    public Employe(String nom, String grade, float salaire) {
        // this.x désigne l'attribut, x seul le paramètre de même nom.
        this.nom = nom;
        this.grade = grade;
        this.salaire = salaire;
    }

    // =================================================================
    //  ACCESSEURS ET MODIFICATEURS (ceux de l'énoncé)
    // =================================================================
    // Les attributs étant private, ces méthodes sont le seul accès possible depuis les autres
    // classes. Il n'y a pas de setNom() : le nom ne doit pas changer après la création.

    /**
     * @return le nom de l'employé
     */
    public String getNom() {
        return nom;
    }

    /**
     * Change le grade de l'employé (une promotion, par exemple).
     *
     * @param grade le nouveau grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * @return le grade de l'employé
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Indique le service de l'employé (appelée par les méthodes de la classe Service).
     *
     * C'est Service qui l'appelle quand il ajoute ou retire l'employé, pour que les deux côtés du
     * lien restent d'accord (l'employé dans le Vector du service ET le service dans l'employé).
     *
     * @param service le nouveau service, ou null pour « aucun service »
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * @return le service de l'employé, ou null s'il n'en a pas
     */
    public Service getService() {
        return service;
    }

    /**
     * Change le salaire de l'employé.
     *
     * @param salaire le nouveau salaire
     */
    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    /**
     * @return le salaire de l'employé
     */
    public float getSalaire() {
        return salaire;
    }

    // =================================================================
    //  REDÉFINITION DE toString() (méthode héritée de la classe Object)
    // =================================================================

    /**
     * Description de l'employé, sans citer son service (comme le demande l'énoncé).
     *
     * Redéfinir toString() permet d'écrire println(e) ou "..." + e : Java appelle cette méthode
     * automatiquement. Sans elle, on verrait un texte du type « Employe@1b6d3586 ».
     *
     * @return par exemple « Ali (Responsable, 2500.0 DT) »
     */
    public String toString() {
        return nom + " (" + grade + ", " + salaire + " DT)";
    }
}
