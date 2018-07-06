package bucket.version;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 23:05 2018/7/6
 */
public class Version {
    public static final String APP_12 = "1.2";
    public static final String APP_11 = "1.1";
    public static final String CURRENT = APP_12;

    public static final Set<String> SUPPORT_VERSION = new HashSet<>();

    static {
        //SUPPORT_VERSION.add(APP_11);
        SUPPORT_VERSION.add(APP_12);
    }

    public static void main(String[] args) throws IOException {
        String checkpath = "bucket/user/";
        String classpath = Version.class.getResource("/").getPath();
        ClassLoader classLoader = Version.class.getClassLoader();
        Files.walkFileTree(Paths.get(classpath.substring(1) + checkpath), new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileStr = file.toString();
                if (fileStr.endsWith(".class")){
                    try {
                        String packageName = fileStr.substring(fileStr.indexOf("bucket\\user\\"));
                        packageName = packageName.replace("\\",".");
                        String className = packageName.substring(0,packageName.length() - 6);
                        Class cls = classLoader.loadClass(className);
                        findHistory(cls);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void findHistory(Class cls){
        History classHistoryInfo = (History) cls.getAnnotation(History.class);
        if (classHistoryInfo != null && !SUPPORT_VERSION.contains(classHistoryInfo.until())) {
            System.out.println("class :" + cls.getName() + ";version:" + classHistoryInfo.until() + "已不在支持版本中。请处理");
        }
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            History fieldHistoryInfo = field.getAnnotation(History.class);
            if (fieldHistoryInfo != null && !SUPPORT_VERSION.contains(fieldHistoryInfo.until())) {
                System.out.println("field :" + cls.getName() + "#" + field.getName() + ";version:" + fieldHistoryInfo.until() + "已不在支持版本中。请处理");
            }
        }
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            History methodHistoryInfo = method.getAnnotation(History.class);
            if (methodHistoryInfo != null && !SUPPORT_VERSION.contains(methodHistoryInfo.until())) {
                System.out.println("method :" + cls.getName() + "#" + method.getName() + ";version:" + methodHistoryInfo.until() + "已不在支持版本中。请处理");
            }
        }
    }
}
