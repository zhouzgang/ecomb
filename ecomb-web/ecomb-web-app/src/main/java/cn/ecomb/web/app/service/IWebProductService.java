package cn.ecomb.web.app.service;

import cn.ecomb.web.app.controller.request.GetProductRequest;
import cn.ecomb.web.app.controller.request.ListProductRequest;
import cn.ecomb.web.app.controller.request.QueryProductRequest;
import cn.ecomb.web.app.controller.response.GetProductResponse;
import cn.ecomb.web.app.controller.response.ListProductResponse;
import cn.ecomb.web.app.controller.response.QueryProductResponse;

/**
 * 商品相关接口
 *
 * @author brian.zhou
 * @date 2020/9/18
 */
public interface IWebProductService {

	/**
	 * 获取商品信息
	 * @param request 商品参数
	 * @return 商品信息
	 */
	GetProductResponse getProduct(GetProductRequest request);

	/**
	 * 批量获取商品信息
	 * @param request
	 * @return
	 */
	ListProductResponse listProduct(ListProductRequest request);

	/**
	 * 查询商品信息
	 * @param request
	 * @return
	 */
	QueryProductResponse queryProduct(QueryProductRequest request);
}
