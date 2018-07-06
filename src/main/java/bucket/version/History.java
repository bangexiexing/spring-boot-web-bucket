package bucket.version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: qyl
 * @Description: 标记历史版本的方法，类
 * @Date: Created in 23:03 2018/7/6
 */
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
//better way with AbstractProcessor
//@Retention(RetentionPolicy.SOURCE)
@Retention(RetentionPolicy.RUNTIME)
public @interface History {
    /**
     *支持的最大版本
     */
    String until();
}
