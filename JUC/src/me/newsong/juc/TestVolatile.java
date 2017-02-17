package me.newsong.juc;

public class TestVolatile {
	public static void main(String[] args) {
		MyThread myThread = new MyThread();
		new Thread(myThread).start();
		while(true){
			synchronized (myThread) {
				if(myThread.isFlag()){
					System.out.println("flag被设置为true");
					break;
				}
			}
		}
	}
}

class MyThread implements Runnable{
	private volatile boolean flag = false;
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		flag = true;
		System.out.println("flag="+flag);
	}
}
