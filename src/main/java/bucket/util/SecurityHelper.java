package bucket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 21:40 2018/6/25
 */
@Component
public class SecurityHelper {

    private static Logger logger = LoggerFactory.getLogger(SecurityHelper.class);

    @Value("${xor.key:131313hello}")
    private String XOR_KEY;

    private static byte[] XOREncryptKey;

    private static SecurityHelper instance;

    @PostConstruct
    public void init() {
        instance = this;
        XOREncryptKey = XOR_KEY.getBytes();
    }

    public static String XOR(String str){
        byte[] data = str.getBytes();
        for (int i = 0; i < data.length; i++){
            data[i] = (byte) (data[i] ^ (XOREncryptKey[i % XOREncryptKey.length]));
        }
        return new String(data,StandardCharsets.UTF_8);
    }

}
