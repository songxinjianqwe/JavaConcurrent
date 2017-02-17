package me.newsong.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		new Thread(ticket, "一号窗口").start();
		new Thread(ticket, "二号窗口").start();
		new Thread(ticket, "三号窗口").start();
	}
}

class Ticket implements Runnable {
	private int tickets = 100;
	private Lock lock = new ReentrantLock();

	@Override
	public void run() {
		while (true) {
			lock.lock();
			try {
				Thread.sleep(50);
				if(tickets > 0){
					System.out.println(Thread.currentThread().getName() + "正在售票，余票为:" + (--tickets));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
}
