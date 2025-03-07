public class Main {
        public static void main(String[] args) {
            System.out.println("Hello world!");
        }}

        public class Tile {
            private int value;
        
            public Tile() {
                this.value = 0; // Default value for an empty tile
            }
        
            public Tile(int value) {
                this.value = value;
            }
        
            public int getValue() {
                return value;
            }
        
            public void setValue(int value) {
                this.value = value;
            }
        
            public boolean isEmpty() {
                return value == 0;
            }
        
            @Override
            public String toString() {
                return "Tile{" +
                        "value=" + value +
                        '}';
            }
        }