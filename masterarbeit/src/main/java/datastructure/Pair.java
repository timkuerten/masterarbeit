package datastructure;

public class Pair<T, U> {

    private final T first;
    private final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "<tripple (" + getFirst() + ", " + getSecond() + ")>";
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pair)) {
            return false;
        }

        Pair pairObject = (Pair) object;
        return first.equals(pairObject.getFirst()) &&
                second.equals(pairObject.getSecond());
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + (first != null ? first.hashCode() : 0);
        hash = 31 * hash + (second != null ? second.hashCode() : 0);
        return hash;
    }

}
