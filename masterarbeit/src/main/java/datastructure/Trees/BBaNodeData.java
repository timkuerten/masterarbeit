package datastructure.Trees;

import java.util.HashSet;
import java.util.Set;

public class BBaNodeData<T extends Comparable<? super T>, U extends Comparable<? super U>> {

    public T key;
    public Set<U> database;
    public BBaNodeData<T, U> left;
    public BBaNodeData<T, U> right;
    public BBaNodeData<T, U> parent;
    public int weight;

    public BBaNodeData(T key, U data) {
        this.key = key;

        weight = 1;
    }

    public BBaNodeData(T key, U data, BBaNodeData<T, U> parent) {
        this.key = key;
        database = new HashSet<>();
        database.add(data);
        this.parent = parent;
    }

    public boolean isLeaf() {
        return right == null;
    }

    public void addLeft(BBaNodeData<T, U> node) {
        left = node;
        node.parent = this;
    }

    public void addRight(BBaNodeData<T, U> node) {
        right = node;
        node.parent = this;
    }

    @Override
    public String toString() {
        String returnString = "key: (" + key +"), w: " + weight;
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

    /*
    @Override
    public String toString() {
        return "<node key: " + key + ", weight: " + weight + ">";
    }*/

}
