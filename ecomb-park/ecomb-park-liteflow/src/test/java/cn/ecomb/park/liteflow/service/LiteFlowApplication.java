package cn.ecomb.park.liteflow.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author brian.zhou
 * @date 2021/4/20
 */
@SpringBootApplication
@ComponentScan({"cn.ecomb.park.liteflow"})
public class LiteFlowApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(LiteFlowApplication.class, args);
	}
}
