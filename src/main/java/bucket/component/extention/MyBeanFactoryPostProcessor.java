package bucket.component.extention;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: qyl
 * @Description: 发生在初始化bean之前
 * @Date: Created in 20:53 2018/6/5
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor , EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(MyBeanFactoryPostProcessor.class);

    private ConfigurableEnvironment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        logger.info("do some thing in postProcessBeanFactory");
        PropertySource propertySource = new MapPropertySource("myTestProperties",ImmutableMap.of("api.sign.msg","hello-properties"));
        environment.getPropertySources().addFirst(propertySource);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
}
