package dk.mtdm;

/**
 * @author Lukas
 * Used to return to values
 * @param <U>
 * @param <V>
 */

public class Pair<U,V> {
    private final U first;
    private final V second;
    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public boolean hasNull() {
        return first == null || second == null;
    }
}
