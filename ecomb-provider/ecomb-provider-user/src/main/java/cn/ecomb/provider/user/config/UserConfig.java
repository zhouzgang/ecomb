package cn.ecomb.provider.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用户相关配置
 * 具体配置放在配置文件或配置中心
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
@Data
@Component
public class UserConfig {

//	@Value("${user.def-user-logo}")
//	private String defUserLogo;
}
