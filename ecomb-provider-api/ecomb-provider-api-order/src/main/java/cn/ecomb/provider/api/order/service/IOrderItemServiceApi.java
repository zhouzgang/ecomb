package cn.ecomb.provider.api.order.service;

import cn.ecomb.provider.api.order.dto.OrderItemDto;
import cn.ecomb.provider.api.order.entity.OrderItem;
import cn.ecomb.provider.api.order.param.ListOrderItemPageQuery;

import java.util.List;

/**
 * 订单明细项相关接口
 * 
 * @author brian.zhou
 * @date 2020/9/22
 */
public interface IOrderItemServiceApi {

	/**
	 * 添加订单明细项
	 * @param orderItems
	 */
	void addOrderItem(List<OrderItem> orderItems);

	/**
	 * 更新订单明细项信息
	 * @param orderItem
	 */
	void updateOrderItem(OrderItem orderItem);

	/**
	 * 删除订单明细项信息
	 * @param ids
	 */
	void remove(List<Integer> ids);

	/**
	 * 获取订单明细项信息
	 * @return
	 */
	OrderItem getOrderItemById(Integer id);

	/**
	 * 获取订单明细项信息
	 * @return
	 */
	List<OrderItem> listOrderItemByOrderId(String orderId);

}
