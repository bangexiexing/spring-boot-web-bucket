package bucket.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 21:47 2018/7/5
 */
public class BigDecimalHelper {

    private static final int DEFAULT_SCALE = 2;
    private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.UP;

    public static BigDecimal getInstance(String number){
        return getInstance(number, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal getInstance(String number, int scale){
        return getInstance(number, scale, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal getInstance(String number, int scale, RoundingMode roundingMode){
        return new BigDecimal(number).setScale(scale,roundingMode);
    }

    public static BigDecimal setScale(BigDecimal bigDecimal){
        return bigDecimal.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }
}
