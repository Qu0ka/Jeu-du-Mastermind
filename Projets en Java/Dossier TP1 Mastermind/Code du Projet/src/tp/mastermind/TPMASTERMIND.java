/*
 Cette cartouche corresponde à la classe Main de notre programme
Cette classe gère tous ce qui est boucle(s) sur les fonctions et méthodes de la classe Joueur, 
afin d'assurer le bon fonctionnement de notre programme

TOGGENBURGER Léo & PALLARD Hugo 13/11/2020

 */
package tp.mastermind;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Léo Togg
 */
public class TPMASTERMIND {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        System.out.println("Bonjour, quel est votre pseudo ?");
        String pseudoJoueur = sc.nextLine();
        tiret();
        System.out.println("Bonjour " + pseudoJoueur + ". Combien voulez-vous faire de manche(s) ?");
        int nbreManche;
        nbreManche = verifInt(sc);
        while (nbreManche < 0){
            System.out.println("Le nombre de manche doit être positif, veuillez rentrer un nouveau nombre :");
            nbreManche = verifInt(sc);
        }
        int joueurscore = 0;
        int pcscore = 0;
        if (nbreManche == 0) {
            System.out.println("Fin du jeu");
            System.exit(0);

        }
            tiret();
            System.out.println("Voulez vous jouer Devineur(1) ou Codeur(-1) ");
            int role = sc.nextInt();
            while (role != -1 && role != 1) {
                System.out.println("Le chiffre est différent de 1 ou -1. Recommencez.");
                role = sc.nextInt();
                
            }
            tiret();
            System.out.println("                         Début du jeu !                  ");
            
            Joueur joueur = new Joueur(pseudoJoueur, role);
            Joueur pc = new Joueur("PC", -1 * role);
            


            for (int i = 0; i < nbreManche; i++) {
                if(joueur.role == 1){
                    tiret();
                    if (i == 0) {
                        System.out.println("Voici la " + (i+1) + "-ère manche, l'ordinateur a choisi un nouveau code :");
                    } else {
                        System.out.println("Voici la " + (i+1) + "-eme manche, l'ordinateur a choisi un nouveau code :");
                    }
                    joueur.creerCode();
                }
                if(joueur.role == -1){
                    tiret();
                    if (i == 0) {
                        System.out.println("Voici la " + (i+1) + "-ère manche :\n");
                    } else {
                        System.out.println("Voici la " + (i+1) + "-eme manche :\n");
                    }
                    joueur.saisirCode();
                    joueur.listProp();                    
                }
                    
                tiret();

                while (joueur.nbReponse != 0 && joueur.nbreProp < 10 ) {
                    if(joueur.role == 1){
                        joueur.saisirProp();
                        joueur.affichProp();
                        joueur.verifPlaces();
                        joueur.finManche();
                        joueur.incrScore();
                    }
                    
                    if(joueur.role == -1){
                        joueur.chooseProp();
                        joueur.saisirPlaces();
                        joueur.verifList();
                        joueur.finManche();
                        joueur.incrScorepc();
                    }    
                }
                if (joueur.role == 1){
                    joueurscore += joueur.score;                    
                }
                if (joueur.role == -1){
                    pcscore += joueur.scorepc;
                }
                joueur.role = joueur.role * -1;
                joueur.nbReponse = 1;
                joueur.nbreProp = 0;
                joueur.score = 0;
                joueur.scorepc =0;
                
            }
            
        sc.close();
        tiret();
        System.out.println("------------------------------");
        joueur.desigGagnant(joueurscore,pcscore, nbreManche);
        System.out.println("La partie est terminée \nMerci d'avoir joué !");
        
        System.exit(0);
        
    }
    public static int verifInt(Scanner sc){
        int entier = 0;
        try {
            entier = sc.nextInt();
            
        }catch (InputMismatchException e){
            System.out.println("Il faut rentrer un entier. Relancez le programme en respectant les consignes.");
            System.exit(0);
        }
        return entier;
    }
    
    public static void tiret(){
        System.out.println("----------------------------------------------------");
    }
    
}
