package cn.ecomb.provider.api.product.dto;

import lombok.Data;

/**
 * 商品数据传输对象
 * @author brian.zhou
 * @date 2020/9/22
 */
@Data
public class ProductDto {

	/** 商品编号 */
	private String productSn;
	/** 商品名称 */
	private String name;

}
