package bucket.component.interceptor;

import bucket.component.SpringApplicationContextHolder;
import bucket.component.extention.TestBean;
import bucket.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.TreeMap;

/**
 * @Author: qyl
 * @Description: 简单的API签名拦截器
 * @Date: Created in 18:40 2018/6/3
 */
@TestBean
@Component
public class SignatureInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SignatureInterceptor.class);

    private static final String ERROR_MESSAGE_SIGN = "签名错误";
    //签名字符串
    private static final String HEADER_NAME_SIGN = "signature";
    //随机数
    private static final String HEADER_NAME_NONCE = "nonce";

    @Autowired
    private SpringApplicationContextHolder springApplicationContextHolder;

    @Value("${api.sign.key}")
    private String signKey;
    @Value("${api.sign.msg}")
    private String signMsg;

    private String signNum;

    @PostConstruct
    public void init(){
        signNum = "9527";
        logger.info("do some thing init");
    }

    @Override
    public String toString() {
        return "SignatureInterceptor{" +
                "springApplicationContextHolder=" + springApplicationContextHolder +
                ", signKey='" + signKey + '\'' +
                ", signMsg='" + signMsg + '\'' +
                ", signNum='" + signNum + '\'' +
                '}';
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("signMsg is {},sighKey is{},signNum is {}",signMsg,signKey);

        String sign = request.getHeader(HEADER_NAME_SIGN);
        String nonce = request.getHeader(HEADER_NAME_NONCE);

        if (sign == null || nonce == null) throw new AppException(ERROR_MESSAGE_SIGN);
        String method = request.getMethod().toUpperCase();
        String path = request.getContextPath();
        String uri = request.getRequestURI();
        if (StringUtils.isNotBlank(path)) {
            uri = uri.replace(path, "");
        }
        TreeMap<String, String[]> parameters = new TreeMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            parameters.put(key, request.getParameterValues(key));
        }
        StringBuilder builder = new StringBuilder();
        parameters.forEach((s, strings) -> {
            {
                for (String str : strings) {
                    builder.append(s).append(str);
                }
            }
        });
        //构造待签名字符串
        String stringToSign = method.concat("\n")
                .concat(uri).concat(builder.toString()).concat("\n")
                .concat(nonce)
                .concat(signKey);
        //签名字符串
        String encodeParamaterStr = null;//todo sign
        if(!encodeParamaterStr.equals(sign)){
            throw new AppException(ERROR_MESSAGE_SIGN);
//            PrintWriter printWriter = response.getWriter();
//            printWriter.write(ERROR_MESSAGE_SIGN);
//            printWriter.flush();
//            printWriter.close();
        }
        return super.preHandle(request, response, handler);
    }

}