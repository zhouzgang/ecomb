package cn.ecomb.web.app.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取订单接口返回结果")
public class QueryProductResponse {

    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("用户名字")
    private String userName;

}
