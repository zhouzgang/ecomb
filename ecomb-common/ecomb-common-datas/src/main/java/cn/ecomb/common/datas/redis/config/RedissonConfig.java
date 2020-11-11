package cn.ecomb.common.datas.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author brian.zhou
 * @date 2020/10/26
 */
//@Configuration
public class RedissonConfig {

	@Value("${redisson.address}")
	private String redissonAddress;

	/** redis连接密码 */
	@Value("${spring.redis.password}")
	private String password;

	/** redis连接时间 */
	@Value("${redisson.timeout}")
	private Integer timeout;

	/** redis连接池大小 */
	@Value("${redisson.connectionPoolSize}")
	private Integer connectionPoolSize;

	/** redis最小连接空闲数 */
	@Value("${redisson.connectionMinimumIdleSize}")
	private Integer connectionMinimumIdleSize;

	public RedissonConfig() {
	}

	/**
	 * 获取单机模式配置
	 * @return
	 */
	@Bean
	public RedissonClient getRedissonClient() {
		Config config = new Config();
		config.useSingleServer()
				.setAddress(redissonAddress)
				.setPassword(password)
				.setTimeout(timeout)
				.setConnectionPoolSize(connectionPoolSize)
				.setConnectionMinimumIdleSize(connectionMinimumIdleSize);
		return Redisson.create(config);
	}
}
