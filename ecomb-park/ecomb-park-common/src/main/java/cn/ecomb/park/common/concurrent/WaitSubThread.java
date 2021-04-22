package cn.ecomb.park.common.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 主线程等待子线程执行完成的各种方法
 * 1. use join method
 * 2. use CountDownLatch
 * 3. use CyclicBarrier
 * 4. use ThreadPool
 * 5. use JDK8 Feature
 *
 * @author brian.zhou
 * @date 2021/4/21
 */
public class WaitSubThread {

	private static final int THREAD_COUNT = 5;
	private static final int SLEEP_TIME = 500;

	public static void main(String[] args) throws InterruptedException {
		useJoin();
		System.out.println("-----------------use join");
		serialMoreJoin();
		System.out.println("-----------------serial more join");
		endMoreJoin();
		System.out.println("-----------------end more join");
		useCountDownLatch();
		System.out.println("-----------------use CountDownLatch");
		useThreadPool();
		System.out.println("-----------------use ThreadPool");
	}

	public static void useThreadPool() throws InterruptedException {
		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		for (int i = 0; i < THREAD_COUNT; i++) {
			executor.execute(new Task());
		}
		shutdownAndAwaitTermination(executor);
		// 使用死循环来一直等待
//		while (!executor.awaitTermination(2, TimeUnit.SECONDS));
		long end = System.currentTimeMillis();
		System.out.println("runtime: " + (end - start));
	}

	/**
	 * ExecutorService's usage example
	 * @param pool
	 */
	public static void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	public static void useCyclicBarrier() {
		long start = System.currentTimeMillis();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_COUNT);

		long end = System.currentTimeMillis();
		System.out.println("runtime: " + (end - start));
	}


	public static void useCountDownLatch() throws InterruptedException {
		long start = System.currentTimeMillis();
		CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
		for (int i = 0; i < THREAD_COUNT; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "start");
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					countDownLatch.countDown();
				}
				System.out.println(Thread.currentThread().getName() + "end");
			}).start();
		}
		countDownLatch.await();
		long end = System.currentTimeMillis();
		System.out.println("runtime: " + (end - start));
	}

	public static void endMoreJoin() throws InterruptedException {
		long start = System.currentTimeMillis();
		List<Thread> threads = new LinkedList<>();
		for (int i = 0; i < THREAD_COUNT; i++) {
			Task task = new Task();
			Thread thread = new Thread(task);
			threads.add(thread);
			thread.start();
		}

		for (int i = 0; i < THREAD_COUNT; i++) {
			threads.get(i).join();
		}

		long end = System.currentTimeMillis();
		System.out.println("runtime: " + (end - start));
	}

	public static void serialMoreJoin() throws InterruptedException {
		long start = System.currentTimeMillis();
		for (int i = 0; i < THREAD_COUNT; i++) {
			Task task = new Task();
			Thread thread = new Thread(task);
			thread.start();
			thread.join();
		}
		long end = System.currentTimeMillis();
		System.out.println("runtime: " + (end - start));
	}

	public static void useJoin() throws InterruptedException {
		long start = System.currentTimeMillis();
		Task task = new Task();
		Thread thread = new Thread(task);
		thread.start();
		thread.join();
		long end = System.currentTimeMillis();
		System.out.println("runtime: " + (end - start));
	}

	static class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "start");
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "end");
		}
	}

}
