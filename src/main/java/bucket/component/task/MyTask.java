package bucket.component.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 19:57 2018/6/10
 */
@Component
public class MyTask {

    private static final Logger logger = LoggerFactory.getLogger(MyTask.class);

    @Scheduled(cron="*/5 * * * * *")
    @Async
    public void time(){
        logger.info("my task do something at "+System.currentTimeMillis());
    }
}
