package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("批量获取商品参数")
public class ListProductRequest {

    @ApiModelProperty("商品ID")
    @NotNull(message = "商品ID 不能为空")
    private List<String> productIds;
}
