package cn.ecomb.park;

/**
 * 测试双重检验锁单例，如果不加 volatile 会不安全吗？singleton = new Singleton();
 * 的指令重排已经在锁里面了。
 * 参考链接：https://juejin.im/post/6844903920599302152
 * synchronized 详解链接： https://www.hollischuang.com/archives/2637
 *
 * idea 内查看 class 文件，控制台打开 class 所在文件夹，java -p class_name
 *
 * @author brian.zhou
 * @date 2020/11/3
 */
public class DoubleCheckSingleton {

	private volatile static DoubleCheckSingleton singleton;

	public static DoubleCheckSingleton singleton() {
		if (singleton == null) {
			synchronized (DoubleCheckSingleton.class) {
				if (singleton == null) {
					singleton = new DoubleCheckSingleton();
					System.out.println("new object");
				}
			}
		}
		return singleton;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					DoubleCheckSingleton.singleton();
				}
			}, "thread-" + i).start();
		}
	}
}
