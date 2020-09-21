package cn.ecomb.provider.order.service.impl;

import cn.ecomb.provider.api.order.entity.OrderOperate;
import cn.ecomb.provider.api.order.entity.type.OrderOperateManEnum;
import cn.ecomb.provider.api.order.entity.type.OrderStatusEnum;
import cn.ecomb.provider.api.order.service.IOrderOperateServiceApi;
import cn.ecomb.provider.order.mapper.IOrderOperateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author brian.zhou
 * @date 2020/9/22
 */
@Service
@Slf4j
public class OrderOperateServiceImpl implements IOrderOperateServiceApi {

	@Autowired
	private IOrderOperateMapper orderOperateMapper;

	@Override
	public void addOrderOperate(OrderOperate orderOperate) {
		orderOperateMapper.addOrderOperate(orderOperate);
	}

	@Override
	public void addOrderOperate(Long orderId, OrderStatusEnum orderStatus, OrderOperateManEnum operateMan) {
		addOrderOperate(OrderOperate.builder()
				.orderId(orderId)
				.operateMan(Optional.ofNullable(operateMan.getOperateMan())
						.orElse(OrderOperateManEnum.ADMIN.getOperateMan()))
				.orderStatus(orderStatus.getStatus())
				.createTime(new Date()).build());
	}

	@Override
	public void updateOrderOperate(OrderOperate orderOperate) {
		orderOperateMapper.updateOrderOperate(orderOperate);
	}

	public List<OrderOperate> listOrderOperateByOrderId(String orderId) {
		return orderOperateMapper.listOrderOperateByOrderId(orderId);
	}

}
