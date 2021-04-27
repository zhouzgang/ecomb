package cn.ecomb.common.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author brian.zhou
 * @date 2020/9/18
 */
@SpringBootApplication(scanBasePackages = {"cn.ecomb.*"})
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class);
	}
}
