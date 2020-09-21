package cn.ecomb.common.provider.sao.pay.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author brian.zhou
 * @date 2020/10/12
 */
@Data
@Builder
public class PayRequest {

	/** 支付方 */
	private Long userId;

	/** 支付金额 */
	private Float payAmount;
}
