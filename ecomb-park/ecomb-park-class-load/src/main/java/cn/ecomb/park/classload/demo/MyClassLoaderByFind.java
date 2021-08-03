package cn.ecomb.park.classload.demo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author brian.zhou
 * @date 2021/7/12
 */
public class MyClassLoaderByFind extends ClassLoader{

    private ClassLoader jdkClassLoader;
    private Map<String, String> classPathMap = new HashMap<>();

    public MyClassLoaderByFind() {
        classPathMap.put("cn.ecomb.park.classload.demo.TestA", "/Users/brian.zhou/Documents/workspace/code/ecomb/ecomb-park/ecomb-park-class-load/target/classes/cn/ecomb/park/classload/demo/TestA.class");
        classPathMap.put("cn.ecomb.park.classload.demo.TestB", "/Users/brian.zhou/Documents/workspace/code/ecomb/ecomb-park/ecomb-park-class-load/target/classes/cn/ecomb/park/classload/demo/TestB.class");
    }

    public MyClassLoaderByFind(ClassLoader jdkClassLoader) {
        this.jdkClassLoader = jdkClassLoader;
        classPathMap.put("cn.ecomb.park.classload.demo.TestA", "/Users/brian.zhou/Documents/workspace/code/ecomb/ecomb-park/ecomb-park-class-load/target/classes/cn/ecomb/park/classload/demo/TestA.class");
        classPathMap.put("cn.ecomb.park.classload.demo.TestB", "/Users/brian.zhou/Documents/workspace/code/ecomb/ecomb-park/ecomb-park-class-load/target/classes/cn/ecomb/park/classload/demo/TestB.class");
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = classPathMap.get(name);
        File file = new File(classPath);
        if (!file.exists()) {
            throw new ClassNotFoundException();
        }
        byte[] classByte = getClassData(file);
        if (classByte.length == 0) {
            throw new ClassNotFoundException();
        }
        return defineClass(classByte, 0, classByte.length);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class result = null;
        try {
            // 这里使用 JDK 的类加载器，加载 java.lang 包里面的类
            result = jdkClassLoader.loadClass(name);
        } catch (Exception e) {

        }
        if (result != null) {
            return result;
        }
        String classPath = classPathMap.get(name);
        File file = new File(classPath);
        if (!file.exists())
            throw new ClassNotFoundException();
        byte[] classByte = getClassData(file);
        if (classByte.length == 0)
            throw new ClassNotFoundException();
        return defineClass(classByte, 0, classByte.length);
    }

    private byte[] getClassData(File file) {
        try {
            InputStream ins = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
}
