package cn.ecomb.park.spring.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  使用 FactoryBean 代理创建 Bean
 *  比如 mybatis 里 MapperFactoryBean 获取的是 mapperRegistry 里面的对象，mybatis 里面又有一个 MapperProxyFactory 代理
 * @author brian.zhou
 * @date 2021/4/21
 */
@Component
public class EcombProxyFactoryBean implements FactoryBean<HelloService>, InitializingBean, DisposableBean {

	private String interfaceName = "cn.ecomb.park.spring.bean.HelloService";
	private Object target = new HelloServiceImpl();
	private HelloService proxyObj;
	@Override
	public void destroy() throws Exception {
		System.out.println("destroy......");
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		proxyObj = (HelloService) Proxy.newProxyInstance(
				this.getClass().getClassLoader(),
				new Class[] { Class.forName(interfaceName) },
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("invoke method......" + method.getName());
						System.out.println("invoke method before......" + System.currentTimeMillis());
						Object result = method.invoke(target, args);
						System.out.println("invoke method after......" + System.currentTimeMillis());
						return result;            }
				});
		System.out.println("afterPropertiesSet......");
	}

	@Override
	public HelloService getObject() throws Exception {
		System.out.println("getObject......");
		return proxyObj;
	}

	@Override
	public Class<?> getObjectType() {
		return proxyObj == null ? Object.class : proxyObj.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
