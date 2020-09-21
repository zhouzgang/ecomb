package cn.ecomb.provider.api.order.service;

import cn.ecomb.provider.api.order.param.PayParam;

/**
 * 支付相关接口
 * 类似这种比较强的范围，要考虑后续拆成独立服务的情况
 *
 * @author brian.zhou
 * @date 2020/10/12
 */
public interface IPayServiceApi {

	/**
	 * 订单支付
	 * @param param 支付参数
	 */
	void pay(PayParam param);
}
