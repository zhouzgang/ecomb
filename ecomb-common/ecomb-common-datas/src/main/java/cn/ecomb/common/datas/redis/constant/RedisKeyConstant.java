package cn.ecomb.common.datas.redis.constant;

/**
 * Redis Key 前缀
 * 所有的 Key 统一管理
 *
 * @author brian.zhou
 * @date 2020/10/14
 */
public class RedisKeyConstant {

	public static final String NOT_FOUND = "not_found";
	public static final String KEY = "key_";

	public static class Business {
		public static final String BUSINESS_KEY = "BUSINESS_KEY_";
		public static final String getBusinessKey(String appKey) {
			return String.format(BUSINESS_KEY, appKey);
		}
	}

}
