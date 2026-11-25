# Soutenance Java – 2ème BI (FSEGT) : séries de TD et projet

Projet NetBeans (Maven) qui regroupe tout le travail du semestre dans un seul menu en console, comme le demande l'énoncé « Menu principal de la soutenance Java » :

- les corrections complètes des **5 séries de TD** :
  - **Série N°1** : Programmation avancée Java (7 exercices)
  - **Série N°2** : Programmation orientée objet Java (4 exercices)
  - **Série N°3** : Programmation avancée Java (3 exercices : pile d'entiers, employés et services, ensemble de chaînes)
  - **Série N°4** : Programmation avancée Java (3 exercices : point nommé, héritage et redéfinition, classe abstraite Salarie)
  - **Série N°5** : Programmation avancée Java (2 exercices : exception TempsException, factorielle et exceptions)
- le **projet Java** : réservation de salles et d'équipements (données enregistrées dans des fichiers texte) ;
- l'affichage des **objectifs** de chaque série et de chaque exercice.

Chaque exercice affiche son objectif, son énoncé, s'exécute (avec saisie au clavier quand il le faut) et répond aux questions de cours.

## Ouvrir et lancer dans NetBeans

1. `File > Open Project…`, choisir le dossier `CorrectionsTD` (il porte l'icône Maven).
2. `Run > Run Project` (F6) : la classe principale est `corrections.Main`.
3. Cliquer dans la fenêtre **Output** avant de taper au clavier.

Il faut un JDK 17 ou plus récent (NetBeans 25 utilise le JDK 25 installé). Le code lui-même reste compatible Java 8.

La fenêtre Output de NetBeans 25 lit et envoie le texte en UTF-8. Le fichier `nbactions.xml` règle donc Java sur l'UTF-8 (`-Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8`, visibles dans `Properties > Run > VM Options`). Si vous copiez ces classes dans un autre projet NetBeans, reprenez ces options, sinon les lettres accentuées s'affichent « � ».

## Utiliser le menu

| Menu | Saisie | Effet |
| --- | --- | --- |
| Menu principal | `1` à `5` | ouvre le menu de la série |
| Menu principal | `6` | lance le projet de réservation |
| Menu principal | `7` | demande un numéro de série (1 à 5), affiche son intitulé et ses objectifs, puis revient au menu principal |
| Menu principal | `0` | quitte le programme |
| Menu d'une série | numéro de l'exercice | affiche l'objectif de l'exercice, puis le lance |
| Menu d'une série | `0` | revient au menu principal |
| Menu du projet | `0. Quitter` | enregistre les données, ferme le projet et revient au menu principal |

À la fin d'un exercice : `Entrée` revient au menu de la série, `R` relance l'exercice, `Q` quitte.

Toutes les saisies sont vérifiées : un texte qui n'est pas un nombre ou un numéro hors de la liste affiche un message d'erreur et la saisie est redemandée, le programme ne s'arrête jamais.

## Comment le menu répond à l'énoncé

| Contrainte de l'énoncé | Où dans le code |
| --- | --- |
| Saisies avec `Scanner` | `Clavier` : un seul `Scanner` sur `System.in`, partagé par les séries et par le projet (`Clavier.getScanner()` dans `Saisie`) |
| Boucle et `switch` pour les menus | `menuPrincipal()` et `menuTD()` de `Main` : boucle `while` et `switch` sur le choix |
| Vérification de chaque saisie | `Clavier.lireEntier(message, min, max)` pour les menus ; boucle de `afficherObjectifsSerie()` (« la série 9 n'existe pas ») |
| Une méthode par menu | `menuPrincipal()`, `menuTD(TD td)`, `afficherObjectifsSerie()`, `lancerProjet()` |
| Intitulés et objectifs dans des tableaux | `SERIES` (`TD[]`) avec les intitulés ; `OBJECTIFS_TD1` à `OBJECTIFS_TD5` (`String[]`) ; `EXERCICES_TD1` à `EXERCICES_TD5` (`Exercice[]`), dont chaque objet garde son titre et son objectif |
| Exercices repris sous forme de méthodes, sans les réécrire | chaque exercice est un objet dont le menu appelle la méthode `executer()` ; le projet est lancé par sa propre méthode `main` |
| Nombre d'exercices variable selon la série | le menu d'une série parcourt le tableau de ses exercices : `td.getNombreExercices()` |

## Le projet de réservation (option 6)

Le projet est repris du projet d'origine « réservation de salles et d'équipements ». Sa logique métier et ses fonctionnalités sont inchangées : salles, équipements, utilisateurs, réservations (conflits de créneau, stock d'équipements), annulation, modification, plannings, recherche de disponibilités, sauvegarde dans les fichiers texte.

Pour l'intégrer, seules deux adaptations ont été nécessaires :

1. **`reservation/Main.java`** : la classe principale du projet était dans le paquetage par défaut. Une classe du paquetage par défaut ne peut pas être utilisée depuis un autre paquetage : elle est donc rangée dans le paquetage `reservation`. L'option 6 appelle `reservation.Main.main(...)`.
2. **`util/Saisie.java`** : le projet lit avec le `Scanner` de `Clavier` au lieu d'en créer un second sur `System.in`. Deux `Scanner` sur le même clavier se volent les lignes tapées.

Les données sont lues et enregistrées dans le dossier **`data/`** à la racine du projet (`salles.txt`, `equipements.txt`, `utilisateurs.txt`, `reservations.txt`). Le programme y crée aussi `compteurs.txt` (derniers numéros attribués) et, si une ligne de données est incorrecte, des copies `*.bak`.

Le projet peut aussi être lancé seul : clic droit sur `reservation/Main.java`, puis `Run File` (Maj+F6).

## Organisation du code

```text
data/                         fichiers texte du projet de réservation
src/main/java/
├── corrections/
│   ├── Main.java             menu principal de la soutenance (options 1 à 7 et 0)
│   ├── outils/               Clavier (un seul Scanner), Ecran (affichage),
│   │                         Exercice (classe abstraite commune : titre, objectif, executer()),
│   │                         TD (une série : intitulé, objectifs, exercices)
│   ├── td1/                  TD1_Ex1 … TD1_Ex7, HelloWorld
│   ├── td2/                  TD2_Ex1 … TD2_Ex4 : énoncés, exécution et réponses aux questions
│   │   ├── ex1/              Morceau, TestMorceau
│   │   ├── ex2/q1/, ex2/q4/  Carte et TestCarte : version de la question 1, puis version encapsulée (question 4)
│   │   ├── ex3/q1/ … ex3/q4/ Forfait et TestForfait : une version par question (constructeurs ajoutés ou enlevés)
│   │   └── ex4/              MotDePasse, TestMotDePasse
│   ├── td3/                  TD3_Ex1 … TD3_Ex3 : énoncés, exécution et réponses aux questions
│   │   ├── ex1/              PileInt, PilePleineException, PileVideException, TestPileInt
│   │   ├── ex2/              Employe, Service (avec un Vector), Test
│   │   └── ex3/q1/, ex3/q3/  EnsembleStr et TestEnsembleStr : tableau de taille fixe, puis ArrayList
│   ├── td4/                  TD4_Ex1 … TD4_Ex3 : énoncés, exécution et réponses aux questions
│   │   ├── ex1/              Point (donnée par l'énoncé), PointNom, TestPointNom
│   │   ├── ex2/              Test.java : les classes A à F et le programme Test de l'énoncé
│   │   └── ex3/              Salarie (abstraite), Employe, Vendeur, Directeur, TestSalaries
│   └── td5/                  TD5_Ex1, TD5_Ex2 : énoncés, exécution et réponses aux questions
│       ├── ex1/              Temps, TempsException, TestTemps
│       └── ex2/              TestFact, NombreNegatifException
├── reservation/              Main : menus du projet de réservation (lancé par l'option 6)
├── model/                    Ressource (abstraite), Salle, Equipement, Utilisateur, Reservation
├── gestion/                  GestionReservations (règles métier), GestionFichiers (fichiers texte)
├── exceptions/               ConflitReservationException, RessourceIntrouvableException
└── util/                     Saisie : saisies vérifiées du projet (dates, heures, identifiants)
```

Quand une question demande de modifier une classe déjà écrite (Carte, Forfait, EnsembleStr), chaque version est gardée dans son propre paquetage (`q1`, `q2`…). On peut ainsi exécuter et comparer toutes les étapes.

Les classes `Test…`, `HelloWorld`, `Temps`, `TD1_Ex4`, `TD1_Ex5` et `reservation.Main` ont leur propre méthode `main` : clic droit sur le fichier, puis `Run File` (Maj+F6) pour les lancer seules.

## Paramètres de la ligne de commande (Série 1, Ex 4 et 5 ; Série 5, Ex 2)

Depuis le menu, les paramètres sont demandés au clavier. Pour les passer vraiment dans NetBeans : clic droit sur le projet, `Properties > Run`, puis Main Class = `corrections.td1.TD1_Ex4` et Arguments = `17 5`.

Sous Windows, `java.exe` remplace un `*` isolé par la liste des fichiers du dossier. Il faut écrire `"*"` (cmd) ou `'"*"'` (PowerShell), ou utiliser `x`, que la calculatrice de l'exercice 5 accepte aussi.

## Messages du compilateur

Quand une réponse dit « cette instruction ne compile pas », le programme affiche le message que donne `javac` pour ce code. Ces messages ont été obtenus en compilant chaque exemple avec le JDK 25.

## Notions du cours utilisées

Le menu et les corrections des séries n'utilisent que des notions vues dans le cours (chapitres 1 à 7) et dans les TD. Le code du projet de réservation est repris tel qu'il a été rendu.

| Chapitre du cours | Où dans le projet |
| --- | --- |
| Ch. 2 : types, conversions, `switch`, boucles | menus de Main (`while` et `switch`), TD1_Ex1 (conversions), TD1_Ex2 (`while`, `do..while`), TD1_Ex3 et TD1_Ex5 (`switch`) |
| Ch. 3 : classes, constructeurs, `this`, `private`, `static`, `final`, classe `Object` | Morceau, Carte, Forfait, MotDePasse ; méthodes `static` de Clavier et Ecran ; `toString` et `equals` de Carte |
| Ch. 4 : `String`, `StringBuffer`, `Scanner` et `nextLine` | Clavier, Ecran, `masquer()` et `cryptage()` de MotDePasse |
| Ch. 5 : tableaux, paramètres de `main`, `Vector`, `ArrayList` | tableaux d'exercices et d'objectifs dans Main, `args` dans TD1_Ex4 et TD1_Ex5, `char[]` dans `classer()`, `int[]` dans PileInt, `String[]` puis `ArrayList` dans EnsembleStr, `Vector` dans Service, `ArrayList` dans Clavier et Ecran |
| Ch. 6 : héritage, `super`, redéfinition, classe abstraite, polymorphisme | Exercice (abstraite) ; TD…_Ex… (`extends`, `super`) ; `exercice.executer()` dans Main ; PointNom (`super(x, y)`, `super.affiche()`), classes A à F (redéfinition, tableau `A[]`), Salarie abstraite et ses classes filles (TD 4) |
| Ch. 7 : exceptions, `try` / `catch`, `throws`, exceptions définies par le programmeur (`throw`) | NumberFormatException (Clavier, TD1_Ex4, TD1_Ex5, TD1_Ex6), ArithmeticException (TD2_Ex3), RuntimeException (Main), PilePleineException et PileVideException (PileInt, TestPileInt) ; TempsException, NombreNegatifException, ArrayIndexOutOfBoundsException et NumberFormatException (TD 5) |
