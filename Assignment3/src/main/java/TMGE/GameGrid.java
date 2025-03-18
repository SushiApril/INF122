package TMGE;
import java.util.Random;

public abstract class GameGrid<T extends GameTile> {
    protected int size;
    protected T[][] grid;
    protected Random random;

    public GameGrid(int size) {
        this.size = size;
        this.grid = createGrid(size);
        this.random = new Random();
        initializeGrid();
    }

    // Factory method to be implemented in subclasses
    protected abstract T[][] createGrid(int size);

    // Initializes the grid with empty/default tiles
    protected abstract void initializeGrid();

    // Retrieves a tile at given coordinates
    public T getTile(int row, int col) {
        return grid[row][col];
    }

    // Prints the grid (generalized to work for both games)
    public void printGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Check if the game is over (to be overridden)
    public abstract boolean isGameOver();
}