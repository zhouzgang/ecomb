package cn.ecomb.web.app.service;

import cn.ecomb.web.app.controller.request.*;
import cn.ecomb.web.app.controller.response.*;

/**
 * 订单相关接口
 *
 * @author brian.zhou
 * @date 2020/9/18
 */
public interface IWebOrderService {

	/**
	 * 创建订单
	 * @param request   订单信息
	 */
	void createOrder(CreateOrderRequest request);

	/**
	 * 订单支付
	 * @param request   支付参数
	 */
	void payOrder(PayOrderRequest request);

	/**
	 * 更新订单
	 * @param request 订单参数
	 */
	void updateOrder(UpdateOrderRequest request);

	/**
	 * 订单发货
	 * @param request 发货参数
	 */
	void deliveryOrder(DeliveryOrderRequest request);

	/**
	 * 订单到货
	 * @param request 到货参数
	 */
	void receiveOrder(ReceiveOrderRequest request);

	/**
	 * 提交订单评论信息
	 * @param request 评论信息
	 */
	void commentOrder(CommentOrderRequest request);

	/**
	 * 删除订单
	 * @param request 删除订单参数
	 */
	void deleteOrder(DeleteOrderRequest request);

	/**
	 * 获取订单
	 * @param request 订单参数
	 * @return 订单信息
	 */
	GetOrderResponse getOrder(GetOrderRequest request);

	/**
	 * 批量获取订单
	 * @param request 批量获取订单参数
	 * @return 订单信息列表
	 */
	ListOrderResponse listOrder(ListOrderRequest request);

	/**
	 * 查询订单信息
	 * @param request 查询参数
	 * @return 查询结果
	 */
	QueryOrderResponse queryOrder(QueryOrderRequest request);

}
