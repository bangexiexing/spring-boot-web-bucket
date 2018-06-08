package bucket.response;

import bucket.exception.AppErrorCode;
import bucket.exception.AppException;
import lombok.Data;

@Data
public class AppResponse {
    private int code;
    private String msg;
    private Object data;

    public AppResponse(){}

    public AppResponse(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public AppResponse(AppErrorCode code){
        this(code.getValue(),code.getMsg(),null);
    }

    public static AppResponse success(){
        return new AppResponse(AppErrorCode.NO_ERROR);
    }

    public static AppResponse success(Object data){
        return new AppResponse(
                AppErrorCode.NO_ERROR.getValue(),
                AppErrorCode.NO_ERROR.getMsg(),
                data);
    }

    public static AppResponse fail(String msg){
        return new AppResponse(AppErrorCode.SYSTEM_ERROR.getValue(),msg,null);
    }

    public static AppResponse fail(AppErrorCode errorCode){
        return new AppResponse(errorCode);
    }

    public static AppResponse fail(AppException appException){
        return new AppResponse(
                appException.getErrorCode().getValue(),
                appException.getMessage(),
                null);
    }
}
