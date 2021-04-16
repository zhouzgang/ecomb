package cn.ecomb.common.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author brian.zhou
 * @date 2021/4/16
 */
@Data
@Configuration
public class KafkaConfig {

	@Value("${spring.kafka.servers}")
	private String serversConfig;
}
