package cn.ecomb.park.spring.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author brian.zhou
 * @date 2021/4/27
 */
@Slf4j
@Component
public class HelloConsumer {

	@RabbitListener(queues = "test.queue", concurrency = "4")
	public void process(String result, Message message, @Headers Map<String, Object> headers, Channel channel)
			throws IOException {
		log.info("【监听到队列消息】 - 【消费时间】 - [{}]- 【消费者】 - [{}]-【消息内容】 - [{}]", new Date(), "precess", result);
	}

	@RabbitListener(queues = "test.one.queue", concurrency = "4")
	public void processOne(String result, Message message, @Headers Map<String, Object> headers, Channel channel)
			throws IOException {
		log.info("【监听到队列消息】 - 【消费时间】 - [{}]- 【消费者】 - [{}]-【消息内容】 - [{}]", new Date(), "precessOne", result);
	}
}
