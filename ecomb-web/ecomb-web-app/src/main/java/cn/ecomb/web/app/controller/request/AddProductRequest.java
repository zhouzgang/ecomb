package cn.ecomb.web.app.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author brian.zhou
 * @date 2020/11/20
 */
@Data
@ApiModel("添加商品接口参数")
public class AddProductRequest {

	@ApiModelProperty("商品名称")
	private String name;
	@ApiModelProperty("副标题")
	private String subTitle;
	@ApiModelProperty("商品描述")
	private String description;
	@ApiModelProperty("市场价")
	private Float originalPrice;
	@ApiModelProperty("库存")
	private Integer stock;
	@ApiModelProperty("单位")
	private String unit;
	@ApiModelProperty("商品重量，默认为克")
	private Float weight;
	@ApiModelProperty("上架状态：0->下架；1->上架")
	private Integer publish_status;
	
}
