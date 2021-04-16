package cn.ecomb.common.kafka.consumer;

import cn.ecomb.common.kafka.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置消费者
 * KafkaListenerContainerFactory 的配置需要考虑不同消息的业务场景，确保消息及时的消费掉。
 * 如果在一个业务场景下，KafkaListenerContainerFactory 可否用同一个，减少重复配置
 *
 * @author brian.zhou
 * @date 2021/4/16
 */
@Configuration
public class KafkaConsumerConfig {

	@Autowired
	private KafkaConfig KafkaConfig;

	private static final String GROUP0_ID = "group0";
	private static final String GROUP1_ID = "group1";

	/**
	 * 1. setAckMode: 消费者手动提交ack
	 *
	 * RECORD： 每处理完一条记录后提交。
	 * BATCH(默认)： 每次poll一批数据后提交一次，频率取决于每次poll的调用频率。
	 * TIME： 每次间隔ackTime的时间提交。
	 * COUNT： 处理完poll的一批数据后并且距离上次提交处理的记录数超过了设置的ackCount就提交。
	 * COUNT_TIME： TIME和COUNT中任意一条满足即提交。
	 * MANUAL： 手动调用Acknowledgment.acknowledge()后，并且处理完poll的这批数据后提交。
	 * MANUAL_IMMEDIATE： 手动调用Acknowledgment.acknowledge()后立即提交。
	 *
	 * 2. factory.setConcurrency(3);
	 * 此处设置的目的在于：假设 topic test 下有 0、1、2三个 partition，Spring Boot中只有一个 @KafkaListener() 消费者订阅此 topic，此处设置并发为3，
	 * 启动后 会有三个不同的消费者分别订阅 p0、p1、p2，本地实际有三个消费者线程。
	 * 而 factory.setConcurrency(1); 的话 本地只有一个消费者线程， p0、p1、p2被同一个消费者订阅。
	 * 由于 一个partition只能被同一个消费者组下的一个消费者订阅，对于只有一个 partition的topic，即使设置 并发为3，也只会有一个消费者，多余的消费者没有 partition可以订阅。
	 *
	 * 3. factory.setBatchListener(true);
	 * 设置批量消费 ，每个批次数量在Kafka配置参数ConsumerConfig.MAX_POLL_RECORDS_CONFIG中配置，
	 * 限制的是 一次批量接收的最大条数，而不是 等到达到最大条数才接收，这点容易被误解。
	 * 实际测试时，接收是实时的，当生产者大量写入时，一次批量接收的消息数量为 配置的最大条数。
	 */
	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String>
				factory = new ConcurrentKafkaListenerContainerFactory<>();
		// 设置消费者工厂
		factory.setConsumerFactory(consumerFactory());
		// 设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
		factory.setBatchListener(true);
		// 消费者组中线程数量,消费者数量<=partition数量，即使配置的消费者数量大于partition数量，多余消费者无法消费到数据。
		factory.setConcurrency(4);
		// 拉取超时时间
		factory.getContainerProperties().setPollTimeout(3000);
		// 手动提交
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
		return factory;
	}

	@Bean
	public ConsumerFactory<Integer, String> consumerFactory() {
		Map<String, Object> map = consumerConfigs();
		map.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP0_ID);
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory1() {
        ConcurrentKafkaListenerContainerFactory<Integer, String>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        // 设置消费者工厂
        factory.setConsumerFactory(consumerFactory1());
        // 设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.setBatchListener(true);
        // 消费者组中线程数量,消费者数量<=partition数量，即使配置的消费者数量大于partition数量，多余消费者无法消费到数据。
        factory.setConcurrency(3);
        // 拉取超时时间
        factory.getContainerProperties().setPollTimeout(3000);
        // 手动提交
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    public ConsumerFactory<Integer, String> consumerFactory1() {
        Map<String, Object> map = consumerConfigs();
        map.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP1_ID);
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		// Kafka地址
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.getServersConfig());
		// 是否自动提交offset偏移量(默认true)
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		// 批量消费
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
		// 消费者组
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-default");
		// 自动提交的频率(ms)
//        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		// Session超时设置
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		// 键的反序列化方式
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// 值的反序列化方式
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// offset偏移量规则设置：
		// (1)、earliest：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
		// (2)、latest：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
		// (3)、none：topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		return props;
	}
}
