package cn.ecomb.park.common;

import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

/**
 * @author brian.zhou
 * @date 2021/7/13
 */
public class JavaPathPark {

    public static void main(String[] args) throws Exception {
        System.out.println("user.dir: " + System.getProperty("user.dir"));//user.dir指定了当前的路径

        File directory = new File("");//设定为当前文件夹
        System.out.println("当前文件夹：" + directory.getCanonicalPath());//获取标准的路径
        System.out.println("当前文件夹：" + directory.getAbsolutePath());//获取绝对路径

        File classpath = new File("classpath:");
        System.out.println("classpath: " + classpath.getCanonicalPath());

        File file = new File(".");
        System.out.println(". : " + file.getCanonicalPath());

        // 读取 Class 对应 jar 的文件
        Class<StringUtils> clazz = StringUtils.class;
        ProtectionDomain domain = clazz.getProtectionDomain();
        // 获取到对应的jar文件
        URL jarFile = domain.getCodeSource().getLocation();
        // 获取到对应的类加载器
        ClassLoader classLoader = domain.getClassLoader();

    }
}
