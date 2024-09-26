package twoFourTree;

public class tester {

	public static void main(String[] args) {
		TwoFourTree twFourTree = new TwoFourTree();

		
		twFourTree.addValue(10);
		twFourTree.addValue(8);
		twFourTree.addValue(20);
		twFourTree.addValue(11); //ok so program is breaking whenever we are splitting a 4 node whos above parent is a root 3 node
		twFourTree.addValue(13);
		twFourTree.addValue(2);
		twFourTree.addValue(27);
		twFourTree.addValue(15);
		twFourTree.addValue(12);
		twFourTree.addValue(1);
		twFourTree.addValue(3);
		twFourTree.addValue(39);
		twFourTree.addValue(50);
		twFourTree.addValue(42);
		twFourTree.addValue(16);
		twFourTree.addValue(19);
		twFourTree.printInOrder();
	}

}
 