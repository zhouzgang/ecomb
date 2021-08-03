package cn.ecomb.park.classload.demo;

import java.lang.reflect.Method;

/**
 * @author brian.zhou
 * @date 2021/7/12
 */
public class Test {

    public static void main(String[] args) throws Exception {
        testLoadClass(args);
    }

    public static void testFindClass(String[] args) throws Exception {
        MyClassLoaderByFind classLoaderByFind = new MyClassLoaderByFind();
        Class testA = classLoaderByFind.findClass("cn.ecomb.park.classload.demo.TestA");
        Method mainMethod = testA.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(null, new Object[]{args});
    }

    public static void testLoadClass(String[] args) throws Exception {
        MyClassLoaderByFind classLoaderByFind = new MyClassLoaderByFind(Thread.currentThread().getContextClassLoader().getParent());
        Class testA = classLoaderByFind.findClass("cn.ecomb.park.classload.demo.TestA");
        Method mainMethod = testA.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(null, new Object[]{args});
    }
}
