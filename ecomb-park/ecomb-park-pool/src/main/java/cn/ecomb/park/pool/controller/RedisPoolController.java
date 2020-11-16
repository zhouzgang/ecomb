package cn.ecomb.park.pool.controller;

import cn.ecomb.common.datas.redis.component.RedisHandle;
import cn.ecomb.park.pool.constant.PoolConstant;
import jodd.util.RandomString;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Pool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author brian.zhou
 * @date 2020/11/13
 */
@RestController
@RequestMapping("/pool/redis")
@Slf4j
public class RedisPoolController {

	@Autowired
	RedisHandle redisHandle;

	@GetMapping
	public String testRedisPool() throws InterruptedException {
		String threadName = Thread.currentThread().getName();
		String key = String.format(PoolConstant.REDIS_TEST_POOL_KEY, new Random().nextInt(100000));
		log.info("set pool key：{}, value: {}", key, threadName);
		redisHandle.set(key, threadName, 5000);
		Thread.sleep(30);
		String redisStr = (String) redisHandle.get(key);
		log.info("get pool value: {}", redisStr);
		return redisStr;
	}
}
