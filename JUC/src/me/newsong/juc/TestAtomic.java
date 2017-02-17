package me.newsong.juc;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomic {
	public static void main(String[] args) {
		AtomicDemo atomicDemo = new AtomicDemo();
		for(int i = 0; i < 10; ++i){
			new Thread(atomicDemo).start();
		}
	}
}

class AtomicDemo implements Runnable{
	private  AtomicInteger serialNumber = new AtomicInteger(0);
	@Override
	public void run() {
		try{
			Thread.sleep(200);
		}catch(InterruptedException e){
			
		}
		System.out.println("Thread:"+Thread.currentThread().getName()+",SerialNumber:"+getSerialNumber());
	}
	//后++
	public int getSerialNumber() {
		return serialNumber.getAndIncrement();
	}
	public void setSerialNumber(AtomicInteger serialNumber) {
		this.serialNumber = serialNumber;
	}
}
