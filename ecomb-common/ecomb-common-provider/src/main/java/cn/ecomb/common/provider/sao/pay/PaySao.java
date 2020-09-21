package cn.ecomb.common.provider.sao.pay;

import cn.ecomb.common.provider.sao.pay.request.PayRequest;
import cn.ecomb.common.provider.sao.pay.response.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 调用外部支付服务接口
 * 模拟调用第三方服务，实际使用时用具体第三方名称，如 AliPaySao「ali pay service access object」
 *
 * @author brian.zhou
 * @date 2020/10/12
 */
@Service
@Slf4j
public class PaySao {

	public PayResponse pay(PayRequest request) {
		log.info("支付……");
		return new PayResponse();
	}
}
