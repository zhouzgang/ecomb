package cn.ecomb.park.common.concurrent;

/**
 * 主线程等待子线程执行完成的各种方法
 *
 * @author brian.zhou
 * @date 2021/4/21
 */
public class WaitSubThread {

//	public void

	public void useJoin() throws InterruptedException {
		long start = System.currentTimeMillis();
		Task task = new Task();
		Thread thread = new Thread(task);
		thread.start();
		thread.join();
		long end = System.currentTimeMillis();
		System.out.println("runtime: " + (end - start));
	}

	class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "start");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "end");
		}
	}

}
