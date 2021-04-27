package cn.ecomb.park.hello;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author brian.zhou
 * @date 2021/1/12
 */
public class Hello {

	public static void main(String[] args) {
		System.out.println("hello");
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(new Date()));
	}
}
