package cn.ecomb.park.spring;

import cn.ecomb.park.spi.IServiceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author brian.zhou
 * @date 2021/4/20
 */
@SpringBootApplication(scanBasePackages = {"cn.ecomb.*"})
public class ParkApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);
		IServiceProvider serviceProvider = context.getBean(IServiceProvider.class);
		serviceProvider.sayHello();
	}
}
