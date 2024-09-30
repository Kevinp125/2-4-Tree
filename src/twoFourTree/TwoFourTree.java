//**NO DUPLICATES IN THIS TREE

package twoFourTree;
public class TwoFourTree {
	
    private class TwoFourTreeItem { //all of this is just a private class that are all the methods for node creation.
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
        	centerChild = null;
        	centerLeftChild = null;
        	centerRightChild = null;
        	values = 1; //set values to 1 since a two node only holds one value
        	isLeaf = true; //by default when a node is created it is technically a leaf (has no children) we will update this variable whenever node becomes an internal node
        }

//        public TwoFourTreeItem(int value1, int value2) { //constructor that creates a 3-node a node with only 2 values 
//        	this.value1 = value1;
//        	this.value2 = value2;
//        	this.value3 = 0;
//        	parent = null;			//set all pointers equal to null
//        	leftChild = null;
//        	rightChild = null;
//        	centerChild = null;
//        	values = 2; //set values variable to 2 so since 3 node holds two values
//        	isLeaf = true;
//        }
//
//        public TwoFourTreeItem(int value1, int value2, int value3) { //constructor that creates a 4-node a node with only 2 values
//        	this.value1 = value1;
//        	this.value2 = value2;
//        	this.value3 = value3;
//        	parent = null;			//set all pointers equal to null
//        	leftChild = null;
//        	rightChild = null;
//        	centerLeftChild = null;
//        	centerRightChild = null;
//        	values = 3; //set values variable to 2 so since 3 node holds two values
//        	isLeaf = true; 
//        }

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
    
    
    //all functions below this are the ones we need to fill out that give the tree its functionality. The TwoFourTreeItem class is private but internal so we can use it here but we don't want outside classes calling it
    
    TwoFourTreeItem root;
    
 

    public boolean addValue(int value) {
      
    	//special case there isn't a tree at all, create a new node and add make root point ot it
    	
    	if(root == null) { 
    		TwoFourTreeItem newNode = new TwoFourTreeItem(value); 
    		root = newNode;
    		return true;
    	}//end of checking if root is empty
    	
    	
    	//Here we are going to work on traversal since we handled the special case of the null root.
    
    	TwoFourTreeItem current = root; //making current point to root so we don't lose our root when we traverse the tree
    	TwoFourTreeItem last = null; //need to keep a last variable so that once we exit the loop we have the location of where we are inserting a value can't use current because it will point to null.
    	
    	while(current != null) {
    		
    		last = current; 
    		
    		
    		if(current.isFourNode()) { //check first if what we are on is a FourNode
    			
        		splitFourNode(current); //if it is call the function that will split it.
        		
        		if(current.parent == null) {//then check if current.parent is null this means the 4 node we split was the root so in order to continue traversing to find spot we need to add value 
        			current = root; //make our current start from the new root
        		}
        		else { //else means 4 node we split was an internal node there is something above it. So that we dont have to traverse entire tree from the top just move current up one. 
        			current = current.parent; //set current to its parent
        		}
        		
        	}//end of checking if root is 4node
        	
    		
    		
    		//node we are on is a TwoNode
    		if(current.isTwoNode()){
    			if(value < current.value1) { //if value we want to insert is less than value in our two node go to the left
    				current = current.leftChild;
    			}
    			else { //else go to right
    				current = current.rightChild;
    			}
    		}
    		
    		//node we are on while traversing is a ThreeeNode
    		
    		else if(current.isThreeNode()){
    			
    			if(value < current.value1) { //if value we want to insert is less than the 3 nodes smallest value move to left
    				current = current.leftChild;
    			}
    			else if(value > current.value2){ //if value we want to insert is greater than the 3 nodes greatest value move to right
    				current = current.rightChild;
    			}
    			else { //else means the value is in between the 3 nodes two values so traverse down the center child.
    				current = current.centerChild;
    			}
    			
    		}
    		
        	
    	}//end of while loop
    	
    	
    	
    	addValueToNode(last, value);
    	return true;
    	
    	//Once we exit while loop and did all the splitting of four nodes on our way down last should be holding the node in which we have to insert our value
    	//Call our addValueToNodeWithSpace function here so that it gets placed in sorted order in whatever node it has to go in.
    
    }//end of addValue function
    
    private void splitFourNode(TwoFourTreeItem fourNode) { //function takes in our current node from addValue (since it is a 4 node) and splits it 
    	
    	//when splitting a 4 node we have to create two new nodes. A left one and a right one no matter what so doing this globally below.
    	
		TwoFourTreeItem newNodeLeft = new TwoFourTreeItem(fourNode.value1); //make a left two node with the first value in the current 4 node
		TwoFourTreeItem newNodeRight = new TwoFourTreeItem(fourNode.value3); //make a right two node with the last value in the current 4 node
	
		//below we check if the four node we got passed in is NOT a leaf. If it isn't this means it has 4 children that all need to be reassigned under the 2 new nodes we made above
		
		if(!fourNode.isLeaf) {
			newNodeLeft.leftChild = fourNode.leftChild;
    		newNodeLeft.rightChild = fourNode.centerLeftChild;
    		newNodeRight.leftChild = fourNode.centerRightChild;
    		newNodeRight.rightChild = fourNode.rightChild;
    		fourNode.leftChild.parent = newNodeLeft;
    		fourNode.centerLeftChild.parent = newNodeLeft;
    		fourNode.rightChild.parent = newNodeRight;
    		fourNode.centerRightChild.parent = newNodeRight;
    		newNodeLeft.isLeaf = false; //important to flag that these new nodes we created are no longer leaves since we linked the fournodes children to them.
    		newNodeRight.isLeaf = false;
			
		}
		
    	//Ok now that general housekeeping has been done above. Which was creating the two new nodes and reassigning to them the 4 node's children we decide below if the 4 node we are splitting is the root or internal
		
		if(fourNode.parent == null) { //if the fourNode.parent is null this means it is the root there we need to make a new root / new level in the tree
			TwoFourTreeItem newRoot = new TwoFourTreeItem(fourNode.value2); //making a new root with the 4 nodes middle value
			newRoot.leftChild = newNodeLeft; //make newRoot left child the Left two node we made above
			newRoot.rightChild = newNodeRight; //make newRoot right child the Right two node we made above
			newNodeLeft.parent = newRoot; //reparent as well
			newNodeRight.parent = newRoot;
			root = newRoot; //once everything has been split and we assigned all the children of the old root to our new twonodes we can make the newPromotedNode the new root.
			root.isLeaf = false; //change this to false since the root now has children
		}
		
		else { //else means it is an internal node
			
			if(fourNode.parent.isTwoNode()) { //if fourNode.parent is a TwoNode we only need to worry about 3 references from the parent because when we promote a value to the middle it becomes a 3 node
				if(fourNode == fourNode.parent.leftChild) { //checks if FourNode is the leftChild of the parent so we can adjust references properly.
					addValueToNode(fourNode.parent, fourNode.value2); //add to the parent node the FourNode's middle value
					fourNode.parent.leftChild = newNodeLeft;
					fourNode.parent.centerChild = newNodeRight;
					newNodeLeft.parent = fourNode.parent; 
					newNodeRight.parent = fourNode.parent;
				}
				else { //else means that the fourNode is on the right side of its parent node so the references we need to adjust change slightly
					addValueToNode(fourNode.parent, fourNode.value2);
					fourNode.parent.centerChild = newNodeLeft; //here since fourNode is on the right of the parent we need to make centerChild of the parent = newNodeLeft
					fourNode.parent.rightChild = newNodeRight; //and then make the fourNode parents rightChild = to the newNodeRight
					newNodeLeft.parent = fourNode.parent;
					newNodeRight.parent = fourNode.parent;
				}
			}
			
			else { //else means the fourNode's parent is a three node turning into a 4 node. Same logic as above check where the fournode we are splitting is in respect to the parentnode this will affect how we adjust the references.
				if(fourNode == fourNode.parent.leftChild) { 
					System.out.println("Inside 4node is leftmost child");
					addValueToNode(fourNode.parent, fourNode.value2);
					fourNode.parent.leftChild = newNodeLeft;
					fourNode.parent.centerLeftChild = newNodeRight;
					fourNode.parent.centerRightChild = fourNode.parent.centerChild;
					fourNode.parent.centerChild = null;
					newNodeLeft.parent = fourNode.parent;
					newNodeRight.parent = fourNode.parent;
				}
				
				else if(fourNode == fourNode.parent.centerChild) {
					addValueToNode(fourNode.parent, fourNode.value2);
					fourNode.parent.centerLeftChild = newNodeLeft;
					fourNode.parent.centerRightChild = newNodeRight;
					fourNode.centerChild = null;
					newNodeLeft.parent = fourNode.parent;
					newNodeRight.parent = fourNode.parent;	
				}
				
				else { //final case if the fournode we are splitting is the right child of its parent
					addValueToNode(fourNode.parent, fourNode.value2);
					fourNode.parent.rightChild = newNodeRight;
					fourNode.parent.centerRightChild = newNodeLeft;
					fourNode.parent.centerLeftChild = fourNode.parent.centerChild;
					fourNode.parent.centerChild = null;
					newNodeLeft.parent = fourNode.parent;
					newNodeRight.parent = fourNode.parent;
				}
								
			}
			
		}
		
	
    } //end of split4node function

    private void addValueToNode(TwoFourTreeItem node, int value) { //function just adds a value into a node into the right order since a node can have 3 different values. They need to remain in sorted order.
    	
    	if(node.isTwoNode()) { //if it is a twoNode it only has a value1
    		if(value < node.value1) { //check if value we want to add is less than value1 if it is shift value1 to next place over
    			node.value2 = node.value1;
    			node.value1 = value;
    		}
    		else {
    			node.value2 = value;
    		}
    		node.values++; //dont forget to increment values counter this allows us to check whether a node is a 2Node 3Node or 4Node
    	}
    	
    	//root has two items in it
    	
    	else if(node.isThreeNode()) { //if it is a 3node we know values 1 and 2 are already taken
    		
    		if(value < node.value2 && value > node.value1) { //if the value we want to insert is in between 1 and 2 
    			node.value3 = node.value2; //move to value 3 our value 2
    			node.value2 = value; //which leaves a space for the value we want to insert in the middle
    		}
    		
    		else if(value < node.value1) { //if the value we want to insert is smaller than the first value we need to shift both value 1 and 2 down 1 so that we can put the smallest value first
    			node.value3 = node.value2;
    			node.value2 = node.value1;
    			node.value1 = value;
    		}
    		else { //else just means the value we want to insert into this 3 node is the greatest one and that space by default is empty so just put it there.
    			node.value3 = value; 
    		}
    		
    		node.values++; //finally dont forget again to update how many values are now in our node.
    		
    	}
   	
    }//end of addValueToNode function
    
    
    public boolean hasValue(int value) {
    	
    	if(root == null) //if there isnt a 2-4 tree how are we going to find the value just return false
    		return false;
    	
    	TwoFourTreeItem current = root;
    	
    	while(current != null) {
    		
    		if(current.value1 == value || current.value2 == value || current.value3 == value) {
    			return true;
    		}
    		
    		else { //else means node we are on doesn't have the value we are searching for so we have to continue traversing down the tree
    			
    			//node we are on is a TwoNode
        		if(current.isTwoNode()){
        			if(value < current.value1) { //if value we want to insert is less than value in our two node go to the left
        				current = current.leftChild;
        			}
        			else { //else go to right
        				current = current.rightChild;
        			}
        		}
        		
        		//node we are on while traversing is a ThreeeNode
        		
        		else if(current.isThreeNode()){
        			
        			if(value < current.value1) { //if value we want to insert is less than the 3 nodes smallest value move to left
        				current = current.leftChild;
        			}
        			else if(value > current.value2){ //if value we want to insert is greater than the 3 nodes greatest value move to right
        				current = current.rightChild;
        			}
        			else { //else means the value is in between the 3 nodes two values so traverse down the center child.
        				current = current.centerChild;
        			}
        			
        		}
        		
        		else { //node we are traversing is a four node
        			if(value<current.value1) { //if value we are searching for is less than the left most value traverse down four nodes left child
        				current = current.leftChild;
        			}
        			else if(value > current.value3) { //if value we are searching for is greater than the fourNodes greatest value traverse down right child
        				current = current.rightChild;
        			}
        			else if(value < current.value3 && value > current.value2) { //if value is in between right most and middle value of 4 node traverse down center rightchild
        				current = current.centerRightChild;
        			}
        			else { //else only leaves the centerLeftChild
        				current = current.centerLeftChild;
        			}
        			
        		}
    			
    		}//end of big else statement for traversal
    		
    	}//end of while loop
    	
        return false; //If we exit while loop and return true never happened it is because value was never found. Return false to indicate that value isn't in the tree.
    }

    public boolean deleteValue(int value) {
    	
    	//handle all the special cases with the root first.
    	
    	if(root == null) //if we don't have a tree there is nothing to delete so just return false
    		return false; 
   
    	
    	if(root.value1 == value || root.value2 == value || root.value3 == value) { //When the value we want to delete is in the root
    		
    		if(root.isTwoNode() && root.isLeaf) { //if the root only has a single value and no children than we know that this is the last item to delete in the tree. Make tree null.
    			root = null;
    			return true;
    		}
    		
    		if(root.isThreeNode() || root.isFourNode() && root.isLeaf) { //if the root has more than 1 value and it is a leaf we can just remove value from there 
    			deleteValAndReorder(root, value); 
    			return true;
    		}
    	
    	}
    	
    	if(root.isTwoNode() && !root.isLeaf) { //if the root is a twoNode and has children we need to either borrow from a sibling or merge root with its children. We do this before entering the loop so everything else goes smoothly.
    		
    		if(root.leftChild.isTwoNode() && root.rightChild.isTwoNode()) { //if root is a twoNode we need to strengthen it. If both children are also twoNodes merge them together to make a four node
    			
    			TwoFourTreeItem newRoot = new TwoFourTreeItem(root.leftChild.value1); //make a new node and put the value in the roots left child in it
    			addValueToNode(newRoot, root.value1); //then add to that same node the value in the root
    			addValueToNode(newRoot, root.rightChild.value1); //and the value in the roots right child
    			
    			//then assign all the children of the past twoNodes to what is essentially going to be our new root
    			newRoot.leftChild = root.leftChild.leftChild; 
    			newRoot.centerLeftChild = root.leftChild.rightChild;
    			newRoot.centerRightChild = root.rightChild.leftChild;
    			newRoot.rightChild = root.rightChild.rightChild;
    			
    			if(!root.leftChild.isLeaf) { //if the left child of the old root wasnt a leaf it means it has children we need to reparent these children to point to the new root
    				newRoot.leftChild.parent = newRoot;
    				newRoot.centerLeftChild.parent = newRoot;
    				newRoot.centerRightChild.parent = newRoot;
    				newRoot.rightChild.parent = newRoot;
    			}
    			
    			root = newRoot; //finally make the root point to our newly merged root.
    		}
    		
    		
    		if(root.leftChild.values >= root.rightChild.values) { //we want to steal an element from the child that has the greater amount of elements because itll be furthest away from becoming a two node
    			
    			if(root.leftChild.isThreeNode()) { //if the leftChild has two values grab the biggest one
    				addValueToNode(root, root.leftChild.value2); //add the greater value to the root
    				deleteValAndReorder(root.leftChild, root.leftChild.value2); //make sure to delete that value from the node we are taking it from
    				root.leftChild.values--; //decrease values counter from node we are taking it from
    				root.values++; //and increase values counter for node we are putting it in
    			}
    			
    			else if(root.leftChild.isFourNode()) { //if leftChild has four values grab biggest one which is value 3
    				addValueToNode(root, root.leftChild.value3);
    				deleteValAndReorder(root.leftChild, root.leftChild.value3);
    				root.leftChild.values--;
    				root.values++;
    				
    			}
    			
    		}
    		
    		else { //else means that the roots right child has more values to steal from so we want to grab the smallest value from the right child and place it in the root
    			addValueToNode(root, root.rightChild.value1); //Don't need if statements here because there will always be the smallest value in the value1 place
    			deleteValAndReorder(root.rightChild, root.rightChild.value1);
    			root.rightChild.values--;
    			root.values++;
    
    		}
    		
    	} //end of case dealing with root being a two node and strengthening it by merging with children or borrowing from children.
    	
    	
    	//Now that we handled special cases above we can begin our loop logic below and fixing twoNodes as we go down.
    	
    	TwoFourTreeItem current = root;  
    	
    	while(current != null) {
    		
    		if(current.value1 == value || current.value2 == value || current.value3 == value) { //we found value we want to delete
    			
    			//delete value and adjust accordingly depening on the case
    			
    			if(current.isLeaf) { //if the value we want to delete is in a leaf all we have to do is delete it
    				deleteValAndReorder(current, value);
    			}
    			
    			else if(!current.isLeaf) { //this means it is an internal node so find min predecessor of the node
    				
    			}
    			
    			
    		}
    		else {
    			
    			if(current.isTwoNode()) {
    				
    				//logic to fix the twoNode as we traverse down
    			}
    			
    			//once thats fixed
    			else if(current.isThreeNode()) {
    				if(value < current.value1) { //if value we want to insert is less than the 3 nodes smallest value move to left
        				current = current.leftChild;
        			}
        			else if(value > current.value2){ //if value we want to insert is greater than the 3 nodes greatest value move to right
        				current = current.rightChild;
        			}
        			else { //else means the value is in between the 3 nodes two values so traverse down the center child.
        				current = current.centerChild;
        			}
    			}
    			
    			else if(current.isFourNode()) {
    				if(value<current.value1) { //if value we are searching for is less than the left most value traverse down four nodes left child
        				current = current.leftChild;
        			}
        			else if(value > current.value3) { //if value we are searching for is greater than the fourNodes greatest value traverse down right child
        				current = current.rightChild;
        			}
        			else if(value < current.value3 && value > current.value2) { //if value is in between right most and middle value of 4 node traverse down center rightchild
        				current = current.centerRightChild;
        			}
        			else { //else only leaves the centerLeftChild
        				current = current.centerLeftChild;
        			}
    			}	
    			    			
    		}
    		
    		
    		
    		return true;
    	}//end of while loop
    	
    	
    	
    	return false;
    }
    
    private void deleteValAndReorder(TwoFourTreeItem node, int delVal) {
    	
    	if(node.isThreeNode()) { //three node only has two values
    		if(delVal == node.value1) { //if the value we want to delete is in the first slot move the second slot to first effectively deleting it
    			node.value1 = node.value2;
    			node.value2 = 0;
    		}
    		else { //else means the value we want to delete is in the second slot so just change second slots value back to 0 and bam that number erased.
    			node.value2 = 0; 
    		}
    		
    	}
    	
    	else if(node.isFourNode()) { //if node has three values we have a couple more cases
    		if(delVal == node.value1) { //if value getting removed is the one left most shift everything to the left
    			node.value1 = node.value2;
    			node.value2 = node.value3;
    			node.value3 = 0; //set the empty space now to 0
    		}
    		else if(delVal == node.value2) { //if value we want to delete is in the middle just shift value on its right to the left one
    			node.value2 = node.value3;
    			node.value3 = 0; //set the empty spot to 0;
    		}
    		else { //else means value we are deleting is the one all the way right, so no shifting has to be done just set that place = 0;
    			node.value3 = 0;
    		}
    		
    	}
    	
    }

    public void printInOrder() {
//    	if(root != null) root.printInOrder(0);
    	
    	System.out.println("node one is: | " + root.value1 + " | " + root.value2 + " | " + root.value3 + " | ");
    	System.out.println("right child of root now is: | " + root.rightChild.value1 + " | " + root.rightChild.value2 + " | " + root.rightChild.value3 + " | ");

    	System.out.println("left child of root now is: | " + root.leftChild.value1 + " | " + root.leftChild.value2 + " | " + root.leftChild.value3 + " | ");

    	
    	
    }

    public TwoFourTree() { //constructor for the TwoFourTree class this gets called in App.Java. Initialize the root of our tree here
    	root = null;
    }
}//end of public TwoFourTree class
