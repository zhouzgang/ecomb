package cn.ecomb.common.rabbitmq.controller;

import cn.ecomb.common.rabbitmq.producer.HelloSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brian.zhou
 * @date 2021/8/9
 */
@RestController
@RequestMapping("/rmq")
public class SendMsgController {

    @Autowired
    private HelloSender helloSender;

    @GetMapping("/send/v1")
    public void sendV1(@RequestParam(name = "msg") String msg) {
        helloSender.sendMessage("sendV1: " + msg);
    }

    @GetMapping("/send/v2")
    public void sendV2(@RequestParam(name = "msg") String msg) {
        for (int i = 0; i <10; i++) {
            helloSender.sendMessage("sendV1: " + msg + ", " + i);
        }
    }
}
