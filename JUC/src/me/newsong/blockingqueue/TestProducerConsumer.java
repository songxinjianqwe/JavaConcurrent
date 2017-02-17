package me.newsong.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class TestProducerConsumer {
	public static void main(String[] args) {
		BlockingQueue<Food> queue = new ArrayBlockingQueue<Food>(6);
		new Thread(new Producer(queue), "A").start();
		new Thread(new Producer(queue), "B").start();
		new Thread(new Consumer(queue), "C").start();
		new Thread(new Consumer(queue), "D").start();
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

class Producer implements Runnable {
	private BlockingQueue<Food> foods;

	public Producer(BlockingQueue<Food> foods) {
		this.foods = foods;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			Food f = new Food(Thread.currentThread().getName() + i);
			try {
				foods.put(f);
				System.out.println("生产者" + Thread.currentThread().getName() + "生产了 " + f);
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable {

	private BlockingQueue<Food> foods;

	public Consumer(BlockingQueue<Food> foods) {
		this.foods = foods;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				Food f = foods.take();
				System.out.println("消费者" + Thread.currentThread().getName() + "消费了 " + f);
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}