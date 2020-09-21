package cn.ecomb.web.app.service.impl;

import cn.ecomb.common.provider.api.exception.ServiceException;
import cn.ecomb.common.utils.idgenerator.IdWorker;
import cn.ecomb.common.utils.util.BeanHandleUtil;
import cn.ecomb.provider.api.order.dto.OrderDto;
import cn.ecomb.provider.api.order.entity.Order;
import cn.ecomb.provider.api.order.entity.OrderItem;
import cn.ecomb.provider.api.order.entity.OrderOperate;
import cn.ecomb.provider.api.order.entity.type.OrderOperateManEnum;
import cn.ecomb.provider.api.order.entity.type.OrderStatusEnum;
import cn.ecomb.provider.api.order.param.*;
import cn.ecomb.provider.api.order.service.IOrderItemServiceApi;
import cn.ecomb.provider.api.order.service.IOrderOperateServiceApi;
import cn.ecomb.provider.api.order.service.IOrderServiceApi;
import cn.ecomb.provider.api.order.service.IPayServiceApi;
import cn.ecomb.provider.api.product.entity.Product;
import cn.ecomb.provider.api.product.param.ProductStockParam;
import cn.ecomb.provider.api.product.service.IProductServiceApi;
import cn.ecomb.web.app.controller.request.*;
import cn.ecomb.web.app.controller.response.GetOrderResponse;
import cn.ecomb.web.app.controller.response.ListOrderResponse;
import cn.ecomb.web.app.controller.response.QueryOrderResponse;
import cn.ecomb.web.app.service.IWebOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.ecomb.common.provider.api.exception.CommonErrorCode.NO_RESOURCES;
import static cn.ecomb.provider.api.order.constant.OrderErrorCode.PRODUCT_NOT_EXIST;

/**
 * @author brian.zhou
 * @date 2020/9/18
 */
@Service
@Slf4j
public class IWebOrderServiceImpl implements IWebOrderService {

	@Autowired
	private IOrderServiceApi orderServiceApi;

	@Autowired
	private IPayServiceApi payServiceApi;

	@Autowired
	private IOrderItemServiceApi orderItemServiceApi;

	@Autowired
	private IOrderOperateServiceApi orderOperateServiceApi;

	@Autowired
	private IProductServiceApi productServiceApi;

	@Override
	public void createOrder(CreateOrderRequest request) {
//todo 商品库存加减，加分布式锁或其他方案，防止超卖，（放到 step-1-2 去做）
		checkStock(request.getOrderItems());
		Long orderId = IdWorker.nextId();
		Order order = Order.builder()
				.orderId(orderId)
				.orderType(request.getSourceType())
				.userId(1001l)
				.userName("zhouzg") //todo 封装登陆功能，和本地线程全局获取登陆信息
				.totalAmount((float)request.getOrderItems().stream()
						.mapToDouble(orderItem -> orderItem.getProductSellingPrice() * orderItem.getProductQuantity())
						.sum())
				.status(OrderStatusEnum.WAIT_PAY.getStatus())
				.build();
		orderServiceApi.addOrder(order);

		productServiceApi.reduceStock(request.getOrderItems().stream()
				.map(orderItem -> {
					ProductStockParam stockParam = new ProductStockParam();
					stockParam.setProductId(orderItem.getProductId());
					stockParam.setStock(orderItem.getProductQuantity());
					return stockParam;
				}).collect(Collectors.toList()));

		orderItemServiceApi.addOrderItem(request.getOrderItems().stream()
				.map(orderItem -> {
					OrderItem item = BeanHandleUtil.copyProperties(orderItem, OrderItem.class);
					item.setOrderId(orderId);
					return item;
				}).collect(Collectors.toList()));
		orderOperateServiceApi.addOrderOperate(OrderOperate.builder()
				.orderId(orderId)
				.orderStatus(OrderStatusEnum.WAIT_PAY.getStatus())
				.operateMan(OrderOperateManEnum.USER.getOperateMan())
				.build());
//商品库存，释放分布式锁
	}

	/**
	 * 检查订单商品库存
	 * @param orderItems   订单商品列表
	 */
	private void checkStock(List<CreateOrderRequest.OrderItem> orderItems) {

		Map<String, Product> productMap = productServiceApi.mapProduct(orderItems.stream()
				.map(CreateOrderRequest.OrderItem::getProductId)
				.collect(Collectors.toList()));

		orderItems.forEach(orderItem -> {
			Product product = Optional.ofNullable(productMap.get(orderItem.getProductId()))
					.orElseThrow(new ServiceException(NO_RESOURCES));
			if (orderItem.getProductQuantity() > product.getStock()) {
				throw new ServiceException(PRODUCT_NOT_EXIST.buildErrorMsg(orderItem.getProductId()));
			}
		});
	}

	@Override
	public void payOrder(PayOrderRequest request) {
		Order order = orderServiceApi.getOrder(request.getOrderId());
		payServiceApi.pay(PayParam.builder()
				.orderId(request.getOrderId())
				.payAmount(order.getPayAmount())
				.userId(1001L).build());
	}

	@Override
	public void updateOrder(UpdateOrderRequest request) {
		orderServiceApi.updateOrder(Order.builder()
				.orderId(request.getOrderId())
				.totalAmount(request.getTotalAmount()).build());
	}

	@Override
	public void deliveryOrder(DeliveryOrderRequest request) {
		orderServiceApi.deliveryOrder(BeanHandleUtil.copyProperties(request, DeliveryOrderParam.class));
	}

	@Override
	public void receiveOrder(ReceiveOrderRequest request) {
		orderServiceApi.receiveOrder(BeanHandleUtil.copyProperties(request, ReceiveOrderParam.class));
	}

	@Override
	public void commentOrder(CommentOrderRequest request) {
		orderServiceApi.commentOrder(BeanHandleUtil.copyProperties(request, CommentOrderParam.class));
	}

	@Override
	public void deleteOrder(DeleteOrderRequest request) {
		orderServiceApi.remove(request.getOrderId());
	}

	@Override
	public GetOrderResponse getOrder(GetOrderRequest request) {
		Order order = orderServiceApi.getOrder(request.getOrderId());
		return BeanHandleUtil.copyProperties(order, GetOrderResponse.class);
	}

	@Override
	public ListOrderResponse listOrder(ListOrderRequest request) {
		List<Order> orders = orderServiceApi.listOrder(request.getOrderIds());
		return ListOrderResponse.builder()
				.list(BeanHandleUtil.copyPropertiesList(orders, ListOrderResponse.Order.class)).build();
	}

	@Override
	public QueryOrderResponse queryOrder(QueryOrderRequest request) {
		List<OrderDto> orderDtos = orderServiceApi.listOrder(BeanHandleUtil.copyProperties(request, ListOrderPageQuery.class));
		return QueryOrderResponse.builder()
				.list(BeanHandleUtil.copyPropertiesList(orderDtos, ListOrderResponse.Order.class)).build();
	}
}
