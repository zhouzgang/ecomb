package cn.ecomb.provider.api.product.service;

import cn.ecomb.provider.api.product.dto.ProductDto;
import cn.ecomb.provider.api.product.entity.Product;
import cn.ecomb.provider.api.product.param.ProductPageQuery;
import cn.ecomb.provider.api.product.param.ProductStockParam;

import java.util.List;
import java.util.Map;

/**
 * 商品相关接口
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
public interface IProductServiceApi {
	
	/**
	 * 添加商品
	 * @param product
	 */
	void addProduct(Product product);

	/**
	 * 更新商品信息
	 * @param product
	 */
	void updateProduct(Product product);

	/**
	 * 删除商品信息
	 * @param productIds
	 */
	void remove(List<String> productIds);

	/**
	 * 获取商品信息
	 * @return
	 */
	Product getProduct(String productId);

	/**
	 * 获取商品信息
	 * @return
	 */
	List<Product> listProduct(List<String> productIds);

	/**
	 * 获取商品信息
	 * @return  key：商品编号，value：商品
	 */
	Map<String, Product> mapProduct(List<String> productIds);

	/**
	 * 分页查询商品信息
	 * @param pageQuery
	 * @return
	 */
	List<ProductDto> queryProduct(ProductPageQuery pageQuery);

	/**
	 * 检查商品库存是否满足条件
	 * @param stockParams   需要检查的商品库存数
	 */
	void checkStock(List<ProductStockParam> stockParams);

	/**
	 * 减少库存，减少参数中商品的库存数
	 * @param stockParams   需要减少的商品和库存数
	 */
	void reduceStock(List<ProductStockParam> stockParams);
}
