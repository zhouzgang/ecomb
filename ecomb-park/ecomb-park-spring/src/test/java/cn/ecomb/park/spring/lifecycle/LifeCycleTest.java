package cn.ecomb.park.spring.lifecycle;

import cn.ecomb.park.spring.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
public class LifeCycleTest extends BaseTest {

	@Autowired
	private LifeCycle lifeCycle;

	@Test
	public void call() {
		lifeCycle.call();
	}
}