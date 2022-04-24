package cn.ecomb.common.supptor.page;

import lombok.Data;

@Data
public class PageQuery {

    /**
     * current page number
     */
    private Integer current = 1;

    /**
     * page size
     */
    private Integer pageSize = 10;

    /**
     * sort field and sort order
     */
    private String orderBy;
}
