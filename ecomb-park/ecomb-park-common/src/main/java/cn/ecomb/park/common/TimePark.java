package cn.ecomb.park.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间处理工具类
 *
 * @author brian.zhou
 * @date 2021/6/15
 */
public class TimePark {

	public static void getCalendar() {
		// 获取当前时间:
		Calendar c = Calendar.getInstance();
		System.out.println(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(c.getTime())); // getTime 转化为Date对象
		System.out.println(c.get(Calendar.YEAR)); // 获取年
		System.out.println(1 + c.get(Calendar.MONTH)); // 获取月
		System.out.println(c.get(Calendar.DAY_OF_MONTH)); // 获取日
		System.out.println(c.get(Calendar.DAY_OF_WEEK)); // 获取周几
		System.out.println(c.get(Calendar.HOUR_OF_DAY)); // 获取小时
		System.out.println(c.get(Calendar.MINUTE)); // 获取分钟
		System.out.println(c.get(Calendar.SECOND)); // 获取秒
		System.out.println(c.get(Calendar.MILLISECOND)); // 获取毫秒
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,2011); // 设置年
		System.out.println(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(calendar.getTime()));
		calendar.clear(); // 清除日期
		System.out.println(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(calendar.getTime()));
	}

	public static void UTCToCST(String UTCStr, String format) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		date = sdf.parse(UTCStr);
		System.out.println("UTC时间: " + date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
		//calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
		System.out.println("北京时间: " + calendar.getTime());
	}


	/**
	 * 将日期时间转换为目标时区的日期时间
	 *
	 * @param dataStr 原时间
	 * @param targetZone 目标时区
	 * @return
	 * @throws ParseException
	 */
	public static String date2ZoneDate(String dataStr, String targetZone) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		Date date = sdf.parse(dataStr);

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);

		sdf.setTimeZone(TimeZone.getTimeZone(targetZone));
		return sdf.format(calendar.getTime());

	}



	public static void main(String[] args) throws ParseException {
		TimePark.date2ZoneDate("2021-06-15 17:38:12", "America/New_York");

	}
}
