package bucket.exception;

import bucket.response.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 19:03 2018/6/9
 */
@RestController
public class MyErrorController extends AbstractErrorController {

    private final Logger logger = LoggerFactory.getLogger(MyErrorController.class);

    public MyErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping("error")
    public AppResponse error(HttpServletRequest request) {
        logger.info("error info is {}",getErrorAttributes(request,true));
        return AppResponse.fail("未知错误");
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
