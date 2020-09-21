package cn.ecomb.common.utils.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地线程变量工具类
 * @author brian.zhou
 * @date 2020/9/15
 */
public class ThreadLocalUtil {

	private static ThreadLocal<Map<String, Object>> holder = new ThreadLocal<>();

	public static void setAttribute(String key, Object obj) {
		Map<String, Object> map = holder.get();
		if (map == null) {
			map = new HashMap();
		}
		map.put(key, obj);
		holder.set(map);
	}

	public static Object getAttribute(String key) {
		Map<String, Object> map = holder.get();
		if (map == null) {
			return null;
		}
		return map.get(key);
	}

	public static void remove() {
		holder.remove();
	}
}
