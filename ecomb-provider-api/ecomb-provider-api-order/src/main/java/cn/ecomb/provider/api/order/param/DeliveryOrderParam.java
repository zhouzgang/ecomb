package cn.ecomb.provider.api.order.param;

import lombok.Data;

import java.util.Date;

/**
 * @author brian.zhou
 * @date 2020/10/12
 */
@Data
public class DeliveryOrderParam {

	/** 订单编号 */
	private Long orderId;

	/** 运费金额 */
	private Float freightAmount;
	/** 物流公司(配送方式) */
	private String deliveryCompany;
	/** 物流单号 */
	private String deliveryId;
	/** 发货时间 */
	private Date deliveryTime;
}
