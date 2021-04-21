package cn.ecomb.park.spring.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
@Component
public class LifeCycle implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, ApplicationContextAware,
		InitializingBean, DisposableBean, BeanPostProcessor, BeanFactoryPostProcessor, EnvironmentPostProcessor {

	public LifeCycle() {
		System.out.println("lifeCycle bean construct");
	}

	@PostConstruct
	public void lifeCycleConstruct() {
		System.out.println("@PostConstruct");
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessBeforeInitialization: " + beanName);
		return bean;
	}

	public void init() {
		System.out.println("lifeCycle bean init");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("lifeCycle bean setBeanName: " + name);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("set classLoader");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("lifeCycle bean setBeanFactory: " + beanFactory.toString());
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("postProcessBeanFactory: " + beanFactory.toString());
	}

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		System.out.println("postProcessEnvironment :" + environment.toString());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("lifeCycle bean setApplicationContext: " + applicationContext.toString());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("lifeCycle bean init afterPropertiesSet");
	}

	/**
	 * 使用这两个方法，可以拦截处理自定义注解，实现特殊功能，其实名字已经写的很清楚了，对 Bean 进行处理
	 *
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessAfterInitialization: " + beanName);
		return bean;
	}

	public void call() {
		System.out.println("call");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("preDestroy");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("lifeCycle bean destroy");
	}
}
