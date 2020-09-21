package cn.ecomb.provider.product.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 商品相关配置
 * 具体配置放在配置文件或配置中心
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
@Data
@Component
public class ProductConfig {

//	@Value("${product.def-product-logo}")
//	private String defProductLogo;
}
