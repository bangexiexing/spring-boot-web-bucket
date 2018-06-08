package bucket.component.extention;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 21:29 2018/6/5
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MyBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if( null != bean.getClass().getAnnotation(TestBean.class)){
            System.out.println(bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if( null != bean.getClass().getAnnotation(TestBean.class)){
            System.out.println(bean);
        }
        return bean;
    }
}
