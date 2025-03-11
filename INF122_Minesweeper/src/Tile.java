public class Tile {
    private boolean isMine;  // If this tile contains a mine
    private int surroundingMines; // Number of adjacent mines
    private boolean revealed; // If this tile has been revealed

    public Tile() {
        this.isMine = false;
        this.surroundingMines = 0;
        this.revealed = false;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public int getSurroundingMines() {
        return surroundingMines;
    }

    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
    }

    @Override
    public String toString() {
        if (revealed) {
            return isMine ? "*" : Integer.toString(surroundingMines);
        } else {
            return "-";
        }
    }
}
