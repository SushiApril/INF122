import java.util.ArrayList;
import java.util.Random;
import java.util.List;

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

    // **Main function to test movement**
    // chatgpted this function to test movement of the grid
    public static void main(String[] args) {
        System.out.println("Starting 2048 Grid Test: \n");
        Grid testGrid = new Grid();
        testGrid.printGrid();

        Random rand = new Random();
        int moves = 20; // Number of moves to test
        int validMoves = 0; // Count how many times movement actually happens

        String[] directions = {"Left", "Right", "Up", "Down"};

        for (int i = 0; i < moves; i++) {
            int move = rand.nextInt(4); // Random move (0 = Left, 1 = Right, 2 = Up, 3 = Down)
            System.out.println("Move " + (validMoves + 1) + ": " + directions[move]);

            boolean moved = false;
            switch (move) {
                case 0:
                    testGrid.moveLeft();
                    moved = true;
                    break;
                case 1:
                    testGrid.moveRight();
                    moved = true;
                    break;
                case 2:
                    testGrid.moveUp();
                    moved = true;
                    break;
                case 3:
                    testGrid.moveDown();
                    moved = true;
                    break;
            }

            if (moved) {
                validMoves++; // Count only successful moves
                testGrid.printGrid();

                // Check if the board is full and no more moves can be made
            }
        }

        System.out.println("Total Moves Performed: " + validMoves);
    }

}
