package cn.ecomb.web.app.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("获取订单接口返回结果")
public class ListProductResponse {

    @ApiModelProperty("商品列表")
    private List<Product> list;

    @Data
    @ApiModel("商品信息")
    public static class Product {
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

}
