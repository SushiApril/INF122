package TMGE.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private GridMinesweeper grid;
    private int size;
    private int numMines;
    private JPanel boardPanel;
    private JLabel playerTurnLabel;
    private JLabel playerScoresLabel;
    private int currentPlayer;
    private int[] playerScores;

    public MinesweeperGUI() {
        getUserInput(); // Get user input **before** initializing anything
        SwingUtilities.invokeLater(this::initializeGame);
    }

    private void getUserInput() {
        boolean validInput = false;
        while (!validInput) {
            try {
                String sizeInput = JOptionPane.showInputDialog(null, "Enter grid size (e.g., 5 for 5x5):", "Minesweeper Setup", JOptionPane.QUESTION_MESSAGE);
                String minesInput = JOptionPane.showInputDialog(null, "Enter number of mines:", "Minesweeper Setup", JOptionPane.QUESTION_MESSAGE);

                if (sizeInput == null || minesInput == null) {
                    System.exit(0); // Exit if user cancels
                }

                size = Integer.parseInt(sizeInput);
                numMines = Integer.parseInt(minesInput);

                if (size > 0 && numMines > 0 && numMines < size * size) {
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter positive numbers, and mines must be less than total tiles.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter numerical values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void initializeGame() {
        frame = new JFrame("Minesweeper - Multiplayer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setLayout(new BorderLayout());

        grid = new GridMinesweeper(size, numMines);
        buttons = new JButton[size][size];

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(size, size));

        currentPlayer = 0;
        playerScores = new int[2];

        playerTurnLabel = new JLabel("Player 1's Turn", SwingConstants.CENTER);
        playerScoresLabel = new JLabel("Scores - Player 1: 0 | Player 2: 0", SwingConstants.CENTER);
        
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(playerTurnLabel);
        topPanel.add(playerScoresLabel);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton(" ");
                final int row = i, col = j;
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) { // Right-click to flag
                            toggleFlag(row, col);
                        } else { // Left-click to reveal
                            handleTileClick(row, col);
                        }
                    }
                });
                boardPanel.add(buttons[i][j]);
            }
        }

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(restartButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void handleTileClick(int row, int col) {
        TileMinesweeper tile = grid.getTile(row, col);
        if (!tile.isFlagged()) { // Prevent revealing flagged tiles
            if (tile.isMine()) {
                buttons[row][col].setText("*");
                buttons[row][col].setBackground(Color.RED);
                JOptionPane.showMessageDialog(frame, "Player " + (currentPlayer + 1) + " hit a mine! Turn skipped.");
                switchTurn();
            } else {
                revealTile(row, col);
                playerScores[currentPlayer]++;
                updateScoreDisplay();
                if (grid.allNonMinesRevealed()) {
                    JOptionPane.showMessageDialog(frame, "Game Over! Player " + (getWinningPlayer() + 1) + " wins!");
                    disableButtons();
                } else {
                    switchTurn();
                }
            }
        }
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer + 1) % 2;
        playerTurnLabel.setText("Player " + (currentPlayer + 1) + "'s Turn");
    }

    private void updateScoreDisplay() {
        playerScoresLabel.setText("Scores - Player 1: " + playerScores[0] + " | Player 2: " + playerScores[1]);
    }

    private int getWinningPlayer() {
        return (playerScores[0] > playerScores[1]) ? 0 : 1;
    }

    private void toggleFlag(int row, int col) {
        TileMinesweeper tile = grid.getTile(row, col);
        if (!tile.isRevealed()) { // Only flag if the tile isn't revealed
            tile.toggleFlag();
            buttons[row][col].setText(tile.isFlagged() ? "🚩" : " ");
        }
    }

    private void revealTile(int row, int col) {
        TileMinesweeper tile = grid.getTile(row, col);
        if (!tile.isRevealed() && !tile.isFlagged()) {
            tile.reveal();
            buttons[row][col].setText(tile.getSurroundingMines() > 0 ? String.valueOf(tile.getSurroundingMines()) : "");
            buttons[row][col].setEnabled(false);
        }
    }

    private void disableButtons() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void restartGame() {
        frame.dispose();
        new MinesweeperGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MinesweeperGUI::new);
    }
}

