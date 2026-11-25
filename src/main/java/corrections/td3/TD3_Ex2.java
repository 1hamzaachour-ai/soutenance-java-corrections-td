package corrections.td3;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;
import corrections.td3.ex2.Test;

// =====================================================================
//  CLASSE TD3_Ex2  -  Série 3, Exercice 2
//  Rôle : présenter les employés et les services : énoncé, diagrammes
//  UML, exécution de la classe Test et explications. Le code demandé est
//  dans le paquetage ex2 (Employe, Service et Test).
// =====================================================================
/**
 * Présentation de l'exercice 2 de la série 3 (employés et services) dans le menu de la soutenance.
 *
 * Le code demandé (Employe, Service et Test) est dans le paquetage ex2. Cette classe le présente :
 * énoncé, diagrammes UML, explications sur le lien entre les deux classes et le Vector, puis
 * exécution réelle de la classe Test.
 */
public class TD3_Ex2 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 3) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD3_Ex2() {
        super("Employés et services (classes Employe et Service)",
                "Relier des employés à leur service (Vector d'employés, chef de service), puis les affecter, les échanger et les augmenter.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, puis répond aux deux questions.
     * Test.main(new String[0]) exécute réellement le programme de test (tableau de paramètres vide).
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Un employé est caractérisé par un nom (String), un grade (String), un salaire (float) "
                + "et le service auquel il est affecté (Service). Un service est caractérisé par un nom "
                + "(String), un numéro de téléphone (int), le chef du service (Employe) et tous les employés "
                + "qui travaillent dans ce service (Vector).");

        // --- 2. Question 1 : les classes Employe et Service (diagrammes UML) ---
        Ecran.question(1, "Définir les classes Employe et Service. Employe offre getNom(), setGrade(), "
                + "getGrade(), setService(), getService(), setSalaire(), getSalaire() et toString(), qui "
                + "décrit l'employé sans citer son service. Service offre getNom(), getNombreEmploye(), "
                + "setNumTel(), getNumTel(), getChef(), setChef(), affecterEmploye(), eliminerEmploye() et "
                + "toString(), qui ne cite que les noms du chef et des employés, le nom du chef une seule fois.");
        String[] attributsE = {"- nom : String", "- grade : String", "- salaire : float", "- service : Service"};
        String[] methodesE = {"+ Employe(nom : String, grade : String, salaire : float)", "+ getNom() : String",
            "+ setGrade(grade : String) : void", "+ getGrade() : String", "+ setService(service : Service) : void",
            "+ getService() : Service", "+ setSalaire(salaire : float) : void", "+ getSalaire() : float",
            "+ toString() : String"};
        Ecran.uml("Employe", attributsE, methodesE);
        String[] attributsS = {"- nom : String", "- numTel : int", "- chef : Employe", "- employes : Vector<Employe>"};
        String[] methodesS = {"+ Service(nom : String, numTel : int)", "+ getNom() : String",
            "+ getNombreEmploye() : int", "+ setNumTel(numTel : int) : void", "+ getNumTel() : int",
            "+ getChef() : Employe", "+ setChef(e : Employe) : void", "+ affecterEmploye(e : Employe) : void",
            "+ eliminerEmploye(e : Employe) : void", "+ toString() : String"};
        Ecran.uml("Service", attributsS, methodesS);
        Ecran.texte("Code : corrections/td3/ex2/Employe.java et Service.java. Points clés :\n"
                + "- les deux classes se désignent l'une l'autre : un employé connaît son service, un service "
                + "connaît son chef et ses employés. affecterEmploye() et eliminerEmploye() mettent à jour les "
                + "deux côtés, avec e.setService(this) puis e.setService(null) ;\n"
                + "- les employés sont rangés dans un Vector, avec les méthodes du cours (p.187-188) : "
                + "addElement, elementAt, contains, remove et size. Avec le type écrit entre < >, comme pour "
                + "ArrayList<Employe> (p.197), elementAt renvoie directement un Employe : pas besoin de cast ;\n"
                + "- contains() et remove() comparent avec equals. Employe ne redéfinit pas equals : c'est "
                + "celle de Object qui sert, elle compare les références. C'est voulu ici : on cherche le même "
                + "objet employé, pas un homonyme ;\n"
                + "- le toString() de Service saute le chef dans la liste des employés (e != chef) : son nom "
                + "n'apparaît qu'une fois.");
        Clavier.pause();

        // --- 3. Question 2 : exécution de la classe Test ---
        Ecran.question(2, "Écrire une classe Test qui crée deux services S1 et S2 et quatre employés E1 à E4 ; "
                + "affecte E1 et E2 au service S1, E3 et E4 au service S2, E1 et E3 étant les chefs ; échange "
                + "E2 et E4 entre les deux services, augmente le salaire de E2 et le rend chef de son nouveau "
                + "service ; affiche les informations des deux chefs, puis celles des deux services.");
        System.out.println("Exécution de Test (corrections/td3/ex2/Test.java) :");
        System.out.println();
        Test.main(new String[0]);
        System.out.println();
        Ecran.texte("Après l'échange, Sami (E2) travaille au service Marketing et en devient le chef à la "
                + "place d'Amira (E3), qui reste employée du service ; son salaire passe de 1500 à 1700 DT. "
                + "Pour l'échange, on élimine d'abord chaque employé de son ancien service, puis on l'affecte "
                + "au nouveau : un employé n'appartient qu'à un seul service. E2.getService() désigne ensuite "
                + "son nouveau service, ce qui permet d'écrire E2.getService().setChef(E2).");
    }
}
