package cn.ecomb.provider.api.product.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * 商品实体
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
@Data
@Builder
public class Product {

	private Integer id;
	/** 商品编号 */
	private String productId;
	/** 商品名称 */
	private String name;
	/** 副标题 */
	private String subTitle;
	/** 商品描述 */
	private String description;
	/** 市场价 */
	private Float originalPrice;
	/** 库存 */
	private Integer stock;
	/** 单位 */
	private String unit;
	/** 商品重量，默认为克 */
	private Float weight;
	/** 销量 */
	private Integer sale;
	/** 删除状态：0->未删除；1->已删除 */
	private Integer delete_status;
	/** 上架状态：0->下架；1->上架 */
	private Integer publish_status;

	@Tolerate
	public Product() {
	}
}
