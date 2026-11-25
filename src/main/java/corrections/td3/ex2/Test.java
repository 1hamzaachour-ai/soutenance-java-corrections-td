package corrections.td3.ex2;

// =====================================================================
//  CLASSE Test  -  Série 3, Exercice 2, question 2
//  Rôle : programme de test. Deux services et quatre employés ; on échange
//  E2 et E4, on augmente E2 et on le nomme chef de son nouveau service.
// =====================================================================

/**
 * Programme de test des classes Employe et Service (question 2 de l'énoncé).
 *
 * Il suit exactement les étapes de l'énoncé : création, affectations, échange de deux employés,
 * augmentation, nouveau chef, puis affichage. Les données sont écrites dans le programme (pas de
 * saisie) : le résultat est toujours le même, ce qui facilite la vérification.
 */
public class Test {

    /**
     * Point d'entrée : création, affectations, échange, puis affichage des chefs et des services.
     *
     * @param args paramètres de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // --- 1. Initialisation : deux services et quatre employés ---
        // Les noms S1, S2, E1 à E4 sont ceux de l'énoncé. 71234567 tient dans un int.
        // 2500 est un int converti automatiquement en float (paramètre salaire).
        Service S1 = new Service("Ventes", 71234567);
        Service S2 = new Service("Marketing", 71234568);
        Employe E1 = new Employe("Ali", "Responsable", 2500);
        Employe E2 = new Employe("Sami", "Commercial", 1500);
        Employe E3 = new Employe("Amira", "Responsable", 2400);
        Employe E4 = new Employe("Yasmine", "Assistante", 1400);

        // --- 2. Affectations : E1 et E2 dans S1, E3 et E4 dans S2 ; E1 et E3 sont les chefs ---
        S1.affecterEmploye(E1);
        S1.affecterEmploye(E2);
        S2.affecterEmploye(E3);
        S2.affecterEmploye(E4);
        S1.setChef(E1);
        S2.setChef(E3);
        System.out.println("Avant l'échange :");
        // println(S1) appelle automatiquement S1.toString().
        System.out.println(S1);
        System.out.println(S2);

        // --- 3. Échange de E2 et E4 entre les deux services ---
        // On retire d'abord chacun de son ancien service (eliminerEmploye), puis on l'affecte au
        // nouveau : affecterEmploye ne retire pas un employé de son ancien service.
        S1.eliminerEmploye(E2);
        S2.eliminerEmploye(E4);
        S1.affecterEmploye(E4);
        S2.affecterEmploye(E2);

        // --- 4. E2 est augmenté et devient chef de son nouveau service (S2) ---
        // Augmentation de 200 DT : on lit l'ancien salaire avec le getter, puis on écrit le nouveau
        // avec le setter (l'attribut salaire est private).
        E2.setSalaire(E2.getSalaire() + 200);
        // E2.getService() renvoie le service actuel de E2 (S2 après l'échange) : on le nomme chef
        // sans avoir à écrire S2 nous-mêmes, ce qui montre que le lien employé -> service est à jour.
        E2.getService().setChef(E2);

        // --- 5. Affichage : les deux chefs, puis les deux services ---
        System.out.println();
        System.out.println("Après l'échange :");
        System.out.println("Chef du service " + S1.getNom() + " : " + S1.getChef());
        System.out.println("Chef du service " + S2.getNom() + " : " + S2.getChef());
        System.out.println(S1);
        System.out.println(S2);
    }
}
