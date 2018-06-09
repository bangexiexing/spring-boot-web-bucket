package componet;

import bucket.configuration.RedisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 14:24 2018/6/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisConfig.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void getHello() throws Exception {
        String value = System.currentTimeMillis() + "";
        redisTemplate.opsForValue().set("test111",value);
        assertEquals(redisTemplate.opsForValue().get("test111"),value);
    }
}