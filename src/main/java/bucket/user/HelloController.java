package bucket.user;


import bucket.component.SpringApplicationContextHolder;
import bucket.component.event.MyEventPublisher;
import bucket.component.webargument.CurrentUserId;
import bucket.response.AppResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class HelloController {

    @Autowired
    private MyEventPublisher myEventPublisher;

    @RequestMapping("/")
    public AppResponse index(){
        return AppResponse.success("hello,i am spring web bucket");
    }

    @RequestMapping("/login")
    public AppResponse login(){
        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setUserId(RandomUtils.nextInt(0,999));
        myEventPublisher.publishUserLoginEvent(user);
        return AppResponse.success();
    }

    @RequestMapping("/time")
    public AppResponse time(){
        Map map = new HashMap<>();
        map.put("time",new Date());
        return AppResponse.success(map);
    }

    @RequestMapping("/findBean")
    public AppResponse findBean(String name){
        return AppResponse.success(SpringApplicationContextHolder.getBean(name).getClass());
    }

    @RequestMapping("/findSomeOne")
    public String findSomeOne(@CurrentUserId int userId){
        return "userId is :"+userId;
    }
}
