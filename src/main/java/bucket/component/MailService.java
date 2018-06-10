package bucket.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 17:51 2018/6/10
 */
@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("qiuyunlong0403@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        mailSender.send(message);
    }

    public void sendMail(String content) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("");
            helper.setTo("");
            helper.setSubject("");
            helper.setText(content);
            mailSender.send(message);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
