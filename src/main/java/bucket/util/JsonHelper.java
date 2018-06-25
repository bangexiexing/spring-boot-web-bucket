package bucket.util;

import bucket.component.json.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 21:07 2018/6/25
 */
public abstract class JsonHelper {

    private static final Logger logger = LoggerFactory.getLogger(JsonHelper.class);

    private static JsonMapper nonDefaultMapper = JsonMapper.nonDefaultMapper();
    private static JsonMapper nonDefaultLowerCaseWithUnderscoresMapper = JsonMapper.nonDefaultMapper(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

    public static <T> List<T> stringToList(String jsonString, Class cls){
        return nonDefaultMapper.fromJson(jsonString, nonDefaultMapper.contructCollectionType(List.class, cls));
    }

    public static <T> T stringToBean(String jsonString, Class<T> cls){
        return nonDefaultMapper.fromJson(jsonString, cls);
    }
    //带泛型的对像
    public static <T> T stringToBeanWithType(String jsonString,TypeReference<?> typeReference){
        try {
            return nonDefaultMapper.getMapper().readValue(jsonString,typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("json转换错误");
        }
    }

    /**
     * user_id => userId
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T lowerCaseWithUnderscoresStringToBean(String jsonString, Class<T> cls){
        return nonDefaultLowerCaseWithUnderscoresMapper.fromJson(jsonString, cls);
    }

    /**
     * userId => user_id
     * @param obj
     * @return
     */
    public static String beanToLowerCaseWithUnderscoresString(Object obj){
        return nonDefaultLowerCaseWithUnderscoresMapper.toJson(obj);
    }

    public static Map<String, Object> stringToMap(String jsonString){
        return nonDefaultMapper.fromJson(jsonString, nonDefaultMapper.contructMapType(HashMap.class, String.class, Object.class));
    }

    public static Map<String, String> stringToStringMap(String jsonString){
        return nonDefaultMapper.fromJson(jsonString, nonDefaultMapper.contructMapType(HashMap.class, String.class, String.class));
    }

    public static Map<String, Object> stringToMap(String jsonString, Class mapClass){
        return nonDefaultMapper.fromJson(jsonString, nonDefaultMapper.contructMapType(mapClass, String.class, Object.class));
    }

    public static String beanToString(Object bean){
        return nonDefaultMapper.toJson(bean);
    }
}
