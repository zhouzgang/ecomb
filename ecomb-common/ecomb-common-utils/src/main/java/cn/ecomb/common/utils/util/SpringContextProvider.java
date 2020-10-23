package cn.ecomb.common.utils.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static <T> T getBean(String name) throws BeansException {

        ConfigurableApplicationContext context1 = (ConfigurableApplicationContext) context;


        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
//        for (Object arg : args) {
//            beanDefinitionBuilder.addConstructorArgValue(arg);
//        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context1.getBeanFactory();
        beanFactory.registerBeanDefinition(name, beanDefinition);

        return (T) context.getBean(name);
    }

    public static <T> T getBean(Class clazz) throws BeansException {
        return (T) context.getBean(clazz);
    }

}
