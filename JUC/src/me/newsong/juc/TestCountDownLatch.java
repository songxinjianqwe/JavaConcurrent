package me.newsong.juc;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(5);
		LatchDemo latchDemo = new LatchDemo(latch);
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 5; ++i) {
			new Thread(latchDemo).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("总计耗时：" + (end - begin));
	}
}

class LatchDemo implements Runnable {
	private CountDownLatch latch;

	public LatchDemo(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		synchronized (this) {
			try {
				for (int i = 0; i < 50000; i++) {
					if (i % 2 == 0) {
						System.out.println(i);
					}
				}
			} finally {
				latch.countDown();
			}
		}
	}
}
