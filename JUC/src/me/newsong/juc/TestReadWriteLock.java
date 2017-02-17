package me.newsong.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {
	public static void main(String[] args) {
		ReadWriteLockDemo demo = new ReadWriteLockDemo();
		for (int i = 0; i < 100; ++i) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					demo.get();
				}
			}, "Read" + i).start();
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				demo.set(222);
			}
		}, "Write").start();

	}
}

class ReadWriteLockDemo {
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private int data = 2;

	public void get() {

		lock.readLock().lock();
		try {
			System.out.println("读操作  " + Thread.currentThread().getName() + ":" + data);
		} finally {
			lock.readLock().unlock();
		}
	}

	public void set(int data) {
		lock.writeLock().lock();
		try {
			System.out.println("写操作  " + Thread.currentThread().getName() + ":" + data);
			this.data = data;
		} finally {
			lock.writeLock().unlock();
		}
	}
}
