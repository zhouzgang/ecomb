package cn.ecomb.common.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * kafka 生产者
 * 一个项目中的消息发送应该统一到同一个地方，统一管理好，而不是散乱到各处。
 * 各种组件，业务模块也应该是一样，要有个统一的分层，划分。
 * 对修改关闭，对扩展开放。
 *
 * @author brian.zhou
 * @date 2021/4/16
 */
@Slf4j
@Component
public class KafkaProducer {

	@Autowired
	private KafkaTemplate kafkaTemplate;

	/**
	 * producer 同步方式发送数据
	 * @param topic    topic名称
	 * @param key      一般用业务id，相同业务在同一partition保证消费顺序
	 * @param message  producer发送的数据
	 */
	protected void sendMessageSync(String topic, String key, String message) throws InterruptedException, ExecutionException, TimeoutException {
		// 默认轮询partition
		kafkaTemplate.send(topic, message)
				.get(10, TimeUnit.SECONDS);
//        // 根据key进行hash运算，再将运算结果写入到不同partition
//        kafkaTemplate.send(topic, key, message).get(10, TimeUnit.SECONDS);

//        // 第二个参数为partition,当partition和key同时设置时partition优先。
//        kafkaTemplate.send(topic, 0, key, message);
	}

	/**
	 * 发送组装消息
	 * @param topic    topic名称
	 * @param key      一般用业务id，相同业务在同一partition保证消费顺序
	 * @param message  producer发送的数据
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws TimeoutException
	 */
	protected void sendBuilderMessageSync(String topic, String key, String message) throws InterruptedException, ExecutionException, TimeoutException {

        // 组装消息
        Message msg = MessageBuilder.withPayload("Send Message(payload,headers) Test")
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.PREFIX,"kafka_")
                .build();
        kafkaTemplate.send(msg).get(10, TimeUnit.SECONDS);

//        // 组装消息
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test", "Send ProducerRecord(topic,value) Test");
//        kafkaTemplate.send(producerRecord).get(10, TimeUnit.SECONDS);
	}

	/**
	 * producer 异步方式发送数据
	 * @param topic    topic名称
	 * @param message  producer发送的数据
	 */
	protected void sendMessageAsync(String topic, String message) {
		ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, message);

		// 设置异步发送消息获取发送结果后执行的动作
		ListenableFutureCallback listenableFutureCallback = new ListenableFutureCallback<SendResult<Integer, String>>() {
			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				System.out.println("success");
			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("failure");
			}
		};

		// 将listenableFutureCallback与异步发送消息对象绑定
		future.addCallback(listenableFutureCallback);
	}

	public void test(String topic, Integer partition, String key, String message) throws InterruptedException, ExecutionException, TimeoutException {
		kafkaTemplate.send(topic, partition, key, message).get(10, TimeUnit.SECONDS);
	}
}
