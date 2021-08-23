package cn.ecomb.common.kafka.controller;

import cn.ecomb.common.kafka.config.KafkaConstant;
import cn.ecomb.common.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author brian.zhou
 * @date 2021/8/23
 */
@RequestMapping("kafka")
@RestController
public class TestController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) throws InterruptedException, ExecutionException, TimeoutException {
        kafkaProducer.test(KafkaConstant.TOPIC, 0, "0", "hello");
    }
}
