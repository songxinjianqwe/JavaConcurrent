package me.newsong.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestAlternate {
	public static void main(String[] args) {
		int threadNum  = 3;
		int loopTimes = 20;
		AlternativeDemo atomicDemo = new AlternativeDemo(threadNum, loopTimes);
		for (int i = 0; i < threadNum; ++i) {
			new Thread(atomicDemo, (char) (i + 'A') + "").start();
		}
	}
}

class AlternativeDemo implements Runnable {
	private char nextThread = 'A';
	private Lock lock = new ReentrantLock();
	private Condition[] conditions;
	private int totalTimes;

	public AlternativeDemo(int threadNum, int totalTimes) {
		this.totalTimes = totalTimes;
		conditions = new Condition[threadNum];
		for (int i = 0; i < threadNum; ++i) {
			conditions[i] = lock.newCondition();
		}
	}

	public void run() {
		for (int i = 1; i <= totalTimes; ++i) {
			lock.lock();
			char currentThreadName = Thread.currentThread().getName().charAt(0);
			try {
				if (nextThread != currentThreadName) {
					conditions[currentThreadName - 'A'].await();
				}
				System.out.println(currentThreadName + "\t" + i);
				nextThread =  (char) ('A' + (nextThread + 1 - 'A') % conditions.length);
				conditions[nextThread - 'A'].signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
}
