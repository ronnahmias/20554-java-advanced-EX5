import java.util.Random;
import java.util.Scanner;

public class Main {

	public static final int MAX = 999;
	public static final int ZERO = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Hello Enter array size:");
		int n = getUserInput(sc);

		System.out.println("Enter Number of Threads:");
		int m = getUserInput(sc);

		int[] arr = generateRandArray(n);

		// init monitor to all threads
		Monitor moni = new Monitor(arr);
		System.out.println("Input Array:");
		moni.printArrayList();

		initStartThreads(moni, m);
		// prints only after all threads ends
		System.out.println("Sum of Array is: " + moni.getSum());
	}

	public static void initStartThreads(Monitor moni, int size) {
		Sum[] threadsArray = new Sum[size];
		for (int i = ZERO; i < size; i++) {
			threadsArray[i] = new Sum(moni);
		}

		for (int i = ZERO; i < size; i++) {
			threadsArray[i].start();
		}

		for (int i = ZERO; i < size; i++) {
			try {
				threadsArray[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getUserInput(Scanner sc) {
		return sc.nextInt();
	}

	public static int[] generateRandArray(int size) {
		Random rd = new Random();
		int[] arr = new int[size];
		for (int i = ZERO; i < size; i++) {
			arr[i] = rd.nextInt(MAX);
		}
		return arr;
	}

}
