package bucket.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 16:26 2018/6/3
 */
@Component
public class SpringApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringApplicationContextHolder.context = context;
    }

    public static <T> T getBean(Class<T> cls) {
        return context == null ? null : context.getBean(cls);
    }

    public static Object getBean(String name) {
        return context == null ? null : context.getBean(name);
    }
}
