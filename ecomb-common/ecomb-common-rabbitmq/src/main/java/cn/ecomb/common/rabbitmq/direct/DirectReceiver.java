package cn.ecomb.common.rabbitmq.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author brian.zhou
 * @date 2020/11/10
 */
@Component
@RabbitListener(queues = "direct")
public class DirectReceiver {

	@RabbitHandler
	public void process(String message) {
		System.out.println("接收者 DirectReceiver," + message);
	}
}
