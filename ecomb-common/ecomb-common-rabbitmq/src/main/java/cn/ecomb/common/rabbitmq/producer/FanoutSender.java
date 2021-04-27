package cn.ecomb.common.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.ecomb.common.rabbitmq.config.QueueEnum.HELLO_FANOUT;
import static cn.ecomb.common.rabbitmq.config.QueueEnum.HELLO_FANOUT_ONE;

/**
 * @author brian.zhou
 * @date 2021/4/27
 */
@Slf4j
@Component
public class FanoutSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(String context) {

		// 消息发送失败返回到队列中, application.properties 配置 spring.rabbitmq.publisher-returns=true
		rabbitTemplate.setMandatory(true);


		this.rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->{
			log.info("return--message:" + new String(message.getBody()) + ",replyCode:" + replyCode +
					",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
		});

		this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (!ack) {
				log.info("HelloSender 发送失败：" + cause + correlationData.toString());
			} else {
				log.info("HelloSender 发送成功");
			}
		});

		log.info("HelloSender 发送的消息内容：{}", context);
		this.rabbitTemplate.convertAndSend(HELLO_FANOUT.getExchange(), HELLO_FANOUT.getRoutingKey(), context);
	}

}
