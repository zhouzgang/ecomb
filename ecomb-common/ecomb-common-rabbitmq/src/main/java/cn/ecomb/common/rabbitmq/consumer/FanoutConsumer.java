package cn.ecomb.common.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static cn.ecomb.common.rabbitmq.config.QueueEnum.HELLO_FANOUT;

/**
 * @author brian.zhou
 * @date 2021/4/27
 */
@Slf4j
@Component
public class FanoutConsumer {

	@RabbitListener(queues = "test.fanout.queue", concurrency = "4")
	public void process(String result, Message message, @Headers Map<String, Object> headers, Channel channel)
			throws IOException {
		log.info("【监听到队列消息】 - 【消费时间】 - [{}]- 【消费者】 - [{}]-【消息内容】 - [{}]", new Date(), "fanout", result);
	}

	@RabbitListener(queues = "test.fanout.one.queue", concurrency = "4")
	public void processOne(String result, Message message, @Headers Map<String, Object> headers, Channel channel)
			throws IOException {
		log.info("【监听到队列消息】 - 【消费时间】 - [{}]- 【消费者】 - [{}]-【消息内容】 - [{}]", new Date(), "fanoutOne", result);
	}
}
