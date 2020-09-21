package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("获取订单参数")
public class ListOrderRequest {

    @ApiModelProperty("订单编号")
    @NotNull(message = "订单编号不能为空")
    private List<Long> orderIds;
}
