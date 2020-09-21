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
@ApiModel("订单评论参数")
public class CommentOrderRequest {

	@ApiModelProperty("订单编号")
	@NotNull(message = "订单编号不能为空")
	private Long orderId;

	@ApiModelProperty("评价")
	@NotNull(message = "评价不能为空")
	private String comment;
	@ApiModelProperty("评价时间")
	private Date commentTime;
}
