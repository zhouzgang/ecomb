package cn.ecomb.park.spring.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
@ToString
@Data
@Builder
public class OrderXXEvent {

	private String orderId;
	private int orderStatus;
}
