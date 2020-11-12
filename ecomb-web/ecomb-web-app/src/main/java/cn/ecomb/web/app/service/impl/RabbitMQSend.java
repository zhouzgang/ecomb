package cn.ecomb.web.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author brian.zhou
 * @date 2020/11/11
 */
@Service
@Slf4j
public class RabbitMQSend implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback{

	private final static MessagePostProcessor messagePostProcessor = message -> {
		message.getMessageProperties().setContentType("application/json");
//		message.getMessageProperties().setContentType("text/plain");
		message.getMessageProperties().setContentEncoding("UTF-8");
		return message;
	};


		@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send() {
		rabbitTemplate.setMandatory(true);

		this.rabbitTemplate.setReturnCallback(this);
//		this.rabbitTemplate.setConfirmCallback(this);

		this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (!ack) {
				log.info("HelloSender 发送失败：" + cause + correlationData.toString());
			} else {
				log.info("HelloSender 发送成功");
			}
		});

		for (int i = 0; i < 10; i++) {
			String message = "{\"name\":\"test_" + i + "\"}";
			log.info("HelloSender 发送的消息内容：{}", message);
			rabbitTemplate.convertAndSend("test_topic", "test_route_key", message, messagePostProcessor);
//			rabbitTemplate.convertAndSend("hello", "context" + i);
		}
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info("消息id: " + correlationData + "确认" + (ack ? "成功:" : "失败"));
	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		log.info("return--message:" + new String(message.getBody()) + ",replyCode:" + replyCode + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
	}
}
