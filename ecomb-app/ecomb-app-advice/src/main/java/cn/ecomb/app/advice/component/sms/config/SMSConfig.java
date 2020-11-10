package cn.ecomb.app.advice.component.sms.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author brian.zhou
 * @date 2020/11/10
 */
@Data
@Component
public class SMSConfig {

	@Value("yunpian.apikey")
	private String yunPianApiKey;
}
