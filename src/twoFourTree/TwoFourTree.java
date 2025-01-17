/*
 * Composed by:
 * Kevin Pereda
 * COP3503C Tues, Thurs 6:00-7:15p.m
 * Dr.Gerber
 */
package twoFourTree;

public class TwoFourTree {

	private class TwoFourTreeItem { // all of this is just a private class that are all the methods for node
																	// creation.
		int values; // this variable will keep track of how many items are in a node
		int value1; // always exists.
		int value2; // exists iff the node is a 3-node or 4-node.
		int value3; // exists iff the node is a 4-node.
		boolean isLeaf;

		TwoFourTreeItem parent; // parent exists iff the node is not root.
		TwoFourTreeItem leftChild; // left and right child exist iff the node is a non-leaf.
		TwoFourTreeItem rightChild;
		TwoFourTreeItem centerChild; // center child exists iff the node is a non-leaf 3-node.
		TwoFourTreeItem centerLeftChild; // center-left and center-right children exist iff the node is a non-leaf
																			// 4-node.
		TwoFourTreeItem centerRightChild;

		public boolean isTwoNode() {
			if (values == 1) {
				return true;
			}
			return false;
		}

		public boolean isThreeNode() {
			if (values == 2) {
				return true;
			}
			return false;
		}

		public boolean isFourNode() {
			if (values == 3) {
				return true;
			}
			return false;
		}

		public boolean isRoot() {
			if (parent == null) {
				return true;
			}
			return false;
		}

		public TwoFourTreeItem(int value1) { // constructor that creates a 2-node a node with only 1 value
			this.value1 = value1;
			this.value2 = 0; // we do this in case we want to add more values to this 2-node later on
			this.value3 = 0;
			parent = null; // set all pointers equal to null
			leftChild = null;
			rightChild = null;
			centerChild = null;
			centerLeftChild = null;
			centerRightChild = null;
			values = 1; // set values to 1 since a two node only holds one value
			isLeaf = true; // by default when a node is created it is technically a leaf (has no children)
											// we will update this variable whenever node becomes an internal node
		}

		private void printIndents(int indent) { // this is just for formatting will allow us to print 2-4 tree pretty
			for (int i = 0; i < indent; i++)
				System.out.printf("  ");
		}

		public void printInOrder(int indent) { // inOrder traversal of the tree
			if (!isLeaf)
				leftChild.printInOrder(indent + 1); // go all the way left first once we find there's a leaf we want to print
																						// value 1
			printIndents(indent);
			System.out.printf("%d\n", value1);
			if (isThreeNode()) { // then itll return up call stack and print previous value 1's and check if we
														// are in a 3 node with two items
				if (!isLeaf)
					centerChild.printInOrder(indent + 1); // if we are the centerChild is the next smallest item so call
																								// recursively on that
				printIndents(indent);
				System.out.printf("%d\n", value2); // once it is done printing value in centerchild well print the second value
																						// of before nodes
			} else if (isFourNode()) { // same for four node
				if (!isLeaf)
					centerLeftChild.printInOrder(indent + 1);
				printIndents(indent);
				System.out.printf("%d\n", value2);
				if (!isLeaf)
					centerRightChild.printInOrder(indent + 1);
				printIndents(indent);
				System.out.printf("%d\n", value3);
			}
			if (!isLeaf)
				rightChild.printInOrder(indent + 1); // once we are done with all the left stuff we can check the right child
																							// since these are greatest values and print them
		}
	}// end of private class TwoFourTreeItem

	// all functions below this are the ones we need to fill out that give the tree
	// its functionality. The TwoFourTreeItem class is private but internal so we
	// can use it here but we don't want outside classes calling it

	TwoFourTreeItem root;

	public boolean addValue(int value) {

		// special case there isn't a tree at all, create a new node and add make root
		// point ot it

		if (root == null) {
			TwoFourTreeItem newNode = new TwoFourTreeItem(value);
			root = newNode;
			return true;
		} // end of checking if root is empty

		// Here we are going to work on traversal since we handled the special case of
		// the null root.

		TwoFourTreeItem current = root; // making current point to root so we don't lose our root when we traverse the
																		// tree
		TwoFourTreeItem last = null; // need to keep a last variable so that once we exit the loop we have the
																	// location of where we are inserting a value can't use current because it will
																	// point to null.

		while (current != null) {

			last = current;

			if (current.isFourNode()) { // check first if what we are on is a FourNode

				splitFourNode(current); // if it is call the function that will split it.

				if (current.parent == null) {// then check if current.parent is null this means the 4 node we split was the
																			// root so in order to continue traversing to find spot we need to add value
					current = root; // make our current start from the new root
				} else { // else means 4 node we split was an internal node there is something above it.
									// So that we dont have to traverse entire tree from the top just move current
									// up one.
					current = current.parent; // set current to its parent
				}

			} // end of checking if root is 4node

			// node we are on is a TwoNode
			if (current.isTwoNode()) {
				if (value < current.value1) { // if value we want to insert is less than value in our two node go to the left
					current = current.leftChild;
				} else { // else go to right
					current = current.rightChild;
				}
			}

			// node we are on while traversing is a ThreeeNode

			else if (current.isThreeNode()) {

				if (value < current.value1) { // if value we want to insert is less than the 3 nodes smallest value move to
																			// left
					current = current.leftChild;
				} else if (value > current.value2) { // if value we want to insert is greater than the 3 nodes greatest value
																							// move to right
					current = current.rightChild;
				} else { // else means the value is in between the 3 nodes two values so traverse down
									// the center child.
					current = current.centerChild;
				}

			}

		} // end of while loop

		addValueToNode(last, value);
		return true;

		// Once we exit while loop and did all the splitting of four nodes on our way
		// down last should be holding the node in which we have to insert our value
		// Call our addValueToNodeWithSpace function here so that it gets placed in
		// sorted order in whatever node it has to go in.

	}// end of addValue function

	private void splitFourNode(TwoFourTreeItem fourNode) { // function takes in our current node from addValue (since it
																													// is a 4 node) and splits it

		// when splitting a 4 node we have to create two new nodes. A left one and a
		// right one no matter what so doing this globally below.

		TwoFourTreeItem newNodeLeft = new TwoFourTreeItem(fourNode.value1); // make a left two node with the first value in
																																				// the current 4 node
		TwoFourTreeItem newNodeRight = new TwoFourTreeItem(fourNode.value3); // make a right two node with the last value in
																																					// the current 4 node

		// below we check if the four node we got passed in is NOT a leaf. If it isn't
		// this means it has 4 children that all need to be reassigned under the 2 new
		// nodes we made above

		if (!fourNode.isLeaf) {
			newNodeLeft.leftChild = fourNode.leftChild;
			newNodeLeft.rightChild = fourNode.centerLeftChild;
			newNodeRight.leftChild = fourNode.centerRightChild;
			newNodeRight.rightChild = fourNode.rightChild;
			fourNode.leftChild.parent = newNodeLeft;
			fourNode.centerLeftChild.parent = newNodeLeft;
			fourNode.rightChild.parent = newNodeRight;
			fourNode.centerRightChild.parent = newNodeRight;
			newNodeLeft.isLeaf = false; // important to flag that these new nodes we created are no longer leaves since
																	// we linked the fournodes children to them.
			newNodeRight.isLeaf = false;

		}

		// Ok now that general housekeeping has been done above. Which was creating the
		// two new nodes and reassigning to them the 4 node's children we decide below
		// if the 4 node we are splitting is the root or internal

		if (fourNode.parent == null) { // if the fourNode.parent is null this means it is the root there we need to
																		// make a new root / new level in the tree
			TwoFourTreeItem newRoot = new TwoFourTreeItem(fourNode.value2); // making a new root with the 4 nodes middle value
			newRoot.leftChild = newNodeLeft; // make newRoot left child the Left two node we made above
			newRoot.rightChild = newNodeRight; // make newRoot right child the Right two node we made above
			newNodeLeft.parent = newRoot; // reparent as well
			newNodeRight.parent = newRoot;
			root = newRoot; // once everything has been split and we assigned all the children of the old
											// root to our new twonodes we can make the newPromotedNode the new root.
			root.isLeaf = false; // change this to false since the root now has children
		}

		else { // else means it is an internal node

			if (fourNode.parent.isTwoNode()) { // if fourNode.parent is a TwoNode we only need to worry about 3 references
																					// from the parent because when we promote a value to the middle it becomes a
																					// 3 node
				if (fourNode == fourNode.parent.leftChild) { // checks if FourNode is the leftChild of the parent so we can
																											// adjust references properly.
					addValueToNode(fourNode.parent, fourNode.value2); // add to the parent node the FourNode's middle value
					fourNode.parent.leftChild = newNodeLeft;
					fourNode.parent.centerChild = newNodeRight;
					newNodeLeft.parent = fourNode.parent;
					newNodeRight.parent = fourNode.parent;
				} else { // else means that the fourNode is on the right side of its parent node so the
									// references we need to adjust change slightly
					addValueToNode(fourNode.parent, fourNode.value2);
					fourNode.parent.centerChild = newNodeLeft; // here since fourNode is on the right of the parent we need to
																											// make centerChild of the parent = newNodeLeft
					fourNode.parent.rightChild = newNodeRight; // and then make the fourNode parents rightChild = to the
																											// newNodeRight
					newNodeLeft.parent = fourNode.parent;
					newNodeRight.parent = fourNode.parent;
				}
			}

			else if (fourNode.parent.isThreeNode()) { // else means the fourNode's parent is a three node turning into a 4
																								// node. Same logic as above check where the fournode we are splitting
																								// is in respect to the parentnode this will affect how we adjust the
																								// references.
				if (fourNode == fourNode.parent.leftChild) {
					addValueToNode(fourNode.parent, fourNode.value2);
					fourNode.parent.leftChild = newNodeLeft;
					fourNode.parent.centerLeftChild = newNodeRight;
					fourNode.parent.centerRightChild = fourNode.parent.centerChild;
					fourNode.parent.centerChild = null;
					newNodeLeft.parent = fourNode.parent;
					newNodeRight.parent = fourNode.parent;
				}

				else if (fourNode == fourNode.parent.centerChild) {
					addValueToNode(fourNode.parent, fourNode.value2);
					fourNode.parent.centerLeftChild = newNodeLeft;
					fourNode.parent.centerRightChild = newNodeRight;
					fourNode.centerChild = null;
					newNodeLeft.parent = fourNode.parent;
					newNodeRight.parent = fourNode.parent;
				}

				else { // final case if the fournode we are splitting is the right child of its parent
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

	} // end of split4node function

	private void addValueToNode(TwoFourTreeItem node, int value) { // function just adds a value into a node into the
																																	// right order since a node can have 3 different
																																	// values. They need to remain in sorted order.

		if (node.isTwoNode()) { // if it is a twoNode it only has a value1
			if (value < node.value1) { // check if value we want to add is less than value1 if it is shift value1 to
																	// next place over
				node.value2 = node.value1;
				node.value1 = value;
			} else {
				node.value2 = value;
			}
			node.values++; // dont forget to increment values counter this allows us to check whether a
											// node is a 2Node 3Node or 4Node
		}

		// root has two items in it

		else if (node.isThreeNode()) { // if it is a 3node we know values 1 and 2 are already taken

			if (value < node.value2 && value > node.value1) { // if the value we want to insert is in between 1 and 2
				node.value3 = node.value2; // move to value 3 our value 2
				node.value2 = value; // which leaves a space for the value we want to insert in the middle
			}

			else if (value < node.value1) { // if the value we want to insert is smaller than the first value we need to
																			// shift both value 1 and 2 down 1 so that we can put the smallest value first
				node.value3 = node.value2;
				node.value2 = node.value1;
				node.value1 = value;
			} else { // else just means the value we want to insert into this 3 node is the greatest
								// one and that space by default is empty so just put it there.
				node.value3 = value;
			}

			node.values++; // finally dont forget again to update how many values are now in our node.

		}

	}// end of addValueToNode function

	public boolean hasValue(int value) {

		if (root == null) // if there isnt a 2-4 tree how are we going to find the value just return false
			return false;

		TwoFourTreeItem current = root;

		while (current != null) {

			if (current.value1 == value || current.value2 == value || current.value3 == value) {
				return true;
			}

			else { // else means node we are on doesn't have the value we are searching for so we
							// have to continue traversing down the tree

				// node we are on is a TwoNode
				if (current.isTwoNode()) {
					if (value < current.value1) { // if value we want to insert is less than value in our two node go to the left
						current = current.leftChild;
					} else { // else go to right
						current = current.rightChild;
					}
				}

				// node we are on while traversing is a ThreeeNode

				else if (current.isThreeNode()) {

					if (value < current.value1) { // if value we want to insert is less than the 3 nodes smallest value move to
																				// left
						current = current.leftChild;
					} else if (value > current.value2) { // if value we want to insert is greater than the 3 nodes greatest value
																								// move to right
						current = current.rightChild;
					} else { // else means the value is in between the 3 nodes two values so traverse down
										// the center child.
						current = current.centerChild;
					}

				}

				else { // node we are traversing is a four node
					if (value < current.value1) { // if value we are searching for is less than the left most value traverse down
																				// four nodes left child
						current = current.leftChild;
					} else if (value > current.value3) { // if value we are searching for is greater than the fourNodes greatest
																								// value traverse down right child
						current = current.rightChild;
					} else if (value < current.value3 && value > current.value2) { // if value is in between right most and middle
																																					// value of 4 node traverse down center
																																					// rightchild
						current = current.centerRightChild;
					} else { // else only leaves the centerLeftChild
						current = current.centerLeftChild;
					}

				}

			} // end of big else statement for traversal

		} // end of while loop

		return false; // If we exit while loop and return true never happened it is because value was
									// never found. Return false to indicate that value isn't in the tree.
	}

	public boolean deleteValue(int value) {

		// handle all the special cases with the root first.

		if (root == null) // if we don't have a tree there is nothing to delete so just return false
			return false;

		else if (root.value1 == value || root.value2 == value || root.value3 == value) { // When the value we want to delete
																																											// is in the root

			if (root.isTwoNode() && root.isLeaf) { // if the root only has a single value and no children than we know that
																							// this is the last item to delete in the tree. Make tree null.
				root = null;
				return true;
			}

			else if ((root.isThreeNode() || root.isFourNode()) && root.isLeaf) { // if the root has more than 1 value and it
																																						// is a leaf we can just remove value from
																																						// there
				deleteValAndReorder(root, value);
				return true;
			}

			else if (root.isTwoNode() && !root.isLeaf) { // if the root is a twoNode and has children we need to either borrow
																										// from a sibling or merge root with its children before deleting
																										// the value

				if (root.leftChild.isTwoNode() && root.rightChild.isTwoNode()) { // if root is a twoNode we need to strengthen
																																					// it. If both children are also twoNodes
																																					// merge them together to make a four node

					TwoFourTreeItem newRoot = new TwoFourTreeItem(root.leftChild.value1); // make a new node and put the value in
																																								// the roots left child in it
					addValueToNode(newRoot, root.value1); // then add to that same node the value in the root
					addValueToNode(newRoot, root.rightChild.value1); // and the value in the roots right child

					// then assign all the children of the past twoNodes to what is essentially
					// going to be our new root

					if (!root.leftChild.isLeaf) {
						newRoot.leftChild = root.leftChild.leftChild;
						newRoot.centerLeftChild = root.leftChild.rightChild;
						newRoot.centerRightChild = root.rightChild.leftChild;
						newRoot.rightChild = root.rightChild.rightChild;
						newRoot.isLeaf = false;
					}

					if (!newRoot.isLeaf) {
						// if the left child of the old root wasnt a leaf it means it has children we
						// need to reparent these children to point to the new root
						newRoot.leftChild.parent = newRoot;
						newRoot.centerLeftChild.parent = newRoot;
						newRoot.centerRightChild.parent = newRoot;
						newRoot.rightChild.parent = newRoot;

						if (newRoot.value1 == value) {
							TwoFourTreeItem maxNodeInLeft = findClosestInLeftSubtree(newRoot.leftChild, value);
							if (maxNodeInLeft.isThreeNode()) {
								deleteValAndReorder(newRoot, newRoot.value1);
								addValueToNode(newRoot, maxNodeInLeft.value2);
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value2);
							}

							else if (maxNodeInLeft.isFourNode()) {
								deleteValAndReorder(newRoot, newRoot.value1);
								addValueToNode(newRoot, maxNodeInLeft.value3);
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value3);
							}
						}

						if (newRoot.value2 == value) {
							TwoFourTreeItem maxNodeInLeft = findClosestInLeftSubtree(newRoot.centerLeftChild, value);
							if (maxNodeInLeft.isThreeNode()) {
								deleteValAndReorder(newRoot, newRoot.value2);
								addValueToNode(newRoot, maxNodeInLeft.value2);
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value2);
							}

							else if (maxNodeInLeft.isFourNode()) {
								deleteValAndReorder(newRoot, newRoot.value2);
								addValueToNode(newRoot, maxNodeInLeft.value3);
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value3);
							}
						}

						if (newRoot.value3 == value) {
							TwoFourTreeItem maxNodeInLeft = findClosestInLeftSubtree(newRoot.centerRightChild, value);
							if (maxNodeInLeft.isThreeNode()) {
								deleteValAndReorder(newRoot, newRoot.value3);
								addValueToNode(newRoot, maxNodeInLeft.value2);
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value2);
							}

							else if (maxNodeInLeft.isFourNode()) {
								deleteValAndReorder(newRoot, newRoot.value3);
								addValueToNode(newRoot, maxNodeInLeft.value3);
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value3);
							}
						}

					} else {
						deleteValAndReorder(newRoot, value);
					}

					root = newRoot; // finally make the root point to our newly merged root.

					return true;

				}

				else { // else means value we want to delete is in root but it has strong children so
								// just find in order predecessor and replace it

					TwoFourTreeItem maxNodeInLeft = findClosestInLeftSubtree(root.leftChild, value);

					if (maxNodeInLeft.isThreeNode()) {
						addValueToNode(root, maxNodeInLeft.value2);
						deleteValAndReorder(root, root.value2);
						deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value2);
					}

					else if (maxNodeInLeft.isFourNode()) {
						addValueToNode(root, maxNodeInLeft.value3);
						deleteValAndReorder(root, root.value2);
						deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value3);
					}

					return true;
				}

			}
		}

		else if (root.isTwoNode() && !root.isLeaf) { // now check anyways even if there isnt a value to delete in the root
																									// if we need to merge root since its children are weak so we dont
																									// have problems later

			if (root.leftChild.isTwoNode() && root.rightChild.isTwoNode()) { // if root is a twoNode we need to strengthen it.
																																				// If both children are also twoNodes merge them
																																				// together to make a four node

				TwoFourTreeItem newRoot = new TwoFourTreeItem(root.leftChild.value1); // make a new node and put the value in
																																							// the roots left child in it
				addValueToNode(newRoot, root.value1); // then add to that same node the value in the root
				addValueToNode(newRoot, root.rightChild.value1); // and the value in the roots right child

				// then assign all the children of the past twoNodes to what is essentially
				// going to be our new root
				if (!root.leftChild.isLeaf) {
					newRoot.leftChild = root.leftChild.leftChild;
					newRoot.centerLeftChild = root.leftChild.rightChild;
					newRoot.centerRightChild = root.rightChild.leftChild;
					newRoot.rightChild = root.rightChild.rightChild;
					newRoot.isLeaf = false;
				}

				if (!newRoot.isLeaf) { // if the left child of the old root wasnt a leaf it means it has children we
																// need to reparent these children to point to the new root
					newRoot.leftChild.parent = newRoot;
					newRoot.centerLeftChild.parent = newRoot;
					newRoot.centerRightChild.parent = newRoot;
					newRoot.rightChild.parent = newRoot;
				}

				root = newRoot; // finally make the root point to our newly merged root.

			}
		}

		// Now that we handled special cases above we can begin our loop logic below and
		// fixing twoNodes as we go down.

		TwoFourTreeItem current = root;

		while (current != null) {

			if (current.parent != null) {

				if (current.isTwoNode()) {

					boolean mergePerformed = fixTwoNode(current);

					if (mergePerformed) { // fixTwoNode returns a boolean on whether or not a merge was performed. If
																// there was it is best to move current back one to its parent before we
																// continue deletion process because tree / node structure was changed
						current = current.parent;
					}
				}

			}

			if (current.value1 == value || current.value2 == value || current.value3 == value) { // we found value we want to
																																														// delete

				if (current.isLeaf) { // if the value we want to delete is in a leaf all we have to do is delete it
					deleteValAndReorder(current, value);
					return true; // value was deleted exit

				}

				else if (!current.isLeaf) { // this means it is an internal node so find highest number in left subtrtee of
																		// node replace it with value we are deleting and delete the other location of
																		// that number

					TwoFourTreeItem maxNodeInLeft = null;

					if (current.isThreeNode() && current.value2 == value) {

						maxNodeInLeft = findClosestInLeftSubtree(current.centerChild, value);

					}

					else if (current.isThreeNode() && current.value1 == value) {

						maxNodeInLeft = findClosestInLeftSubtree(current.leftChild, value);

					}

					else if (current.isFourNode() && current.value1 == value) {

						maxNodeInLeft = findClosestInLeftSubtree(current.leftChild, value);

					}

					else if (current.isFourNode() && current.value2 == value) {

						maxNodeInLeft = findClosestInLeftSubtree(current.centerLeftChild, value);

					}

					else if (current.isFourNode() && current.value3 == value) {

						maxNodeInLeft = findClosestInLeftSubtree(current.centerRightChild, value);

					}

					if (current.value1 == value || current.value2 == value || current.value3 == value) {

						if (current.isThreeNode() && current.value1 == value) {
							if (maxNodeInLeft.isThreeNode()) {
								current.value1 = maxNodeInLeft.value2;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value2);

							}
							if (maxNodeInLeft.isFourNode()) {
								current.value1 = maxNodeInLeft.value3;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value3);

							}
						}

						else if (current.isThreeNode() && current.value2 == value) {
							if (maxNodeInLeft.isThreeNode()) {
								current.value2 = maxNodeInLeft.value2;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value2);

							}
							if (maxNodeInLeft.isFourNode()) {
								current.value2 = maxNodeInLeft.value3;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value3);
							}

						}

						else if (current.isFourNode() && current.value1 == value) {
							if (maxNodeInLeft.isThreeNode()) {
								current.value1 = maxNodeInLeft.value2;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value2);

							}
							if (maxNodeInLeft.isFourNode()) {
								current.value1 = maxNodeInLeft.value3;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value3);
							}

						}

						else if (current.isFourNode() && current.value2 == value) {
							if (maxNodeInLeft.isThreeNode()) {
								current.value2 = maxNodeInLeft.value2;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value2);

							}
							if (maxNodeInLeft.isFourNode()) {
								current.value2 = maxNodeInLeft.value3;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value3);
							}

						}

						else if (current.isFourNode() && current.value3 == value) {
							if (maxNodeInLeft.isThreeNode()) {
								current.value3 = maxNodeInLeft.value2;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value2);

							}
							if (maxNodeInLeft.isFourNode()) {
								current.value3 = maxNodeInLeft.value3;
								deleteValAndReorder(maxNodeInLeft, maxNodeInLeft.value3);
							}

						}

					}

				} // end of if bracket that identifies node we are in to delete something isnt a
					// leaf

			} // end of if brackets that identify if the node we are on has value we wish to
				// delete

			else { // else means node we are on does not have the value we want to delete so we
							// keep traversing

				if (current.isTwoNode()) {
					if (value < current.value1) {
						current = current.leftChild;
					} else {
						current = current.rightChild;
					}
				}

				else if (current.isThreeNode()) {
					if (value < current.value1) { // if value we want to insert is less than the 3 nodes smallest value move to
																				// left
						current = current.leftChild;
					} else if (value > current.value2) { // if value we want to insert is greater than the 3 nodes greatest value
																								// move to right
						current = current.rightChild;
					} else { // else means the value is in between the 3 nodes two values so traverse down
										// the center child.
						current = current.centerChild;
					}
				}

				else if (current.isFourNode()) {
					if (value < current.value1) { // if value we are searching for is less than the left most value traverse down
																				// four nodes left child
						current = current.leftChild;
					} else if (value > current.value3) { // if value we are searching for is greater than the fourNodes greatest
																								// value traverse down right child
						current = current.rightChild;
					} else if (value < current.value3 && value > current.value2) { // if value is in between right most and middle
																																					// value of 4 node traverse down center
																																					// rightchild
						current = current.centerRightChild;
					} else { // else only leaves the centerLeftChild
						current = current.centerLeftChild;
					}
				}

			}

		} // end of while loop

		return false; // if we exit while loop and never returned true we were return false meaning
									// value was never in the tree
	}

	private void deleteValAndReorder(TwoFourTreeItem node, int delVal) {

		if (node.isThreeNode()) { // three node only has two values
			if (delVal == node.value1) { // if the value we want to delete is in the first slot move the second slot to
																		// first effectively deleting it
				node.value1 = node.value2;
				node.value2 = 0;
			} else { // else means the value we want to delete is in the second slot so just change
								// second slots value back to 0 and bam that number erased.
				node.value2 = 0;
			}
			node.values--;
		}

		else if (node.isFourNode()) { // if node has three values we have a couple more cases
			if (delVal == node.value1) { // if value getting removed is the one left most shift everything to the left
				node.value1 = node.value2;
				node.value2 = node.value3;
				node.value3 = 0; // set the empty space now to 0
			} else if (delVal == node.value2) { // if value we want to delete is in the middle just shift value on its right
																					// to the left one
				node.value2 = node.value3;
				node.value3 = 0; // set the empty spot to 0;
			} else { // else means value we are deleting is the one all the way right, so no shifting
								// has to be done just set that place = 0;
				node.value3 = 0;
			}

			node.values--;

		}

	}

	private TwoFourTreeItem findClosestInLeftSubtree(TwoFourTreeItem subTreeNode, int delVal) { // function will be used
																																															// to determine what the
																																															// closet number is to the
																																															// one we are trying to
																																															// delete so we can swap
																																															// them. Will return a
																																															// reference to a
																																															// TwoFourTreeItem node so
																																															// we can manipulate it in
																																															// delete function

		TwoFourTreeItem current = subTreeNode;
		TwoFourTreeItem last = null;

		while (current != null) { // since we passed in the Nodes proper subtree we want to find the highest value
															// of we just need to keep going right every single time

			last = current;

			if (current.isTwoNode()) { // **IMPLEMENT fixTwoNode In this function so we can fix any twoNodes on the way
																	// down to find the closest node to the one we are deleting
				boolean mergePerformed = fixTwoNode(current);
				if (mergePerformed) {
					current = current.parent;
				}
			}

			if (current.isTwoNode()) {
				current = current.rightChild;
			} else if (current.isThreeNode()) {
				if (delVal <= current.value1) { // if value we want to insert is less than the 3 nodes smallest value move to
																				// left
					current = current.leftChild;
				} else if (delVal > current.value2) { // if value we want to insert is greater than the 3 nodes greatest value
																							// move to right

					current = current.rightChild;
				} else { // else means the value is in between the 3 nodes two values so traverse down
									// the center child.
					current = current.centerChild;
				}
			}

			else if (current.isFourNode()) {
				if (delVal <= current.value1) { // if value we are searching for is less than the left most value traverse down
																				// four nodes left child
					current = current.leftChild;
				} else if (delVal > current.value3) { // if value we are searching for is greater than the fourNodes greatest
																							// value traverse down right child
					current = current.rightChild;
				} else if (delVal < current.value3 && delVal > current.value2) { // if value is in between right most and middle
																																					// value of 4 node traverse down center
																																					// rightchild
					current = current.centerRightChild;
				} else { // else only leaves the centerLeftChild
					current = current.centerLeftChild;
				}
			}

		} // once we exit this loop last should be storing the rightmost leaf of our
			// subtree which will contain whether it be values1, 2, 3 our closest value to
			// value we are trying to delete

		return last;
	}

	private boolean fixTwoNode(TwoFourTreeItem node) { // this function will get passed in the current node we are on in
																											// our delVal function that is a twoNode that needs strengthening.

		boolean mergePerformed = false;

		if (node.parent.isTwoNode()) {

			if (node.parent.rightChild == node) {

				if (node.parent.leftChild.isThreeNode()) {
					addValueToNode(node.parent, node.parent.leftChild.value2);
					addValueToNode(node, node.parent.value2);

					if (!node.parent.leftChild.isLeaf) {
						node.centerChild = node.leftChild;
						node.leftChild = node.parent.leftChild.rightChild;
						node.leftChild.parent = node;
						node.parent.leftChild.rightChild = node.parent.leftChild.centerChild;
						node.parent.leftChild.centerChild = null;
					}
					deleteValAndReorder(node.parent.leftChild, node.parent.leftChild.value2);
					deleteValAndReorder(node.parent, node.parent.value2);

				} else if (node.parent.leftChild.isFourNode()) {
					addValueToNode(node.parent, node.parent.leftChild.value3);
					addValueToNode(node, node.parent.value2);
					if (!node.parent.leftChild.isLeaf) {
						node.centerChild = node.leftChild;
						node.leftChild = node.parent.leftChild.rightChild;
						node.leftChild.parent = node;
						node.parent.leftChild.rightChild = node.parent.leftChild.centerRightChild;
						node.parent.leftChild.centerChild = node.parent.leftChild.centerLeftChild;
						node.parent.leftChild.centerRightChild = null;
						node.parent.leftChild.centerLeftChild = null;
					}
					deleteValAndReorder(node.parent.leftChild, node.parent.leftChild.value3);
					deleteValAndReorder(node.parent, node.parent.value2);
				}
			}

			else if (node.parent.leftChild == node) {

				if (node.parent.rightChild.isThreeNode()) {
					addValueToNode(node.parent, node.parent.rightChild.value1);
					addValueToNode(node, node.parent.value1);
					if (!node.parent.rightChild.isLeaf) {
						node.centerChild = node.rightChild;
						node.rightChild = node.parent.rightChild.leftChild;
						node.rightChild.parent = node;
						node.parent.rightChild.leftChild = node.parent.rightChild.centerChild;
						node.parent.rightChild.centerChild = null;
					}
					deleteValAndReorder(node.parent.rightChild, node.parent.rightChild.value1);
					deleteValAndReorder(node.parent, node.parent.value1);
				}

				else if (node.parent.rightChild.isFourNode()) {
					addValueToNode(node.parent, node.parent.rightChild.value1);
					addValueToNode(node, node.parent.value1);
					if (!node.parent.rightChild.isLeaf) {
						node.centerChild = node.rightChild;
						node.rightChild = node.parent.rightChild.leftChild;
						node.rightChild.parent = node;
						node.parent.rightChild.leftChild = node.parent.rightChild.centerLeftChild;
						node.parent.rightChild.centerChild = node.parent.rightChild.centerRightChild;
						node.parent.rightChild.centerLeftChild = null;
						node.parent.rightChild.centerRightChild = null;

					}
					deleteValAndReorder(node.parent.rightChild, node.parent.rightChild.value1);
					deleteValAndReorder(node.parent, node.parent.value1);

				}

			}

		}
		// if parent has two Values

		else if (node.parent.isThreeNode()) {

			if (node.parent.leftChild == node) {

				if (node.parent.centerChild.isTwoNode()) {

					TwoFourTreeItem mergeNode = new TwoFourTreeItem(node.value1);
					addValueToNode(mergeNode, node.parent.value1);
					addValueToNode(mergeNode, node.parent.centerChild.value1);// execute merge code
					deleteValAndReorder(node.parent, node.parent.value1);
					mergeNode.parent = node.parent;

					if (!node.isLeaf) { // we have to also check if the node we are merging if it has children if it
															// does we need to reassign all these children to mergeNode
						mergeNode.leftChild = node.leftChild;
						mergeNode.centerLeftChild = node.rightChild;
						mergeNode.centerRightChild = node.parent.centerChild.leftChild;
						mergeNode.rightChild = node.parent.centerChild.rightChild;
						mergeNode.leftChild.parent = mergeNode;
						mergeNode.centerLeftChild.parent = mergeNode;
						mergeNode.centerRightChild.parent = mergeNode;
						mergeNode.rightChild.parent = mergeNode;
						mergeNode.isLeaf = false; // mergeNode is no longer a leaf
					} // end of if checking isLead

					node.parent.leftChild = mergeNode;
					node.parent.centerChild = null;
					mergePerformed = true;

				} // end of if statement that detects if to merge

				else { // else means we shift
					if (node.parent.centerChild.isThreeNode()) {

						addValueToNode(node, node.parent.value1);
						addValueToNode(node.parent, node.parent.centerChild.value1);
						if (!node.parent.centerChild.isLeaf) {
							node.centerChild = node.rightChild;
							node.rightChild = node.parent.centerChild.leftChild;
							node.rightChild.parent = node;
							node.parent.centerChild.leftChild = node.parent.centerChild.centerChild;
							node.parent.centerChild.centerChild = null;
						}
						deleteValAndReorder(node.parent, node.parent.value1);
						deleteValAndReorder(node.parent.centerChild, node.parent.centerChild.value1);
					}

					if (node.parent.centerChild.isFourNode()) {

						addValueToNode(node, node.parent.value1);
						addValueToNode(node.parent, node.parent.centerChild.value1);
						if (!node.parent.centerChild.isLeaf) {
							node.centerChild = node.rightChild;
							node.rightChild = node.parent.centerChild.leftChild;
							node.rightChild.parent = node;
							node.parent.centerChild.leftChild = node.parent.centerChild.centerLeftChild;
							node.parent.centerChild.centerChild = node.parent.centerChild.centerRightChild;
							node.parent.centerChild.centerLeftChild = null;
							node.parent.centerChild.centerRightChild = null;
						}
						deleteValAndReorder(node.parent, node.parent.value1);
						deleteValAndReorder(node.parent.centerChild, node.parent.centerChild.value1);
					}

				} // end of else that borrows from center child

			} // end of if statement that borrows / merges with siblings if you are all the
				// way on the left

			// if twoNode we are strengthening is in the middle it can borrow from left or
			// right sibling or just merge with one of the two
			else if (node.parent.centerChild == node) {

				if (node.parent.leftChild.isTwoNode() && node.parent.rightChild.isTwoNode()) { // this means the node we are
																																												// trying to fix cannot borrow
																																												// but rather has to merge

					TwoFourTreeItem mergeNode = new TwoFourTreeItem(node.value1);
					addValueToNode(mergeNode, node.parent.value1);
					addValueToNode(mergeNode, node.parent.leftChild.value1);// execute merge code
					deleteValAndReorder(node.parent, node.parent.value1);
					mergeNode.parent = node.parent;

					if (!node.isLeaf) { // we have to also check if the node we are merging if it has children if it
															// does we need to reassign all these children to mergeNode
						mergeNode.leftChild = node.parent.leftChild.leftChild;
						mergeNode.centerLeftChild = node.parent.leftChild.rightChild;
						mergeNode.centerRightChild = node.leftChild;
						mergeNode.rightChild = node.rightChild;
						mergeNode.leftChild.parent = mergeNode;
						mergeNode.centerLeftChild.parent = mergeNode;
						mergeNode.centerRightChild.parent = mergeNode;
						mergeNode.rightChild.parent = mergeNode;
						mergeNode.isLeaf = false; // mergeNode is no longer a leaf
					}

					node.parent.leftChild = mergeNode;
					node.parent.centerChild = null;
					mergePerformed = true;

				} // end of if statement that merges node we are on if sibling had nothing to
					// borrow

				else { // else means the twoNode we are fixing has siblings that are three Nodes or
								// FourNodes so borrow from them

					if (node.parent.leftChild.values >= node.parent.rightChild.values) { // grab whichever of the two siblings has
																																								// the greater amount of values to
																																								// borrow from to preserve the 2-4tree
																																								// structure even better

						if (node.parent.leftChild.isThreeNode()) { // if that greater sibling in this case the leftChild is a
																												// threeNode we grab the farthest value of a threeNode (value2)
							addValueToNode(node, node.parent.value1);
							addValueToNode(node.parent, node.parent.leftChild.value2);
							if (!node.parent.leftChild.isLeaf) {
								node.centerChild = node.leftChild;
								node.leftChild = node.parent.leftChild.rightChild;
								node.leftChild.parent = node;
								node.parent.leftChild.rightChild = node.parent.leftChild.centerChild;
								node.parent.leftChild.centerChild = null;
							}
							deleteValAndReorder(node.parent, node.parent.value2);
							deleteValAndReorder(node.parent.leftChild, node.parent.leftChild.value2);
						} else if (node.parent.leftChild.isFourNode()) { // if that greater sibling is a fourNode than we grab the
																															// farthest value of a fourNode which is (value3)
							addValueToNode(node, node.parent.value1);
							addValueToNode(node.parent, node.parent.leftChild.value3);
							if (!node.parent.leftChild.isLeaf) {
								node.centerChild = node.leftChild;
								node.leftChild = node.parent.leftChild.rightChild;
								node.leftChild.parent = node;
								node.parent.leftChild.rightChild = node.parent.leftChild.centerRightChild;
								node.parent.leftChild.centerChild = node.parent.leftChild.centerLeftChild;
								node.parent.leftChild.centerRightChild = null;
								node.parent.leftChild.centerLeftChild = null;
							}
							deleteValAndReorder(node.parent, node.parent.value2);
							deleteValAndReorder(node.parent.leftChild, node.parent.leftChild.value3);
						}

					} // end of if statement determining which sibling had greater values

					else { // else means values in the right sibling are greater than the ones in the left
									// sibling so borrow from them

						if (node.parent.rightChild.isThreeNode()) {

							addValueToNode(node, node.parent.value2);
							addValueToNode(node.parent, node.parent.rightChild.value1);
							if (!node.parent.rightChild.isLeaf) {
								node.centerChild = node.rightChild;
								node.rightChild = node.parent.rightChild.leftChild;
								node.rightChild.parent = node;
								node.parent.rightChild.leftChild = node.parent.rightChild.centerChild;
								node.parent.rightChild.centerChild = null;
							}
							deleteValAndReorder(node.parent, node.parent.value2);
							deleteValAndReorder(node.parent.rightChild, node.parent.rightChild.value1);

						}

						else if (node.parent.rightChild.isFourNode()) {

							addValueToNode(node, node.parent.value2);
							addValueToNode(node.parent, node.parent.rightChild.value1);
							if (!node.parent.rightChild.isLeaf) {
								node.centerChild = node.rightChild;
								node.rightChild = node.parent.rightChild.leftChild;
								node.rightChild.parent = node;
								node.parent.rightChild.leftChild = node.parent.rightChild.centerLeftChild;
								node.parent.rightChild.centerChild = node.parent.rightChild.centerRightChild;
								node.parent.rightChild.centerRightChild = null;
								node.parent.rightChild.centerLeftChild = null;

							}
							deleteValAndReorder(node.parent, node.parent.value2);
							deleteValAndReorder(node.parent.rightChild, node.parent.rightChild.value1);

						}

					}

				}

			} // end of else if checking if node we are working with is centerChild

			else if (node.parent.rightChild == node) {

				if (node.parent.centerChild.isTwoNode()) {

					TwoFourTreeItem mergeNode = new TwoFourTreeItem(node.value1);
					addValueToNode(mergeNode, node.parent.value2);
					addValueToNode(mergeNode, node.parent.centerChild.value1);// execute merge code
					deleteValAndReorder(node.parent, node.parent.value2);
					mergeNode.parent = node.parent;

					if (!node.isLeaf) { // we have to also check if the node we are merging if it has children if it
															// does we need to reassign all these children to mergeNode
						mergeNode.leftChild = node.parent.centerChild.leftChild;
						mergeNode.centerLeftChild = node.parent.centerChild.rightChild;
						mergeNode.centerRightChild = node.leftChild;
						mergeNode.rightChild = node.rightChild;
						mergeNode.leftChild.parent = mergeNode;
						mergeNode.centerLeftChild.parent = mergeNode;
						mergeNode.centerRightChild.parent = mergeNode;
						mergeNode.rightChild.parent = mergeNode;
						mergeNode.isLeaf = false; // mergeNode is no longer a leaf
					} // end of if checking isLead

					node.parent.rightChild = mergeNode;
					node.parent.centerChild = null;
					mergePerformed = true;

				} // end of if statement that detects if to merge

				else { // else means we can borrow as a node all the way on the right from our left
								// sibling in the middle

					if (node.parent.centerChild.isThreeNode()) {
						addValueToNode(node, node.parent.value2);
						addValueToNode(node.parent, node.parent.centerChild.value2);
						if (!node.parent.centerChild.isLeaf) {
							node.centerChild = node.leftChild;
							node.leftChild = node.parent.centerChild.rightChild;
							node.leftChild.parent = node;
							node.parent.centerChild.rightChild = node.parent.centerChild.centerChild;
							node.parent.centerChild.centerChild = null;
						}
						deleteValAndReorder(node.parent, node.parent.value3);
						deleteValAndReorder(node.parent.centerChild, node.parent.centerChild.value2);

					} else if (node.parent.centerChild.isFourNode()) {
						addValueToNode(node, node.parent.value2);
						addValueToNode(node.parent, node.parent.centerChild.value3);
						if (!node.parent.centerChild.isLeaf) {
							node.centerChild = node.leftChild;
							node.leftChild = node.parent.centerChild.rightChild;
							node.leftChild.parent = node;
							node.parent.centerChild.rightChild = node.parent.centerChild.centerRightChild;
							node.parent.centerChild.centerChild = node.parent.centerChild.centerLeftChild;
							node.parent.centerChild.centerLeftChild = null;
							node.parent.centerChild.centerRightChild = null;
							// also need to adjust center child since it is four node
						}
						deleteValAndReorder(node.parent, node.parent.value3);
						deleteValAndReorder(node.parent.centerChild, node.parent.centerChild.value3);
					}

				}

			} // end of else if that encases the logic for a twoNode who is on the right side
				// of its parent

		} // end of case where we are working with the node we are strengthening and its
			// parent is a threeNode

		// now we have to also deal with fixing a two node when its parent is a fourNode
		else if (node.parent.isFourNode()) {

			if (node.parent.leftChild == node) {

				if (node.parent.centerLeftChild.isTwoNode()) {

					TwoFourTreeItem mergeNode = new TwoFourTreeItem(node.value1);
					addValueToNode(mergeNode, node.parent.value1);
					addValueToNode(mergeNode, node.parent.centerLeftChild.value1);// execute merge code
					deleteValAndReorder(node.parent, node.parent.value1);
					mergeNode.parent = node.parent;

					if (!node.isLeaf) { // we have to also check if the node we are merging if it has children if it
															// does we need to reassign all these children to mergeNode
						mergeNode.leftChild = node.leftChild;
						mergeNode.centerLeftChild = node.rightChild;
						mergeNode.centerRightChild = node.parent.centerLeftChild.leftChild;
						mergeNode.rightChild = node.parent.centerLeftChild.rightChild;
						mergeNode.leftChild.parent = mergeNode;
						mergeNode.centerLeftChild.parent = mergeNode;
						mergeNode.centerRightChild.parent = mergeNode;
						mergeNode.rightChild.parent = mergeNode;
						mergeNode.isLeaf = false; // mergeNode is no longer a leaf
					} // end of if checking isLead

					node.parent.leftChild = mergeNode;
					node.parent.centerLeftChild = null;
					node.parent.centerChild = node.parent.centerRightChild;
					node.parent.centerRightChild = null;
					mergePerformed = true;

				} // end of if statement that detects if to merge

				else {

					if (node.parent.centerLeftChild.isThreeNode()) {

						addValueToNode(node, node.parent.value1);
						deleteValAndReorder(node.parent, node.parent.value1);
						if (!node.parent.centerLeftChild.isLeaf) {
							node.centerChild = node.rightChild;
							node.rightChild = node.parent.centerLeftChild.leftChild;
							node.rightChild.parent = node;
							node.parent.centerLeftChild.leftChild = node.parent.centerLeftChild.centerChild;
							node.parent.centerLeftChild.centerChild = null;
						}
						addValueToNode(node.parent, node.parent.centerLeftChild.value1);
						deleteValAndReorder(node.parent.centerLeftChild, node.parent.centerLeftChild.value1);
					}

					if (node.parent.centerLeftChild.isFourNode()) {

						addValueToNode(node, node.parent.value1);
						deleteValAndReorder(node.parent, node.parent.value1);
						if (!node.parent.centerLeftChild.isLeaf) {
							node.centerChild = node.rightChild;
							node.rightChild = node.parent.centerLeftChild.leftChild;
							node.rightChild.parent = node;
							node.parent.centerLeftChild.leftChild = node.parent.centerLeftChild.centerLeftChild;
							node.parent.centerLeftChild.centerChild = node.parent.centerLeftChild.centerRightChild;
							node.parent.centerLeftChild.centerLeftChild = null;
							node.parent.centerLeftChild.centerRightChild = null;
						}
						addValueToNode(node.parent, node.parent.centerLeftChild.value1);
						deleteValAndReorder(node.parent.centerLeftChild, node.parent.centerLeftChild.value1);
					}

				} // end of else which just means we can borrow from our sibling

			} // end of if case if the node we are deleting is the leftChild of the fourNode

			else if (node.parent.centerLeftChild == node) {

				if (node.parent.leftChild.isTwoNode() && node.parent.centerRightChild.isTwoNode()) {

					TwoFourTreeItem mergeNode = new TwoFourTreeItem(node.value1);
					addValueToNode(mergeNode, node.parent.value1);
					addValueToNode(mergeNode, node.parent.leftChild.value1);
					deleteValAndReorder(node.parent, node.parent.value1);
					mergeNode.parent = node.parent;

					if (!node.isLeaf) { // we have to also check if the node we are merging if it has children if it
															// does we need to reassign all these children to mergeNode
						mergeNode.leftChild = node.parent.leftChild.leftChild;
						mergeNode.centerLeftChild = node.parent.leftChild.rightChild;
						mergeNode.centerRightChild = node.leftChild;
						mergeNode.rightChild = node.rightChild;
						mergeNode.leftChild.parent = mergeNode;
						mergeNode.centerLeftChild.parent = mergeNode;
						mergeNode.centerRightChild.parent = mergeNode;
						mergeNode.rightChild.parent = mergeNode;
						mergeNode.isLeaf = false; // mergeNode is no longer a leaf
					} // end of if checking isLead

					node.parent.leftChild = mergeNode;
					node.parent.centerLeftChild = null;
					node.parent.centerChild = node.parent.centerRightChild;
					node.parent.centerRightChild = null;
					mergePerformed = true;

				} // end of if statement that detects if to merge

				else { // else means the twoNode we are fixing has siblings that are three Nodes or
								// FourNodes so borrow from them

					if (node.parent.leftChild.values >= node.parent.centerRightChild.values) { // grab whichever of the two
																																											// siblings has the greater amount
																																											// of values to borrow from to
																																											// preserve the 2-4tree structure
																																											// even better

						if (node.parent.leftChild.isThreeNode()) { // if that greater sibling is a threeNode we grab the farthest
																												// value of a threeNode (value2)
							addValueToNode(node, node.parent.value1);
							deleteValAndReorder(node.parent, node.parent.value1);
							if (!node.parent.leftChild.isLeaf) {
								node.centerChild = node.leftChild;
								node.leftChild = node.parent.leftChild.rightChild;
								node.leftChild.parent = node;
								node.parent.leftChild.rightChild = node.parent.leftChild.centerChild;
								node.parent.leftChild.centerChild = null;
							}
							addValueToNode(node.parent, node.parent.leftChild.value2);
							deleteValAndReorder(node.parent.leftChild, node.parent.leftChild.value2);
						} else if (node.parent.leftChild.isFourNode()) { // if that greater sibling is a fourNode than we grab the
																															// farthest value of a fourNode which is (value3)
							addValueToNode(node, node.parent.value1);
							deleteValAndReorder(node.parent, node.parent.value1);
							if (!node.parent.leftChild.isLeaf) {
								node.centerChild = node.leftChild;
								node.leftChild = node.parent.leftChild.rightChild;
								node.leftChild.parent = node;
								node.parent.leftChild.rightChild = node.parent.leftChild.centerRightChild;
								node.parent.leftChild.centerChild = node.parent.leftChild.centerLeftChild;
								node.parent.leftChild.centerRightChild = null;
								node.parent.leftChild.centerLeftChild = null;
							}
							addValueToNode(node.parent, node.parent.leftChild.value3);
							deleteValAndReorder(node.parent.leftChild, node.parent.leftChild.value3);
						}

					} // end of if statement determining which sibling had greater values

					else { // else means values in the right sibling are greater than the ones in the left
									// sibling so borrow from them

						if (node.parent.centerRightChild.isThreeNode()) {
							// Don't need more if statements here because if sibling is on the right we just
							// grab the smallest value which if it is a three Node or fourNode it will
							// always be value1
							addValueToNode(node, node.parent.value2);
							deleteValAndReorder(node.parent, node.parent.value2);
							if (!node.parent.centerRightChild.isLeaf) {
								node.centerChild = node.rightChild;
								node.rightChild = node.parent.centerRightChild.leftChild;
								node.rightChild.parent = node;
								node.parent.centerRightChild.leftChild = node.parent.centerRightChild.centerChild;
								node.parent.centerRightChild.centerChild = null;
							}
							addValueToNode(node.parent, node.parent.centerRightChild.value1);
							deleteValAndReorder(node.parent.centerRightChild, node.parent.centerRightChild.value1);

						}

						else if (node.parent.centerRightChild.isFourNode()) {
							addValueToNode(node, node.parent.value2);
							deleteValAndReorder(node.parent, node.parent.value2);
							if (!node.parent.centerRightChild.isLeaf) {
								node.centerChild = node.rightChild;
								node.rightChild = node.parent.centerRightChild.leftChild;
								node.rightChild.parent = node;
								node.parent.centerRightChild.leftChild = node.parent.centerRightChild.centerLeftChild;
								node.parent.centerRightChild.centerChild = node.parent.centerRightChild.centerRightChild;
								node.parent.centerRightChild.centerLeftChild = null;
								node.parent.centerRightChild.centerRightChild = null;
							}
							addValueToNode(node.parent, node.parent.centerRightChild.value1);
							deleteValAndReorder(node.parent.centerRightChild, node.parent.centerRightChild.value1);

						}

					}

				}

			} // end of condition checking if the node that needs to get fixed is the
				// centerLeftChild of fourNode

			else if (node.parent.centerRightChild == node) {

				if (node.parent.centerLeftChild.isTwoNode() && node.parent.rightChild.isTwoNode()) {

					TwoFourTreeItem mergeNode = new TwoFourTreeItem(node.value1);
					addValueToNode(mergeNode, node.parent.value3);
					addValueToNode(mergeNode, node.parent.rightChild.value1);
					deleteValAndReorder(node.parent, node.parent.value3);
					mergeNode.parent = node.parent;

					if (!node.isLeaf) { // we have to also check if the node we are merging if it has children if it
															// does we need to reassign all these children to mergeNode
						mergeNode.leftChild = node.leftChild;
						mergeNode.centerLeftChild = node.rightChild;
						mergeNode.centerRightChild = node.parent.rightChild.leftChild;
						mergeNode.rightChild = node.parent.rightChild.rightChild;
						mergeNode.leftChild.parent = mergeNode;
						mergeNode.centerLeftChild.parent = mergeNode;
						mergeNode.centerRightChild.parent = mergeNode;
						mergeNode.rightChild.parent = mergeNode;
						mergeNode.isLeaf = false; // mergeNode is no longer a leaf
					} // end of if checking isLead

					node.parent.rightChild = mergeNode;
					node.parent.centerRightChild = null;
					node.parent.centerChild = node.parent.centerLeftChild;
					node.parent.centerLeftChild = null;
					mergePerformed = true;

				} // end of if statement that detects if to merge

				else { // else means the twoNode we are fixing has siblings that are three Nodes or
								// FourNodes so borrow from them

					if (node.parent.centerLeftChild.values >= node.parent.rightChild.values) { // grab whichever of the two
																																											// siblings has the greater amount
																																											// of values to borrow from to
																																											// preserve the 2-4tree structure
																																											// even better

						if (node.parent.centerLeftChild.isThreeNode()) { // if that greater sibling is a threeNode we grab the
																															// farthest value of a threeNode (value2)
							addValueToNode(node, node.parent.value2);
							deleteValAndReorder(node.parent, node.parent.value2);
							if (!node.parent.centerLeftChild.isLeaf) {
								node.centerChild = node.leftChild;
								node.leftChild = node.parent.centerLeftChild.rightChild;
								node.leftChild.parent = node;
								node.parent.centerLeftChild.rightChild = node.parent.centerLeftChild.centerChild;
								node.parent.centerLeftChild.centerChild = null;
							}
							addValueToNode(node.parent, node.parent.centerLeftChild.value2);
							deleteValAndReorder(node.parent.centerLeftChild, node.parent.centerLeftChild.value2);
						} else if (node.parent.centerLeftChild.isFourNode()) { // if that greater sibling is a fourNode than we grab
																																		// the farthest value of a fourNode which is
																																		// (value3)
							addValueToNode(node, node.parent.value2);
							deleteValAndReorder(node.parent, node.parent.value2);
							if (!node.parent.centerLeftChild.isLeaf) {
								node.centerChild = node.leftChild;
								node.leftChild = node.parent.centerLeftChild.rightChild;
								node.leftChild.parent = node;
								node.parent.centerLeftChild.rightChild = node.parent.centerLeftChild.centerRightChild;
								node.parent.centerLeftChild.centerChild = node.parent.centerLeftChild.centerLeftChild;
								node.parent.centerLeftChild.centerRightChild = null;
								node.parent.centerLeftChild.centerLeftChild = null;
							}
							addValueToNode(node.parent, node.parent.centerLeftChild.value3);
							deleteValAndReorder(node.parent.centerLeftChild, node.parent.centerLeftChild.value3);
						}

					} // end of if statement determining which sibling had greater values

					else { // else means values in the right sibling are greater than the ones in the left
									// sibling so borrow from them

						// Don't need more if statements here because if sibling is on the right we just
						// grab the smallest value which if it is a three Node or fourNode it will
						// always be value1
						if (node.parent.rightChild.isThreeNode()) {
							addValueToNode(node, node.parent.value3);
							deleteValAndReorder(node.parent, node.parent.value3);
							if (!node.parent.rightChild.isLeaf) {
								node.centerChild = node.rightChild;
								node.rightChild = node.parent.rightChild.leftChild;
								node.rightChild.parent = node;
								node.parent.rightChild.leftChild = node.parent.rightChild.centerChild;
								node.parent.rightChild.centerChild = null;
							}
							addValueToNode(node.parent, node.parent.rightChild.value1);
							deleteValAndReorder(node.parent.rightChild, node.parent.rightChild.value1);

						}

						else if (node.parent.rightChild.isFourNode()) {
							addValueToNode(node, node.parent.value3);
							deleteValAndReorder(node.parent, node.parent.value3);
							if (!node.parent.rightChild.isLeaf) {
								node.centerChild = node.rightChild;
								node.rightChild = node.parent.rightChild.leftChild;
								node.rightChild.parent = node;
								node.parent.rightChild.leftChild = node.parent.rightChild.centerLeftChild;
								node.parent.rightChild.centerChild = node.parent.rightChild.centerRightChild;
								node.parent.rightChild.centerLeftChild = null;
								node.parent.rightChild.centerRightChild = null;
							}
							addValueToNode(node.parent, node.parent.rightChild.value1);
							deleteValAndReorder(node.parent.rightChild, node.parent.rightChild.value1);

						}

					}

				}

			} // end of condition checking if the node that needs to get fixed is the
				// centerRightChild of fourNode

			else if (node.parent.rightChild == node) {

				if (node.parent.centerRightChild.isTwoNode()) {

					TwoFourTreeItem mergeNode = new TwoFourTreeItem(node.value1);
					addValueToNode(mergeNode, node.parent.value3);
					addValueToNode(mergeNode, node.parent.centerRightChild.value1);// execute merge code
					deleteValAndReorder(node.parent, node.parent.value3);
					mergeNode.parent = node.parent;

					if (!node.isLeaf) { // we have to also check if the node we are merging if it has children if it
															// does we need to reassign all these children to mergeNode
						mergeNode.leftChild = node.parent.centerRightChild.leftChild;
						mergeNode.centerLeftChild = node.parent.centerRightChild.rightChild;
						mergeNode.centerRightChild = node.leftChild;
						mergeNode.rightChild = node.rightChild;
						mergeNode.leftChild.parent = mergeNode;
						mergeNode.centerLeftChild.parent = mergeNode;
						mergeNode.centerRightChild.parent = mergeNode;
						mergeNode.rightChild.parent = mergeNode;
						mergeNode.isLeaf = false; // mergeNode is no longer a leaf
					} // end of if checking isLead

					node.parent.rightChild = mergeNode;
					node.parent.centerRightChild = null;
					node.parent.centerChild = node.parent.centerLeftChild;
					node.parent.centerLeftChild = null;
					mergePerformed = true;

				} // end of if statement that detects if to merge

				else {

					if (node.parent.centerRightChild.isThreeNode()) {

						addValueToNode(node, node.parent.value3);
						deleteValAndReorder(node.parent, node.parent.value3);
						if (!node.parent.centerRightChild.isLeaf) {
							node.centerChild = node.leftChild;
							node.leftChild = node.parent.centerRightChild.rightChild;
							node.leftChild.parent = node;
							node.parent.centerRightChild.rightChild = node.parent.centerRightChild.centerChild;
							node.parent.centerRightChild.centerChild = null;
						}
						addValueToNode(node.parent, node.parent.centerRightChild.value2);
						deleteValAndReorder(node.parent.centerRightChild, node.parent.centerRightChild.value2);
					} else if (node.parent.centerRightChild.isFourNode()) {
						addValueToNode(node, node.parent.value3);
						deleteValAndReorder(node.parent, node.parent.value3);
						if (!node.parent.centerRightChild.isLeaf) {
							node.centerChild = node.leftChild;
							node.leftChild = node.parent.centerRightChild.rightChild;
							node.leftChild.parent = node;
							node.parent.centerRightChild.rightChild = node.parent.centerRightChild.centerRightChild;
							node.parent.centerRightChild.centerChild = node.parent.centerRightChild.centerLeftChild;
							node.parent.centerRightChild.centerRightChild = null;
							node.parent.centerRightChild.centerLeftChild = null;
						}
						addValueToNode(node.parent, node.parent.centerRightChild.value3);
						deleteValAndReorder(node.parent.centerRightChild, node.parent.centerRightChild.value3);

					}

				} // end of else which just means we can borrow from our sibling

			} // end of condition checking if the node that needs to get fixed is the
				// centerRightChild of fourNode

		} // end of ifParent is a fourNode if logic

		return mergePerformed;

	} // end of fixTwoNode Function

	public void printInOrder() {
		if (root != null)
			root.printInOrder(0);
	}

	public TwoFourTree() { // constructor for the TwoFourTree class this gets called in App.Java.
													// Initialize the root of our tree here
		root = null;
	}
}// end of public TwoFourTree class
