package cn.ecomb.provider.product.mapper;

import cn.ecomb.provider.api.product.dto.ProductDto;
import cn.ecomb.provider.api.product.entity.Product;
import cn.ecomb.provider.api.product.param.ProductPageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品相关数据库操作
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
@Mapper
public interface IProductMapper {

	/**
	 * 添加商品
	 * @param product
	 */
	void addProduct(@Param("product") Product product);

	/**
	 * 更新商品信息
	 * @param product
	 */
	void updateProduct(@Param("product") Product product);

	/**
	 * 更新库存
	 * @param product
	 */
	void updateStock(@Param("product") Product product);

	/**
	 * 删除商品信息
	 * @param productIds
	 */
	void remove(@Param("list") List<String> productIds);

	/**
	 * 获取商品信息
	 * @return
	 */
	Product getProduct(@Param("productId") String productId);

	/**
	 * 获取商品信息
	 * @return
	 */
	List<Product> listProductByIds(@Param("list") List<String> productIds);

	/**
	 * 分页查询商品信息
	 * @param pageQuery
	 * @return
	 */
	List<ProductDto> listProduct(@Param("list") ProductPageQuery pageQuery);
}
