package bucket.component.redis;

import bucket.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 16:45 2018/6/17
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    private Map<String,RedisScript> scriptMap = new HashMap<>();

    @PostConstruct
    public void loadScript() throws IOException {
        File file = ResourceUtils.getFile("classpath:redis-script");
        Files.walkFileTree(Paths.get(file.getAbsolutePath()),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                if(fileName.endsWith(".lua")){
                    String scriptText = new String(Files.readAllBytes(file),StandardCharsets.UTF_8);
//                    int dotIndex = fileName.lastIndexOf(".");
//                    String scriptName = fileName.substring(0,dotIndex);
                    String scriptName = fileName.substring(0,fileName.length() - 4);
                    /**
                     * @See org.springframework.data.redis.connection.ReturnType
                     */
                    scriptMap.put(scriptName,new DefaultRedisScript<Long>(scriptText));
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public Object runScript(String scriptName,String... keys){
        RedisScript redisScript = scriptMap.get(scriptName);
        if (redisScript == null) throw new AppException("can not find redis-script:"+scriptName);
        Object object = redisTemplate.execute(redisScript,Arrays.asList(keys));
        return object;
    }

    public Set<String> scanKeys(String pattern){
        if(StringUtils.isEmpty(pattern)) throw new AppException("scan pattern can not be null");
        Set result = (Set) redisTemplate.execute(new RedisCallback<Set<Object>>() {
            @Override
            public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<Object> binaryKeys = new HashSet<>();
                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(pattern).count(1000).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                try {
                    cursor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return binaryKeys;
            }
        });
        return result;
    }
}
