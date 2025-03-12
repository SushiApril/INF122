import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinesweeperGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private Grid grid;
    private int size = 5;  // Default grid size
    private int numMines = 5; // Default number of mines

    public MinesweeperGUI() {
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        
        grid = new Grid(size, numMines);
        buttons = new JButton[size][size];

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(size, size));
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton(" ");
                final int row = i, col = j;
                buttons[i][j].addActionListener(e -> handleTileClick(row, col));
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
        if (grid.isMine(row, col)) {
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

    private void revealTile(int row, int col) {
        Tile tile = grid.getTile(row, col);
        if (!tile.isRevealed()) {
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
