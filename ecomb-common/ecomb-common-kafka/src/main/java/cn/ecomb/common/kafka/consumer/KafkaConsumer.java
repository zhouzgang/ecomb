package cn.ecomb.common.kafka.consumer;

import cn.ecomb.common.kafka.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * kafka 消费端
 *
 * @author brian.zhou
 * @date 2021/4/16
 */
@Slf4j
@Component
public class KafkaConsumer {

    /**
     * 单条消费
     * @param message
     */
    @KafkaListener(id = "id0", topics = {KafkaConstant.TOPIC}, containerFactory="kafkaListenerContainerFactory")
    public void kafkaListener0(String message){
        log.info("consumer:group0 --> message:{}", message);
    }

    @KafkaListener(id = "id1", topics = {KafkaConstant.TOPIC}, groupId = "group1")
    public void kafkaListener1(String message){
        log.info("consumer:group1 --> message:{}", message);
    }
    /**
     * 监听某个 Topic 的某个分区示例,也可以监听多个 Topic 的分区
     * 为什么找不到group2呢？
     * @param message
     */
    @KafkaListener(id = "id2", groupId = "group2",
		    topicPartitions = { @TopicPartition(topic = KafkaConstant.TOPIC, partitions = { "0" }) })
    public void kafkaListener2(String message) {
        log.info("consumer:group2 --> message:{}", message);
    }

    /**
     * 获取监听的 topic 消息头中的元数据
     * @param message
     * @param topic
     * @param key
     */
    @KafkaListener(id = "id3", topics = KafkaConstant.TOPIC, groupId = "group3")
    public void kafkaListener(@Payload String message,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String partition,
                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        Long threadId = Thread.currentThread().getId();
        log.info("consumer:group3 --> message:{}, topic:{}, partition:{}, key:{}, threadId:{}",
		        message, topic, partition, key, threadId);
    }

    /**
     * 监听 topic 进行批量消费
     * @param messages
     */
    @KafkaListener(id = "id4", topics = KafkaConstant.TOPIC, groupId = "group4")
    public void kafkaListener(List<String> messages) {
        for(String msg:messages){
            log.info("consumer:group4 --> message:{}", msg);
        }
    }

    /**
     * 监听topic并手动提交偏移量
     * @param messages
     * @param acknowledgment
     */
    @KafkaListener(id = "id5", topics = KafkaConstant.TOPIC,groupId = "group5")
    public void kafkaListener(List<String> messages, Acknowledgment acknowledgment) {
        for(String msg:messages){
            log.info("consumer:group5 --> message:{}", msg);
        }
        // 触发提交offset偏移量
        acknowledgment.acknowledge();
    }

    /**
     * 模糊匹配多个 Topic
     * @param message
     */
    @KafkaListener(id = "id6", topicPattern = "test.*",groupId = "group6")
    public void annoListener2(String message) {
        log.error("consumer:group6 --> message:{}", message);
    }

	/**
	 * 完整consumer
	 * @return
	 */
	@KafkaListener(id = "id7", topics = {KafkaConstant.TOPIC}, groupId = "group7")
	public boolean consumer4(List<ConsumerRecord<?, ?>> data) {
		for (int i=0; i<data.size(); i++) {
			ConsumerRecord<?, ?> record = data.get(i);
			Optional<?> kafkaMessage = Optional.ofNullable(record.value());

			Long threadId = Thread.currentThread().getId();
			if (kafkaMessage.isPresent()) {
				Object message = kafkaMessage.get();
				log.info("consumer:group7 --> message:{}, topic:{}, partition:{}, key:{}, offset:{}, threadId:{}", message.toString(), record.topic(), record.partition(), record.key(), record.offset(), threadId);
			}
		}

		return true;
	}
}
