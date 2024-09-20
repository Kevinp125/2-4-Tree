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
    	
    	/*
    	 * Other special case when the root needs to be split. This adds a height of one to the tree that is why it is a special case. 
    	   Whenever we split a four node down the tree the middle value just gets added to an above two or three node since we split 4 nodes on the way down.
    	   Probably make a function that splits these internal four nodes as it is different than the root case.
    	 */
    	
    	if(root.isFourNode()) {
    		
    		//if root is a fourNode it means it has four children we need to split it up
    		
    		TwoFourTreeItem newNodeLeft = new TwoFourTreeItem(root.value1); //make a left two node with the first value in the current 4 node
    		TwoFourTreeItem newNodeRight = new TwoFourTreeItem(root.value3); //make a right two node with the last value in the current 4 node
    	
    		/*
    		 * In below if statement we are checking if we need to do child reassignment. Since the root is a 4 node it must have 4 children.
    		 * Therefore if only one of the root's children is null all of them are null, therefore we cant acess root.XChild because it will be
    		 * null. So if any child ISNT null we reassign all the childs of the old root (4 node) that we are in the process of splitting to the two
    		 * new nodes we created above. Reparent everything as well.
    		 */
    		
    		if(root.leftChild != null) { 
    			newNodeLeft.leftChild = root.leftChild;
        		newNodeLeft.rightChild = root.centerLeftChild;
        		newNodeRight.leftChild = root.centerRightChild;
        		newNodeRight.rightChild = root.rightChild;
        		root.leftChild.parent = newNodeLeft;
        		root.centerLeftChild.parent = newNodeLeft;
        		root.rightChild.parent = newNodeRight;
        		root.centerRightChild.parent = newNodeRight;
    			
    		}
    		
    		//Once that is done let us promote the middle value of the root 4 node
    		
    		TwoFourTreeItem newPromotedNode = new TwoFourTreeItem(root.value2); //promoting middle value
    		newPromotedNode.leftChild = newNodeLeft; //make new promoted values left child the Left two node we made above
    		newPromotedNode.rightChild = newNodeRight; //make new promoted values right child the Right two node we made above
    		newNodeLeft.parent = newPromotedNode; //reparent as well
    		newNodeRight.parent = newPromotedNode;
    		root = newPromotedNode; //once everything has been split and we assigned all the children of the old root to our new twonodes we can make the newPromotedNode the new root.
    	}
    	
    	
    	//Here we are going to work on traversal since we handled the special case of the root
    	
    	TwoFourTreeItem current = root;
    	
    	while(current != null) {
   
    		//as we go down to find where to place the value we split the other 4 nodes		
    		
    	}
    	
        return false; //return false if value wasn't added successfully
    }

    private void addValueToNodeWithSpace(TwoFourTreeItem node, int value) { //this function I made to clean up the insert function a bit still need to figure out how to make generic. It is to add a value to a node that has space
    	
    	if(root.isTwoNode()) {
    		if(value < root.value1) {
    			root.value2 = root.value1;
    			root.value1 = value;
    			root.values++;
    		}
    		else {
    			root.value2 = value;
    			root.values++;
    		}
    	}
    	
    	//root has two items in it
    	
    	if(root.isThreeNode()) {
    		if(value < root.value2 && value > root.value1) {
    			root.value3 = root.value2;
    			root.value2 = value;
    			root.values++;
    		}
    		
    		else if(value < root.value1) {
    			root.value3 = root.value2;
    			root.value2 = root.value1;
    			root.value1 = value;
    			root.values++;
    		}
    		else {
    			root.value3 = value;
    			root.values++;
    		}
    		
    	}
   	
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
