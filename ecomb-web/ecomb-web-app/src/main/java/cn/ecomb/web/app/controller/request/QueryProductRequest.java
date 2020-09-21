package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询订单参数")
public class QueryProductRequest {

    @ApiModelProperty("用户ID")
    private Integer userId;
}
