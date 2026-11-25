package corrections.td4;

import corrections.outils.Clavier;
import corrections.outils.Ecran;
import corrections.outils.Exercice;
import corrections.td4.ex3.TestSalaries;

// =====================================================================
//  CLASSE TD4_Ex3  -  Série 4, Exercice 3
//  Rôle : présenter la gestion du personnel : énoncé, réponses aux quatre
//  questions (classe abstraite Salarie, hiérarchie, classes filles) et
//  exécution de TestSalaries. Le code est dans le paquetage ex3.
// =====================================================================
/**
 * Présentation de l'exercice 3 de la série 4 (gestion du personnel, classe abstraite Salarie).
 *
 * Le code est dans le paquetage ex3 : Salarie (abstraite), Employe, Vendeur, Directeur et
 * TestSalaries. Cette classe répond aux quatre questions (pourquoi Salarie est abstraite, sa
 * définition, la hiérarchie, les classes filles), affiche les messages de javac pour les erreurs
 * typiques, puis exécute le programme de test.
 */
public class TD4_Ex3 extends Exercice {

    // =================================================================
    //  CONSTRUCTEUR
    // =================================================================

    /**
     * Donne son titre (affiché dans le menu du TD 4) et son objectif (affiché avant l'exécution).
     * super(...) transmet ces deux textes au constructeur de la classe mère Exercice.
     */
    public TD4_Ex3() {
        super("Gestion du personnel (classe abstraite Salarie)",
                "Modéliser les salariés avec la classe abstraite Salarie et ses classes filles Employe, Vendeur et Directeur.");
    }

    // =================================================================
    //  EXÉCUTION DE L'EXERCICE
    // =================================================================

    /**
     * Affiche l'énoncé, répond aux quatre questions, puis exécute TestSalaries.
     * Deux pauses découpent la présentation ; TestSalaries.main(new String[0]) exécute le test.
     */
    public void executer() {
        // --- 1. Énoncé ---
        Ecran.section("Énoncé");
        Ecran.enonce("Dans une entreprise de vente, chaque salarié a un matricule et un nom. Les employés "
                + "sont payés selon le nombre d'heures travaillées et un taux horaire ; les vendeurs sont "
                + "des employés qui touchent en plus une commission = vente * pourcentage ; les directeurs "
                + "touchent un salaire fixe augmenté d'une prime mensuelle. La classe Salarie doit avoir un "
                + "constructeur paramétré, une méthode afficher() et une méthode calculSalaire().");

        // --- 2. Question 1 : peut-on tout implémenter dans Salarie ? ---
        Ecran.question(1, "Peut-on implémenter toutes les méthodes de la classe Salarie ? Expliquer.");
        Ecran.texte("Non. Le constructeur et afficher() peuvent être écrits dans Salarie : le matricule et "
                + "le nom sont communs à tous les salariés. Mais calculSalaire() ne peut pas l'être : la "
                + "formule dépend du type de salarié (heures × taux, plus une commission, ou fixe + prime), "
                + "et Salarie ne possède aucune de ces données.\n"
                + "On déclare donc calculSalaire() abstraite, sans corps, et Salarie devient une classe "
                + "abstraite (cours p.222-223) : on ne peut pas créer d'objet Salarie, et chaque classe "
                + "fille doit écrire calculSalaire(). javac le vérifie. Avec new Salarie(101, \"Karim\") :");
        Ecran.champ("javac", "erreur : Salarie is abstract; cannot be instantiated");
        Ecran.texte("Et si Employe oublie d'écrire calculSalaire() :");
        Ecran.champ("javac", "erreur : Employe is not abstract and does not override abstract method "
                + "calculSalaire() in Salarie");
        Clavier.pause();

        // --- 3. Question 2 : la classe Salarie (diagramme UML) ---
        Ecran.question(2, "Donner la définition de la classe Salarie.");
        String[] attributsS = {"- matricule : int", "- nom : String"};
        String[] methodesS = {"# Salarie(matricule : int, nom : String)", "+ afficher() : void",
            "+ calculSalaire() : double   {abstract}"};
        Ecran.uml("Salarie   {abstraite}", attributsS, methodesS);
        Ecran.texte("Code : corrections/td4/ex3/Salarie.java. Points clés :\n"
                + "- le constructeur est protected (# en UML) : il ne sert qu'aux classes filles, qui "
                + "l'appellent avec super(matricule, nom) (cours p.209 et p.214-215) ;\n"
                + "- afficher() appelle calculSalaire() alors qu'elle est abstraite : à l'exécution, "
                + "l'objet est forcément un Employe, un Vendeur ou un Directeur, et c'est sa version qui "
                + "s'exécute (liaison dynamique, cours p.218).");

        // --- 4. Question 3 : la hiérarchie des classes ---
        Ecran.question(3, "Identifier les autres classes avec leur hiérarchie.");
        String[] arbre = {
            "             Salarie   {abstraite}",
            "            /       \\",
            "       Employe     Directeur",
            "          |",
            "       Vendeur"};
        Ecran.code(arbre);
        Ecran.texte("- Employe hérite de Salarie : salaire = nombre d'heures × taux horaire.\n"
                + "- Vendeur hérite d'Employe : un vendeur est un employé qui touche en plus une commission.\n"
                + "- Directeur hérite de Salarie : salaire = salaire fixe + prime mensuelle.");

        // --- 5. Question 4 : les classes filles (diagrammes UML) ---
        Ecran.question(4, "Écrire les classes identifiées.");
        String[] attributsE = {"- nbHeures : int", "- tauxHoraire : double"};
        String[] methodesE = {"+ Employe(matricule, nom, nbHeures, tauxHoraire)", "+ calculSalaire() : double",
            "+ afficher() : void"};
        Ecran.uml("Employe extends Salarie", attributsE, methodesE);
        String[] attributsV = {"- vente : double", "- pourcentage : double"};
        String[] methodesV = {"+ Vendeur(matricule, nom, nbHeures, tauxHoraire, vente, pourcentage)",
            "+ commission() : double", "+ calculSalaire() : double", "+ afficher() : void"};
        Ecran.uml("Vendeur extends Employe", attributsV, methodesV);
        String[] attributsD = {"- salaireFixe : double", "- prime : double"};
        String[] methodesD = {"+ Directeur(matricule, nom, salaireFixe, prime)", "+ calculSalaire() : double",
            "+ afficher() : void"};
        Ecran.uml("Directeur extends Salarie", attributsD, methodesD);
        Ecran.texte("Code : corrections/td4/ex3/. Points clés :\n"
                + "- chaque constructeur commence par super(...) : la partie commune est initialisée par "
                + "la classe mère ;\n"
                + "- Vendeur.calculSalaire() réutilise le calcul d'Employe : super.calculSalaire() + "
                + "commission() ;\n"
                + "- chaque afficher() commence par super.afficher(), puis ajoute ses propres détails.");
        Clavier.pause();

        // --- 6. Exécution de TestSalaries ---
        Ecran.section("Exécution de TestSalaries");
        TestSalaries.main(new String[0]);
        System.out.println();
        Ecran.texte("Le personnel est rangé dans un tableau Salarie[] : la même boucle appelle afficher() "
                + "et calculSalaire() sur chaque salarié, et Java choisit chaque fois la méthode de la vraie "
                + "classe de l'objet (tableau polymorphe, cours p.220). Pour Nour, la vendeuse, "
                + "Salarie.afficher() appelle calculSalaire() : c'est la version de Vendeur qui s'exécute, "
                + "900 + 1000 = 1900 DT.");
    }
}
