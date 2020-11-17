package cn.ecomb.park.pool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brian.zhou
 * @date 2020/11/13
 */
@RestController
@RequestMapping
@Slf4j
public class HelloController {

	@GetMapping("hello")
	public String testString() {
		return "hello";
	}
}
