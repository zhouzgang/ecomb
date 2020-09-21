package cn.ecomb.common.provider.api.model.query;

import lombok.Data;

/**
 * 分页查询基础类
 * @author: brian.zhou
 * @create: 2019-09-29 15:48
 **/
@Data
public class BasePageQuery {

    protected int offset;

    /** 每页数据大小 */
    private Integer pageSize = 10;
    /** 每页数据大小 */
    private Integer pageNum = 1;

    private String orderBy;

    public int getOffset() {
        offset = (pageNum - 1) * pageSize;
        if (offset < 0) {
            offset = 0;
        }
        return offset;
    }
}