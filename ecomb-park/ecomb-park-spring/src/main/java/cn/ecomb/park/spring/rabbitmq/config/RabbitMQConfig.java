package cn.ecomb.park.spring.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

import static cn.ecomb.park.spring.rabbitmq.config.QueueEnum.HELLO_FANOUT;
import static cn.ecomb.park.spring.rabbitmq.config.QueueEnum.HELLO_FANOUT_ONE;

/**
 * @author brian.zhou
 * @date 2020/11/10
 */
@Configuration
public class RabbitMQConfig {

	@Autowired
	private Environment env;

	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.port}")
	private String port;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;

	@Bean
	public RabbitTemplate rabbitTemplate() throws Exception {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setChannelTransacted(true);
		return rabbitTemplate;
	}

	@Bean
	public ConnectionFactory connectionFactory() throws Exception {
		RabbitConnectionFactoryBean connFactoryBean = new RabbitConnectionFactoryBean();
		connFactoryBean.setHost(host);
		connFactoryBean.setPort(Integer.parseInt(port));
		connFactoryBean.setUsername(username);
		connFactoryBean.setPassword(password);
		connFactoryBean.afterPropertiesSet();

		return new CachingConnectionFactory(Objects.requireNonNull(connFactoryBean.getObject()));
	}

	@Bean
	public AmqpAdmin amqpAdmin() throws Exception {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	// topic 模式
	@Bean
	public Queue queue() {
		return new Queue("test.queue",true,  false, false);
	}

	@Bean
	public Queue queueOne() {
		return new Queue("test.one.queue",true,  false, false);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange("test_topic", true, false);
	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with("test.route.key");
	}

	@Bean
	public Binding bbBinding() {
		return BindingBuilder.bind(queue()).to(exchange()).with("test.route.key.one");
	}

	@Bean
	public Binding bindingOne() {
		return BindingBuilder.bind(queueOne()).to(exchange()).with("test.route.one.uuu");
	}


	@Bean
	public TopicExchange bExchange() {
		return new TopicExchange("test_b_topic", true, false);
	}

	@Bean
	public Binding bBinding() {
		return BindingBuilder.bind(queue()).to(bExchange()).with("test.route.bQueue");
	}

	// fanout 模式
	@Bean
	public Queue fanoutQueue() {
		return new Queue(HELLO_FANOUT.getQueue(),true,  false, false);
	}

	@Bean
	public Queue fanoutOneQueue() {
		return new Queue(HELLO_FANOUT_ONE.getQueue(),true,  false, false);
	}

	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(HELLO_FANOUT.getExchange(), true, false);
	}

	@Bean
	public Binding fanoutBinding() {
		return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
	}

	@Bean
	public Binding fanoutOneBinding() {
		return BindingBuilder.bind(fanoutOneQueue()).to(fanoutExchange());
	}


// todo 需要安装延时插件
//	@Bean
//	public Queue immediateQueue() {
//		return new Queue(QueueEnum.QUEUE_DELAY.getQueue());
//	}
//
//	@Bean
//	public CustomExchange customExchange() {
//		Map<String, Object> args = new HashMap<>();
//		args.put("x-delayed-type", "direct");
//		return new CustomExchange(QueueEnum.QUEUE_DELAY.getExchange(), "x-delayed-message",
//				true, false, args);
//	}
//
//	@Bean
//	public Binding bindingNotify(@Qualifier("immediateQueue") Queue queue,
//	                             @Qualifier("customExchange") CustomExchange customExchange) {
//		return BindingBuilder.bind(queue).to(customExchange).with(QueueEnum.QUEUE_DELAY.getRoutingKey()).noargs();
//	}
}
