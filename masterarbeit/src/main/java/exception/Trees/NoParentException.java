package exception.Trees;

import datastructure.Trees.Node;

public class NoParentException extends NullPointerException {

    public NoParentException(Node node) {
        super("Node " + node + " has no parent to remove");
    }

}
