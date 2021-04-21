package cn.ecomb.park.spring.event;

import cn.ecomb.park.spring.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
public class TriggerOrderXXEventTest extends BaseTest {

	@Autowired
	private TriggerOrderXXEvent triggerOrderXXEvent;

	@Test
	public void trigger() {
		triggerOrderXXEvent.trigger();
	}
}