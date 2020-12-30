package cn.ecomb.provider.api.order.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

/**
 * 订单操作记录实体
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
@Data
@Builder
public class OrderOperate {

	private Integer id;
	/** 订单id */
	private Long orderId;
	/** 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单 */
	private Integer orderStatus;
	/** 操作人：用户；系统；后台管理员 */
	private String operateMan;
	/** 操作时间 */
	private Date createTime;
	/** 备注 */
	private String note;

	@Tolerate
	public OrderOperate() {
	}
}
