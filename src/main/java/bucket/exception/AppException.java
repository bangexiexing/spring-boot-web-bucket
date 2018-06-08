package bucket.exception;

public class AppException extends RuntimeException{

    private AppErrorCode errorCode;
    private String msg;

    public AppException(String msg){
        this.errorCode = AppErrorCode.SYSTEM_ERROR;
        this.msg = msg;
    }

    public AppException(AppErrorCode code){
        this.errorCode = code;
    }

    public AppException(AppErrorCode code,String msg){
        this.errorCode = code;
        this.msg = msg;
    }

    public AppErrorCode getErrorCode(){
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return msg == null ? errorCode.getMsg() : msg;
    }
}
