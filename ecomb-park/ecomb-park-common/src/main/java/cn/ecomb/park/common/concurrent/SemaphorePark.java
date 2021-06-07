package cn.ecomb.park.common.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author brian.zhou
 * @date 2021/6/2
 */
@Slf4j
public class SemaphorePark {

	public static void main(String[] args) {
		SemaphoreMutex.test();
		log.info("---------");

	}

	/**
	 * 使用 Semaphore 控制资源池访问数量
	 */
	@Slf4j
	static class SemaphorePool {
		private static final int MAX_AVAILABLE = 100;
		private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
		private Object[] items = new Object[MAX_AVAILABLE];
		private boolean[] used = new boolean[MAX_AVAILABLE];

		public Object getItem() throws InterruptedException {
			available.acquire();
			return getNextAvailableItem();
		}

		public boolean release(Object item) {
			if (releaseItem(item)) {
				available.release();
				return true;
			}
			return false;
		}

		private synchronized Object getNextAvailableItem() {
			for (int i = 0; i < MAX_AVAILABLE; i++) {
				if (!used[i]) {
					used[i] = true;
					return items[i];
				}
			}
			return null;
		}

		private synchronized boolean releaseItem(Object item) {
			for (int i = 0; i < MAX_AVAILABLE; i++) {
				if (item == items[i]) {
					if (used[i]) {
						used[i] = false;
						return true;
					} else {
						return false;
					}
				}
			}
			return false;
		}
	}


	/**
	 * 使用 Semaphore 实现互斥量
	 */
	@Slf4j
	static class SemaphoreMutex {

		private final static Semaphore mutex = new Semaphore(1);

		public static void test() {
			ExecutorService pools = Executors.newCachedThreadPool();

			for (int i = 0; i < 10; i++) {
				int finalI = i;
				pools.execute(new Runnable() {
					@Override
					public void run() {
						try {
							mutex.acquire();
							log.info("线程:{}---执行:{}---当前时间:{}", Thread.currentThread().getId(), finalI, LocalDateTime.now());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}finally {
							mutex.release();
							log.info("释放信号量");
						}
					}
				});
			}

			pools.shutdown();
		}
	}
}

