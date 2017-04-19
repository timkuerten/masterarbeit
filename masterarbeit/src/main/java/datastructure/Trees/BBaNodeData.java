package datastructure.Trees;

import exception.Trees.DataNullPointerException;

import java.util.HashSet;
import java.util.Set;

public class BBaNodeData<T extends Comparable<? super T>, U> {

    protected T key;
    protected Set<U> database;
    protected BBaNodeData<T, U> left;
    protected BBaNodeData<T, U> right;
    protected BBaNodeData<T, U> parent;
    protected int weight;

    public BBaNodeData(T key, U data) {
        this.key = key;
        database = new HashSet<>();
        insertData(data);
        weight = 1;
    }

    public BBaNodeData(T key, Set<U> database) {
        this.key = key;
        this.database = database;
        weight = 1;
    }

    public BBaNodeData(T key, U data, BBaNodeData<T, U> parent) {
        this.key = key;
        database = new HashSet<>();
        insertData(data);
        this.parent = parent;
    }

    public void insertData(U data) {
        if (data == null) {
            throw new DataNullPointerException();
        }

        database.add(data);
    }

    public boolean removeData(U data) {
        if (data == null) {
            throw new DataNullPointerException();
        }

        if (database.contains(data)) {
            database.remove(data);
            return true;
        } else {
            return false;
        }
    }

    public boolean hasEmptyDatabase() {
        return database.isEmpty();
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
        String returnString = "key: (" + key + "), w: " + weight;
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
