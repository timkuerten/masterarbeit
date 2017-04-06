package datastructure.Trees;

import exception.Trees.DataNullPointerException;

import java.util.HashSet;
import java.util.Set;

public class BinaryNodeData<T extends Comparable<? super T>, U extends Comparable<? super T>> extends BinaryNode<T> {

    private Set<U> database;

    public BinaryNodeData(T key, U data) {
        super(key);
        this.database = new HashSet<>();
        addData(data);
    }

    protected void addData(U data) {
        if (data == null) {
            throw new DataNullPointerException();
        }
        this.database.add(data);
    }

    public Set<U> getData() {
        return database;
    }

}
