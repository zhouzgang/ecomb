package cn.ecomb.provider.api.order.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

/**
 * 订单实体
 * 简化处理订单信息，可以根据后续需要，拆分出更多的模块。
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
@Data
@Builder
public class Order {

	private Integer id;
	/** 订单编号 */
	private Long orderId;
	/** 用户Id */
	private Long userId;
	/** 用户帐号名 */
	private String userName;
	/** 订单总金额 */
	private Float totalAmount;
	/** 订单来源：0->PC订单；1->app订单 */
	private Integer sourceType;
	/** 订单类型：0->正常订单；1->秒杀订单 */
	private Integer orderType;
	/** 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单 */
	private Integer status;

	/** 应付金额（实际支付金额） */
	private Float payAmount;
	/** 支付方式：0->未支付；1->支付宝；2->微信 */
	private Integer payType;
	/** 支付时间 */
	private Date paymentTime;

	/** 运费金额 */
	private Float freightAmount;
	/** 物流公司(配送方式) */
	private String deliveryCompany;
	/** 物流单号 */
	private String deliveryId;
	/** 发货时间 */
	private Date deliveryTime;
	/** 确认收货状态：0->未确认；1->已确认 */
	private Integer confirmStatus;
	/** 确认收货时间 */
	private Date receiveTime;

	/** 评价 */
	private String comment;
	/** 评价时间 */
	private Date commentTime;

	/** 删除状态：0->未删除；1->已删除 */
	private Integer deleteStatus;

	/** 提交时间 */
	private Date createTime;
	/** 修改时间 */
	private Date modifyTime;

	/**
	 * 确认收货状态
	 */
	public enum ConfirmStatus {
		CONFIRM(1), UN_CONFIRM(0),
		;

		private Integer value;

		ConfirmStatus(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	@Tolerate
	public Order() {
	}
}
