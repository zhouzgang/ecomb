package cn.ecomb.park.pool.controller;

import cn.ecomb.common.datas.redis.component.RedisHandle;
import cn.ecomb.park.pool.constant.PoolConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

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
