package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("获取商品接口参数")
public class GetProductRequest {

    @ApiModelProperty("商品ID")
    @NotNull(message = "商品 ID 不能为空")
    private String productId;
}
