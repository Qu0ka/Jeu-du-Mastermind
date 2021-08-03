/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import application.Main;
import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author hugop
 */
public class SudokuGUI {

    private final JFrame mainFrame;
    private JPanel sudokuPanel;
    private JPanel menuPanel;
    private JPanel keyboardPanel;
    private JPanel menuButtonPanel;
    private JButton newGridButton;
    private JCheckBox eraseButton;
    private JButton verifWinButton;
    private JButton sudokuSolverButton;

    private final int boardSize;

    private final int mainFrameHeightInPX;
    private final int sudokuPanelWidth;
    private final int menuPanelWidth;

    private final ArrayList<ArrayList<JButton>> sudokuButtonTab;
    private final ArrayList<JButton> listOfKeyboardButtons;
    private final Dimension dim;
    private final Controller controller;
    private final Font verdana25Bold;
    private long keyboardPanelOutput;
    private long menuButtonPanelOutput;
    private long lStartTime;
    private long sudokuPanelFillingOutput;
    private long menuPanelOutput;
    private long lendTime;
    private final long mainFrameOutput;
    private final long fieldInstOutput;
    private final Font verdana25Italic;

    public SudokuGUI(int mainFrameWidthInPX, int mainFrameHeightInPX) {

        lStartTime = System.currentTimeMillis();

        this.boardSize = Main.getSudoku().getSize();
        this.mainFrameHeightInPX = mainFrameHeightInPX;

        this.sudokuPanelWidth = 600;
        this.menuPanelWidth = 200;

        sudokuButtonTab = new ArrayList<>();
        listOfKeyboardButtons = new ArrayList<>();

        this.verdana25Bold = new Font("Verdana", Font.BOLD, 25);
        this.verdana25Italic = new Font("Verdana", Font.ITALIC, 25);
        this.controller = new Controller();
        this.dim = Toolkit.getDefaultToolkit().getScreenSize();

        lendTime = System.currentTimeMillis();
        fieldInstOutput = lendTime - lStartTime;

        keyBoardPanel();
        MenuButtonPanel();
        MenuPanel();
        SudokuPanel(); // Panel du Sudoku
        SudokuPanelFilling();

        lStartTime = System.currentTimeMillis();

        mainFrame = new JFrame("Sudoku Solver");
        mainFrame.add(sudokuPanel, BorderLayout.CENTER);
        mainFrame.add(menuPanel, BorderLayout.WEST);
        mainFrame.setPreferredSize(new Dimension(mainFrameWidthInPX, mainFrameHeightInPX));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(dim.width / 2 - mainFrameWidthInPX / 2, dim.height / 2 - mainFrameHeightInPX / 2);
        mainFrame.pack();
        mainFrame.setVisible(true);

        lendTime = System.currentTimeMillis();
        mainFrameOutput = lendTime - lStartTime;
    }

    public final void ResetGridButton() {
        newGridButton = new JButton("<html><center>" + "New " + "<br>" + "Grid" + "</center></html>");
        newGridButton.addMouseListener(controller);
        newGridButton.setBackground(Color.WHITE);
        newGridButton.setFocusPainted(false);
    }

    private void SudokuSolverButton() {
        sudokuSolverButton = new JButton("<html><center>" + "Résolution" + "<br>" + "auto" + "</center></html>");
        sudokuSolverButton.addMouseListener(controller);
        sudokuSolverButton.setBackground(Color.WHITE);
        sudokuSolverButton.setFocusPainted(false);

    }

    private void SudokuVerifWinButton() {
        verifWinButton = new JButton("<html><center>" + "Vérifier" + "<br>" + "victoire" + "</center></html>");
        verifWinButton.addMouseListener(controller);
        verifWinButton.setBackground(Color.WHITE);
        verifWinButton.setFocusPainted(false);

    }

    private void EraseButton() {
        eraseButton = new JCheckBox("<html><center>" + "Supprimer" + "<br>" + "chiffre" + "</center></html>");
        eraseButton.addMouseListener(controller);
        eraseButton.setBackground(Color.WHITE);
        eraseButton.setFocusPainted(false);

    }

    private void MenuButtonPanel() {
        lStartTime = System.currentTimeMillis();

        ResetGridButton();
        SudokuSolverButton();
        EraseButton();
        SudokuVerifWinButton();
        menuButtonPanel = new JPanel(new GridLayout(2, 2, 10, 50));
        menuButtonPanel.addMouseListener(controller);
        menuButtonPanel.add(newGridButton);
        menuButtonPanel.add(sudokuSolverButton);
        menuButtonPanel.add(eraseButton);
        menuButtonPanel.add(verifWinButton);
        menuButtonPanel.setBackground(Color.DARK_GRAY);

        lendTime = System.currentTimeMillis();
        menuButtonPanelOutput = lendTime - lStartTime;
    }

    private void keyBoardPanel() {

        lStartTime = System.currentTimeMillis();

        this.keyboardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        this.keyboardPanel.setBackground(Color.DARK_GRAY);
        this.keyboardPanel.addMouseListener(controller);

        for (int i = 0; i < this.boardSize; i++) {
            JButton button = new JButton(String.valueOf(i + 1));
            button.setFocusPainted(false);
            this.listOfKeyboardButtons.add(button);
            this.listOfKeyboardButtons.get(i).addMouseListener(controller);
            this.listOfKeyboardButtons.get(i).setBackground(Color.WHITE);
            this.keyboardPanel.add(this.listOfKeyboardButtons.get(i));
        }

        lendTime = System.currentTimeMillis();
        keyboardPanelOutput = lendTime - lStartTime;
    }

    public final void MenuPanel() {

        lStartTime = System.currentTimeMillis();

        menuPanel = new JPanel(new GridLayout(2, 1, 0, 50));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        menuPanel.add(menuButtonPanel);
        menuPanel.add(keyboardPanel);
        menuPanel.setPreferredSize(new Dimension(menuPanelWidth, mainFrameHeightInPX));
        menuPanel.setBackground(Color.DARK_GRAY);

        lendTime = System.currentTimeMillis();
        menuPanelOutput = lendTime - lStartTime;
    }

    public final void SudokuPanel() {
        sudokuPanel = new JPanel(new GridLayout(this.boardSize, this.boardSize));
        sudokuPanel.setPreferredSize(new Dimension(sudokuPanelWidth, mainFrameHeightInPX));
        sudokuPanel.setBorder(BorderFactory.createEmptyBorder());
        sudokuPanel.setBackground(Color.WHITE);
    }

    public final void SudokuPanelFilling() {

        lStartTime = System.currentTimeMillis();

        try {
            for (int w = 0; w < this.boardSize; w++) {
                ArrayList<JButton> list = new ArrayList<>();
                sudokuButtonTab.add(list);
                for (int h = 0; h < this.boardSize; h++) {
                    JButton sudokuBox = new JButton();
                    sudokuBox.setBackground(Color.WHITE);
                    sudokuBox.putClientProperty("row", w);
                    sudokuBox.putClientProperty("column", h);

                    if (!"0".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
                        sudokuBox.setFont(new Font("Verdana", Font.BOLD, 25));
                        sudokuBox.setText(String.valueOf(Main.getSudoku().getSudokuStringTab().get(w).get(h)));
                    } else {
                        sudokuBox.addMouseListener(controller);
                    }

//                    SudokuColorVerif(sudokuBox, w, h); // verification visuelle et manuelle du sudoku avec des couleurs
                    list.add(sudokuBox);
                    sudokuPanel.add(sudokuButtonTab.get(w).get(h));
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        lendTime = System.currentTimeMillis();
        sudokuPanelFillingOutput = lendTime - lStartTime;
    }

    public final void SudokuGUIEraser() {
        for (int w = 0; w < this.boardSize; w++) {
            for (int h = 0; h < this.boardSize; h++) {
                sudokuButtonTab.get(w).get(h).setText(null);
                sudokuPanel.remove(sudokuButtonTab.get(w).get(h));
            }
        }
        sudokuButtonTab.clear();
    }

    public void SudokuColorVerif(JButton sudokuBox, int w, int h) {
        if ("1".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
            sudokuBox.setBackground(Color.RED);
        }
        if ("2".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
            sudokuBox.setBackground(Color.YELLOW);
        }
        if ("3".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
            sudokuBox.setBackground(Color.GREEN);
        }
        if ("4".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
            sudokuBox.setBackground(Color.BLUE);
        }
        if ("5".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
            sudokuBox.setBackground(Color.DARK_GRAY);
        }
        if ("6".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
            sudokuBox.setBackground(Color.ORANGE);
        }
        if ("7".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
            sudokuBox.setBackground(Color.MAGENTA);
        }
        if ("8".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
            sudokuBox.setBackground(Color.PINK);
        }
        if ("9".equals(Main.getSudoku().getSudokuStringTab().get(w).get(h))) {
            sudokuBox.setBackground(Color.lightGray);
        }
    }

    public JButton getNewGridButton() {
        return newGridButton;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JPanel getSudokuPanel() {
        return sudokuPanel;
    }

    public ArrayList<JButton> getListOfKeyboardButtons() {
        return listOfKeyboardButtons;
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public ArrayList<ArrayList<JButton>> getSudokuButtonTab() {
        return sudokuButtonTab;
    }

    public Font getVerdana25Bold() {
        return verdana25Bold;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public JPanel getKeyboardPanel() {
        return keyboardPanel;
    }

    public JPanel getMenuButtonPanel() {
        return menuButtonPanel;
    }

    public JCheckBox getEraseButton() {
        return eraseButton;
    }

    public long getKeyboardPanelOutput() {
        return keyboardPanelOutput;
    }

    public long getMenuButtonPanelOutput() {
        return menuButtonPanelOutput;
    }

    public long getSudokuPanelFillingOutput() {
        return sudokuPanelFillingOutput;
    }

    public long getMenuPanelOutput() {
        return menuPanelOutput;
    }

    public long getMainFrameOutput() {
        return mainFrameOutput;
    }

    public long getFieldInstOutput() {
        return fieldInstOutput;
    }

    public Font getVerdana25Italic() {
        return verdana25Italic;
    }

    public JButton getVerifWinButton() {
        return verifWinButton;
    }

    public JButton getSudokuSolverButton() {
        return sudokuSolverButton;
    }

}
