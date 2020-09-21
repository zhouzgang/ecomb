package cn.ecomb.provider.product.service.impl;

import cn.ecomb.provider.api.product.dto.ProductDto;
import cn.ecomb.provider.api.product.entity.Product;
import cn.ecomb.provider.api.product.param.ProductPageQuery;
import cn.ecomb.provider.api.product.param.ProductStockParam;
import cn.ecomb.provider.api.product.service.IProductServiceApi;
import cn.ecomb.provider.product.mapper.IProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品相关接口
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductServiceApi {

	@Autowired
	private IProductMapper productMapper;

	@Override
	public void addProduct(Product product) {
		productMapper.addProduct(product);
	}

	@Override
	public void updateProduct(Product product) {
		productMapper.updateProduct(product);
	}

	@Override
	public void remove(List<String> productIds) {
		productMapper.remove(productIds);
	}

	@Override
	public Product getProduct(String productId) {
		return productMapper.getProduct(productId);
	}

	@Override
	public List<Product> listProduct(List<String> productIds) {
		return productMapper.listProductByIds(productIds);
	}

	@Override
	public Map<String, Product> mapProduct(List<String> productIds) {
		List<Product> products = productMapper.listProductByIds(productIds);
		return products.stream()
				.collect(Collectors.toMap(Product::getProductId, product -> product));
	}

	@Override
	public List<ProductDto> queryProduct(ProductPageQuery pageQuery) {
		return productMapper.listProduct(pageQuery);
	}

	@Override
	public void checkStock(List<ProductStockParam> stockParams) {

	}

	@Override
	public void reduceStock(List<ProductStockParam> stockParams) {
		Map<String, Product> productMap = mapProduct(stockParams.stream()
				.map(ProductStockParam::getProductId)
				.collect(Collectors.toList()));
		//todo 减库存，在判断库存到执行减库存间，如果库存被减少了，如何处理并发问题呢？
		stockParams.forEach(stockParam -> {
			productMapper.updateStock(Product.builder()
					.productId(stockParam.getProductId())
					.stock(productMap.get(stockParam.getProductId()).getStock() - stockParam.getStock())
					.build());
		});
	}
}
