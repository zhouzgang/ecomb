package cn.ecomb.provider.api.order.param;

import lombok.Builder;
import lombok.Data;

/**
 * @author brian.zhou
 * @date 2020/10/12
 */
@Data
@Builder
public class PayParam {

	/** 订单编号 */
	private Long orderId;

	/** 支付方 */
	private Long userId;

	/** 支付金额 */
	private Float payAmount;

	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);
	}

}
