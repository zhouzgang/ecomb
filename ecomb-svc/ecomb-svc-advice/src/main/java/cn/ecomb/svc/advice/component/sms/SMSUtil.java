package cn.ecomb.svc.advice.component.sms;

import cn.ecomb.svc.advice.exception.AdviceException;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

import java.util.Map;

/**
 * @author brian.zhou
 * @date 2020/11/10
 */
public class SMSUtil {

	private static volatile YunpianClient yunpianClient;

	/**
	 * 发送短信
	 */
	public static boolean sendSMS(String phone, String smsContent) {
		YunpianClient yunpianClient = getYunpianClient();
		Map<String, String> param = yunpianClient.newParam(2);
		param.put(YunpianClient.MOBILE, phone);
		param.put(YunpianClient.TEXT, smsContent);
		Result<SmsSingleSend> result = yunpianClient.sms().single_send(param);
		if (result == null || result.getCode() != 0) {
			throw new AdviceException(000, "发送短信失败");
		}
		return true;
	}

	public static YunpianClient getYunpianClient() {
		if (yunpianClient == null) {
			synchronized (SMSUtil.class) {
				if (yunpianClient == null) {
					yunpianClient = new YunpianClient();
				}
			}
		}
		return yunpianClient;
	}
}
