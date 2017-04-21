package datastructure;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

        if (object == this) {
            return true;
        }

        Pair pairObject = (Pair) object;
        return new EqualsBuilder().
                append(first, pairObject.getFirst()).
                append(second, pairObject.getSecond()).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(first.hashCode()).
                append(second.hashCode()).
                toHashCode();
    }

}
