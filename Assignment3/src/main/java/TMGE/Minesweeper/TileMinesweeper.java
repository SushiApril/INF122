package TMGE.Minesweeper;
import TMGE.GameTile;

public class TileMinesweeper extends GameTile {
    private boolean isMine;
    private int surroundingMines;
    private boolean revealed;
    private boolean flagged;

    // Constructor for an empty tile or a mine tile
    public TileMinesweeper() {
        this.isMine = false;
        this.surroundingMines = 0;
        this.revealed = false;
        this.flagged = false;
    }

    @Override
    public boolean isEmpty() {
        return !revealed;  // Tile is considered empty if not revealed
    }

    @Override
    public String toString() {
        if (flagged) {
            return "F";  // Show flagged tile
        } else if (!revealed) {
            return "X";  // Show unopened tile
        } else if (isMine) {
            return "*";  // Show mine
        } else {
            return Integer.toString(surroundingMines);  // Show surrounding mines count
        }
    }

    // Getters and Setters
    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }
    
    

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
        this.flagged = false; // Remove flag if revealed
    }

    public void toggleFlag() {
        this.flagged = !this.flagged;
    }

    public int getSurroundingMines() {
        return surroundingMines;
    }

    public void setSurroundingMines(int count) {
        this.surroundingMines = count;
    }
    public boolean isFlagged() {
        return flagged;
    }

}
