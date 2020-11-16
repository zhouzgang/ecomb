package cn.ecomb.park.pool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 压测，线程池，连接数，连接池的优化
 *
 * @author brian.zhou
 * @date 2020/9/18
 */
@SpringBootApplication(scanBasePackages = {"cn.ecomb.*"})
public class PoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoolApplication.class);
	}
}
