package bucket.user;

import bucket.component.event.UserLoginEvent;
import bucket.component.statemachine.UserStateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 20:40 2018/6/6
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private Map<Integer,StateMachine> userStateMap = new HashMap<>();

    @Autowired
    private UserMapper userMapper;

    @EventListener
    @Async
    public void userLogin(UserLoginEvent userLoginEvent) throws Exception {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StateMachine<UserStates,UserEvent> stateMachine = UserStateMachine.buildMachine();
        stateMachine.start();
        logger.info("stateMachine state :{}", stateMachine.getState());
        stateMachine.sendEvent(UserEvent.ON_LINE);
        userStateMap.put(userLoginEvent.getUser().getUserId(), stateMachine);
        logger.info("receive login event:{}",userLoginEvent.getUser());
    }

    @Transactional
    public void addUser(String name,String password){
        userMapper.insertByName(name, password);
    }

    @Cacheable(value = "user", key = "#name")
    public User findUserByName(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = User.builder()
                .name(name)
                .password("11111111111")
                .userId(ThreadLocalRandom.current().nextInt())
                .build();
        return user;
    }
}
