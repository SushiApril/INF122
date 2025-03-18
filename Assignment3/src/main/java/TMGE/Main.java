package TMGE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import TMGE.game2048.GUI;
import TMGE.Minesweeper.MinesweeperGUI;


public class Main extends JFrame {
    public Main() {
        setTitle("Tile Matching Game Environment");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        JButton minesweeperButton = new JButton("Minesweeper");
        minesweeperButton.addActionListener(e -> launchMinesweeper());

        JButton game2048Button = new JButton("2048");
        game2048Button.addActionListener(e -> launch2048());

        add(minesweeperButton);
        add(game2048Button);

        setVisible(true);
    }

    private void launchMinesweeper() {
        System.out.println("Launching Minesweeper...");
        try {
            new MinesweeperGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void launch2048() {
        try {
            new GUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}