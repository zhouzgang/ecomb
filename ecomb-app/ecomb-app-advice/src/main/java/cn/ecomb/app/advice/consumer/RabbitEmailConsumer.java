package cn.ecomb.app.advice.consumer;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 邮箱消费消息队列消息
 * 
 * @author brian.zhou
 * @date 2020/11/10
 */
@Component
@RabbitListener(queues = "test_queue")
public class RabbitEmailConsumer {

	@RabbitHandler
	public void process(String context, Message message, AMQP.Channel channel){
		System.out.println(context);
	}
}
