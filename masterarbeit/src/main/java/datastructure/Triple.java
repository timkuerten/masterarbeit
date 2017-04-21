package datastructure;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

        if (object == this) {
            return true;
        }

        Triple tripleObject = (Triple) object;
        return new EqualsBuilder().
                append(first, tripleObject.getFirst()).
                append(second, tripleObject.getSecond()).
                append(third, tripleObject.getThird()).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(first.hashCode()).
                append(second.hashCode()).
                append(third.hashCode()).
                toHashCode();
    }

}
