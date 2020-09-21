package cn.ecomb.web.app.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取用户接口返回结果")
public class GetProductResponse {

    @ApiModelProperty("商品编号")
    private String productId;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("副标题")
    private String subTitle;
    @ApiModelProperty("商品描述")
    private String description;
    @ApiModelProperty("市场价")
    private Float originalPrice;

}
