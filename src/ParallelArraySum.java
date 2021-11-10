
public class ParallelArraySum extends Thread {
	private int[] array;
	private int low, high, partial;
	public ParallelArraySum(int[] array, int low, int high) {
		this.array = array;
		this.low = low;
		this.high = Math.min(high, array.length);
	}
	public int getPartialSum() {
		return partial;
	}
	public void run() {
		partial = sum(array, low, high);
	}
	public static int sum(int[] array) {
		return sum(array, 0, array.length);
	}
	public static int sum(int[] array, int low, int high) {
		int total = 0;
		for (int i = low; i < high; i++) {
		total += array[i];
	}
	return total;
	}
	public static int parallelSum(int[] array) {
		return parallelSum(array, Runtime.getRuntime().availableProcessors());
	}
	public static int parallelSum(int[] array, int threads) {
		int size = (int) Math.ceil(array.length * 1.0 / threads);
		ParallelArraySum[] sums = new ParallelArraySum[threads];
		for (int i = 0; i < threads; i++) {
			sums[i] = new ParallelArraySum(array, i * size, (i + 1) * size);
			sums[i].start();
		}
		try {
			for (ParallelArraySum sum : sums) {
				sum.join();
			}
		} catch (InterruptedException e) { 
		}
		int total = 0;
		for (ParallelArraySum sum : sums) {
			total += sum.getPartialSum();
		}
		return total;
	}
}