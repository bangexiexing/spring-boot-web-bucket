package bucket.component.redis;

import bucket.component.SpringApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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
    private RetryStrategy retryStrategy;

    private RedisLock(){}

    public RedisLock(String key){
        this.key = key;
        this.redisTemplate = SpringApplicationContextHolder.getBean(RedisTemplate.class);
        this.retryStrategy = new LockIfAbsent();
    }

    public RedisLock(String key,int expiredSecond){
        this.key = key;
        this.EXPIRED_SECOND = expiredSecond;
        this.redisTemplate = SpringApplicationContextHolder.getBean(RedisTemplate.class);
        this.retryStrategy = new LockIfAbsent();
    }

    public void useLockIfExpiredStrategy(){
        this.retryStrategy = new LockIfExpired();
    }

    public String getKey() {
        return key;
    }

    public boolean isLocked() {
        return locked;
    }

    interface RetryStrategy{
        boolean retry();
    }
    //只有对方释放锁后才获取锁，超时抛异常
    class LockIfAbsent implements RetryStrategy{
        @Override
        public boolean retry() {
            long start = System.currentTimeMillis();
            final Random r = ThreadLocalRandom.current();
            int time_out = EXPIRED_SECOND * 1000;
            Long value = (Long) redisTemplate.opsForValue().get(key);
            //maybe unnecessary
            if (value != null && (System.currentTimeMillis() - value > time_out)){
                throw new RuntimeException(Thread.currentThread().getName()+" get redis lock time out, key is :" + key);
            }
            //start = value == null ? start : value;
            while (!tryLock()){
                if (System.currentTimeMillis() - start > time_out){
                    throw new RuntimeException(Thread.currentThread().getName()+" get redis lock time out, key is :" + key);
                }
                try {
                    Thread.sleep(10, r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    //对方锁持有时间超时后，尝试获取锁。
    class LockIfExpired implements RetryStrategy{
        @Override
        public boolean retry() {
            final Random r = new Random();
            int time_out = EXPIRED_SECOND * 1000;
            BoundValueOperations valueOps = redisTemplate.boundValueOps(key);
            while (!tryLock()){
                Long value = (Long) valueOps.getKey();
                if (value == null) continue;
                if (System.currentTimeMillis() - value > time_out){
                    Long oldValue = (Long) valueOps.getAndSet(System.currentTimeMillis());
                    //如果是空，表示锁已经被释放
                    //oldValue != value 表示已经有人先一步获取了锁
                    if (oldValue == null || oldValue == value) {
                        locked = true;
                        break;
                    }
                }else {
                    try {
                        Thread.sleep(10, r.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
    }

    @Override
    public void lock() {
        if (!tryLock()){
            retryStrategy.retry();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        locked = redisTemplate.opsForValue().setIfAbsent(key,System.currentTimeMillis());
        //确保时间内业务能完成,或许应该不设过期时间，交给持有者释放
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
