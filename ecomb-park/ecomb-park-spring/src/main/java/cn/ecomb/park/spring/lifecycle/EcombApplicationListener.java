package cn.ecomb.park.spring.lifecycle;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
public class EcombApplicationListener implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("ecomb listener: " + event.toString());
	}
}
