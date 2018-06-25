package bucket.util;

import bucket.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 21:21 2018/6/25
 */
public abstract class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    private static final Pattern usernameReg = Pattern.compile("[`~!@#$^&*()=|{}':;',/\\\\/\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");

    private static final String mobileNumberReg = "^1[0-9]{10}$";

    private static final Pattern passwordReg1 = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$).{8,20}$");

    private static final Pattern passwordReg2 = Pattern.compile("[‘’“”\'\"]");

    private static final Pattern passwordReg3 = Pattern.compile("[\\u4e00-\\u9fa5]");

    private static final Pattern numberReg = Pattern.compile("[\\d]");

    public static String regNumber(String str){
        return numberReg.matcher(str).replaceAll("");
    }

    public static void isTrue(boolean bool, String mesg){
        if(!bool)
            throw new AppException(mesg);
    }

    public static void isNotTrue(boolean bool, String mesg){
        if(bool){
            throw new AppException(mesg);
        }
    }

    public static void checkNickname(String nickname){
        logger.info("nickname is {}",nickname);
        if(nickname.length()>16){
            throw new AppException("昵称不能超过16个字符");
        }
        Matcher m = usernameReg.matcher(nickname);
        isNotTrue(m.find(),"昵称不能包含非法字符");
    }

    public static void isMobileNumber(String number){
        isMobileNumber(number, "手机号码格式错误");
    }

    public static void isMobileNumber(String number, String errorMesg){
        isTrue(number.matches(mobileNumberReg), errorMesg);
    }

    public static void checkPassword(String pwd){
        isTrue(pwd.length()>=8,"密码必须大于8个字符");

        Matcher m = passwordReg3.matcher(pwd);
        isTrue(!m.find(),"亲，密码不能包含中文哦！");

        m = passwordReg1.matcher(pwd);
        isTrue(m.matches(),"密码格式错误，请输入英文，数字或字符组合");

        m = passwordReg2.matcher(pwd);
        isTrue(!m.find(),"亲，密码不能包含引号哦！");
    }

    public static void isNotNull(Object obj, String mesg){
        isTrue(obj != null, mesg);
    }

    public static void isNull(Object obj, String mesg){
        isTrue(obj == null, mesg);
    }

    public static void error(String mesg){
        throw new RuntimeException(mesg);
    }
}
