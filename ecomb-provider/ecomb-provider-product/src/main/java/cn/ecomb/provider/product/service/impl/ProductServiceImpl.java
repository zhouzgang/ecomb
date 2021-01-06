package cn.ecomb.provider.product.service.impl;

import cn.ecomb.common.datas.redis.component.RedisHandle;
import cn.ecomb.common.provider.api.exception.ServiceException;
import cn.ecomb.common.provider.api.utils.ValidationUtil;
import cn.ecomb.provider.api.product.dto.ProductDto;
import cn.ecomb.provider.api.product.entity.Product;
import cn.ecomb.provider.api.product.param.ProductPageQuery;
import cn.ecomb.provider.api.product.param.ProductInventoryParam;
import cn.ecomb.provider.api.product.service.IProductServiceApi;
import cn.ecomb.provider.product.mapper.IProductMapper;
import jodd.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.ecomb.provider.product.config.ProductErrorCode.PRODUCT_STOCK_SHORT;

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

	@Autowired
	RedisHandle redisHandle;

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
	public void checkStock(List<ProductInventoryParam> stockParams) {

	}

	@Override
	public void reduceInventory(List<ProductInventoryParam> stockParams) {
		Map<String, Product> productMap = mapProduct(stockParams.stream()
				.map(ProductInventoryParam::getProductId)
				.collect(Collectors.toList()));
		//todo 减库存，在判断库存到执行减库存间，如果库存被减少了，如何处理并发问题呢？
		stockParams.forEach(stockParam -> {
			int count = productMapper.updateStock(Product.builder()
					.productId(stockParam.getProductId())
					.stock(stockParam.getNum())
					.build());
			if (count <= 0) {
				throw new ServiceException(PRODUCT_STOCK_SHORT.buildErrorMsg(stockParam.getProductId()));
			}
		});
	}

	/**
	 * 感觉这样实现，前面加一个redis判断库存，很鸡肋
	 * @param inventoryParams
	 */
	@Override
	public void reduceInventoryWithLock(List<ProductInventoryParam> inventoryParams) {
		inventoryParams.forEach(product -> {
			String key = product.getProductId();
			Integer inventory = (Integer) redisHandle.get(key);
			// 这样处理会存在 redis/db 数据一致性问题
			if (inventory == null) {
				Product product1 = productMapper.getProduct(product.getProductId());
				if (product1.getStock() > 0) {
					redisHandle.expire(key, (60 + MathUtil.randomInt(0, 100)) * 1000);
				}
			}

			ValidationUtil.ifTrueThrow(inventory < product.getNum(),
					PRODUCT_STOCK_SHORT.buildErrorMsg(product.getProductId()));

			long value = redisHandle.incr(key, -product.getNum());
			if (value > 0) {
				int count = productMapper.updateStock(Product.builder()
						.productId(product.getProductId())
						.stock(product.getNum())
						.build());
				if (count <= 0) {
					throw new ServiceException(PRODUCT_STOCK_SHORT.buildErrorMsg(product.getProductId()));
				}
			} else {
				// 回滚 redis 中的库存，这里与 incr 操作之间不是原子的，会导致，10库存，A要减 12，不行，回滚，同时 B 要减 6 实际是可以的但是库存还没回滚，导致无法下单。
				redisHandle.decr(key, product.getNum());
				log.info("商品 {} 库存不足", product.getNum());
			}
		});
	}
}
