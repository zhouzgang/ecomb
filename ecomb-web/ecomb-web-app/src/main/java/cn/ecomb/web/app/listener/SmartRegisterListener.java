package cn.ecomb.web.app.listener;

import cn.ecomb.provider.api.user.entity.User;
import cn.ecomb.web.app.listener.event.UserRegisterEvent;
import cn.ecomb.web.app.service.IWebUserService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户注册后，邮件通知
 * 有序监听
 * @author brian.zhou
 * @date 2020/12/15
 */
@Component
public class SmartRegisterListener implements SmartApplicationListener {
	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
		//只有UserRegisterEvent监听类型才会执行下面逻辑
		return aClass == UserRegisterEvent.class;
	}

	@Override
	public boolean supportsSourceType(Class<?> aClass) {
		//只有在UserService内发布的UserRegisterEvent事件时才会执行下面逻辑
		return aClass == IWebUserService.class;
	}

	/**
	 * 开启异步组件，使用异步监听
	 * @param applicationEvent
	 */
	@Async
	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		//转换事件类型
		UserRegisterEvent userRegisterEvent = (UserRegisterEvent) applicationEvent;
		//获取注册用户对象信息
		User user = (User) userRegisterEvent.getSource();
		//.../完成注册业务逻辑

		System.out.println("SmartRegisterListener" + user.getNickName());

	}

	/**
	 * return 的数值越小证明优先级越高，执行顺序越靠前。
	 * @return
	 */
	@Override
	public int getOrder() {
		return 10;
	}
}
