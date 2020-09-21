package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author brian.zhou
 * @date 2020/10/9
 */
@Data
@ApiModel("订单到货参数")
public class ReceiveOrderRequest {

	@ApiModelProperty("订单编号")
	@NotNull(message = "订单编号不能为空")
	private Long orderId;

	@ApiModelProperty("确认收货时间")
	private Date receiveTime;
}
