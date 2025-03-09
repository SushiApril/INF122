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

    // Main function to test grid Delete later
    public static void main(String[] args) {
        System.out.println("Create 2048 Grid: \n");
        Grid testGrid = new Grid();

        // Print initial grid with two tiles
        System.out.println("Initial Grid:");
        testGrid.printGrid();

        // Generate a few more tiles to test functionality
        for (int i = 0; i < 3; i++) {
            System.out.println("Adding a new tile!");
            testGrid.generateNewTile();
            testGrid.printGrid();
        }
    }
}
