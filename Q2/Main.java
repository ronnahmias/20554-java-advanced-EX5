import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Random rand = new Random();

		System.out.println("Hello Enter Number of Threads:");
		int n = getUserInput(sc);

		System.out.println("Enter number of rounds:");
		int m = getUserInput(sc);

		// init monitor to all threads
		Monitor moni = new Monitor(n, m); // Monitor for all the threads
		initStartThreads(moni, rand, n, m);

	}

	public static void initStartThreads(Monitor moni, Random rand, int arrSize, int roundSize) {
		Item[] threadsArray = new Item[arrSize];
		int i;
		for (i = 0; i < arrSize; i++) {
			// create arrSize of threads with random num between 1-100
			threadsArray[i] = new Item(i, rand.nextInt(100) + 1, arrSize, moni);
		}

		for (i = 0; i < arrSize; i++) {
			// set threads neighbors - in circular
			threadsArray[i].setLRNeighbors(
					threadsArray[(i - 1) % arrSize < 0 ? (((i - 1) % arrSize) + arrSize) % arrSize : (i - 1) % arrSize],
					threadsArray[(i + 1) % arrSize]);
		}

		for (i = 0; i < arrSize; i++) {
			threadsArray[i].start();
		}
	}

	public static int getUserInput(Scanner sc) {
		return sc.nextInt();
	}

}
