package cn.ecomb.park.spi;

/**
 * 服务约定接口，类似 java.sql.Driver
 *
 * @author brian.zhou
 * @date 2021/4/20
 */
public interface IServiceProvider {

	/**
	 * 用户服务提供方实现方法。
	 * 最好是那些非常固定的方法，比如支付，连接相关的方法，他们是可以枚举的。
	 */
	void sayHello();
}
