package cn.ecomb.common.datas.redis.component;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author brian.zhou
 * @date 2020/10/26
 */
//@Component
public class RedissonLockImpl implements IRedissionLock {
	@Autowired
	private RedissonClient redissonClient;

	/**
	 * 获取锁超时时间
	 */
	private static final long waitTime = 50000L;

	/**
	 * 锁过期时间
	 * 单个线程拥有锁的最长时间（有自动续期的逻辑）
	 */
	private static final long leaseTime = 20000L;

	private static TimeUnit timeUnit = TimeUnit.MILLISECONDS;

	private static final ConcurrentMap<String, RLock> rLockConcurrentMap = new ConcurrentHashMap<>();

	@Value("${redisson.lockKeyPre}")
	private String lockKeyPre;

	@Override
	public boolean tryLock(String key) {
		return this.tryLock(key, waitTime, leaseTime, timeUnit);
	}

	@Override
	public boolean tryLock(String key, long leaseTime) {
		return this.tryLock(key, waitTime, leaseTime, timeUnit);
	}

	@Override
	public boolean tryLock(String key, long leaseTime, long sleepTime) {
		return this.tryLock(key, waitTime, sleepTime, timeUnit);
	}

	@Override
	public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit timeUnit) {
		RLock rLock = this.getRLock(lockKeyPre + key);
		try {
			return rLock.tryLock(waitTime, leaseTime, timeUnit);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void unLock(String key) {
		this.getRLock(lockKeyPre + key).unlock();
	}

	private RLock getRLock(String objectName) {
		String saveKey = Thread.currentThread().getId() + ":" + objectName;
		RLock rLock = rLockConcurrentMap.get(saveKey);

		if (rLock == null) {
			rLock = this.newRLock(objectName);
			rLockConcurrentMap.put(saveKey, rLock);
		}

		return rLock;
	}

	private RLock newRLock(String key) {
		return redissonClient.getLock(key);
	}

}
