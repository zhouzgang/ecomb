package cn.ecomb.park.spring.spi;

import cn.ecomb.park.spi.IServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author brian.zhou
 * @date 2021/4/21
 */
@Component
public class SpringSPI {

	@Autowired
	IServiceProvider serviceProvider;

	public void invokeSpiMethod() {
		serviceProvider.sayHello();
	}
}
