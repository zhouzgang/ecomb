package cn.ecomb.park.common.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 测试 Unsafe 的功能
 *
 * @author brian.zhou
 * @date 2021/4/22
 */
public class UnsafePark {



	/**
	 * get Unsafe instance by reflect
	 * @return
	 */
	private static Unsafe reflectUnsafe() {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe) field.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
