package cn.ecomb.park.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 一般在服务使用项目类
 *
 * @author brian.zhou
 * @date 2021/4/20
 */
public class TestUseSPI {

	public static void main(String[] args) {
		ServiceLoader<IServiceProvider> serviceLoader = ServiceLoader.load(IServiceProvider.class);
		Iterator iterator = serviceLoader.iterator();
		while (iterator.hasNext()) {
			IServiceProvider item = (IServiceProvider) iterator.next();
			item.sayHello();
		}
	}
}
