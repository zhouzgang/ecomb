package cn.ecomb.web.app.listener;

import cn.ecomb.web.app.listener.event.CreateOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 监听事件
 * @author brian.zhou
 * @date 2020/12/14
 */
@Component
@Slf4j
public class CreateOrderListener {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@EventListener
	public void CreateOrder(CreateOrderEvent createOrderEvent) {
		log.info("下单后发送短信，邮件:{}", createOrderEvent.getSource());
	}
}
