package cn.ecomb.common.rabbitmq;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * @author brian.zhou
 * @date 2020/11/10
 */
@Configuration
//@PropertySource(value = {"classpath:rabbitmq.yml"})
//@ConfigurationProperties(prefix = "rabbitmq")
//@Data
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
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue queue() {
		return new Queue("test_queue",true,  false, false);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange("test_topic", true, false);
	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with("test_route_key");
	}
}
