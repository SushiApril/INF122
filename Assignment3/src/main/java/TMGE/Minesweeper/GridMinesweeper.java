package TMGE.Minesweeper;

import TMGE.GameGrid;
import java.util.Random;

public class GridMinesweeper extends GameGrid<TileMinesweeper> {
    private int size;         // Grid size as a variable instead of constant
    private int numMines;     // Number of mines as a variable

    public GridMinesweeper(int size, int numMines) {
        super(size); // Pass size to the parent GameGrid constructor
        this.size = size;   // Store size in instance variable
        this.numMines = numMines; // Store mine count in instance variable
        initializeGrid();
    }

    @Override
    protected TileMinesweeper[][] createGrid(int size) {
        return new TileMinesweeper[size][size];
    }

    @Override
    protected void initializeGrid() {
        // Initialize the grid with empty tiles
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = new TileMinesweeper();
            }
        }

        // Place mines randomly on the grid
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < numMines) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if (!grid[x][y].isMine()) {
                grid[x][y].setMine(true);
                minesPlaced++;
            }
        }

        // Set the number of surrounding mines for each tile
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (!grid[x][y].isMine()) {
                    int surroundingMines = countSurroundingMines(x, y);
                    grid[x][y].setSurroundingMines(surroundingMines);
                }
            }
        }
    }

    private int countSurroundingMines(int x, int y) {
        int mineCount = 0;

        // Check all surrounding cells (8 neighbors)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;

                // Skip the current tile
                if (dx == 0 && dy == 0) continue;

                // Check if the neighbor is within bounds and has a mine
                if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                    if (grid[nx][ny].isMine()) {
                        mineCount++;
                    }
                }
            }
        }

        return mineCount;
    }

    public TileMinesweeper getTile(int row, int col) {
        return grid[row][col];
    }

    public void revealTile(int row, int col) {
        TileMinesweeper tile = grid[row][col];
        if (!tile.isRevealed()) {
            tile.reveal();
        }
    }

    public int getSize() {
        return size;  // Return the size of the grid
    }
    
    public boolean isMine(int row, int col) {
        return getTile(row, col).isMine();
    }

    public boolean allNonMinesRevealed() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!grid[i][j].isMine() && !grid[i][j].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    @Override
    public boolean isGameOver() {
        // Check if a mine has been revealed (game over if true)
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[x][y].isMine() && grid[x][y].isRevealed()) {
                    return true;  // Game over if a mine has been revealed
                }
            }
        }
        return false;  // If no mine has been revealed, game is not over
    }
}
