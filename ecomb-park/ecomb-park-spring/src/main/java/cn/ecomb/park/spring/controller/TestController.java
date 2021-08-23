package cn.ecomb.park.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brian.zhou
 * @date 2021/8/20
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${test.v1.ppp}")
    private String filed;

    @GetMapping
    public String test() {
        System.out.println(filed);
        return filed;
    }
}
