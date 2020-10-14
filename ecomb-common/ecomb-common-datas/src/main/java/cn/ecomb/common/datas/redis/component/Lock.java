package cn.ecomb.common.datas.redis.component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * 分布式锁
 *
 * @author brian.zhou
 * @date 2020/10/14
 */
public class Lock implements java.util.concurrent.locks.Lock {

	@Override
	public void lock() {

	}

	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {

	}

	@Override
	public Condition newCondition() {
		return null;
	}
}
