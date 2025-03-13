import java.util.Random;

public class Grid {
    private Tile[][] grid;
    private int size;
    private int numMines;

    public Grid(int size, int numMines) {
        this.size = size;
        this.numMines = numMines;
        this.grid = new Tile[size][size];

        // Initialize the grid with empty tiles
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Tile();
            }
        }

        // Place mines randomly on the grid
        placeMines();

        // Calculate the number of surrounding mines for each tile
        calculateNumbers();
    }
    public int getSize()
    {
        return size;
    }

    private void placeMines() {
        Random random = new Random();
        int placedMines = 0;

        while (placedMines < numMines) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);

            if (!grid[row][col].isMine()) {
                grid[row][col].setMine(true);
                placedMines++;
            }
        }
    }

    private void calculateNumbers() {
        // Iterate through every tile and calculate surrounding mines
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].isMine()) {
                    continue;
                }

                int mineCount = 0;
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        int newRow = i + x;
                        int newCol = j + y;
                        if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                            if (grid[newRow][newCol].isMine()) {
                                mineCount++;
                            }
                        }
                    }
                }
                grid[i][j].setSurroundingMines(mineCount);
            }
        }
    }

    public Tile getTile(int row, int col) {
        return grid[row][col];
    }

    public void revealTile(int row, int col) {
        Tile tile = getTile(row, col);
        if (!tile.isRevealed()) {
            tile.reveal();

            if(tile.getSurroundingMines() == 0)
            {
                for(int x = -1; x <= 1; ++x)
                {
                    for(int y = -1; y <= 1; ++y)
                    {
                        int nRow = row + x;
                        int nCol = col + y;
                        if (nRow >= 0 && nRow < size && nCol >= 0 && nCol < size)
                        {
                            revealAdjacentTiles(nRow, nCol);
                        }
                    }
                }
            }
        }
    }

    public void revealAdjacentTiles(int row, int col) {
        Tile tile = getTile(row, col);
        
        if (tile.isRevealed() || tile.isMine()) {
            return;
        }
        
        revealTile(row, col);
        
        if (tile.getSurroundingMines() == 0) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    int newRow = row + x;
                    int newCol = col + y;
                    if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                        revealAdjacentTiles(newRow, newCol); // Recursive call
                    }
                }
            }
        }
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
}
