package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("获取订单接口")
public class GetOrderRequest {

    @ApiModelProperty("订单编号")
    @NotNull(message = "订单编号不能为空")
    private Long orderId;
}
