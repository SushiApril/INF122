import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private Grid grid;
    private int size;
    private int numMines;
    private JPanel boardPanel;

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
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        
        grid = new Grid(size, numMines);
        buttons = new JButton[size][size];

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(size, size));
        
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
        
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(restartButton, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }

    private void handleTileClick(int row, int col) {
        Tile tile = grid.getTile(row, col);
        if (!tile.isFlagged()) { // Prevent revealing flagged tiles
            if (tile.isMine()) {
                buttons[row][col].setText("*");
                buttons[row][col].setBackground(Color.RED);
                JOptionPane.showMessageDialog(frame, "Game Over! You hit a mine.");
                disableButtons();
            } else {
                revealTile(row, col);
                if (grid.allNonMinesRevealed()) {
                    JOptionPane.showMessageDialog(frame, "Congratulations! You win!");
                    disableButtons();
                }
            }
        }
    }

    private void toggleFlag(int row, int col) {
        Tile tile = grid.getTile(row, col);
        if (!tile.isRevealed()) { // Only flag if the tile isn't revealed
            tile.toggleFlag();
            buttons[row][col].setText(tile.isFlagged() ? "🚩" : " ");
        }
    }

    private void revealTile(int row, int col) {
        Tile tile = grid.getTile(row, col);
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
