package cn.ecomb.provider.api.product.param;

import lombok.Data;

/**
 * 商品库存参数
 * @author brian.zhou
 * @date 2020/10/10
 */
@Data
public class ProductStockParam {

	/** 商品编号 */
	private String productId;
	/** 库存 */
	private Integer stock;
}
