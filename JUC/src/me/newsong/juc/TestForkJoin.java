package me.newsong.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TestForkJoin {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinCalculator calculator = new ForkJoinCalculator(0, 10000000L);
		Long result = pool.invoke(calculator);
		System.out.println(result);
		pool.shutdown();
	}
}

class ForkJoinCalculator extends RecursiveTask<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6682191224530210391L;

	private long start;
	private long end;
	private static final long THRESHOLD = 10000L;

	public ForkJoinCalculator(long start, long end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		long length = end - start;
		if (length < THRESHOLD) {
			long sum = 0L;
			for (long i = start; i < end; ++i) {
				sum += i;
			}
			return sum;
		} else {
			long middle = (start + end) / 2;
			ForkJoinCalculator left = new ForkJoinCalculator(start, middle);
			left.fork();
			ForkJoinCalculator right = new ForkJoinCalculator(middle, end);
			right.fork();
			return left.join() + right.join();
		}
	}

}