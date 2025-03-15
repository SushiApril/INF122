public class Tile {
    private boolean isMine;
    private int surroundingMines;
    private boolean revealed;
    private boolean flagged; // New flag variable

    public Tile() {
        this.isMine = false;
        this.surroundingMines = 0;
        this.revealed = false;
        this.flagged = false; // Initialize as not flagged
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void toggleFlag() {
        this.flagged = !this.flagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
        this.flagged = false; // Remove flag if revealed
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

    public void setSurroundingMines(int count) {
        this.surroundingMines = count;
    }
}
