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
@ApiModel("订单发货参数")
public class DeliveryOrderRequest {

	@ApiModelProperty("订单编号")
	@NotNull(message = "订单编号不能为空")
	private Long orderId;

	@ApiModelProperty("运费金额")
	@NotNull(message = "运费金额不能为空")
	private Float freightAmount;
	@ApiModelProperty("物流公司(配送方式)")
	@NotNull(message = "物流公司不能为空")
	private String deliveryCompany;
	@ApiModelProperty("物流单号")
	@NotNull(message = "物流单号不能为空")
	private String deliveryId;
	@ApiModelProperty("发货时间")
	private Date deliveryTime;
}
