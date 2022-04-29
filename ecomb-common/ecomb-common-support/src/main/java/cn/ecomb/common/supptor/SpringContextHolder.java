package cn.ecomb.common.supptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author brian.zhou
 * @date 2/10/22
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public void destroy() throws Exception {

    }
}
