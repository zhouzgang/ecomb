package cn.ecomb.common.rabbitmq;

import lombok.Getter;

/**
 * 队列配置枚举
 *
 * @author brian.zhou
 * @date 2020/12/14
 */
@Getter
public enum  QueueEnum {

	QUEUE_DELAY("ecomb_queue_delay_exchange", "delay_routing_key", "delay_queue");

	private String exchange;
	private String routingKey;
	private String queue;

	QueueEnum(String exchange, String routingKey, String queue) {
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.queue = queue;
	}
}
