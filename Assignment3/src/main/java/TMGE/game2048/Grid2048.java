package TMGE.game2048;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Scanner;
import TMGE.GameGrid;

public class Grid2048 extends GameGrid<Tile2048> {
    private static final int SIZE = 4;

    private Random random;
    private int score = 0; // Score for the current player

    // Constructor adapted to call super(SIZE) so GameGrid can initialize the grid array
    public Grid2048() {
        super(SIZE);
        random = new Random();
        initializeGrid();
        generateNewTile();
        generateNewTile();
    }

    // Creates the 2D array of Tile2048 (overriding from GameGrid)
    @Override
    protected Tile2048[][] createGrid(int size) {
        return new Tile2048[size][size];
    }

    // Initializes board to be filled with tiles (can be empty or contain value)
    @Override
    protected void initializeGrid() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                grid[x][y] = new Tile2048();
            }
        }
    }
    


    public Tile2048 getTile(int x, int y) {
        return grid[x][y];
    }

    public int getScore() {
        return score;
    }

    // Helper function to obtain list with position of empty tiles, used for adding random new tiles
    private List<int[]> getEmptyTiles() {
        List<int[]> emptyTiles = new ArrayList<>();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (grid[x][y].isEmpty()) {
                    emptyTiles.add(new int[]{x, y});
                }
            }
        }
        return emptyTiles;
    }

    // Gets list of empty tile positions and randomly selects and sets value to random 2-4
    private void generateNewTile() {
        List<int[]> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            int[] position = emptyTiles.get(random.nextInt(emptyTiles.size()));
            grid[position[0]][position[1]].setValue(random.nextInt(2) == 0 ? 2 : 4);
        }
    }

    // Function to print grid for testing purposes
    public void printGrid() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                System.out.printf("%4d", grid[x][y].getValue()); // Print tile value directly
            }
            System.out.println();
        }
        System.out.println("Current Score: " + getScore()); // Display current score
    }
    

    // Movement heavily relies on shiftAndMergeRow function
    // Moves left
    public void moveLeft() {
        boolean moved = false;
        for (int x = 0; x < SIZE; x++) {
            if (shiftAndMergeRow(grid[x])) {
                moved = true;
            }
        }
        if (moved) generateNewTile();
    }
    // Moves right
    public void moveRight() {
        boolean moved = false;
        for (int x = 0; x < SIZE; x++) {
            // Reverses each row so shift and merge can be used
            Tile2048[] reversed = reverseArray(grid[x]);
            if (shiftAndMergeRow(reversed)) {
                moved = true;
            }
            // Reverses back to original order
            grid[x] = reverseArray(reversed);
        }
        if (moved) generateNewTile();
    }
    

    // Moves up by changing to column then shift and merging
    public void moveUp() {
        boolean moved = false;
        for (int y = 0; y < SIZE; y++) {
            Tile2048[] column = getColumn(y);
            if (shiftAndMergeRow(column)) {
                moved = true;
            }
            setColumn(y, column);
        }
        if (moved) generateNewTile();
    }

    // Reverse column, move up, reverse back
    public void moveDown() {
        boolean moved = false;
        for (int y = 0; y < SIZE; y++) {
            Tile2048[] column = getColumn(y);
            Tile2048[] reversed = reverseArray(column);
            if (shiftAndMergeRow(reversed)) {
                moved = true;
            }
            setColumn(y, reverseArray(reversed));
        }
        if (moved) generateNewTile();
    }
    

    // Shifts and merges the tiles in a row and returns true if any movement happened
    private boolean shiftAndMergeRow(Tile2048[] line) {
        boolean moved = false;
        Tile2048[] newLine = new Tile2048[SIZE];
        boolean[] merged = new boolean[SIZE];

        for (int i = 0; i < SIZE; i++) {
            newLine[i] = new Tile2048();
            merged[i] = false;
        }

        int insertPos = 0;
        for (int i = 0; i < SIZE; i++) {
            if (!line[i].isEmpty()) {
                int currentValue = line[i].getValue();
                if (insertPos > 0 && !merged[insertPos - 1] && newLine[insertPos - 1].getValue() == currentValue) {
                    newLine[insertPos - 1].doubleValue();
                    score += newLine[insertPos - 1].getValue(); // Award 8 points for each merge to the current player
                    merged[insertPos - 1] = true;
                    moved = true;
                } else {
                    if (i != insertPos) {
                        moved = true;
                    }
                    newLine[insertPos] = new Tile2048(currentValue);
                    insertPos++;
                }
            }
        }
        System.arraycopy(newLine, 0, line, 0, SIZE);
        return moved;
    }
    
    // Gets column as an array to be worked on by shift and merge
    private Tile2048[] getColumn(int y) {
        Tile2048[] column = new Tile2048[SIZE];
        for (int x = 0; x < SIZE; x++) {
            column[x] = grid[x][y];
        }
        return column;
    }



    // Sets column back onto the grid
    private void setColumn(int y, Tile2048[] column) {
        for (int x = 0; x < SIZE; x++) {
            grid[x][y] = column[x];
        }
    }

    // Reverses array for movement (right and down)
    private Tile2048[] reverseArray(Tile2048[] array) {
        Tile2048[] reversed = new Tile2048[SIZE];
        for (int i = 0; i < SIZE; i++) {
            reversed[i] = array[SIZE - 1 - i];
        }
        return reversed;
    }

    // Checks if player has won the game (tile reaches 2048)
    public boolean hasWon() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (grid[x][y].getValue() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }
    // Checks if no more moves are possible (Game Over condition)
    @Override
    public boolean isGameOver() {
        if (!getEmptyTiles().isEmpty()) {
            return false; // If there are empty tiles, the game isn't over
        }

        // Check if any moves can still be made
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                int currentValue = grid[x][y].getValue();

                // Check adjacent tiles
                if (x > 0 && grid[x - 1][y].getValue() == currentValue) return false; // Left
                if (x < SIZE - 1 && grid[x + 1][y].getValue() == currentValue) return false; // Right
                if (y > 0 && grid[x][y - 1].getValue() == currentValue) return false; // Up
                if (y < SIZE - 1 && grid[x][y + 1].getValue() == currentValue) return false; // Down
            }
        }

        return true; // No moves left
    }


    // Main function to test movement of the grid
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grid2048 gameGrid = new Grid2048();
        boolean gameOver = false;

        System.out.println("Welcome to 2048!");
        gameGrid.printGrid();

        while (!gameOver) {
            System.out.print("Enter move (w=Up, a=Left, s=Down, d=Right, q=Quit): ");
            String input = scanner.nextLine().trim().toLowerCase();

            boolean moved = false;

            switch (input) {
                case "w":
                    gameGrid.moveUp();
                    moved = true;
                    break;
                case "a":
                    gameGrid.moveLeft();
                    moved = true;
                    break;
                case "s":
                    gameGrid.moveDown();
                    moved = true;
                    break;
                case "d":
                    gameGrid.moveRight();
                    moved = true;
                    break;
                case "q":
                    System.out.println("Game Over! You quit.");
                    gameOver = true;
                    continue;
                default:
                    System.out.println("Invalid input. Use w, a, s, d to move, or q to quit.");
                    continue;
            }

            if (moved) {
                gameGrid.printGrid();
                System.out.println("Score: " + gameGrid.getScore());  // Show updated score

                if (gameGrid.hasWon()) {
                    System.out.println("Congratulations! You won the game!");
                    System.out.println("Final Score: " + gameGrid.getScore());
                    gameOver = true;
                } else if (gameGrid.isGameOver()) {
                    System.out.println("Game Over! No more moves left.");
                    System.out.println("Final Score: " + gameGrid.getScore());
                    gameOver = true;
                }
            }
        }

        scanner.close();
    }
}
