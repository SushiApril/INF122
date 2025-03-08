import java.util.Random;

public class Tiles {
    // Value of the tile
    public int value;

    // Default constructor: sets value to 0 (empty tile)
    public Tiles() {
        this.value = 0;
    }

    // Constructor that accepts a value for the tile
    public Tiles(int value) {
        this.value = value;
    }

    // Getter function to retrieve the value of the tile
    public int getValue() {
        return value;
    }

    // Setter function to set the value of the tile
    public void setValue(int value) {
        this.value = value;
    }

    // Function to check if the tile is empty (value = 0)
    public boolean isEmpty() {
        return value == 0;
    }

    // Checks if the tile can merge with another tile (same value and not empty)
    public boolean canMerge(Tiles otherTile) {
        return otherTile != null && this.value == otherTile.getValue() && this.value != 0;
    }

    // Generates a new tile with either a 2 or 4 value (random)
    public static Tiles generateRandomTile() {
        Random random = new Random();
        // Generate a random number, either 0 or 1
        int randomValue = random.nextInt(2);

        // Assign value 2 or 4 based on random value
        int value = (randomValue == 0) ? 2 : 4;
        
        // Return a new tile with the generated value
        return new Tiles(value);
    }

    // Resets the tile value to 0 (makes it empty)
    public void setTilesEmpty() {
        this.value = 0;
    }
}
