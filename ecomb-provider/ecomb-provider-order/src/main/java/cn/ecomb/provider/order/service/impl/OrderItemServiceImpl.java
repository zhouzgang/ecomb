package cn.ecomb.provider.order.service.impl;

import cn.ecomb.provider.api.order.entity.OrderItem;
import cn.ecomb.provider.api.order.service.IOrderItemServiceApi;
import cn.ecomb.provider.order.mapper.IOrderItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author brian.zhou
 * @date 2020/9/22
 */
@Service
@Slf4j
public class OrderItemServiceImpl implements IOrderItemServiceApi {

	@Autowired
	private IOrderItemMapper orderItemMapper;

	@Override
	public void addOrderItem(List<OrderItem> orderItems) {
		orderItemMapper.addOrderItem(orderItems);
	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		orderItemMapper.updateOrderItem(orderItem);
	}

	@Override
	public void remove(List<Integer> ids) {
		orderItemMapper.remove(ids);
	}

	@Override
	public OrderItem getOrderItemById(Integer id) {
		return orderItemMapper.getOrderItem(id);
	}

	@Override
	public List<OrderItem> listOrderItemByOrderId(String orderId) {
		return orderItemMapper.listOrderItemByOrderId(orderId);
	}
}
