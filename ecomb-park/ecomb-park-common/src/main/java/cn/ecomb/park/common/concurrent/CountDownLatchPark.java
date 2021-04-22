package cn.ecomb.park.common.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 测试 CountDownLatch 注释里面的例子
 * 1. 新建 CountDownLatch 时就创建了一个 AQS 的子类对象 Sync
 * 2. 调用 countDown() 时，先 tryReleaseShared，在 doReleaseShared。try 使用 UnSafe 的 CAS 实现
 * 3. 调用 await() 时，调用 AQS 的 acquireSharedInterruptibly() 方法。
 * Unsafe 和 AQS 的问题，去各自的 park 测试
 *
 * Reference
 * - [Java 魔法类：Unsafe应用解析](https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html)
 * - [Java Magic. Part 4: sun.misc.Unsafe](http://mishadoff.com/blog/java-magic-part-4-sun-dot-misc-dot-unsafe/)
 * @author brian.zhou
 * @date 2021/4/22
 */
public class CountDownLatchPark {

	private static final int COUNT = 5;

	public static void main(String[] args) throws InterruptedException {
		CountDownLatchPark.instantBegin();
		System.out.println("-------------instant begin");
		CountDownLatchPark.countDown();
		System.out.println("-------------simple countDown");
	}

	public static void countDown() throws InterruptedException {

		CountDownLatch doneSignal = new CountDownLatch(COUNT);
		Executor e = Executors.newCachedThreadPool();

		for (int i = 0; i < COUNT; ++i) {
			e.execute(new WorkerRunnable(doneSignal, i));
		}
		// wait for all to finish
		doneSignal.await();
		System.out.println("complete");
	}

	static class WorkerRunnable implements Runnable {
		private final CountDownLatch doneSignal;
		private final int i;

		WorkerRunnable(CountDownLatch doneSignal, int i) {
			this.doneSignal = doneSignal;
			this.i = i;
		}

		public void run() {
			doWork(i);
			doneSignal.countDown();
		}

		public void doWork(int i) {
			System.out.println(Thread.currentThread().getName() + " do work");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 统一发起式工作指令
	 *
	 * @throws InterruptedException
	 */
	public static void instantBegin() throws InterruptedException {
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(COUNT);

		for (int i = 0; i < COUNT; ++i) {
			new Thread(new Worker(startSignal, doneSignal)).start();
		}

		System.out.println("do something before");
		// 这里相当于一个指令，开始放开所有线程做事
		startSignal.countDown();
		System.out.println("begin to do something");
		doneSignal.await();		   // wait for all to finish
		System.out.println("complete");
	}

	static class Worker implements Runnable {

		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;

		Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		public void run() {
			try {
				startSignal.await();
				doWork();
				doneSignal.countDown();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}

		public void doWork() {
			System.out.println(Thread.currentThread().getName() + " do work");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
