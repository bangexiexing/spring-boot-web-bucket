package bucket.user;


import bucket.component.SpringApplicationContextHolder;
import bucket.component.event.MyEventPublisher;
import bucket.component.scheduledtask.ScheduleService;
import bucket.component.valiadated.CustomValidate;
import bucket.component.webargument.CurrentUserId;
import bucket.exception.AppErrorCode;
import bucket.response.AppResponse;
import bucket.util.SecurityHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
//在controller上添加可直接在 RequestMapping 的入参上校验参数,如 testParam2
@Validated
public class HelloController {

    @Autowired
    private MyEventPublisher myEventPublisher;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public AppResponse index(){
        return AppResponse.success("hello,i am spring web bucket");
    }

    @RequestMapping("/test")
    public AppResponse hashInfo(){
        String str = ThreadLocalRandom.current().nextInt()+"";
        return AppResponse.success(SecurityHelper.XOR(str));
    }

    @RequestMapping("/login")
    public AppResponse login(){
        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setUserId(RandomUtils.nextInt(0,999));
        //myEventPublisher.publishUserLoginEvent(user);
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

    @RequestMapping("/findSomeOneByName")
    public AppResponse findSomeOne(String name){
        User user = userService.findUserByName(name);
        return AppResponse.success(user);
    }

    @RequestMapping("/findSomeOne")
    public String findSomeOne(@CurrentUserId int userId){
        return "userId is :"+userId;
    }

    @RequestMapping("/testParam")
    public AppResponse testParam(@Validated RequestData requestData){
        System.out.println(requestData.getName());
        System.out.println(requestData.getNumber());
        return AppResponse.success();
    }

    @RequestMapping("/testParam2")
    public AppResponse testParam2(@NotEmpty String name, @CustomValidate String number, @Autowired Validator validator){
        System.out.println(name);
        System.out.println(number);
        return AppResponse.success();
    }

}
