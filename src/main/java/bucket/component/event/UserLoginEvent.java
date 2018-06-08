package bucket.component.event;

import bucket.user.User;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 20:43 2018/6/6
 * @see bucket.user.UserService#userLogin(UserLoginEvent)
 */
public class UserLoginEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public UserLoginEvent(Object source) {
        super(source);
    }

    public User getUser(){
        return (User) source;
    }
}
