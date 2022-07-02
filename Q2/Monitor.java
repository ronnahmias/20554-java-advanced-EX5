import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor extends Thread {
	private int numOfThreads;
	private int numOfRounds;
	private int printed = 0; // Counter for how many threads passed through the print() function
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();

	public Monitor(int numOfThreads, int numOfRounds) {
		this.numOfThreads = numOfThreads;
		this.numOfRounds = numOfRounds;
	}

	public int getNumOfROunds() {
		return this.numOfRounds;
	}

	public synchronized int checkNeighbors(Item t, Item left, Item right, int num) {
		// Gets the nums of the neighbors
		int l = left.getNum();
		int r = right.getNum();

		t.setCheckedNeighbors(true);

		int updateVal = num;
		if (num < l && num < r)
			updateVal = num + 1;
		if (num > l && num > r)
			updateVal = num - 1;
		if ((num < l && num > r) || (num > l && num < r) || (num == l || num == r))
			updateVal = updateVal;
		notifyAll();
		return updateVal;
	}

	public synchronized void updateValue(Item t, Item left, Item right, int n) {
		while (!left.getCheckedNeighbors() || !right.getCheckedNeighbors()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		t.setNum(n);
		notifyAll(); // update all threads to wake and check neighbors
	}

	public void printStateRound(Item running, boolean toPrint, int m) {
		lock.lock();
		try {
			if (toPrint) {
				System.out.print(" Thread Number: " + running.getID() + " Random Number: " + running.getNum()
						+ " Round Number " + m + ", ");
				printed++;
			}
			cond.signalAll(); // check if all threads prints their state in current round
		} finally {
			lock.unlock();
		}
	}

	public void finishPrintRound(Item t, boolean roundEnded, int m) {
		lock.lock();
		try {
			while (printed < numOfThreads * (m + 1)) {
				cond.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		if (roundEnded) {
			System.out.println("");
		}
	}
}
