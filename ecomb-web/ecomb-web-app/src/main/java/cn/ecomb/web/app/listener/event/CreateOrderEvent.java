package cn.ecomb.web.app.listener.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author brian.zhou
 * @date 2020/12/14
 */
@Getter
public class CreateOrderEvent extends ApplicationEvent {

	private String orderId;

	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public CreateOrderEvent(Object source) {
		super(source);
	}
}
