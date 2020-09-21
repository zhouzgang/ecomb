package cn.ecomb.provider.api.order.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 订单明细项实体
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
@Data
@Builder
public class OrderItem {

	private Integer id;
	/** 订单项id */
	private Long orderItemId;
	/** 订单id */
	private Long orderId;
	/** 订单编号 */
//	private String orderSn;
	/** 商品Id */
	private String productId;
	/** 商品编号 */
//	private String productSn;
	/** 商品价格 */
	private String productPrice;
	/** 商品名字 */
	private String productName;
	/** 销售价格 */
	private Float productSellingPrice;
	/** 购买数量 */
	private Integer productQuantity;
	/** 商品sku编号 */
	private Integer productSkuId;
	/** 商品sku条码 */
	private String productSkuCode;
	/** 删除状态：0->未删除；1->已删除 */
	private Integer deleteStatus;

}
