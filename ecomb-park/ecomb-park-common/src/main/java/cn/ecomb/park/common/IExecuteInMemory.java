package cn.ecomb.park.common;

/**
 * i=1 在内存的执行过程
 *
 * @author brian.zhou
 * @date 2020/11/10
 */
public class IExecuteInMemory {
	public static void main(String[] args) {
		int i = 0;
	}

	/**
	 * 一个栈帧包含：
	 * 局部变量表
	 * 操作数栈
	 * 动态链接
	 * 返回地址
	 *
	 *  public static int add(int);
	 *     descriptor: (I)I
	 *     flags: ACC_PUBLIC, ACC_STATIC
	 *     Code:
	 *       stack=2, locals=3, args_size=1
	 *          0: iconst_1 把常量 1 压入操作数栈栈顶
	 *          1: istore_1 把操作数栈栈顶的出栈放入局部变量表索引为 1 的位置
	 *          2: iconst_2 把常量 2 压入操作数栈栈顶
	 *          3: istore_2 把操作数栈栈顶的出栈放入局部变量表索引为 2 的位置
	 *          4: iload_1 把局部变量表索引为 1 的值放入操作数栈栈顶
	 *          5: iload_2 把局部变量表索引为 2 的值放入操作数栈栈顶
	 *          6: iadd 将操作数栈栈顶的和栈顶下面的一个进行加法运算后放入栈顶
	 *          7: iload_0
	 *          8: iadd
	 *          9: ireturn
	 *       LineNumberTable:
	 *         line 15: 0
	 *         line 16: 2
	 *         line 17: 4
	 *       LocalVariableTable:
	 *         Start  Length  Slot  Name   Signature
	 *             0      10     0     k   I
	 *             2       8     1     i   I
	 *             4       6     2     j   I
	 *     MethodParameters:
	 *       Name                           Flags
	 *       k
	 *
	 *
	 * @param k
	 * @return
	 */
	public static int add(int k) {
		int i = 1;
		int j = 2;
		return i + j + k;
	}
}
