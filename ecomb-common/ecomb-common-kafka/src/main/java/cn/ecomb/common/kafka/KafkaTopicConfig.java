package cn.ecomb.common.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brian.zhou
 * @date 2021/4/16
 */
//@Configuration
public class KafkaTopicConfig {

	@Autowired
	private KafkaConfig KafkaConfig;

	/**
	 * 定义一个KafkaAdmin的bean，可以自动检测集群中是否存在topic，不存在则创建
	 */
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		// 指定多个kafka集群多个地址，例如：192.168.2.11,9092,192.168.2.12:9092,192.168.2.13:9092
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.getServersConfig());
		return new KafkaAdmin(configs);
	}

	/**
	 * 创建 Topic
	 */
	@Bean
	public NewTopic topicinfo() {
		// 创建topic，需要指定创建的topic的"名称"、"分区数"、"副本数量(副本数数目设置要小于Broker数量)"
		return new NewTopic("test", 3, (short) 2);
	}
}
