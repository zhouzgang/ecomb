package cn.ecomb.park.spring.bean;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
public class HelloServiceImpl implements HelloService{

	@Override
	public void sayHello() {
		System.out.println("hello， factory bean");
	}
}
