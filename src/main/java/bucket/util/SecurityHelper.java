package bucket.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.swing.plaf.PanelUI;
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

    @Value("${salt:112313}")
    private static String SALT;

    private static HmacUtils hmacUtils;

    @PostConstruct
    public void init() {
        instance = this;
        XOREncryptKey = XOR_KEY.getBytes();
        hmacUtils = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, SALT.getBytes());
    }

    public static String XOR(String str){
        byte[] data = str.getBytes();
        for (int i = 0; i < data.length; i++){
            data[i] = (byte) (data[i] ^ (XOREncryptKey[i % XOREncryptKey.length]));
        }
        return new String(data, StandardCharsets.UTF_8);
    }

    public static String MD5(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        return DigestUtils.md5Hex(text);
    }

    public static String SHA1(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        return DigestUtils.sha1Hex(text);
    }

    public static String Hamc(String text){
        return hmacUtils.hmacHex(text);
    }
}
