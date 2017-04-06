package datastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KdNodeGeneric <T extends Comparable<? super T>, U> {

    protected List<T> coordinateValues;

    public KdNodeGeneric left;
    public KdNodeGeneric right;
    public KdNodeGeneric parent;
    public Set<U> database;

    public KdNodeGeneric(U data, List<T> coordinateValues, KdNodeGeneric<T, U> parent) {
        // add data
        database = new HashSet<>();
        database.add(data);
        // set coordinate values
        this.coordinateValues = new ArrayList<>(coordinateValues);
        this.parent = parent;
    }

    public T getValue(int i) {
        if (i < coordinateValues.size()) {
            return coordinateValues.get(i);
        } else {
            throw new ArrayIndexOutOfBoundsException("Index for coordinateValues is out of bounds");
        }
    }

    public void copyData(KdNodeGeneric<T, U> kdNode) {
        coordinateValues = kdNode.coordinateValues;
        database = kdNode.database;
    }

    @Override
    public String toString() {
        String returnString = "v: " + coordinateValues + ", #: " + database.size();
        if (left != null) {
            returnString += ", l: (" + left.toString() + ")";
            returnString += ")";
        }
        if (right != null) {
            returnString += ", r: (" + right.toString() + ")";
            returnString += ")";
        }
        return returnString;
    }

}
