package bucket.component.scheduledtask;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 22:07 2018/6/17
 */
@Service
public class ScheduleService {

    private Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private Scheduler scheduler;

    public void changeJobCorn() throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(new JobKey("sampleJob"));
        logger.info("jobDetail is {}",jobDetail);
        Trigger trigger = newTrigger()
                .withIdentity("sampleTrigger")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(40)
                        .repeatForever())
                .build();
        scheduler.rescheduleJob(new TriggerKey("sampleTrigger"),trigger);
    }
}
