package cn.ecomb.provider.order.service.impl;

import cn.ecomb.provider.api.order.dto.OrderDto;
import cn.ecomb.provider.api.order.entity.Order;
import cn.ecomb.provider.api.order.param.CommentOrderParam;
import cn.ecomb.provider.api.order.param.DeliveryOrderParam;
import cn.ecomb.provider.api.order.param.ListOrderPageQuery;
import cn.ecomb.provider.api.order.param.ReceiveOrderParam;
import cn.ecomb.provider.api.order.service.IOrderOperateServiceApi;
import cn.ecomb.provider.api.order.service.IOrderServiceApi;
import cn.ecomb.provider.order.mapper.IOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static cn.ecomb.provider.api.order.entity.type.OrderOperateManEnum.USER;
import static cn.ecomb.provider.api.order.entity.type.OrderStatusEnum.DELIVERED;
import static cn.ecomb.provider.api.order.entity.type.OrderStatusEnum.FINISHED;

/**
 * @author brian.zhou
 * @date 2020/9/22
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderServiceApi {

	@Autowired
	private IOrderMapper orderMapper;

	@Autowired
	private IOrderOperateServiceApi orderOperateServiceApi;

	@Override
	public void addOrder(Order order) {
		orderMapper.addOrder(order);
	}

	@Override
	public void updateOrder(Order order) {
		orderMapper.updateOrder(order);
	}

	@Override
	public void updateStatus(Order order) {
		orderMapper.updateStatus(order);
	}

	@Override
	public void remove(Long orderId) {
		remove(Arrays.asList(orderId));
	}

	@Override
	public void remove(List<Long> orderIds) {
		orderMapper.remove(orderIds);
	}

	@Override
	public Order getOrder(Long orderId) {
		return orderMapper.getOrder(orderId);
	}

	@Override
	public List<Order> listOrder(List<Long> orderIds) {
		return orderMapper.listOrderByOrderIds(orderIds);
	}

	@Override
	public List<OrderDto> listOrder(ListOrderPageQuery pageQuery) {
		return orderMapper.listOrder(pageQuery);
	}

	@Override
	public void deliveryOrder(DeliveryOrderParam param) {
		Order order = getOrder(param.getOrderId());
		order.setDeliveryId(param.getDeliveryId());
		order.setDeliveryCompany(param.getDeliveryCompany());
		order.setDeliveryTime(Optional.ofNullable(param.getDeliveryTime()).orElse(new Date()));
		order.setFreightAmount(param.getFreightAmount());
		order.setConfirmStatus(Order.ConfirmStatus.UN_CONFIRM.getValue());
		order.setStatus(DELIVERED.getStatus());
		updateOrder(order);
		orderOperateServiceApi.addOrderOperate(param.getOrderId(), DELIVERED, USER);
	}

	@Override
	public void receiveOrder(ReceiveOrderParam param) {
		Order order = getOrder(param.getOrderId());
		order.setDeliveryTime(Optional.ofNullable(param.getReceiveTime()).orElse(new Date()));
		order.setConfirmStatus(Order.ConfirmStatus.CONFIRM.getValue());
		order.setStatus(FINISHED.getStatus());
		updateOrder(order);
		orderOperateServiceApi.addOrderOperate(param.getOrderId(), FINISHED, USER);
	}

	@Override
	public void commentOrder(CommentOrderParam param) {
		Order order = getOrder(param.getOrderId());
		order.setComment(param.getComment());
		order.setDeliveryTime(Optional.ofNullable(param.getCommentTime()).orElse(new Date()));
		updateOrder(order);
	}
}
