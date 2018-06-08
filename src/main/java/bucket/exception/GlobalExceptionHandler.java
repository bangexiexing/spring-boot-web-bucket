package bucket.exception;

import bucket.response.AppResponse;
import com.google.common.io.CharStreams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * 捕获异常统一处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final static String LOG_TEMPLATE = "\nreqeust uri: %s\nparams: %s\nerror message:%s";


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AppResponse handleException(HttpServletRequest request, Exception ex){
        String errorLog = String.format(LOG_TEMPLATE,request.getRequestURI(),getParamString(request),ex.getMessage());
        logger.error(errorLog, ex);
        if(ex instanceof AppException){
            return AppResponse.fail((AppException) ex);
        }
        return AppResponse.fail(AppErrorCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public AppResponse handleNotFoundException(HttpServletRequest request, Exception ex){
        String errorLog = String.format(LOG_TEMPLATE,request.getRequestURI(),getParamString(request),ex.getMessage());
        logger.error(errorLog, ex);
        return AppResponse.fail(AppErrorCode.NOT_FOUND);
    }

    private String getParamString(HttpServletRequest request){

        try {
            StringBuilder result = new StringBuilder();
            if(!request.getInputStream().isFinished()){
                String str = CharStreams.toString(new InputStreamReader(request.getInputStream(),StandardCharsets.UTF_8));
                result.append(str).append(";");
            }
            Enumeration<String> params = request.getParameterNames();
            while (params.hasMoreElements()){
                String key = params.nextElement();
                result.append(key)
                        .append("=")
                        .append(request.getParameter(key))
                        .append(";");
            }
            return result.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }
}