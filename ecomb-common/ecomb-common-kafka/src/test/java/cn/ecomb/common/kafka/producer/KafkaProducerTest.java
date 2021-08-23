package cn.ecomb.common.kafka.producer;

import cn.ecomb.common.kafka.BaseTest;
import cn.ecomb.common.kafka.config.KafkaConstant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author brian.zhou
 * @date 2021/4/16
 */
public class KafkaProducerTest extends BaseTest {

	@Autowired
	private KafkaProducer kafkaProducer;

	@Test
	public void test() throws InterruptedException, ExecutionException, TimeoutException {
		kafkaProducer.test(KafkaConstant.TOPIC, 0, "0", "hello");
	}
}