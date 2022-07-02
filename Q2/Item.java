public class Item extends Thread {
	private Item left; // left neighbor
	private Item right; // right neighbor

	private int num;
	private int id;
	private Monitor moni;

	private boolean haveCheckedNeighbors = false; // True if the this thread checked its neighbors

	public Item(int id, int num, int threads, Monitor moni) {
		this.id = id;
		this.num = num;
		this.moni = moni;
	}

	public void setLRNeighbors(Item left, Item right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void run() {
		super.run();
		for (int i = 0; i < moni.getNumOfROunds(); i++) {
			moni.printStateRound(this, true, i); // print state of the round
			moni.finishPrintRound(this, true, i); // check that all threads print their state
			haveCheckedNeighbors = false;
			moni.updateValue(this, left, right, moni.checkNeighbors(this, left, right, num));
			moni.printStateRound(this, false, i); // Counts the threads that call this function
			moni.finishPrintRound(this, false, i); // checks that all threads end their round to begin new round in
													// parallel
		}
		// print the last round
		moni.printStateRound(this, true, moni.getNumOfROunds());
		moni.finishPrintRound(this, true, moni.getNumOfROunds());
	}

	public synchronized boolean getCheckedNeighbors() {
		return haveCheckedNeighbors;
	}

	public void setCheckedNeighbors(boolean setTo) {
		haveCheckedNeighbors = setTo;
	}

	public synchronized int getNum() {
		return num;
	}

	public synchronized void setNum(int n) {
		num = n;
	}

	public synchronized int getID() {
		return id;
	}
}
