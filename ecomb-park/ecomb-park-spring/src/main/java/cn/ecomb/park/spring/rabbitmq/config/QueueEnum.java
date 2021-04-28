package cn.ecomb.park.spring.rabbitmq.config;

import lombok.Getter;

/**
 * 队列配置枚举
 *
 * @author brian.zhou
 * @date 2020/12/14
 */
@Getter
public enum QueueEnum {
	HELLO_TOPIC("test_topic", "test.route.key", "test.queue"),

	HELLO_FANOUT("test_fanout", "test_fanout_route_key", "test.fanout.queue"),
	HELLO_FANOUT_ONE("test_fanout", "test_fanout_route_one_key", "test.fanout.one.queue"),

	QUEUE_DELAY("ecomb_queue_delay_exchange", "delay_routing_key", "delay_queue");

	private String exchange;
	private String routingKey;
	private String queue;

	QueueEnum(String exchange, String routingKey, String queue) {
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.queue = queue;
	}

	public String getExchange() {
		return exchange;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public String getQueue() {
		return queue;
	}
}
