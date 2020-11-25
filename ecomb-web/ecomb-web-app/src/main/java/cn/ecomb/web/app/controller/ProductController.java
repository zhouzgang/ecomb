package cn.ecomb.web.app.controller;

import cn.ecomb.web.app.controller.request.AddProductRequest;
import cn.ecomb.web.app.controller.request.GetProductRequest;
import cn.ecomb.web.app.controller.request.ListProductRequest;
import cn.ecomb.web.app.controller.request.QueryProductRequest;
import cn.ecomb.web.app.controller.response.GetProductResponse;
import cn.ecomb.web.app.controller.response.ListProductResponse;
import cn.ecomb.web.app.controller.response.QueryProductResponse;
import cn.ecomb.web.app.service.IWebProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brian.zhou
 * @date 2020/10/9
 */
@Api("商品相关接口")
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private IWebProductService webProductService;

	@PostMapping
	@ApiOperation("添加商品")
	public void addProduct(AddProductRequest request) {
		webProductService.addProduct(request);
	}

	@GetMapping
	@ApiOperation("获取商品信息")
	public GetProductResponse getProduct(GetProductRequest request) {
		return webProductService.getProduct(request);
	}

	@GetMapping("/list")
	@ApiOperation("批量获取商品信息")
	public ListProductResponse listProduct(ListProductRequest request) {
		return webProductService.listProduct(request);
	}

	@GetMapping("/query")
	@ApiOperation(value = "查询商品", notes = "根据商品关键词，获取商品信息")
	public QueryProductResponse queryProduct(QueryProductRequest request) {
		return webProductService.queryProduct(request);
	}
}
