
public class Sum extends Thread {

	private Monitor m;
	public final int ZERO = 0;

	public Sum(Monitor m) {
		this.m = m;
	}

	public void run() {
		super.run();

		Integer sum = ZERO;
		while (sum != null) {
			Integer a[];

			a = m.getItem();
			if (a != null) {
				sum = a[ZERO] + a[1];
			} else {
				sum = null;
			}
			m.addSum(sum);
		}

	}

}
