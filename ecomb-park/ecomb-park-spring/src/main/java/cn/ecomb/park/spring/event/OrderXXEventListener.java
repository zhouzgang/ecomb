package cn.ecomb.park.spring.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 事件监听
 *
 * @author brian.zhou
 * @date 2021/4/21
 */
@Component
@Slf4j
public class OrderXXEventListener {

	@EventListener
	public void orderXXStatusChange(OrderXXEvent orderXXEvent) {
		// 做一些业务处理，比如发送消息
		System.out.println("listener change: " + orderXXEvent.toString());
	}
}
