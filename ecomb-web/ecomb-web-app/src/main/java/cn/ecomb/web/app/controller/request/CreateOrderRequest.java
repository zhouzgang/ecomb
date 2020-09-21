package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author brian.zhou
 * @date 2020/10/9
 */
@Data
@ApiModel("创建订单信息参数")
public class CreateOrderRequest {

	@ApiModelProperty("用户Id")
	private Integer userId;

	@ApiModelProperty("订单来源：0->PC订单；1->app订单")
	@NotNull(message = "订单来源不能为空")
	private Integer sourceType;

	@ApiModelProperty("订单类型：0->正常订单；1->秒杀订单")
	private Integer orderType;

	@ApiModelProperty("订单明细")
	@NotNull(message = "订单明细不能为空")
	private List<OrderItem> orderItems;

	@Data
	@ApiModel("订单明细")
	public class OrderItem {

		@ApiModelProperty("商品Id")
		private String productId;
		@ApiModelProperty("商品价格")
		private String productPrice;
		@ApiModelProperty("商品名字")
		private String productName;
		@ApiModelProperty("销售价格")
		private Float productSellingPrice;
		@ApiModelProperty("购买数量")
		private Integer productQuantity;
	}

}
