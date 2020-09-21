package cn.ecomb.common.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 随行请求接口
 * @author zhouzg
 * @date 2019/9/9
 */
@Data
public class WebBaseRequestPage extends WebBaseRequest {

    /** 每页数据大小 */
    @ApiModelProperty(value = "每页数据大小",required = true)
    private Integer pageSize = 10;
    /** 第几页 */
    @ApiModelProperty(value = "页码",required = true)
    private Integer pageNum = 1;
}
