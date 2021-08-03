package cn.ecomb.park.classload.dynamic;

import cn.ecomb.park.spi.IServiceProvider;

import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * ServiceProvider 工厂类
 *
 * @author brian.zhou
 * @date 2021/7/12
 */
public class ServiceProviderFactory {

    ServiceLoader<IServiceProvider> serviceLoader = ServiceLoader.load(IServiceProvider.class);

    public static void main(String[] args) {

        // 如果之类没错的话，使用 jar 自己的，对应版本的load。
//        ExtensionJarLoader extensionJarLoader =
//                new ExtensionJarLoader(Thread.currentThread().getContextClassLoader().getParent());
        ServiceLoader<IServiceProvider> serviceLoader = ServiceLoader.load(IServiceProvider.class, Thread.currentThread().getContextClassLoader().getParent());

        // 这里浪费点性能，直接只要对应 jar 的功能
        Iterator iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            IServiceProvider item = (IServiceProvider) iterator.next();
            item.sayHello();
        }
    }
}
