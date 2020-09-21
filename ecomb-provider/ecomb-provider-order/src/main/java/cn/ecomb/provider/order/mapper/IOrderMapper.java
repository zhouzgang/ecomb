package cn.ecomb.provider.order.mapper;

import cn.ecomb.provider.api.order.dto.OrderDto;
import cn.ecomb.provider.api.order.entity.Order;
import cn.ecomb.provider.api.order.param.ListOrderPageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author brian.zhou
 * @date 2020/9/22
 */
@Mapper
public interface IOrderMapper {

	/**
	 * 添加订单
	 * @param order
	 */
	void addOrder(@Param("order") Order order);

	/**
	 * 更新订单信息
	 * @param order
	 */
	void updateOrder(@Param("order") Order order);

	/**
	 * 更新订单状态
	 * @param order
	 */
	void updateStatus(@Param("order") Order order);

	/**
	 * 删除订单信息
	 * @param orderIds
	 */
	void remove(@Param("ids") List<Long> orderIds);

	/**
	 * 获取订单信息
	 * @return
	 */
	Order getOrder(@Param("orderId") Long orderId);

	/**
	 * 获取订单信息
	 * @return
	 */
	List<Order> listOrderByOrderIds(@Param("list") List<Long> orderIds);

	/**
	 * 分页查询订单信息
	 * @param pageQuery
	 * @return
	 */
	List<OrderDto> listOrder(@Param("pageQuery") ListOrderPageQuery pageQuery);

}
