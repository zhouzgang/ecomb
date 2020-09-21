package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author brian.zhou
 * @date 2020/10/9
 */
@Data
@ApiModel("更新订单参数")
public class UpdateOrderRequest {

	@ApiModelProperty("订单编号")
	@NotNull(message = "订单编号不能为空")
	private Long orderId;

	@ApiModelProperty("订单总金额")
	private Float totalAmount;
}
