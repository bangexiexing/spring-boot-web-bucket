package bucket.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 13:09 2018/6/3
 */
public class TimeUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    public static LocalDateTime expireDays(int days){
        return LocalDate.now().plusDays(days + 1).atStartOfDay().minusSeconds(1);
    }

    public static LocalDateTime fromUnixTime(long unixTime){
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime),ZoneId.systemDefault());
    }
}
