import java.util.Random;

public class Grid {
    private static final int SIZE = 4; //4x4 for 2048, can be changed for larger grid

    private Tiles[][] grid;
    private Random random;

    public Grid(){
        grid = new Tiles[SIZE][SIZE];
        random = new Random();
        initializeGrid();
    }

    // Initalizes board to be filled with tiles(can be empty or contain value)
    private void initializeGrid(){
        for(int x = 0; x < SIZE; x++){
            for(int y = 0; y < SIZE; y++){
                grid[x][y] = new Tiles();
            }
        }
    }
    
}