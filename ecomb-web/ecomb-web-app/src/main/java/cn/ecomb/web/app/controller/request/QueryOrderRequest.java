package cn.ecomb.web.app.controller.request;

import cn.ecomb.common.web.request.WebBaseRequestPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询订单参数")
public class QueryOrderRequest extends WebBaseRequestPage {

    @ApiModelProperty("用户ID")
    private Integer userId;
}
