package bucket.component.redis;

import bucket.component.SpringApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 16:00 2018/6/18
 */
public class RedisLock implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private int EXPIRED_SECOND = 120;
    private RedisTemplate redisTemplate;
    private String key;
    private volatile boolean locked = false;

    private RedisLock(){}

    private RedisLock(String key){
        this.key = key;
        this.redisTemplate = SpringApplicationContextHolder.getBean(RedisTemplate.class);
    }

    private RedisLock(String key,int expiredSecond){
        this.key = key;
        this.EXPIRED_SECOND = expiredSecond;
        this.redisTemplate = SpringApplicationContextHolder.getBean(RedisTemplate.class);
    }

    public String getKey() {
        return key;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void lock() {
        long start = System.currentTimeMillis();
        final Random r = new Random();
        int time_out = EXPIRED_SECOND * 1000;
        while (!tryLock()){
            if(System.currentTimeMillis() - start > time_out){
                throw new RuntimeException(Thread.currentThread().getName()+" get redis lock time out,key is :"+key);
            }
            try {
                Thread.sleep(10, r.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        locked = redisTemplate.opsForValue().setIfAbsent(key,Instant.now().getEpochSecond());
        //确保时间内业务能完成
        redisTemplate.expire(key,EXPIRED_SECOND,TimeUnit.SECONDS);
        return locked;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        //todo
        return false;
    }

    @Override
    public void unlock() {
        if (locked){
            redisTemplate.delete(key);
            locked = false;
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
