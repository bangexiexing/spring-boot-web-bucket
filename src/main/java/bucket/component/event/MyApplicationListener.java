package bucket.component.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 20:18 2018/6/6
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MyApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextClosedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ContextRefreshedEvent ){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ContextStartedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ContextStoppedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else{
            logger.info("有其它事件发生:"+event.getClass().getName());
        }
    }
}
