package cn.ecomb.park.spring.lifecycle;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
public class EcombApplicationContextInitializer implements ApplicationContextInitializer {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		System.out.println("init ecomb");
	}
}
