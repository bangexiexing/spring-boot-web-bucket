package bucket.user;


import bucket.component.SpringApplicationContextHolder;
import bucket.component.event.MyEventPublisher;
import bucket.component.scheduledtask.ScheduleService;
import bucket.component.webargument.CurrentUserId;
import bucket.exception.AppErrorCode;
import bucket.response.AppResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class HelloController {

    @Autowired
    private MyEventPublisher myEventPublisher;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("/")
    public AppResponse index(){
        return AppResponse.success("hello,i am spring web bucket");
    }

    @RequestMapping("/login")
    public AppResponse login(){
        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setUserId(RandomUtils.nextInt(0,999));
        //myEventPublisher.publishUserLoginEvent(user);
        //添加用户认证性息
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "xxxx",Collections.singletonList(new SimpleGrantedAuthority("admin")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return AppResponse.success();
    }

    @RequestMapping("/redis")
    public AppResponse redis(){
        redisTemplate.opsForValue().set(
                RandomStringUtils.randomAlphabetic(5),RandomUtils.nextInt(0,999));
        return AppResponse.success();
    }

    @RequestMapping("/time")
    public AppResponse time() throws SchedulerException {
        Map map = new HashMap<>();
        map.put("time",new Date());
        scheduleService.changeJobCorn();
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
