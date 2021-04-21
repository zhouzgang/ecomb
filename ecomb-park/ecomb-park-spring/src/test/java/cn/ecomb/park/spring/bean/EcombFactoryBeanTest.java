package cn.ecomb.park.spring.bean;

import cn.ecomb.park.spring.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
public class EcombFactoryBeanTest extends BaseTest {

	@Autowired
	private EcombProxyFactoryBean ecombFactoryBean;

	@Test
	public void factoryBean() throws Exception {
		HelloService helloService = ecombFactoryBean.getObject();
		helloService.sayHello();
	}

}