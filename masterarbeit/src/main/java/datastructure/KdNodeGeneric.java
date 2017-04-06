package datastructure;

import java.util.HashSet;
import java.util.Set;

public class KdNodeGeneric <T extends Comparable<? super T>> {

    protected String[] coordinateValues;

    public KdNodeGeneric left;
    public KdNodeGeneric right;
    public KdNodeGeneric parent;
    public Set<Profile> profiles;

    public KdNodeGeneric(Profile profile, String[] coordinates, KdNodeGeneric parent) {
        // add profile
        profiles = new HashSet<>();
        profiles.add(profile);
        // set coordinate values
        coordinateValues = new String[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            coordinateValues[i] = profile.getProfileData().get(coordinates[i]);
        }
        this.parent = parent;
    }

    public String getValue(int i) {
        if (i < coordinateValues.length) {
            return coordinateValues[i];
        } else {
            throw new ArrayIndexOutOfBoundsException("Index for coordinateValues is out of bounds");
        }
    }

    public void copyData(KdNodeGeneric<T> t) {
        coordinateValues = t.coordinateValues;
        profiles = t.profiles;
    }

    public String coordinateValuesAsString() {
        String returnString = "(" + coordinateValues[0];
        for (int i = 1; i < coordinateValues.length; i++) {
            returnString += "," + coordinateValues[i];
        }
        returnString += ")";
        return returnString;
    }

    @Override
    public String toString() {
        String returnString = "v: (" + coordinateValues[0];
        for (int i = 1; i < coordinateValues.length; i++) {
            returnString += "," + coordinateValues[i];
        }
        returnString += "), #: " + profiles.size();
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
