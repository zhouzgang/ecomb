package cn.ecomb.provider.api.order.param;

import lombok.Data;

import java.util.Date;

/**
 * @author brian.zhou
 * @date 2020/10/12
 */
@Data
public class ReceiveOrderParam {

	/** 订单编号 */
	private Long orderId;

	/** 确认收货时间 */
	private Date receiveTime;
}
