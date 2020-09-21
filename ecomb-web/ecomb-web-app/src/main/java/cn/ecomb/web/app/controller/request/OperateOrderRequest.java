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
@ApiModel("订单操作参数")
public class OperateOrderRequest {

	@ApiModelProperty("订单来源：0->PC订单；1->app订单")
	@NotNull(message = "订单来源不能为空")
	private Integer sourceType;
}
