import java.util.Scanner;

public class Minesweeper {
    private Grid grid;

    public Minesweeper(int size, int numMines) {
        grid = new Grid(size, numMines);
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            grid.printBoard();

            System.out.print("Enter row (0 to " + (grid.getSize() - 1) + "): ");
            int row = scanner.nextInt();

            System.out.print("Enter column (0 to " + (grid.getSize() - 1) + "): ");
            int col = scanner.nextInt();
            

            if (row < 0 || row >= grid.getSize() || col < 0 || col >= grid.getSize()) {
                System.out.println("Invalid coordinates. Try again.");
                continue;
            }

            if (grid.isMine(row, col)) {
                System.out.println("Game Over! You hit a mine.");
                gameOver = true;
            } else {
                grid.revealTile(row, col);
                if (grid.allNonMinesRevealed()) {
                    System.out.println("Congratulations, you win!");
                    gameOver = true;
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Minesweeper game = new Minesweeper(5, 5); // 5x5 grid with 5 mines
        game.startGame();
    }
}
