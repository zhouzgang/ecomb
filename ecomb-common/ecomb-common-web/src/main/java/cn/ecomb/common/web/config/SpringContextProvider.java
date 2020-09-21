package cn.ecomb.common.web.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by roma on 2017/11/14
 * @since 2.2
 */
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
        return (T) context.getBean(name);
    }

    public static <T> T getBean(Class clazz) throws BeansException {
        return (T) context.getBean(clazz);
    }

}
