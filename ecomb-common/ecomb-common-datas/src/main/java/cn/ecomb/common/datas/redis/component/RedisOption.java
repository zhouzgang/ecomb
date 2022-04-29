package cn.ecomb.common.datas.redis.component;

import cn.ecomb.common.datas.redis.constant.RedisKeyConstant;
import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilter;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

import static cn.ecomb.common.datas.redis.constant.RedisKeyConstant.NOT_FOUND;

/**
 * @author brian.zhou
 * @date 2/12/22
 */
@Repository("redisHandle")
@Slf4j
public class RedisOption {

    @Autowired
    private RedisHandle redisHandle;

    @Value("${business.bussLogicRedisExpire:60}")
    private int bussLogicRedisExpire;

    /**
     * redis 操作模版
     * 问题：缓存穿透、缓存雪崩、缓存击穿
     * 一句话概括，缓存因为各种原因，没有生效，导致数据库压力过大，服务不可用。
     * 数据库访问量级一般是数千水平，Redis 缓存一般可以达到数万水平，相差数十倍。
     *
     * 在这条线上，有哪些可能的情况：
     * 1. 缓存服务不可用，访问数据
     * 2. 大量不存在的 key 访问缓存，导致访问数据库，增加数据库压力。
     * 3. 大量存在的 key 同时过期，导致访问数据。
     *
     * @param appKey 业务appKey
     *
     * @return 业务信息
     */
    public Object getBusinessByKey(String appKey) {
        Object businessEntity = null;
        String key = String.format(RedisKeyConstant.Business.BUSINESS_KEY,appKey);
        BloomFilter bloomFilter = new BitMapBloomFilter(1000);
        String bussStr = (String) redisHandle.get(key);
        if(StringUtils.isEmpty(bussStr)){
            // 这里进行数据库查询操作
//            businessEntity = businessRepository.getBussByAppKey(appKey);
            log.info("数据查到的业务信息{}", businessEntity);
            int expire = RandomUtil.randomInt(bussLogicRedisExpire, bussLogicRedisExpire * 3);
            if (businessEntity != null) {
                redisHandle.set(key, businessEntity, expire);
            } else {
                redisHandle.set(key, NOT_FOUND, expire);
            }
        }else{
            if (NOT_FOUND.equals(bussStr)) {
                throw new RuntimeException("not found key");
            }
            businessEntity = JSONUtil.toBean(bussStr, Object.class);
        }
        return businessEntity;
    }
}
