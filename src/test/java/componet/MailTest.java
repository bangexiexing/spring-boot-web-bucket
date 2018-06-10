package componet;

import bucket.Application;
import bucket.component.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 17:55 2018/6/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MailTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail(){
        mailService.sendMail();
    }
}
