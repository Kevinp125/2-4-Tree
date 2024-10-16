package twoFourTree;

public class tester {

	public static void main(String[] args) {
		
		TwoFourTree tft = new TwoFourTree();
		int[] primeArray = new int[25];  // Array to hold the first 30 prime numbers
        int count = 0;  // Counter to keep track of how many primes have been found
        int num = 2;  // Start checking from 2 (the first prime number)


	    while (count < 25) {
            if (isPrime(num)) {
                primeArray[count] = num;  // If it's prime, add it to the array
                count++;  // Increment the prime counter
            }
            num++;  // Move to the next number to check
        }
	
	    int iterCounter = 1;
	    System.out.println();
	   for(int i = 0; i<25;i++) {
		  
		   tft.addValue(primeArray[i]);
		  
		   iterCounter++;
	   }
	   
	   tft.printInOrder();
	   tft.deleteValue(37);
       System.out.println("\nWithout 37:");
       tft.printInOrder();
       tft.deleteValue(73);
       System.out.println("\nWithout 73:");
       tft.printInOrder();
//	   
	}
	
	
	
	
	public static boolean isPrime(int number) {
	    if (number <= 1) {
	        return false;
	    }
	    for (int i = 2; i <= Math.sqrt(number); i++) {
	        if (number % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}
	
	
}


//        System.out.println("Static test: first few prime numbers:");
//        tft.printInOrder();
//        tft.deleteValue(37);
//        System.out.println("after deleting 37");
//        tft.printInOrder();
//        tft.deleteValue(73);
//        System.out.println("after deleting 73");
//        tft.printInOrder();


		
		
		
//		if(twFourTree.hasValue(3))
//			System.out.println("Value was found!");
//		else {
//			System.out.println("Value not found :(");
//		}



 