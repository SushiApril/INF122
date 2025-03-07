// Tiles class which will be stored in Grid

public class Tiles {
    public int value;

    public Tiles() {
        //default value is 0 
        this.value = 0;
    }

    // get function
    public int getValue() {
        return value;
    }
    //set function
    public void setValue(int value) {
        this.value = value;
    }

    // func to see if its empty, tile is empty if value = 0
    public boolean isEmpty() {
        return value == 0;
    }
}
