package cn.ecomb.provider.order.mapper;

import cn.ecomb.provider.api.order.entity.OrderOperate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author brian.zhou
 * @date 2020/9/22
 */
@Mapper
public interface IOrderOperateMapper {

	/**
	 * 添加订单操作
	 * @param orderOperate
	 */
	void addOrderOperate(@Param("orderOperate") OrderOperate orderOperate);

	/**
	 * 更新订单操作信息
	 * @param orderOperate
	 */
	void updateOrderOperate(@Param("orderOperate") OrderOperate orderOperate);

	/**
	 * 获取订单操作信息
	 * @return
	 */
	List<OrderOperate> listOrderOperateByOrderId(@Param("orderId") String orderId);
}
