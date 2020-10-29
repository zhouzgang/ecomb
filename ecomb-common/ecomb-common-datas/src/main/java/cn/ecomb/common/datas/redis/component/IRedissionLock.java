package cn.ecomb.common.datas.redis.component;

import java.util.concurrent.TimeUnit;

/**
 * @author brian.zhou
 * @date 2020/10/26
 */
public interface IRedissionLock {

	boolean tryLock(String key);

	boolean tryLock(String key, long waitTime);

	boolean tryLock(String key, long waitTime, long sleepTime);

	boolean tryLock(String key, long waitTime, long sleepTime, TimeUnit timeUnit);

	void unLock(String key);
}
