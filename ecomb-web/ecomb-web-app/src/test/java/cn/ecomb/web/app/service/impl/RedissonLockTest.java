package cn.ecomb.web.app.service.impl;

import cn.ecomb.common.datas.redis.component.IRedissionLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author brian.zhou
 * @date 2020/10/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonLockTest {

	@Autowired
	private IRedissionLock redssionLock;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Integer sum = 0;


	/**
	 * redisLock demo
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void test() throws InterruptedException {
		logger.info("redis-lock-test:    client start! ");
		ExecutorService executorService = Executors.newCachedThreadPool();
		//同步工具类
		CountDownLatch latch = new CountDownLatch(100);
		for (int i = 0; i < 100; i++) {
			//从线程池中获取100个线程，并执行
			executorService.submit(new MyLock("client-" + i, latch));
			logger.info("client-" + i + " init completed !");
		}
		//关闭线程池
		executorService.shutdown();
		latch.await();
		logger.info("redis-lock-test: all client completed -> sum: {} ", sum);
	}


	class MyLock implements Runnable {

		private String name;
		private CountDownLatch latch;

		public MyLock(String name, CountDownLatch latch) {
			this.name = name;
			this.latch = latch;
		}

		@Override
		public void run() {
			String key = "root";
			try {
				if (redssionLock.tryLock(key)) {
					sum += 1;
					logger.info(this.name + " get lock success key: " + key + "  work -> sum = " + sum);
				} else {
					logger.error(this.name + " get lock key: " + key + " timeout!");
				}
				latch.countDown();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					redssionLock.unLock(key);
					logger.info(this.name + "    release    " + key);
				} catch (Exception e) {
					logger.error(this.name + " release " + key + " error");
				}
			}
		}
	}
}
