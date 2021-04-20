package cn.ecomb.park.spi.spring;

import cn.ecomb.park.spi.IServiceProvider;

/**
 * 服务接口实现类，一般都是在服务提供方的 jar 包里实现，这里简化处理。
 *
 * @author brian.zhou
 * @date 2021/4/20
 */
public class IServiceProviderSpringImpl implements IServiceProvider {

	@Override
	public void sayHello() {
		System.out.println("hello spring spi, then do something more");
	}
}
