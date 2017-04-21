package datastructure;

public class Triple<T, U, V> {

    private final T first;
    private final U second;
    private final V third;

    public Triple(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }

    @Override
    public String toString() {
        return "<tripple (" + getFirst() + ", " + getSecond() + ", " + getThird() + ")>";
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Triple)) {
            return false;
        }

        Triple tripleObject = (Triple) object;
        return first.equals(tripleObject.getFirst()) &&
                second.equals(tripleObject.getSecond()) &&
                third.equals(tripleObject.getThird());
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 53 * hash + (first != null ? first.hashCode() : 0);
        hash = 53 * hash + (second != null ? second.hashCode() : 0);
        hash = 53 * hash + (third != null ? third.hashCode() : 0);
        return hash;
    }

}
