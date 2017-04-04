package datastructure;

public class BBaNode {

    int key;
    BBaNode left;
    BBaNode right;
    BBaNode parent;
    int weight;

    public BBaNode(int key) {
        this.key = key;
        weight = 1;
    }

    public BBaNode(int key, BBaNode parent) {
        this(key);
        this.parent = parent;
    }

    public boolean isLeaf() {
        return right == null;
    }

    public void addLeft(BBaNode node) {
        left = node;
        node.parent = this;
    }

    public void addRight(BBaNode node) {
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
