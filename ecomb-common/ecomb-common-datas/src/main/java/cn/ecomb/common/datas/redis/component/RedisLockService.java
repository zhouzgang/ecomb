package cn.ecomb.common.datas.redis.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Redis 实现分布式锁
 *
 * @author brian.zhou
 * @date 2020/10/26
 */
@Service
@Slf4j
public class RedisLockService {

    @Autowired
    private RedisHandle redisHandle;

    private static final Long lockExpiresTime = 5000L;
    private static final String KEY_PRE = "REDIS_LOCK_";
    private static final String SET_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final Long TRY_LOCK_TIME = 2000L;

    public String lock(String key) {
        String value = fetchLockValue();
        if (redisHandle.setNx(KEY_PRE + key, value, lockExpiresTime)) {
            return value;
        }
        return null;
    }

    public String tryLock(String key) {
        Long firstTryTime = new Date().getTime();
        while (new Date().getTime() - firstTryTime < TRY_LOCK_TIME) {
            String value = lock(key);
            if (value != null) {
                return value;
            }
            log.info("Redis 加锁失败，等待尝试下一个加锁");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean unLock(String key, String value) {
        String command = "if redis.call('get',KEYS[1])==ARGV[1] " +
                "then return redis.call('del',KEYS[1]) " +
                "else return 0 end";
        Long release = (Long) redisHandle.command(RedisScript.of(command, Long.class),
                Collections.singletonList(key),
                Collections.singletonList(value));
        return release.equals(RELEASE_SUCCESS);
    }

    private String fetchLockValue() {
        return UUID.randomUUID().toString() + "_" + df.format(new Date());
    }

}
