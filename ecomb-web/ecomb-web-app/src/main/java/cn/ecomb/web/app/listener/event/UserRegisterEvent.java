package cn.ecomb.web.app.listener.event;

import cn.ecomb.provider.api.user.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * @author brian.zhou
 * @date 2020/12/15
 */
public class UserRegisterEvent extends ApplicationEvent {

	private User user;

	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public UserRegisterEvent(Object source) {
		super(source);
	}
}
