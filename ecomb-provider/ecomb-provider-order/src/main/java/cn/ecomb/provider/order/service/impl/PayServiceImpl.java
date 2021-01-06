package cn.ecomb.provider.order.service.impl;

import cn.ecomb.common.provider.api.exception.ServiceException;
import cn.ecomb.common.provider.api.utils.ValidationUtil;
import cn.ecomb.common.provider.sao.pay.PaySao;
import cn.ecomb.common.provider.sao.pay.request.PayRequest;
import cn.ecomb.common.provider.sao.pay.response.PayResponse;
import cn.ecomb.provider.api.order.entity.Order;
import cn.ecomb.provider.api.order.param.PayParam;
import cn.ecomb.provider.api.order.service.IOrderOperateServiceApi;
import cn.ecomb.provider.api.order.service.IOrderServiceApi;
import cn.ecomb.provider.api.order.service.IPayServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static cn.ecomb.provider.api.order.constant.OrderErrorCode.PAY_ERROR;
import static cn.ecomb.provider.api.order.entity.type.OrderOperateManEnum.USER;
import static cn.ecomb.provider.api.order.entity.type.OrderStatusEnum.WAIT_DELIVERY;

/**
 * @author brian.zhou
 * @date 2020/10/12
 */
@Service
@Slf4j
public class PayServiceImpl implements IPayServiceApi {

	@Autowired
	private PaySao paySao;

	@Autowired
	private IOrderServiceApi orderServiceApi;

	@Autowired
	private IOrderOperateServiceApi orderOperateServiceApi;

	@Override
	public void pay(PayParam param) {
		PayResponse payResponse = paySao.pay(PayRequest.builder()
				.userId(param.getUserId())
				.payAmount(param.getPayAmount()).build());
		Optional.ofNullable(payResponse)
				.orElseThrow(new ServiceException(PAY_ERROR));
		ValidationUtil.ifFalseThrow(payResponse.getPayStatus(), PAY_ERROR);

		// 这里的状态更新操作需要一个乐观锁吗
		orderServiceApi.updateStatus(Order.builder()
				.orderId(param.getOrderId())
				.status(WAIT_DELIVERY.getStatus()).build());
		orderOperateServiceApi.addOrderOperate(param.getOrderId(), WAIT_DELIVERY, USER);
		log.info("支付完成");
	}
}
