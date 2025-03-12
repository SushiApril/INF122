import java.util.Scanner;

public class Minesweeper {
    private Grid grid;
    boolean gameOver = false;
    Scanner scanner = new Scanner(System.in);

    public Minesweeper(int size, int numMines) {
        grid = new Grid(size, numMines);
    }

    public int correctNum(String n)
    {
        int num = 5;
        while (true) { 
            if(n.equals("0"))
            {
                return Integer.parseInt(n);
            }
            else if(n.equals("1"))
            {
                return Integer.parseInt(n);
            }
            else if(n.equals("2"))
            {
                return Integer.parseInt(n);
            }
            else if(n.equals("3"))
            {
                return Integer.parseInt(n);
            }
            else if(n.equals("4"))
            {
                return Integer.parseInt(n);
            }
            else if(n.equals("q"))
            {
                gameOver = true;
                break;
            }
            else 
            {
                System.out.println("Put in a valid answer");
                n = scanner.nextLine();
            }
        }
        return num;
    }

    public void startGame() {

        while (!gameOver) {
            grid.printBoard();

            System.out.print("Enter row (0 to " + (grid.getSize() - 1) + ") or q to quit: ");
            String sRow = scanner.nextLine();
            int row = correctNum(sRow);

            if(row == 5)
            {
                break;
            }

            System.out.print("Enter column (0 to " + (grid.getSize() - 1) + ") or q to quit: ");
            String sCol = scanner.nextLine();
            int col = correctNum(sCol);
            

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
