package cn.ecomb.provider.api.order.param;

import lombok.Data;

import java.util.Date;

/**
 * @author brian.zhou
 * @date 2020/10/12
 */
@Data
public class CommentOrderParam {

	/** 订单编号 */
	private Long orderId;

	/** 评价 */
	private String comment;
	/** 评价时间 */
	private Date commentTime;
}
