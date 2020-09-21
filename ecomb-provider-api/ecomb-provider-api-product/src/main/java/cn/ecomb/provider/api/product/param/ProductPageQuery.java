package cn.ecomb.provider.api.product.param;

import cn.ecomb.common.provider.api.model.query.BasePageQuery;
import lombok.Data;

/**
 * 分页查询商品信息参数
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
@Data
public class ProductPageQuery extends BasePageQuery {

	/** 商品名称 */
	private String name;
	/** 副标题 */
	private String subTitle;
}
