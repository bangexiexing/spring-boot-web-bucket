package bucket.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

/**
 * @Author: qyl
 * @Description:目前只支持 logback
 * @Date: Created in 20:59 2018/6/25
 */
public abstract class LogUtil {
    public static void setRootLogLevel(String level) {
        Object logObj = LoggerFactory.getILoggerFactory();
        if (logObj instanceof LoggerContext){
            LoggerContext loggerContext = (LoggerContext) logObj;
            Logger logger=loggerContext.getLogger("root");
            logger.setLevel(Level.toLevel(level));
        }else {
            throw new RuntimeException("不支持此类log修改");
        }

    }

    public static void setSingleClassLogLevel(String level,String className){
        Object logObj = LoggerFactory.getILoggerFactory();
        if (logObj instanceof LoggerContext){
            LoggerContext loggerContext = (LoggerContext) logObj;
            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Logger logger=loggerContext.getLogger(clazz);
            logger.setLevel(Level.toLevel(level));
        }else {
            throw new RuntimeException("不支持此类log修改");
        }
    }

    public static void setPkgLogLevel(String level,String pkgName){
        Object logObj = LoggerFactory.getILoggerFactory();
        if (logObj instanceof LoggerContext){
            LoggerContext loggerContext = (LoggerContext) logObj;
            loggerContext.getLoggerList().forEach(logger -> {
                if(logger.getName().startsWith(pkgName)){
                    logger.setLevel(Level.toLevel(level));
                }
            });
        }else {
            throw new RuntimeException("不支持此类log修改");
        }
    }
}
