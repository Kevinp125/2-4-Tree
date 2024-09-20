//**NO DUPLICATES IN THIS TREE

package twoFourTree;
public class TwoFourTree {
	
    private class TwoFourTreeItem { //all of this is just a private class that are all the methods for node manipulation.
        int values;								//this variable will keep track of how many items are in a node
        int value1;                             // always exists.
        int value2;                             // exists iff the node is a 3-node or 4-node.
        int value3;                             // exists iff the node is a 4-node.
        boolean isLeaf;
        
        TwoFourTreeItem parent;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild;           // left and right child exist iff the node is a non-leaf.
        TwoFourTreeItem rightChild;          
        TwoFourTreeItem centerChild;         // center child exists iff the node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild;

        public boolean isTwoNode() {
        	if(values == 1) {
        		return true;
        	}
            return false;
        }

        public boolean isThreeNode() {
        	if(values == 2) {
        		return true;
        	}
            return false;
        }

        public boolean isFourNode() {
        	if(values == 3) {
        		return true;
        	}
            return false;
        }

        public boolean isRoot() {
        	if(parent == null) {
        		return true;
        	}
            return false;
        }
        

        public TwoFourTreeItem(int value1) { //constructor that creates a 2-node a node with only 1 value
        	this.value1 = value1;
        	this.value2 = 0; //we do this in case we want to add more values to this 2-node later on
        	this.value3 = 0;
        	parent = null;			//set all pointers equal to null
        	leftChild = null;
        	rightChild = null;
        	values = 1; //set values to 1 since a two node only holds one value
        	isLeaf = true; //by default when a node is created it is technically a leaf (has no children) we will update this variable whenever node becomes an internal node
        }

        public TwoFourTreeItem(int value1, int value2) { //constructor that creates a 3-node a node with only 2 values 
        	this.value1 = value1;
        	this.value2 = value2;
        	this.value3 = 0;
        	parent = null;			//set all pointers equal to null
        	leftChild = null;
        	rightChild = null;
        	centerChild = null;
        	values = 2; //set values variable to 2 so since 3 node holds two values
        	isLeaf = true;
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
        	isLeaf = true; 
        }

        private void printIndents(int indent) { //this is just for formatting will allow us to print 2-4 tree pretty
            for(int i = 0; i < indent; i++) System.out.printf("  ");
        }

        public void printInOrder(int indent) { //inOrder traversal of the tree
            if(!isLeaf) leftChild.printInOrder(indent + 1); //go all the way left first once we find there's a leaf we want to print value 1
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if(isThreeNode()) { //then itll return up call stack and print previous value 1's and check if we are in a 3 node with two items
                if(!isLeaf) centerChild.printInOrder(indent + 1); //if we are the centerChild is the next smallest item so call recursively on that
                printIndents(indent);
                System.out.printf("%d\n", value2); //once it is done printing value in centerchild well print the second value of before nodes
            } else if(isFourNode()) { //same for four node
                if(!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if(!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if(!isLeaf) rightChild.printInOrder(indent + 1); //once we are done with all the left stuff we can check the right child since these are greatest values and print them
        }
    }//end of private class TwoFourTreeItem 
    
    
    //all functions below this are the ones we need to fill out that give the tree its functionality. The TwoFourTreeItem class is private but internal so we can use it here but we dont want outside classes callign it
    
    TwoFourTreeItem root;

    public boolean addValue(int value) {
  
    /*Below cases all cover when we are inserting values into a brand new tree
     * We need to make the root point to first node and then add other values in that same
     * node to make it a 4 node so it can later split
     */
    	
    	//special case there isn't a tree at all
    	
    	if(root == null) { 
    		TwoFourTreeItem newNode = new TwoFourTreeItem(value); 
    		root = newNode;
    		return true;
    	}
    	
    	
    	//root only has one item in it
    	
    	if(root.isTwoNode()) {
    		if(value < root.value1) {
    			root.value2 = root.value1;
    			root.value1 = value;
    			root.values++;
    			System.out.println("added to two node values is now" + root.values);
    		}
    		else {
    			root.value2 = value;
    			root.values++;
    			System.out.println("added to two node values is now" + root.values);
    		}
   
    		return true;
    	}
    	
    	//root has two items in it
    	
    	if(root.isThreeNode()) {
    		if(value < root.value2 && value > root.value1) {
    			root.value3 = root.value2;
    			root.value2 = value;
    			root.values++;
    			System.out.println("added to two node values is now" + root.values);
    		}
    		
    		else if(value < root.value1) {
    			root.value3 = root.value2;
    			root.value2 = root.value1;
    			root.value1 = value;
    			root.values++;
    			System.out.println("added to two node values is now" + root.values);
    		}
    		else {
    			root.value3 = value;
    			root.values++;
    			System.out.println("added to two node values is now" + root.values);
    		}
    		
    		return true;
    		
    		
    	}
    	
        return false; //return false if value wasn't added successfully
    }

    public boolean hasValue(int value) {
        return false; //return false if we didnt find the value
    }

    public boolean deleteValue(int value) {
        return false; 
    }

    public void printInOrder() {
//        if(root != null) root.printInOrder(0);
    	System.out.println("Value 1 in root = " + root.value1);
    	System.out.println("Value 2 in root = " + root.value2);
    	System.out.println("Value 3 in root = " + root.value3);
    }

    public TwoFourTree() { //constructor for the TwoFourTree class this gets called in App.Java. Initialize the root of our tree here
    	root = null;
    }
}//end of public TwoFourTree class
