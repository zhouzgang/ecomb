package cn.ecomb.common.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页response
 *
 * @author zhouzg
 * @date 2019/8/6
 **/
@Data
@ApiModel("分页结果集")
public class WebPageResponse<T> {

    @ApiModelProperty("数据列表")
    private List<T> list;

    @ApiModelProperty("当前请求页数")
    private Integer pageNum;

    @ApiModelProperty("每页记录数")
    private Integer pageSize;

    @ApiModelProperty("总页数")
    private Integer pages;

    @ApiModelProperty("总记录数")
    private Integer total;

    public WebPageResponse(List<T> list, Integer pages, Integer total, Integer pageNum, Integer pageSize) {
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
        this.total = total;
    }
}
