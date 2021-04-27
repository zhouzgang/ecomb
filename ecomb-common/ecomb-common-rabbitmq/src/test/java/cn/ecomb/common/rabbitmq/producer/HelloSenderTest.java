package cn.ecomb.common.rabbitmq.producer;

import cn.ecomb.common.rabbitmq.BaseTest;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author brian.zhou
 * @date 2021/4/26
 */
public class HelloSenderTest extends BaseTest {

	@Autowired
	private HelloSender helloSender;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void sendMessage() {
		for (int i = 0; i < 10; i++) {
			helloSender.sendMessage("{\"data\":\"hello\" " + i + "}");
		}
	}

	@Test
	public void test() {
		for (int i = 0; i < 10; i++) {
			rabbitTemplate.convertAndSend("test_topic", "test.route.key", "context");
			rabbitTemplate.convertAndSend("test_topic", "test.route.one.key", "context_one");
		}
	}
}