package cn.ecomb.park.liteflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author brian.zhou
 * @date 2021/4/20
 */
@SpringBootApplication
public class LiteFlowApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(LiteFlowApplication.class, args);
	}
}
