package cn.ecomb.app.advice.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * 配置默认的交换机模式
 * Direct Exchange是RabbitMQ默认的交换机模式，根据key全文匹配去寻找队列。
 *
 * @author brian.zhou
 * @date 2020/11/10
 */
public class RabbitDirectConfig {

	@Bean
	public Queue helloQueue() {
		return new Queue("hello");
	}

	@Bean
	public Queue directQueue() {
		return new Queue("direct");
	}

	//-------------------配置默认的交换机模式，可以不需要配置以下-----------------------------------
	@Bean
	DirectExchange directExchange() {
		return new DirectExchange("directExchange");
	}

	//绑定一个key "direct"，当消息匹配到就会放到这个队列中
	@Bean
	Binding bindingExchangeDirectQueue(Queue directQueue, DirectExchange directExchange) {
		return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
	}
	// 推荐使用 helloQueue（） 方法写法，这种方式在 Direct Exchange 模式 多此一举，没必要这样写
	//---------------------------------------------------------------------------------------------
}
