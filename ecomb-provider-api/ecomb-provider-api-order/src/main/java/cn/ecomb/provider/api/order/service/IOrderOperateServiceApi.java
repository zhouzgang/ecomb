package cn.ecomb.provider.api.order.service;

import cn.ecomb.provider.api.order.dto.OrderOperateDto;
import cn.ecomb.provider.api.order.entity.OrderOperate;
import cn.ecomb.provider.api.order.entity.type.OrderOperateManEnum;
import cn.ecomb.provider.api.order.entity.type.OrderStatusEnum;
import cn.ecomb.provider.api.order.param.ListOrderOperatePageQuery;

import java.util.List;

/**
 * 订单操作相关接口
 * 
 * @author brian.zhou
 * @date 2020/9/22
 */
public interface IOrderOperateServiceApi {

	/**
	 * 添加订单操作
	 * @param orderOperate
	 */
	void addOrderOperate(OrderOperate orderOperate);

	/**
	 * 添加订单操作
	 * @param orderId   订单编号
	 * @param orderStatus   需要修改的订单状态
	 * @param operateMan    操作者
	 */
	void addOrderOperate(Long orderId, OrderStatusEnum orderStatus, OrderOperateManEnum operateMan);

	/**
	 * 更新订单操作信息
	 * @param orderOperate
	 */
	void updateOrderOperate(OrderOperate orderOperate);

	/**
	 * 获取订单操作信息
	 * @return
	 */
	List<OrderOperate> listOrderOperateByOrderId(String orderId);

}
