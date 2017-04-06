package exception.Trees;

import datastructure.Trees.BinaryNode;
import datastructure.Trees.BinaryNodeData;
import datastructure.Trees.Node;

public class NoParentException extends NullPointerException {

    public NoParentException(Node node) {
        super("Node " + node + " has no parent to remove");
    }

    public NoParentException(BinaryNode node) {
        super("Node " + node + " has no parent to remove");
    }

    public NoParentException(BinaryNodeData node) {
        super("Node " + node + " has no parent to remove");
    }

}
