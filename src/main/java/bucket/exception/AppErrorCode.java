package bucket.exception;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 14:55 2018/6/3
 */
public enum AppErrorCode {

    NO_ERROR(0,null),
    NOT_FOUND(404,"未找到该方法"),
    SYSTEM_ERROR(10001,"系统内部错误"),
    ;


    private int value;
    private String msg;

    AppErrorCode(int value, String msg){
        this.value = value;
        this.msg = msg;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
