public abstract class GameTile {
    // Enforce all tile subclasses to define their own "empty" state
    public abstract boolean isEmpty();

    // Enforce implementation in subclasses to define string representation
    @Override
    public abstract String toString();
}
