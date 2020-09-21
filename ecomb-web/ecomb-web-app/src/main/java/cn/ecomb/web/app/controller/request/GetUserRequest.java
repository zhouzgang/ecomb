package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取用户接口")
public class GetUserRequest {

    @ApiModelProperty("用户ID")
    private Long userId;
}
