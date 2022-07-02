import java.util.ArrayList;

public class Monitor {

	private ArrayList<Integer> stock;
	private int count;
	private int size;
	public final int ZERO = 0;
	public final int ONE = 1;
	public final int TWO = 2;

	public Monitor(int[] array) {
		stock = new ArrayList<Integer>();
		for (int i = ZERO; i < array.length; i++) {
			stock.add(array[i]);
		}
		count = ZERO;
		size = array.length;
	}

	public void printArrayList() {
		System.out.println(stock.toString());
	}

	public synchronized Integer[] getItem() {
		while (stock.size() < TWO && count != size - ONE) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (stock.size() >= TWO) {
			count++;
			return new Integer[] { stock.remove(stock.size() - ONE), stock.remove(stock.size() - ONE) };
		}
		return null;
	}

	public synchronized void addSum(Integer sum) {
		if (sum != null) {
			stock.add(sum);
		}
		notifyAll();
	}

	public int getSum() {
		return stock.get(ZERO);
	}

}
