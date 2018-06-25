package bucket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


public abstract class BeanHelper {

    private static Logger logger = LoggerFactory.getLogger(BeanHelper.class);

    public static <T>T toBean(Object bean, Class<T> cls){
        if(bean == null)
            return null;

        T newBean = null;
        try {
            newBean = cls.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        BeanUtils.copyProperties(bean, newBean);
        return newBean;
    }

    public static <T>List<T> toList(List list, Class<T> cls){
        if(list == null)
            return null;

        List<T> newList = new ArrayList<>();
        list.forEach(o -> newList.add(toBean(o, cls)));
        return newList;
    }

}