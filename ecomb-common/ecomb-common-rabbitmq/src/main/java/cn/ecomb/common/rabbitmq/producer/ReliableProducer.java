package cn.ecomb.common.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.ecomb.common.rabbitmq.config.QueueEnum.HELLO_FANOUT;

/**
 * @author brian.zhou
 * @date 2021/5/25
 */
@Slf4j
@Component
public class ReliableProducer {

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
				log.info("删除数据库中消息记录");
			} else {
				log.info("HelloSender 发送成功");
				log.info("定时任务重发消息");
			}
		});

		log.info("HelloSender 发送的消息内容：{}", context);

		// 数据操作，比如修改状态，记录数等
		log.info("往数据库插入一条记录");
		// 注意 mq 发送消息是异步的，如果非常严谨的来讲，插入记录失败，就应该回滚事务，因为不能确保消息可以被检查到。
		this.rabbitTemplate.convertAndSend(HELLO_FANOUT.getExchange(), HELLO_FANOUT.getRoutingKey(), context);
	}
}
