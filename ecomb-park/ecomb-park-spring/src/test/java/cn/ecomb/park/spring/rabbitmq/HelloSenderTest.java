package cn.ecomb.park.spring.rabbitmq;

import cn.ecomb.park.spring.BaseTest;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author brian.zhou
 * @date 2021/4/26
 */
public class HelloSenderTest extends BaseTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void test() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			rabbitTemplate.convertAndSend("test_topic", "test.route.key", "context");


			rabbitTemplate.convertAndSend("test_topic", "test.route.key.one", "context_bb");
//			rabbitTemplate.convertAndSend("test_topic", "test.route.one.key", "context_one");
		}
		Thread.sleep(10000);
	}
}