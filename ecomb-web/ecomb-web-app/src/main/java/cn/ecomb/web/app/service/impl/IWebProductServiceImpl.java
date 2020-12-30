package cn.ecomb.web.app.service.impl;

import cn.ecomb.common.utils.util.BeanHandleUtil;
import cn.ecomb.provider.api.product.entity.Product;
import cn.ecomb.provider.api.product.service.IProductServiceApi;
import cn.ecomb.web.app.controller.request.AddProductRequest;
import cn.ecomb.web.app.controller.request.GetProductRequest;
import cn.ecomb.web.app.controller.request.ListProductRequest;
import cn.ecomb.web.app.controller.request.QueryProductRequest;
import cn.ecomb.web.app.controller.response.GetProductResponse;
import cn.ecomb.web.app.controller.response.ListProductResponse;
import cn.ecomb.web.app.controller.response.QueryProductResponse;
import cn.ecomb.web.app.service.IWebProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author brian.zhou
 * @date 2020/9/18
 */
@Service
@Slf4j
public class IWebProductServiceImpl implements IWebProductService {

	@Autowired
	IProductServiceApi productServiceApi;

	@Override
	public void addProduct(AddProductRequest request) {
		Product product = BeanHandleUtil.copyProperties(request, Product.class);
		product.setProductId("001");
		product.setDelete_status(0);
		productServiceApi.addProduct(product);
	}

	@Override
	public GetProductResponse getProduct(GetProductRequest request) {
		return Optional.ofNullable(productServiceApi.getProduct(request.getProductId()))
				.map(product -> {
					GetProductResponse response = new GetProductResponse();
					BeanUtils.copyProperties(product, response);
					return response;
				})
				.orElse(null);
	}

	@Override
	public ListProductResponse listProduct(ListProductRequest request) {
		return Optional.ofNullable(productServiceApi.listProduct(request.getProductIds()))
				.map(products -> {
					ListProductResponse response = new ListProductResponse();
					response.setList(BeanHandleUtil.copyPropertiesList(products, ListProductResponse.Product.class));
					return response;
				})
				.orElse(null);
	}

	@Override
	public QueryProductResponse queryProduct(QueryProductRequest request) {
		return null;
	}
}
