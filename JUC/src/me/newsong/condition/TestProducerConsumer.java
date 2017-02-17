package me.newsong.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProducerConsumer {
	public static void main(String[] args) {
		SyncStack ss = new SyncStack();
		new Thread(new Producer(ss), "A").start();
		new Thread(new Producer(ss), "B").start();
		new Thread(new Consumer(ss), "C").start();
		new Thread(new Consumer(ss), "D").start();

	}
}

class Food {
	private String id;

	public Food(String id) {
		this.id = id;
	}

	public String toString() {
		return "产品" + id;
	}
}

class SyncStack {
	private int index = 0;
	private Food[] foods = new Food[6];
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public SyncStack() {
	}

	public void push(Food f) {
		lock.lock();
		try {
			while (index == foods.length) {
				try {
					System.out.println("容器已满");
					condition.await();
					//相当于this.wait()
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			foods[index] = f;
			index++;
			condition.signalAll();
			//相当于this.notifyAll()
		} finally {
			lock.unlock();
		}
	}

	public Food pop() {
		lock.lock();
		try {
			while (index == 0) {
				try {
					System.out.println("容器已空");
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			index--;
			condition.signalAll();
		} finally {
			lock.unlock();
		}
		return foods[index];
	}
}

class Producer implements Runnable {
	private SyncStack ss;

	public Producer(SyncStack ss) {
		this.ss = ss;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			Food f = new Food(Thread.currentThread().getName() + i);
			ss.push(f);
			System.out.println("生产者" + Thread.currentThread().getName() + "生产了 " + f);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable {
	private SyncStack ss;

	public Consumer(SyncStack ss) {
		this.ss = ss;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			Food f = ss.pop();
			System.out.println("消费者" + Thread.currentThread().getName() + "消费了 " + f);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
