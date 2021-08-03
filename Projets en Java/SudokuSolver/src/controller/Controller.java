/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import application.Main;
import application.SudokuGenerator;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author hugop
 */
public class Controller implements MouseListener {

    private JButton selectedKeyButton;
    private JButton boutonSelectionne;

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            if (e.getSource() == Main.getSudokuGUI().getNewGridButton()) {

                if (JOptionPane.showConfirmDialog(null, "Générer une nouvelle grille : Oui / Non.", "Générer une nouvelle grille", JOptionPane.YES_NO_OPTION) == 0) {
                    long lStartTime = System.currentTimeMillis();
                    Main.getSudokuGUI().SudokuGUIEraser();
                    Main.getSudoku().SudokuGenerator();
                    Main.getSudokuGUI().SudokuPanelFilling();
                    for (int i = 0; i < Main.getSudoku().getSize(); i++) {
                        Main.getSudokuGUI().getListOfKeyboardButtons().get(i).setBackground(Color.WHITE);
                    }
                    long lEndTime = System.currentTimeMillis();
                    long output = lEndTime - lStartTime;
                    System.out.println("Elapsed time in milliseconds to generate a new sudoku: " + output);
                }
            }
            if (e.getSource() == Main.getSudokuGUI().getSudokuSolverButton()) {
                if (JOptionPane.showConfirmDialog(null, "Let the computer solve the Sudoku ?", "Sudoku Solver", JOptionPane.YES_NO_OPTION) == 0) {
                    System.out.println("Doesnt do anything yet haha");
                }
            }
            if (e.getSource() == Main.getSudokuGUI().getEraseButton()) {
                for (int k = 0; k < Main.getSudoku().getSize(); k++) {
                    Main.getSudokuGUI().getListOfKeyboardButtons().get(k).setBackground(Color.WHITE);
                }
            }
            if (e.getSource() == Main.getSudokuGUI().getVerifWinButton()) {
                System.out.println("Helllooo");
                Main.getSudoku().SudokuVerifWin();
            }
            for (int i = 0; i < Main.getSudoku().getSize(); i++) {
                if (e.getSource() == Main.getSudokuGUI().getListOfKeyboardButtons().get(i)) {
                    Main.getSudokuGUI().getEraseButton().setSelected(false);
                    selectedKeyButton = Main.getSudokuGUI().getListOfKeyboardButtons().get(i);
                    selectedKeyButton.setBackground(Color.YELLOW);
                    repaintOtherButtons();
                }
            }

            for (int i = 0; i < Main.getSudoku().getSize(); i++) {
                for (int j = 0; j < Main.getSudoku().getSize(); j++) {
                    if (e.getSource() == Main.getSudokuGUI().getSudokuButtonTab().get(i).get(j)) {
                        boutonSelectionne = Main.getSudokuGUI().getSudokuButtonTab().get(i).get(j);
                        boutonSelectionne.setFont(Main.getSudokuGUI().getVerdana25Italic());
                        boutonSelectionne.setText(selectedKeyButton.getText());
                        Main.getSudoku().getSudokuStringTab().get(i).set(j, selectedKeyButton.getText());
                    }
                    if (e.getSource() == Main.getSudokuGUI().getSudokuButtonTab().get(i).get(j) && Main.getSudokuGUI().getEraseButton().isSelected()) {
                        boutonSelectionne.setText(null);
                    }
                }
            }
            Main.getSudokuGUI().getMainFrame().repaint();
            Main.getSudokuGUI().getMainFrame().revalidate();
        } catch (NullPointerException n) {
            System.out.println("Selectionnez un bouton du clavier pour commencer à écrire un chiffre dans la grille");
        }
    }

    public void repaintOtherButtons() {
        for (int j = 0; j < Main.getSudoku().getSize(); j++) {
            if (Main.getSudokuGUI().getListOfKeyboardButtons().get(j) != selectedKeyButton && selectedKeyButton != Main.getSudokuGUI().getListOfKeyboardButtons().get(8)) {
                Main.getSudokuGUI().getListOfKeyboardButtons().get(j).setBackground(Color.WHITE);
                Main.getSudokuGUI().getListOfKeyboardButtons().get(8).setBackground(Color.WHITE);
            }
            if (Main.getSudokuGUI().getListOfKeyboardButtons().get(j) != selectedKeyButton) {
                Main.getSudokuGUI().getListOfKeyboardButtons().get(j).setBackground(Color.WHITE);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
