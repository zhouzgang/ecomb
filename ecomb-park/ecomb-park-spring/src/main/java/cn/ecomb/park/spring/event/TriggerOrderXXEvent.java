package cn.ecomb.park.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 业务代码中出发事件
 *
 * @author brian.zhou
 * @date 2021/4/21
 */
@Component
public class TriggerOrderXXEvent {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public void trigger() {
		System.out.println("change order status");
		eventPublisher.publishEvent(OrderXXEvent.builder()
				.orderId("123").orderStatus(2).build());
	}
}
