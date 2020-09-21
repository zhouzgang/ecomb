package cn.ecomb.provider.api.user.param;

import cn.ecomb.common.provider.api.model.query.BasePageQuery;
import lombok.Data;

/**
 * 批量查询用户信息参数
 *
 * @author brian.zhou
 * @date 2020/9/21
 */
@Data
public class ListUserPageQuery extends BasePageQuery {

	private String name;
}
