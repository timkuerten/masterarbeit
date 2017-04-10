package datastructure.Trees;

import datastructure.Pair;

import java.util.List;
import java.util.Set;

public interface KdTree<T extends Comparable<? super T>, U> {

    KdNode<T, U> insert(List<T> key, U data);

    boolean contains(List<T> key, U data);

    Set<U> get(int dim, T value);

    Set<U> get(int dim, T minValue, T maxValue);

    Set<U> get(List<Pair<T, T>> searchValues);

    boolean delete(List<T> key, U data);

    KdNode<T, U> findNode(List<T> key);

    T findMin(int dim);

}
