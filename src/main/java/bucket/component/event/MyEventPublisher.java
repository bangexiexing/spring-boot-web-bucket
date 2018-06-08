package bucket.component.event;

import bucket.user.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 20:57 2018/6/6
 */
@Component
public class MyEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void publishUserLoginEvent(User user){
        eventPublisher.publishEvent(new UserLoginEvent(user));
    }
}
