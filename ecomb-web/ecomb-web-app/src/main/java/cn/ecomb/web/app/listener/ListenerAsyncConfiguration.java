package cn.ecomb.web.app.listener;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 开启异步组件
 * @author brian.zhou
 * @date 2020/12/15
 */
@Configuration
@EnableAsync
public class ListenerAsyncConfiguration implements AsyncConfigurer
{
	/**
	 * 获取异步线程池执行对象
	 * @return
	 */
	@Override
	public Executor getAsyncExecutor() {
		//使用Spring内置线程池任务对象
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		//设置线程池参数
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}
}

