/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import GUI.SudokuGUI;

/**
 *
 * @author hugop
 */
public class Main {

    private static SudokuGenerator sudoku;
    private static SudokuGUI sudokuGUI;
    private static long lStartTime;
    private static long lEndTime;

    public static void main(String[] args) {

        lStartTime = System.currentTimeMillis();
        sudoku = new SudokuGenerator(); // Argument = level of difficulty 0 to 2 included(Higher means more blanks)
        sudoku.SudokuFiller();
        sudoku.SudokuGenerator();
        lEndTime = System.currentTimeMillis();
        long interMethodsOutput = lEndTime - lStartTime;

        lStartTime = System.currentTimeMillis();
        sudokuGUI = new SudokuGUI(800, 600);
        lEndTime = System.currentTimeMillis();
        long windowOutput = lEndTime - lStartTime;

        System.out.println("Log report : ");
        System.out.println("[GRA] Temps en ms pour le panel clavier: " + sudokuGUI.getKeyboardPanelOutput());
        System.out.println("[GRA] Temps en ms pour le panel des boutons: " + sudokuGUI.getMenuButtonPanelOutput());
        System.out.println("[GRA] Temps en ms pour le panel contenant les 2 derniers: " + sudokuGUI.getMenuPanelOutput());
        System.out.println("[GRA] Temps en ms pour remplir le panel du sudoku: " + sudokuGUI.getSudokuPanelFillingOutput());
        System.out.println("[GRA] Temps en ms juste pour générer la mainFrame: " + sudokuGUI.getMainFrameOutput());
        System.out.println("[GRA] Temps en ms juste pour instancier les champs de sudokuGUI: " + sudokuGUI.getFieldInstOutput());
        System.out.println("[GRA] Temps en ms pour instacier sudokuGUI (le total graphique): " + (windowOutput));
        System.out.println("[INT] Temps en ms pour les méthodes internes: " + interMethodsOutput);

    }

    public static SudokuGenerator getSudoku() {
        return sudoku;
    }

    public static SudokuGUI getSudokuGUI() {
        return sudokuGUI;
    }

    public static void setSudokuGUI(SudokuGUI sudokuGUI) {
        Main.sudokuGUI = sudokuGUI;
    }

}
