package twoFourTree;

public class tester {

	public static void main(String[] args) {
		TwoFourTree twFourTree = new TwoFourTree();

		
		twFourTree.addValue(10);
		twFourTree.addValue(8);
		twFourTree.addValue(20);
		twFourTree.addValue(12);
		twFourTree.addValue(5);
		twFourTree.addValue(6);
		twFourTree.addValue(17);
		twFourTree.addValue(22);
		twFourTree.addValue(3); //issue when adding 3 to this tree
		twFourTree.printInOrder();
	}

}
 