package componet;

import bucket.Application;
import bucket.component.redis.RedisUtil;
import bucket.user.User;
import bucket.util.LocalBackupUtil;
import bucket.util.SecurityHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;


/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 14:24 2018/6/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testSecurity() throws IOException {
        String str = RandomStringUtils.randomAlphabetic(16);
        String xorStr = SecurityHelper.XOR(str);
        Assert.assertEquals(str,SecurityHelper.XOR(xorStr));
//        System.out.println(str);
//        System.out.println(xorStr);
//        System.out.println();
    }

    @Test
    public void getHello() throws Exception {
        String value = System.currentTimeMillis() + "";
        redisTemplate.opsForValue().set("test111",value);
        Assert.assertEquals(redisTemplate.opsForValue().get("test111"),value);
    }

    @Test
    public void testScan(){
        redisUtil.runScript("test","some_key");
    }

    @Test
    public void testBackup() {
        System.out.println(LocalBackupUtil.load(User.class));
    }
}