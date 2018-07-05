package bucket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    public static <T> T mapToBean(Map map, Class<T> cls){
        if (map == null)
            return null;

        T newBean = null;
        BeanInfo beanInfo = null;
        try {
            newBean = cls.newInstance();
            beanInfo = Introspector.getBeanInfo(cls);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor p : propertyDescriptors) {
                Method setter = p.getWriteMethod();
                setter.invoke(newBean,map.get(p.getName()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("map to bean error :" + e.getMessage());
        }
        return newBean;
    }
}