package cn.ecomb.provider.api.product.param;

import lombok.Data;

/**
 * 商品库存参数
 * @author brian.zhou
 * @date 2020/10/10
 */
@Data
public class ProductInventoryParam {

	/** 商品编号 */
	private String productId;
	/** 数量 */
	private Integer num;
}
