package cn.ecomb.common.rabbitmq.producer;

import cn.ecomb.common.rabbitmq.BaseTest;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static cn.ecomb.common.rabbitmq.config.QueueEnum.HELLO_FANOUT;
import static cn.ecomb.common.rabbitmq.config.QueueEnum.HELLO_FANOUT_ONE;
import static org.junit.Assert.*;

/**
 * @author brian.zhou
 * @date 2021/4/27
 */
public class FanoutSenderTest extends BaseTest {

	@Autowired
	private FanoutSender fanoutSender;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void sendMessage() {

		for (int i = 0; i < 10; i++) {
//			routingKey 会被忽略
			rabbitTemplate.convertAndSend(HELLO_FANOUT.getExchange(), "", "context");
//			rabbitTemplate.convertAndSend(HELLO_FANOUT_ONE.getExchange(), HELLO_FANOUT_ONE.getRoutingKey(), "context_one");
		}
	}
}