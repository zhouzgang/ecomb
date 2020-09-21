package cn.ecomb.provider.api.order.service;

import cn.ecomb.provider.api.order.dto.OrderDto;
import cn.ecomb.provider.api.order.entity.Order;
import cn.ecomb.provider.api.order.param.CommentOrderParam;
import cn.ecomb.provider.api.order.param.DeliveryOrderParam;
import cn.ecomb.provider.api.order.param.ListOrderPageQuery;
import cn.ecomb.provider.api.order.param.ReceiveOrderParam;
import com.sun.javafx.tools.packager.CommonParams;

import java.util.List;

/**
 * 订单相关接口
 * 
 * @author brian.zhou
 * @date 2020/9/22
 */
public interface IOrderServiceApi {

	/**
	 * 添加订单
	 * @param order
	 */
	void addOrder(Order order);

	/**
	 * 更新订单信息
	 * @param order
	 */
	void updateOrder(Order order);

	/**
	 * 更新订单状态
	 * @param order
	 */
	void updateStatus(Order order);

	/**
	 * 删除订单信息
	 * @param orderId
	 */
	void remove(Long orderId);

	/**
	 * 删除订单信息
	 * @param orderIds
	 */
	void remove(List<Long> orderIds);

	/**
	 * 获取订单信息
	 * @return
	 */
	Order getOrder(Long orderId);

	/**
	 * 获取订单信息
	 * @return
	 */
	List<Order> listOrder(List<Long> orderIds);

	/**
	 * 分页查询订单信息
	 * @param pageQuery
	 * @return
	 */
	List<OrderDto> listOrder(ListOrderPageQuery pageQuery);

	/**
	 * 订单发货
	 * @param param 发货参数
	 */
	void deliveryOrder(DeliveryOrderParam param);

	/**
	 * 订单到货
	 * @param param 到货参数
	 */
	void receiveOrder(ReceiveOrderParam param);

	/**
	 * 提交订单评论信息
	 * @param param 评论信息
	 */
	void commentOrder(CommentOrderParam param);
}
