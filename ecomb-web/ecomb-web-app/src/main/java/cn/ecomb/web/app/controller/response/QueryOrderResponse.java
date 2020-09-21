package cn.ecomb.web.app.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ApiModel("获取订单接口返回结果")
public class QueryOrderResponse {

    @ApiModelProperty("订单列表")
    private List<ListOrderResponse.Order> list;

    @Data
    @ApiModel("订单信息")
    public class Order {
        @ApiModelProperty("订单编号")
        private Long orderId;
        @ApiModelProperty("订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
        private Integer status;
    }

}
