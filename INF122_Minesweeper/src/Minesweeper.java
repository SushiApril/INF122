import java.util.Scanner;

public class Minesweeper {
    private Grid grid;
    boolean gameOver = false;
    Scanner scanner = new Scanner(System.in);
    boolean player = true;

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

        }
        return num;
    }

    public void startGame() {

        while (!gameOver) {
            grid.printBoard();

            if(player)
            {
                System.out.println("Player 1's turn");
            }
            else
            {
                System.out.println("Player 2's turn");
            }
            System.out.println("Do you  want to click on a tile(c) or flag a tile(f)?");
            String input = scanner.nextLine();

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
            if(input.equals("c"))
            {
                if (grid.isMine(row, col)) {
                    if(player)
                    {
                        System.out.println("Game Over! Player 1 hit the mine. Player 2 wins");
                        grid.revealTile(row, col);
                        gameOver = true;
                        grid.printBoard();
                    }
                    else
                    {
                        System.out.println("Game Over! Player 2 hit the mine. Player 1 wins");
                        grid.revealTile(row, col);
                        gameOver = true;
                        grid.printBoard();
                    }
                    
                } else {
                    grid.revealTile(row, col);
                    if (grid.allNonMinesRevealed()) {
                        if(player)
                        {
                            System.out.println("Congratulations, you win player 1!");
                            gameOver = true;
                            grid.printBoard();
                        }
                        else
                        {
                            System.out.println("Congratulations, you win player 2!");
                            gameOver = true;
                            grid.printBoard();
                        }
                        
                    }
                }
            }
            else if(input.equals("f"))
            {
                grid.getTile(row, col).setFlag();
            }
            
            player = !player;
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
