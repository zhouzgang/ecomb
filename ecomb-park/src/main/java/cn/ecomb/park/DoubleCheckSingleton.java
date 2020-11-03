package cn.ecomb.park;

/**
 * 测试双重检验锁单例，如果不加 volatile 会不安全吗？singleton = new Singleton();
 * 的指令重排已经在锁里面了。
 * 参考链接：https://juejin.im/post/6844903920599302152
 * synchronized 详解链接： https://www.hollischuang.com/archives/2637
 *
 * todo 可以借助这个问题，把 JVM 知识在过一篇
 *
 * idea 内查看 class 文件，控制台打开 class 所在文件夹，java -p class_name
 *
 * @author brian.zhou
 * @date 2020/11/3
 */
public class DoubleCheckSingleton {

	/**
	 * volatile 为了解决 对象创建过程中的指令重排
	 * 1. 分配对象内存
	 * 2. 初始化
	 * 3. 赋值引用
	 *
	 * 为什么在使用了 synchronized 后，还需要使用 volatile 关键字？
	 * 1. synchronized 不能禁止编译器优化和指令重排
	 * 2. synchronized 可以保证线程内的顺序性，但是不能保证外顺序性
	 * 3. 对象的创建，和引用的赋值属于主内存内，synchronized 管不到这里的内容
	 *
	 * 不加 volatile 关键字会导致什么问题？
	 * 1. 线程-1 获取锁执行了创建对象的 1，3 操作，这时候对象在堆内存中。CPU时间片结束，释放CPU资源
	 * 2. 线程-2 执行第一层 if 判断，获取到堆中的引用，它是不等于空的，返回一个没有初始化的对象
	 * 总结：会有可能导致 返回一个空对象的问题
	 */
	private volatile static DoubleCheckSingleton singleton;

	/**
	 * 同步锁外判断，为避免在实例已经创建的情况下每次获取实例都加锁取，影响性能；
	 * 锁内判断，考虑多线程情况下，两个以上线程都已经运行至同步锁处，也就是都已经判断变量为空，如锁内不再次判断，会导致实例重复创建。
	 * @return
	 */
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
