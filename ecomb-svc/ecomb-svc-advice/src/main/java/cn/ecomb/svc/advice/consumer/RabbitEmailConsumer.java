package cn.ecomb.svc.advice.consumer;

import com.rabbitmq.client.AMQP;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 邮箱消费消息队列消息
 * 
 * @author brian.zhou
 * @date 2020/11/10
 */
@Configuration
@Component
@RabbitListener(queues = "test_queue")
@Slf4j
public class RabbitEmailConsumer {

	@RabbitHandler
	public void process(User context){
		try {
			log.info("process context:{}", context);
			System.out.println(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Data
	public class User {
		private String name;
	}
}
