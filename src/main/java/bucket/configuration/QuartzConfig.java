package bucket.configuration;

import bucket.component.scheduledtask.SimpleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 21:29 2018/6/17
 */
//@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(SimpleJob.class).withIdentity("sampleJob")
                .usingJobData("name", "World").storeDurably().build();
    }

    @Bean
    public Trigger sampleJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(2).repeatForever();

        return TriggerBuilder
                .newTrigger().forJob(sampleJobDetail())
                .withIdentity("sampleTrigger").withSchedule(scheduleBuilder).build();
    }



}
