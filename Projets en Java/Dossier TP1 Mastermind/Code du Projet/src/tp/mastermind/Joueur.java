/*
 Cette cartouche corresponde à la classe Joueur de notre programme
Cette classe gère tous les évènements liés à l'IA de l'ordinateur, ou le Joueur devinant un code de l'ordinateur

TOGGENBURGER Léo & PALLARD Hugo 13/11/2020
 */
package tp.mastermind;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Léo Togg
 */
public class Joueur extends TPMASTERMIND {

    public String pseudo;
    public int score;
    public int role;
    public int[] codeSecret;
    public int[] propositions;
    public int nbreProp;
    public int roleOrdinateur;
    public int nbReponse = 1;
    public int[][] listprop;
    public int[] resultat;
    public int scorepc;
    public int nbplay;
    
    public Joueur(String pseudoJoueur, int roleJoueur) { // Le constructeur de notre classe Joueur
        this.pseudo = pseudoJoueur;
        this.role = roleJoueur;
    }

    public void AfficherJoueur() { //La fonction affiche les caractéristiques du Joueur à l'aide d'une condition if & else pour gérer les différents rôles
        String rolejoueur;
        if (this.role == 1) {
            rolejoueur = "Devineur";
        } else {
            rolejoueur = "Codeur";
        }
        System.out.println("Les caractéristiques du joueurs sont : pseudo " + pseudo + ", rôle " + rolejoueur + ", nombre de proposition " + nbreProp + ", score " + score);

    }

    public void AfficherOrdinateur() {//La fonction affiche les caractéristiques de l'Ordinateur à l'aide d'une condition if & else pour gérer les différents rôles
        String rolepc;
        if (this.role == -1) {
            rolepc = "Devineur";
        } else {
            rolepc = "Codeur";
        }
        System.out.println("Les caractéristiques de l'ordinateur sont : rôle " + rolepc + ", nombre de proposition " + nbreProp + ", score " + scorepc);
    }

    // ------------ Les fonctions concernant le rôle " DEVINEUR " commencent ici ------------------- \\
    
    
    public void creerCode() {                                                   // Cette fonction permet de générer un code Ordinateur Aléatoire
        int[] tab = new int[5];                                                  // Pour que l'utilisateur le devine
        int nb;
        for (int i = 0; i < tab.length; i++) {
            Random randomCode = new Random();
            nb = randomCode.nextInt(10);                                        // On génère un chiffre aléatoire
            boolean verif = true;
            while (verif == true) {                                             // Une boucle while permettant de générer des chiffres tant que...
                verif = false;                                                  // Par défaut, on ne souhaite pas rester dans cette boucle while
                for (int j = 0; j < i; j++) {                                   // On parcours un tableau préalablement initialisé

                    if (nb == tab[j]) {                                         // ... Le chiffre généré est égal à l'un des éléements i du tableau
                        verif = true;                                           // Alors on reste dans la boucle while
                        nb = randomCode.nextInt(10);                            // Puis on génère un nouveau chiffre
                    }
                }
            }                                                                   // On sort de cette boucle while si l'on ne rentre pas dans le if ci-dessus
            tab[i] = nb;
        }
        //System.out.println(Arrays.toString(tab));                             //Afficher le code de l'ordinateur
        this.codeSecret = tab;                                                  // On affecte le tableau générer à une variable à la hauteur de la classe, pour pouvoir l'utiliser plus tard
    }

    public void saisirProp() {                                                  // Cette fonction permet au joueur d'entrer une proposition          
        int[] tab = new int[5];
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un code à 5 chiffres :\n");                  // Ouverture d'un scanner permettant à l'utilisateur d'utiliser le clavier

        for (int i = 0; i < tab.length; i++) {
            System.out.println("Entrez le " + (i + 1) + "i-ème numero");        // On lui demande d'entrer les chiffres du code 1 par 1...

            int numerocode = verifInt(sc);
            while (numerocode > 9 || numerocode < 0) {                                            // On vérifie si le chiffre n'est pas un nombre supérieur ou égal à 10

                System.out.println("Rentrez un chiffre compris entre 0 et 9."); // Si telle est le cas, on lui demande de rentrer un autre chiffre tant qu'il ne respectent pas le message indiqué
                numerocode = verifInt(sc);
            }
            tab[i] = numerocode;                                                // A ce niveau, on affecte le chiffre rentré par l'utilisateur dans la case i d'un tableau
        }
        this.propositions = tab;                                                // Ce tableau est affecté dans une variable à l'échelle de la classe, il correspond à une proposition du joueur
        this.nbreProp = this.nbreProp + 1;                                      // On incrémente une variable correspondant au nombre de propositions réalisées par le joueur

    }

    public void verifPlaces() {                                                 // Cette fonction est une fonction de l'ordinateur, elle permet d'évaluer la proposition du joueur
        int bonnereponse = 0;
        int malplace = 0;                                                       // A l'aide d'un " double for " on parcours les éléments du code généré par l'ordinateur
                                                                                // Puis pour chaque élément de ce code, on parcours les éléments de la proposition du joueur
        for (int i = 0; i < this.codeSecret.length; i++) {                      
            for (int j = 0; j < this.codeSecret.length; j++) {
                if (this.codeSecret[i] == this.propositions[j] && i == j) {     // Ce if permet d'incrémenter un compteur de chiffre(s) bien placé(s) càd si les éléments de la proposition et du code                                                                    // correspondent 1 à 1
                    bonnereponse = bonnereponse + 1;                            // sont égaux 1 à 1
                }
                if (this.codeSecret[i] == this.propositions[j] && i != j) {     //Ce if permet d'incrémenter un compteur de chiffre(s) différent(s) càd si les éléments de la proposition et du code
                    malplace = malplace + 1;                                    // sont différents 1 à 1
                }
            }
        }
        // Affichage d'un récapitulatif de l'évaluation
        System.out.println("\n" + "Vous avez " + bonnereponse + " chiffre(s) bien placé(s).");
        System.out.println("Vous avez " + malplace + " chiffre(s) mal placé(s).");
        System.out.println("Vous avez " + (5 - bonnereponse - malplace) + " chiffre(s) qui n'existe(nt) pas.\n");
        this.nbReponse = 5 - bonnereponse;
    }

    public void finManche() {                                                   // Fonction simple permettant l'affichage de la fin d'une manche
        if(this.nbreProp == 10){
            this.nbplay += 1;
        }
        if (this.nbReponse == 0) {
            if (this.role == 1) {
                this.nbplay += 1;
            }
            tiret();
            System.out.println("La manche est terminée,\nBien joue Beau gosse !");

        }
    }

    public void incrScore() {                                                   // Fonction simple permettant l'incrémentation d'une variable de score
        int score = 10 - this.nbreProp;
        this.score = score;
    }

    public void affichProp() {                                                  // Fonction simple permettant l'affichage de la proposition du joueur
        System.out.println("\nVoici votre proposition : " + Arrays.toString(this.propositions));
    }

// ------------ Les fonctions concernant le rôle " CODEUR " commencent ici ------------------- \\ 
    public void saisirCode() {
        //méthode qui permet au joeur de rentrer un code secret

        int[] tab = new int[5];
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un code secret à 5 chiffres :\n");
        System.out.println("Entrez le premier numéro :");
        int numero1 = verifInt(sc);                                             // On demande au Joueur un numéro, on vérifie que le caractère entré est bien un entier
        while (numero1 > 9 || numero1 < 0) {                                                   // On vérifie qu'il est compris entre 0 et 9
            System.out.println("Rentrez un chiffre compris entre 0 et 9.");
            numero1 = verifInt(sc);
        }
        tab[0] = numero1;                                                       // Ici, on affecte alors le numéro rentré à la case n°0 du tableau CodeSecret

        for (int i = 1; i < tab.length; i++) {                                  // On parcours jusqu'à la taille du tableau CodeSecret

            System.out.println("Entrez le " + (i + 1) + "-ème numéro");         // On demande d'entrer les i-numéros
            int numerocode = verifInt(sc);
            while (numerocode > 9 || numerocode < 0) {                                            // Toujours les mêmes vérifications
                System.out.println("Rentrez un chiffre compris entre 0 et 9.");
                numerocode = verifInt(sc);
            }
            boolean verif = true;
            while (verif == true) {                                             // Pour chaque numéro on rentre dans un while 
                verif = false;                                                  // qui suit la même logique que le while de la fonction SaisirProp
                for (int j = 0; j < i; j++) {
                    if (tab[j] == numerocode) {
                        verif = true;
                        System.out.println("Rentrez un numéro différent d'un des numéros des précédents");
                        numerocode = verifInt(sc);
                        while (numerocode > 9 || numerocode < 0) {
                            System.out.println("Rentrez un chiffre compris entre 0 et 9.");
                            numerocode = verifInt(sc);
                        }
                    }
                }

            }

            tab[i] = numerocode;
        }

        this.codeSecret = tab;
    }

    public void listProp() {
        
        //Methode qui permet de creer une matrice contenant toutes les propositions possibles, pour tout les nombres
        //entre 0 et 99 999. On va verifier lesquels ont des chiffres differents entre eux. Pour ça on va separer
        //les chiffres et les stocker dans un tableau pour les comparer entre eux, si ils sont differents on stocke
        //les elements de ce tableau dans la matrice liste prop.
        
        int[][] listeprop = new int[30240][5];              //creation d'une matrice de taille 30240x5
        int nb = 0;                                         //nombre de départ
        int list = 0;                                       //indice de la ligne de la matrice, va servir plus tard
        int nbleft;                                         //nbleft est le chiffre le plus a gauche du nombre, c'est celui ci qu'on veut separer

        for (int j = 0; j < 100000; j++) {                        //la boucle parcourt les 100 000 nombres possibles, de 0 à 99 999
            int nombre = nb;                                //nb est le nombre que l'on veut separer
            int[] tab = new int[5];                         //tableau qui va servir a accueillir les chiffres
            int a = 0;                                        // a est l'indice du tableau
            for (int i = 5; 0 < i; i--) {                        //cette boucle va permettre de separer les 5 chiffres d'un nombre
                nbleft = (int) (nombre / Math.pow(10, i - 1));  //ici on divise notre nombre par 10^(i-1) et on obtient la partie entière, qui est le chiffre tt a gauche du nombre
                tab[a] = nbleft;                            //la partie entière est le chiffre que l'on a séparé, on le stocke dans le tableau
                a++;                                        //on passe a l'indice suivant
                nombre = nombre - nbleft * (int) (Math.pow(10, i - 1));  //on enleve au nombre la partie entiere*10^(i-1), le nombre perd le chiffre de gauche
                //on parcourt la boucle for jusqu'a avoir separer les 5 chiffres 
            }
            nb++;                                          //on passe au nombre suivant
            int compteur = 0;                               //on initialise un compteur
            for (int k = 0; k < 5; k++) {                       //cette boucle va permettre de verifier si 2 chiffres sont pareils dans le nombre
                for (int p = 0; p < 5; p++) {
                    if (tab[k] == tab[p] && p != k) {            //si 2 chiffres d'indices différents sont pareils, alors le compteur augmente
                        compteur++;
                    }
                }
            }
            if (compteur == 0) {                                //si le compteur est nul, tt les chiffres du nombres sont differents entre eux
                for (int c = 0; c < 5; c++) {                   //on va donc copier ce nombre dans la liste des propositions possibles
                    listeprop[list][c] = tab[c];
                }
                list++;
            }
        }
        this.listprop = listeprop;                        //on attribut la matrice que l'on vient de creer a la variable liste propositions de notre classe joueur   
    }

    public void chooseProp() {
        //methode qui permet de tirer une proposition au hasard parmi celles de la matrice qui contient la liste de ttes les prop restantes
        int nb;
        int[] tab = new int[5];
        Random randomCode = new Random();
        int pif = this.listprop.length;
        nb = randomCode.nextInt(pif);
        for (int i = 0; i < 5; i++) {
            tab[i] = this.listprop[nb][i];
        }
        this.propositions = tab;
    }

    public void saisirPlaces() {
        //methode qui permet au joueur de comparer son code secret à la prop que l'ordi lui soumet
        boolean verif = true;
        while (verif == true) {
            System.out.println("\nVoici votre code secret :\n" + Arrays.toString(this.codeSecret) + "\n");   //affiche le code secret du joueur
            System.out.println("Voici la proposition du pc :\n" + Arrays.toString(this.propositions) + "\n"); //affiche la prop de l'ordi
            Scanner sc = new Scanner(System.in);
            System.out.println("Combien de chiffres sont bien placés ?");
            int nbbienplace = verifInt(sc);
            System.out.println("Combien de chiffres sont bons mais mal placés ?");
            int nbmalplace = verifInt(sc);
            System.out.println("Combien de chiffres sont mauvais ?");
            int nbmauvais = verifInt(sc);
            int[] tab = new int[3];
            tab[0] = nbbienplace;
            tab[1] = nbmalplace;
            tab[2] = nbmauvais;

            int bonnereponse = 0;
            int malplace = 0;
            int[] tabverif = new int[3];

            for (int i = 0; i < this.codeSecret.length; i++) {
                for (int j = 0; j < this.codeSecret.length; j++) {
                    if (this.codeSecret[i] == this.propositions[j] && i == j) {
                        bonnereponse = bonnereponse + 1;
                    }
                    if (this.codeSecret[i] == this.propositions[j] && i != j) {
                        malplace = malplace + 1;
                    }
                }
            }
            tabverif[0] = bonnereponse;
            tabverif[1] = malplace;
            tabverif[2] = 5 - bonnereponse - malplace;
            if (tabverif[0] != tab[0] || tabverif[1] != tab[1] || tabverif[2] != tab[2]) {
                System.out.println("\nEtes vous sur ?");
                verif = true;
            } else {

                this.resultat = tab;                                    //on stocke la comparaison dans un tableau que l'on nomme resultat
                this.nbReponse = 5 - nbbienplace;
                this.nbreProp = this.nbreProp + 1;
                verif = false;
            }
        }
    }

    public void verifList() {
        //methode qui permet de comparer la proposition de l'ordi à ttes les prop de la liste, puis garder celles qui ont le même resultat que la comparaison avec le code secret du joueur
        //methode vraiment pas opti mais osef
        int taillelist = 0;
        int indice = 0;
        for (int k = 0; k < this.listprop.length; k++) {
            //premiere boucle qui permet de compter cb de prop ont le meme resultat, permet d'obtenir la taille du tableau ou on va les stocker
            int bonnereponse = 0;
            int malplace = 0;
            int[] tab = new int[3];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (this.listprop[k][i] == this.propositions[j] && i == j) {
                        bonnereponse = bonnereponse + 1;
                    }
                    if (this.listprop[k][i] == this.propositions[j] && i != j) {
                        malplace = malplace + 1;
                    }
                }
            }
            tab[0] = bonnereponse;
            tab[1] = malplace;
            tab[2] = 5 - bonnereponse - malplace;
            if (tab[0] == this.resultat[0] && tab[1] == this.resultat[1] && tab[2] == this.resultat[2]) {//on verifie si le resultat est le meme que celui avec le code secret
                taillelist = taillelist + 1;  //determine la taille de la nouvelle matrice
            }
        }

        int[][] nvllist = new int[taillelist][5]; //on initialise une nouvelle matrice de taille tallelistx5 où on va stocker les bonnes prop

        for (int k = 0; k < this.listprop.length; k++) {
            //compare encore une fois la prop aux prop de la liste
            int bonnereponse = 0;
            int malplace = 0;
            int[] tab = new int[3];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (this.listprop[k][i] == this.propositions[j] && i == j) {
                        bonnereponse = bonnereponse + 1;
                    }
                    if (this.listprop[k][i] == this.propositions[j] && i != j) {
                        malplace = malplace + 1;
                    }
                }
            }
            tab[0] = bonnereponse;
            tab[1] = malplace;
            tab[2] = 5 - bonnereponse - malplace;

            if (tab[0] == this.resultat[0] && tab[1] == this.resultat[1] && tab[2] == this.resultat[2]) {
                //si le resultat est identique, on copie la prop dans la nouvelle liste
                for (int i = 0; i < 5; i++) {
                    nvllist[indice][i] = this.listprop[k][i];

                }
                indice = indice + 1; //permet de passer a la ligne suivante dans notre nouvelle matrice
            }

        }
        this.listprop = nvllist;   //affecte la nouvelle liste a la liste de la classe
    }

    public void incrScorepc() {     // Méthode permettant d'incrémenter le score de l'ordinateur
        int score = 10 - this.nbreProp;
        this.scorepc = score;
    }



    public void desigGagnant(int joueurscore, int pcscore, int nbreManche) {    // Méthode permettant de choisir un gagnant
        if (this.nbplay == 0){
            joueurscore = 0;        
        }else{
            joueurscore = joueurscore / this.nbplay;
        }
        if ((nbreManche - this.nbplay) != 0) {
            pcscore = pcscore / (nbreManche - this.nbplay);
        } else {
            pcscore = 0;
        }
        System.out.println("Le score final du joueur est : " + joueurscore + "\n");
        System.out.println("Le score final du pc est : " + pcscore + "\n");
        if (pcscore < joueurscore) {
            System.out.println("Vous avez gagné la partie, vous êtes vraiment trop fort !\n");
        } else {
            System.out.println("Vous avez perdu, dommage.\n");
        }
    }

}
