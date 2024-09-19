package twoFourTree;
public class TwoFourTree {
	
    private class TwoFourTreeItem { //all of this is just a private class that are all the methods for node manipulation.
        int values = 1;								//this variable will keep track of how many items are in a node
        int value1 = 0;                             // always exists.
        int value2 = 0;                             // exists iff the node is a 3-node or 4-node.
        int value3 = 0;                             // exists iff the node is a 4-node.
        boolean isLeaf = true;
        
        TwoFourTreeItem parent;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild;           // left and right child exist iff the node is a non-leaf.
        TwoFourTreeItem rightChild;          
        TwoFourTreeItem centerChild;         // center child exists iff the node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild;

        public boolean isTwoNode() {
            return false;
        }

        public boolean isThreeNode() {
            return false;
        }

        public boolean isFourNode() {
            return false;
        }

        public boolean isRoot() {
            return false;
        }

        public TwoFourTreeItem(int value1) { //constructor that creates a 2-node a node with only 1 value
        	this.value1 = value1;
        	parent = null;			//set all pointers equal to null
        	leftChild = null;
        	rightChild = null;
        	values = 1; //set values to 1 since a two node only holds one value
        }

        public TwoFourTreeItem(int value1, int value2) { //constructor that creates a 3-node a node with only 2 values 
        	this.value1 = value1;
        	this.value2 = value2;
        	parent = null;			//set all pointers equal to null
        	leftChild = null;
        	rightChild = null;
        	centerChild = null;
        	values = 2; //set values variable to 2 so since 3 node holds two values
        }

        public TwoFourTreeItem(int value1, int value2, int value3) { //constructor that creates a 4-node a node with only 2 values
        	this.value1 = value1;
        	this.value2 = value2;
        	this.value3 = value3;
        	parent = null;			//set all pointers equal to null
        	leftChild = null;
        	rightChild = null;
        	centerLeftChild = null;
        	centerRightChild = null;
        	values = 3; //set values variable to 2 so since 3 node holds two values
        }

        private void printIndents(int indent) {
            for(int i = 0; i < indent; i++) System.out.printf("  ");
        }

        public void printInOrder(int indent) {
            if(!isLeaf) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if(isThreeNode()) {
                if(!isLeaf) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
            } else if(isFourNode()) {
                if(!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if(!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if(!isLeaf) rightChild.printInOrder(indent + 1);
        }
    }

    TwoFourTreeItem root = null;

    public boolean addValue(int value) {
        return false;
    }

    public boolean hasValue(int value) {
        return false;
    }

    public boolean deleteValue(int value) {
        return false;
    }

    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() { //constructor for the TwoFourTree class this gets called in App.Java. Will probably initialize the root or what not in here

    }
}
