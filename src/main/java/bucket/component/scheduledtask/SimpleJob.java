package bucket.component.scheduledtask;

import lombok.Data;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 21:25 2018/6/17
 */
@Data
public class SimpleJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(SimpleJob.class);

    private String name;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("SimpleJob name {}",name);
    }
}
