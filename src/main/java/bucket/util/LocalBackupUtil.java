package bucket.util;

import org.springframework.util.ResourceUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 9:11 2018/7/7
 */
public class LocalBackupUtil {

    private static String BACKUP_PATH = "classpath:localbackup";
    private static File BACKUP_DIR;

    static {
        try {
            BACKUP_DIR = ResourceUtils.getFile(BACKUP_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void backup(Object obj) {
        String str = JsonHelper.beanToString(obj);
        File file = new File(BACKUP_DIR + "/" + obj.getClass().getName());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.toURI()), StandardCharsets.UTF_8)) {
            writer.write(str);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T load(Class<T> cls){
        File file = new File(BACKUP_DIR + "/" + cls.getName());
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
            return JsonHelper.stringToBean(new String(bytes, StandardCharsets.UTF_8), cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
