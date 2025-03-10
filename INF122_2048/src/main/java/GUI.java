import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private Grid grid;  // The game logic class that handles all game mechanics
    private JLabel[][] tileLabels;  // Labels to display tiles on the grid
    private JLabel scorePlayer1Label, scorePlayer2Label, currentPlayerLabel;  // Labels for displaying scores and the current player
    private static final int GRID_SIZE = 4;  // Size of the grid, 4x4 for 2048
    private int currentPlayer = 1;  // Variable to track whose turn it is (1 for Player 1, 2 for Player 2)
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;

    public GUI() {
        grid = new Grid();  // Initialize the game logic
        setTitle("2048 Multiplayer Game");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupBoard();
        setupControls();
        setupScorePanel();
        setVisible(true);
    }

    private void setupBoard() {
        JPanel boardPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        tileLabels = new JLabel[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.BOLD, 24));
                label.setBorder(BorderFactory.createLineBorder(Color.black));
                label.setOpaque(true);
                tileLabels[i][j] = label;
                boardPanel.add(label);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
        updateBoard();
    }

    private void setupControls() {
        JPanel controlsPanel = new JPanel(new GridLayout(1, 5));
        String[] commands = {"Up", "Down", "Left", "Right", "New Game"};
        for (String command : commands) {
            JButton button = new JButton(command);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    performAction(e.getActionCommand());
                }
            });
            controlsPanel.add(button);
        }
        add(controlsPanel, BorderLayout.SOUTH);
    }

    private void setupScorePanel() {
        JPanel scorePanel = new JPanel(new GridLayout(1, 3));
        scorePlayer1Label = new JLabel("Player 1: 0");
        currentPlayerLabel = new JLabel("Current Turn: Player 1");
        scorePlayer2Label = new JLabel("Player 2: 0");

        scorePanel.add(scorePlayer1Label);
        scorePanel.add(currentPlayerLabel);
        scorePanel.add(scorePlayer2Label);

        add(scorePanel, BorderLayout.NORTH);
    }

    private void performAction(String command) {
        if ("New Game".equals(command)) {
            resetGame();
        } else {
            int prevScore = grid.getScore();
            
            if ("Up".equals(command)) grid.moveUp();
            else if ("Down".equals(command)) grid.moveDown();
            else if ("Left".equals(command)) grid.moveLeft();
            else if ("Right".equals(command)) grid.moveRight();
            
            int scoreGained = grid.getScore() - prevScore;
            if (currentPlayer == 1) {
                scorePlayer1 += scoreGained;
                scorePlayer1Label.setText("Player 1: " + scorePlayer1);
            } else {
                scorePlayer2 += scoreGained;
                scorePlayer2Label.setText("Player 2: " + scorePlayer2);
            }
            
            updateBoard();
            switchPlayers();
            checkGameOver();
        }
    }

    private void resetGame() {
        grid = new Grid();
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        scorePlayer1Label.setText("Player 1: 0");
        scorePlayer2Label.setText("Player 2: 0");
        currentPlayer = 1;
        currentPlayerLabel.setText("Current Turn: Player 1");
        updateBoard();
    }

    private void updateBoard() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int value = grid.getTile(i, j).getValue();
                tileLabels[i][j].setText(value > 0 ? String.valueOf(value) : "");
                tileLabels[i][j].setBackground(getTileColor(value));
            }
        }
    }

    private void switchPlayers() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        currentPlayerLabel.setText("Current Turn: Player " + currentPlayer);
    }

    private void checkGameOver() {
        if (grid.isGameOver()) {
            String winner = (scorePlayer1 > scorePlayer2) ? "Player 1 Wins!" : (scorePlayer1 < scorePlayer2) ? "Player 2 Wins!" : "It's a Draw!";
            JOptionPane.showMessageDialog(this, "Game Over! " + winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private Color getTileColor(int value) {
        switch (value) {
            case 2:    return new Color(0xeee4da);
            case 4:    return new Color(0xede0c8);
            case 8:    return new Color(0xf2b179);
            case 16:   return new Color(0xf59563);
            case 32:   return new Color(0xf67c5f);
            case 64:   return new Color(0xf65e3b);
            case 128:  return new Color(0xedcf72);
            case 256:  return new Color(0xedcc61);
            case 512:  return new Color(0xedc850);
            case 1024: return new Color(0xedc53f);
            case 2048: return new Color(0xedc22e);
            default:   return new Color(0xcdc1b4);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}