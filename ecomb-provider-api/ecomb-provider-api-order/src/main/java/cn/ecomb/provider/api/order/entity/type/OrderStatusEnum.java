package cn.ecomb.provider.api.order.entity.type;

/**
 * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
 * @author brian.zhou
 * @date 2020/10/10
 */
public enum OrderStatusEnum {

	WAIT_PAY(0),
	WAIT_DELIVERY(1),
	DELIVERED(2),
	FINISHED(3),
	CLOSE(4),
	INVALID(5);

	private Integer status;

	OrderStatusEnum(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
}
