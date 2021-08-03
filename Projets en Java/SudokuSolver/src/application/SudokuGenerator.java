/*
Classe de génération du Sudoku, réalisée le 19/07/2021
 */
package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;

public class SudokuGenerator {

    private final int size;
    private final int level;
    private int valueLigne;
    private int scoreTotalLigne;
    private int valueCol;
    private int scoreTotalCol;

    private final ArrayList<ArrayList<String>> sudokuStringTab;
    private final ArrayList<ArrayList<String>> sudokuStringTabTransposed;
    private ArrayList<ArrayList<String>> finalSudokuTransposed;

    private ArrayList<String> firstRow;
    private final ArrayList<String> numberAvailable;
    private ArrayList<String> inUseNumbers;

    public SudokuGenerator() {

        this.size = 9;
        this.valueLigne = 0;
        this.valueCol = 0;
        this.scoreTotalLigne = 0;
        this.scoreTotalCol = 0;
        this.level = 2;
        this.sudokuStringTab = new ArrayList<>();
        this.sudokuStringTabTransposed = new ArrayList<>();
        this.finalSudokuTransposed = new ArrayList<>();
        this.firstRow = new ArrayList<>();
        this.numberAvailable = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
        this.inUseNumbers = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));

    }

    public void SudokuFiller() {
        switch (level) {
            case 0 ->
                System.out.println("Le niveau de difficulté est facile");
            case 1 ->
                System.out.println("Le niveau de difficulté est moyen");
            case 2 ->
                System.out.println("Le niveau de difficulté est difficile");
            default -> {
            }
        }
        for (int w = 0; w < size; w++) {
            sudokuStringTab.add(new ArrayList<>());
            sudokuStringTabTransposed.add(new ArrayList<>());
            finalSudokuTransposed.add(new ArrayList<>());
            for (int h = 0; h < size; h++) {
                sudokuStringTab.get(w).add("0");
                sudokuStringTabTransposed.get(w).add("0");
                finalSudokuTransposed.get(w).add("0");
            }
        }
    }

    public void SudokuGenerator() {

        // Remplissage de la 1ère ligne de chiffres aléatoirement choisis
        for (int h = 0; h < size; h++) {
            int r = new Random().nextInt(inUseNumbers.size());
            sudokuStringTab.get(0).set(h, String.valueOf(inUseNumbers.get(r)));
            this.firstRow.add(h, String.valueOf(inUseNumbers.get(r)));
            inUseNumbers.remove(r);
        }
        inUseNumbers = (ArrayList<String>) numberAvailable.clone();

        // On commence la génération du Sudoku
        for (int row = 1; row < size; row++) {

            ArrayList<String> temporaryRow = (ArrayList<String>) this.firstRow.clone(); // Copie de la 1ère ligne

            if (row == 1 || row == 2 || row == 4 || row == 5 || row == 7 || row == 8) {
                temporaryRow = SudokuRowShifter(temporaryRow, 3);                           // On shift la première ligne de 3 
                this.firstRow = (ArrayList<String>) temporaryRow.clone();                // Cette ligne temporaire devient notre nouvelle premiere ligne

            } else if (row == 3 || row == 6) {
                temporaryRow = SudokuRowShifter(temporaryRow, 1);
                this.firstRow = (ArrayList<String>) temporaryRow.clone();
            }

            for (int j = 0; j < size; j++) {
                this.sudokuStringTab.get(row).set(j, temporaryRow.get(j));
            }

        }

        System.out.println("Le sudoku généré");
        ShowSudoku(sudokuStringTab);

        int randomPath = new Random().nextInt(3);

        System.out.print("Chemin Choisi : " + randomPath + "\n"); 

        if (randomPath == 0) {

            // Procedure
            SudokuTransposer();
            SudokuInverser(sudokuStringTabTransposed);
            SudokuReTransposer();
            SudokuInverser(sudokuStringTab);
            // Show
            System.out.println("Le sudoku généré transposé, permuté ligne par ligne, retransposé, de nouveau permuté");
            ShowSudoku(sudokuStringTab);
        }
        if (randomPath == 1) {

            // Procedure
            SudokuTransposer();
            SudokuInverser(sudokuStringTabTransposed);
            // Show
            System.out.println("Le sudoku généré transposé, permuté ligne par ligne");
            ShowSudoku(sudokuStringTabTransposed);
        }
        if (randomPath == 2) {

            // Procedure
            SudokuTransposer();
            SudokuInverser(sudokuStringTabTransposed);
            SudokuReTransposer();
            // Show
            System.out.println("Le sudoku généré transposé, permuté ligne par ligne, retransposé");
            ShowSudoku(sudokuStringTab);

        } else {

        }
        System.out.println("---------------------------------------");
        SudokuZeroFiller();
    }

    public void SudokuReTransposer() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.sudokuStringTab.get(j).set(i, this.sudokuStringTabTransposed.get(i).get(j));
            }
        }
    }

    public void SudokuInverser(ArrayList<ArrayList<String>> sudokuToInverse) {
        for (int i = 0; i < size - 1; i++) {
            ArrayList<String> tempCol = (ArrayList<String>) sudokuToInverse.get(i).clone();
            sudokuToInverse.set(i, sudokuToInverse.get(i + 1));
            sudokuToInverse.set(i + 1, tempCol);

        }
    }

    public void SudokuTransposer() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.sudokuStringTabTransposed.get(j).set(i, this.sudokuStringTab.get(i).get(j));
            }
        }
    }

    public void SudokuZeroFiller() {
        int comptorZero = 0;
        // Quand on quitte le for de génération, on remplace aléatoire des 0 dans la grille en fct de la difficulté
        for (int w = 0; w < size; w++) {
            for (int h = 0; h < size; h++) {
                if ((level == 0 && comptorZero < 3) || (level == 1 && comptorZero < 6) || (level == 2 && comptorZero < 8)) {
                    sudokuStringTab.get(w).set(new Random().nextInt(9), "0");
                    comptorZero++;
                }
            }
            comptorZero = 0;
        }
    }

    public ArrayList<String> SudokuRowShifter(ArrayList<String> temporaryRow, int shiftIntensity) {

        if (shiftIntensity == 3) {
            temporaryRow.set(6, this.firstRow.get(0));
            temporaryRow.set(7, this.firstRow.get(1));
            temporaryRow.set(8, this.firstRow.get(2));

            temporaryRow.set(0, this.firstRow.get(3));
            temporaryRow.set(1, this.firstRow.get(4));
            temporaryRow.set(2, this.firstRow.get(5));

            temporaryRow.set(3, this.firstRow.get(6));
            temporaryRow.set(4, this.firstRow.get(7));
            temporaryRow.set(5, this.firstRow.get(8));
        } else if (shiftIntensity == 1) {
            temporaryRow.set(8, this.firstRow.get(0));
            temporaryRow.set(0, this.firstRow.get(1));
            temporaryRow.set(1, this.firstRow.get(2));

            temporaryRow.set(2, this.firstRow.get(3));
            temporaryRow.set(3, this.firstRow.get(4));
            temporaryRow.set(4, this.firstRow.get(5));

            temporaryRow.set(5, this.firstRow.get(6));
            temporaryRow.set(6, this.firstRow.get(7));
            temporaryRow.set(7, this.firstRow.get(8));
        }
        return temporaryRow;
    }

    public void ShowSudoku(ArrayList<ArrayList<String>> showCurrentSudoku) {
        for (int w = 0; w < size; w++) {
            System.out.println(showCurrentSudoku.get(w).toString().replace("[", "").replace("]", "").replace(",", ""));
        }
        System.out.println("\n");
    }

    public void SudokuVerifWin() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                finalSudokuTransposed.get(j).set(i, sudokuStringTab.get(i).get(j));
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                valueLigne = valueLigne + Integer.parseInt(sudokuStringTab.get(i).get(j));
                valueCol = valueCol + Integer.parseInt(finalSudokuTransposed.get(i).get(j));
            }
            scoreTotalLigne = valueLigne + scoreTotalLigne;
            scoreTotalCol = valueCol + scoreTotalCol;

            valueLigne = 0;
            valueCol = 0;
        }
        System.out.println("ScoreTotal Ligne " + this.scoreTotalLigne);
        System.out.println("ScoreTotal col " + this.scoreTotalCol);

        if (scoreTotalLigne == 405 && scoreTotalCol == 405) { //45 * 9 == le score max de chaque ligne additionné
            JOptionPane.showMessageDialog(null, "Vous avez réussi le sudoku, bravo !", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Il y a une erreur dans votre sudoku", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        scoreTotalLigne = 0;
        scoreTotalCol = 0;

    }

    public ArrayList<ArrayList<String>> getSudokuStringTab() {
        return sudokuStringTab;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<ArrayList<String>> getFinalSudokuTransposed() {
        return finalSudokuTransposed;
    }

    public void setFinalSudokuTransposed(ArrayList<ArrayList<String>> finalSudokuTransposed) {
        this.finalSudokuTransposed = finalSudokuTransposed;
    }

}
