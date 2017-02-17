package me.newsong.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScheduledThreadPool {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
		for(int i = 0; i < 10 ;++i){
			Future<Integer> result = pool.schedule(new ThreadPoolDemo2(), 800, TimeUnit.MILLISECONDS);
			System.out.println(result.get());
		}
		pool.shutdown();
	}
}
