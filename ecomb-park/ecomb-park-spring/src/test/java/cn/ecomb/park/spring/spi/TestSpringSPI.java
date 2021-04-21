package cn.ecomb.park.spring.spi;

import cn.ecomb.park.spring.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
public class TestSpringSPI extends BaseTest {

	@Autowired
	private SpringSPI springSPI;

	@Test
	public void invokeSpiMethod() {
		springSPI.invokeSpiMethod();
	}
}