package cn.ecomb.web.app.controller;

import cn.ecomb.web.app.controller.request.*;
import cn.ecomb.web.app.controller.response.GetOrderResponse;
import cn.ecomb.web.app.controller.response.ListOrderResponse;
import cn.ecomb.web.app.controller.response.QueryOrderResponse;
import cn.ecomb.web.app.service.IWebOrderService;
import cn.ecomb.web.app.service.impl.RabbitMQSend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author brian.zhou
 * @date 2020/10/9
 */
@Api("订单相关接口")
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IWebOrderService webOrderService;

	@Autowired
	private RabbitMQSend rabbitMQSend;

	@PostMapping("/create")
	@ApiOperation(value = "创建订单", notes = "前端下单创建一个「待支付」的订单")
	public void createOrder(@RequestBody CreateOrderRequest request) {
//		rabbitMQSend.send();
		webOrderService.createOrder(request);
	}

	@PostMapping("/pay")
	@ApiOperation(value = "订单支付", notes = "为「待支付」订单，提供支付功能")
	public void payOrder(@RequestBody PayOrderRequest request) {
		webOrderService.payOrder(request);
	}

	@PostMapping("/update")
	@ApiOperation(value = "更新订单信息", notes = "更新订单基础信息")
	public void updateOrder(@RequestBody UpdateOrderRequest request) {
		webOrderService.updateOrder(request);
	}

	@PostMapping("/delivery")
	@ApiOperation(value = "订单发货", notes = "订单支付后，更新发货信息")
	public void deliveryOrder(@RequestBody DeliveryOrderRequest request) {
		webOrderService.deliveryOrder(request);
	}

	@PostMapping("/receive")
	@ApiOperation(value = "订单收货", notes = "订单发货后，更新收货信息")
	public void receiveOrder(@RequestBody ReceiveOrderRequest request) {
		webOrderService.receiveOrder(request);
	}

	@PostMapping("/comment")
	@ApiOperation(value = "订单评论", notes = "订单收货后，更新评论信息")
	public void commentOrder(@RequestBody CommentOrderRequest request) {
		webOrderService.commentOrder(request);
	}

	@PostMapping("/delete")
	@ApiOperation(value = "删除订单", notes = "标记删除订单记录")
	public void deleteOrder(@RequestBody DeleteOrderRequest request) {
		webOrderService.deleteOrder(request);
	}

	@GetMapping
	@ApiOperation(value = "获取订单", notes = "根据订单ID，获取订单信息")
	public GetOrderResponse getOrder(GetOrderRequest request) {
		return webOrderService.getOrder(request);
	}

	@GetMapping("/list")
	@ApiOperation(value = "批量获取订单", notes = "根据多个订单ID，获取订单信息")
	public ListOrderResponse listOrder(ListOrderRequest request) {
		return webOrderService.listOrder(request);
	}

	@GetMapping("/query")
	@ApiOperation(value = "查询订单", notes = "根据订单关键词，获取订单信息")
	public QueryOrderResponse queryOrder(QueryOrderRequest request) {
		return webOrderService.queryOrder(request);
	}

}
