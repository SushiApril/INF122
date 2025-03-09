import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Scanner;

public class Grid {
    private static final int SIZE = 4; //4x4 for 2048, can be changed for larger grid

    private Tiles[][] grid;
    private Random random;

    public Grid(){
        grid = new Tiles[SIZE][SIZE];
        random = new Random();
        initializeGrid();
        generateNewTile();
        generateNewTile();
    }

    // Initalizes board to be filled with tiles(can be empty or contain value)
    private void initializeGrid(){
        for(int x = 0; x < SIZE; x++){
            for(int y = 0; y < SIZE; y++){
                grid[x][y] = new Tiles();
            }
        }
    }
    
    // Helper function to obtain list with position of empty tiles, used for adding random new tiles
    private List<int[]> getEmptyTiles(){
        List<int[]> emptyTiles = new ArrayList<int[]>();
        for(int x = 0; x < SIZE; x++){
            for(int y = 0; y < SIZE; y++){
                if(grid[x][y].isEmpty()){
                    emptyTiles.add(new int[]{x, y});
                }
            }
        }
        return emptyTiles;
    }

    // Gets list of empty tile positions and randomly selects and sets value to random 2-4
    private void generateNewTile(){
        List<int[]> emptyTiles = getEmptyTiles();
        if(emptyTiles.size() > 0){
            int[] position = emptyTiles.get(random.nextInt(emptyTiles.size()));
            grid[position[0]][position[1]].setValue(random.nextInt(2) == 0 ? 2 : 4);
        }
    }
    
    // Function to print grid for testing purposes Delete later
    public void printGrid() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                System.out.printf("%4d", grid[x][y].getValue()); // Print tile value directly
            }
            System.out.println();
        }
        System.out.println();
    }

    //MOVEMENT heavily relies on shiftAndMergeRow function.

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
            //Reverses each row so shift and merge can be used
            Tiles[] reversed = reverseArray(grid[x]);
            if (shiftAndMergeRow(reversed)) {
                moved = true;
            }
            //Reverses back to original order
            grid[x] = reverseArray(reversed);
        }
        if (moved) generateNewTile();
    }
    // Vertical movement is done by getting each column(maybe reversed), shifting and merging the tiles, then column back into the grid.
    // MOves up by chnging to column then shift and merging
    public void moveUp() {
        boolean moved = false;
        for (int y = 0; y < SIZE; y++) {
            Tiles[] column = getColumn(y);
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
            Tiles[] column = getColumn(y);
            Tiles[] reversed = reverseArray(column);
            if (shiftAndMergeRow(reversed)) {
                moved = true;
            }
            setColumn(y, reverseArray(reversed));
        }
        if (moved) generateNewTile();
    }

    // Shifts and merges the tiles in a row and returns true if any movement happened
    private boolean shiftAndMergeRow(Tiles[] line) {
        boolean moved = false;
        Tiles[] newLine = new Tiles[SIZE];
        // "merged" tracks whether a tile at that index in newLine has already merged
        boolean[] merged = new boolean[SIZE];
        
        // Initialize newLine with emopty tiles
        for (int i = 0; i < SIZE; i++) {
            newLine[i] = new Tiles();
            merged[i] = false;
        }

        int insertPos = 0;
        for (int i = 0; i < SIZE; i++) {
            if (!line[i].isEmpty()) {
                int currentValue = line[i].getValue();
                //check if the previous tile can merge and hasnt yet
                if (insertPos > 0 && !merged[insertPos - 1] && newLine[insertPos - 1].getValue() == currentValue) {
                    newLine[insertPos - 1].doubleValue();
                    merged[insertPos - 1] = true;
                    moved = true;
                } else {
                    //mark movement if tile is not in the same position
                    if (i != insertPos) {
                        moved = true;
                    }
                    newLine[insertPos] = new Tiles(currentValue);
                    insertPos++;
                }
            }
        }
        // copy and merge line back
        System.arraycopy(newLine, 0, line, 0, SIZE);
        return moved;
    }

    // Gets column as an array to be worked on by shift and merge
    private Tiles[] getColumn(int y) {
        Tiles[] column = new Tiles[SIZE];
        for (int x = 0; x < SIZE; x++) {
            column[x] = grid[x][y];
        }
        return column;
    }

    // Sets column back onto the grid
    private void setColumn(int y, Tiles[] column) {
        for (int x = 0; x < SIZE; x++) {
            grid[x][y] = column[x];
        }
    }

    // Reverses array for movement (right and don)
    private Tiles[] reverseArray(Tiles[] array) {
        Tiles[] reversed = new Tiles[SIZE];
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
public boolean isGameOver() {
    if (!getEmptyTiles().isEmpty()) {
        return false; // If there are empty tiles, game isn't over
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

    // **Main function to test movement**
    // chatgpted this function to test movement of the grid
    public static void main(String[] args) {Scanner scanner = new Scanner(System.in);
        Grid gameGrid = new Grid();
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

                if (gameGrid.hasWon()) {
                    System.out.println("Congratulations! You won the game!");
                    gameOver = true;
                } else if (gameGrid.isGameOver()) {
                    System.out.println("Game Over! No more moves left.");
                    gameOver = true;
                }
            }
        }

        scanner.close();
    }
    }


