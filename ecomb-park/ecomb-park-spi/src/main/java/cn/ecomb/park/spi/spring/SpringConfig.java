package cn.ecomb.park.spi.spring;

import cn.ecomb.park.spi.IServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author brian.zhou
 * @date 2021/4/20
 */
@Configuration
public class SpringConfig {

	@Bean
	public IServiceProvider build() {
		return new IServiceProviderSpringImpl();
	}
}
