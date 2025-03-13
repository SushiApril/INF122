import java.util.Scanner;

public class Minesweeper {
    private Grid grid;
    boolean gameOver = false;
    Scanner scanner = new Scanner(System.in);

    public Minesweeper(int size, int numMines) {
        grid = new Grid(size, numMines);
    }

    public boolean isInteger(String str)
    {
        try{
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    public int correctNum(String n)
    {
        int num = grid.getSize();
        while (true) { 
            
            if(n.equals("q"))
            {
                gameOver = true;
                break;
            }
            else if(isInteger(n))
            {
                int numbers = Integer.parseInt(n);
                if(numbers >= num - num && numbers <= num - 1)
                {
                    return numbers;
                }
                else
                {
                    System.out.println("Put in a valid answer");
                    n = scanner.nextLine();
                }
            }
            else 
            {
                System.out.println("Put in a valid answer");
                n = scanner.nextLine();
            }

            // if(n.equals("0"))
            // {
            //     return Integer.parseInt(n);
            // }
            // else if(n.equals("1"))
            // {
            //     return Integer.parseInt(n);
            // }
            // else if(n.equals("2"))
            // {
            //     return Integer.parseInt(n);
            // }
            // else if(n.equals("3"))
            // {
            //     return Integer.parseInt(n);
            // }
            // else if(n.equals("4"))
            // {
            //     return Integer.parseInt(n);
            // }
            // else if(n.equals("q"))
            // {
            //     gameOver = true;
            //     break;
            // }
            // else 
            // {
            //     System.out.println("Put in a valid answer");
            //     n = scanner.nextLine();
            // }
        }
        return num;
    }

    public void startGame() {

        while (!gameOver) {
            grid.printBoard();

            System.out.print("Enter row (0 to " + (grid.getSize() - 1) + ") or q to quit: ");
            String sRow = scanner.nextLine();
            int row = correctNum(sRow);

            if(row == grid.getSize())
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
        System.out.println("How many rows and columns do you want in the game?");
        Scanner s = new Scanner(System.in);
        String r = s.nextLine();
        int ro = 0;
        while(true)
        {
            try{
                ro = Integer.parseInt(r);
                break;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Put in a valid answer");
                r = s.nextLine();
            }
        }

        System.out.println("How many mines do you want in the game?");
        String m = s.nextLine();
        int mi = 0;

        while(true)
        {
            try{
                mi = Integer.parseInt(m);
                break;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Put in a valid answer");
                m = s.nextLine();
            }
        }

        Minesweeper game = new Minesweeper(ro, mi); 
        game.startGame();
    }
}
