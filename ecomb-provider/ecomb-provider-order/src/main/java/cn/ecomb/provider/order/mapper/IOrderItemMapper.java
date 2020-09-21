package cn.ecomb.provider.order.mapper;

import cn.ecomb.provider.api.order.dto.OrderItemDto;
import cn.ecomb.provider.api.order.entity.OrderItem;
import cn.ecomb.provider.api.order.param.ListOrderItemPageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author brian.zhou
 * @date 2020/9/22
 */
@Mapper
public interface IOrderItemMapper {

	/**
	 * 添加订单明细项
	 * @param orderItems
	 */
	void addOrderItem(@Param("list") List<OrderItem> orderItems);

	/**
	 * 更新订单明细项信息
	 * @param orderItem
	 */
	void updateOrderItem(@Param("orderItem") OrderItem orderItem);

	/**
	 * 删除订单明细项信息
	 * @param ids
	 */
	void remove(@Param("list") List<Integer> ids);

	/**
	 * 获取订单明细项信息
	 * @return
	 */
	OrderItem getOrderItem(@Param("id") Integer id);

	/**
	 * 获取订单明细项信息
	 * @return
	 */
	List<OrderItem> listOrderItemByOrderId(@Param("orderId") String orderId);

}
