package cn.ecomb.common.utils.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author brian.zhou
 * @date 2020/10/22
 */
//@Component
@Slf4j
public class PersonBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory defaultListableBeanFactory
                = (DefaultListableBeanFactory) beanFactory;

        //注册Bean定义，容器根据定义返回bean
        log.info("register personManager1>>>>>>>>>>>>>>>>");
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(Object.class);
        beanDefinitionBuilder.addPropertyReference("personDao", "personDao");
        BeanDefinition personManagerBeanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        defaultListableBeanFactory.registerBeanDefinition("personManager1", personManagerBeanDefinition);

        //注册bean实例
        log.info("register personManager2>>>>>>>>>>>>>>>>");
        Object personDao = beanFactory.getBean(Object.class);
        Object personManager = new Object();
//        personManager.setPersonDao(personDao);
        beanFactory.registerSingleton("personManager2", personManager);

    }
}
