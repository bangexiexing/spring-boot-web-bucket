package bucket.user;

import bucket.version.History;
import bucket.version.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 18:08 2018/6/24
 */
@RestController
@Slf4j
public class FileController {

    @RequestMapping(value = "upload", headers = "api-version=" + Version.APP_12)
    public String uploadFile(MultipartFile myfile){
        log.info("receive file :{}",myfile);
        //do save
        return "success";
    }

    @History(until = Version.APP_11)
    @RequestMapping("upload/batch")
    public String batchUploadFile(MultipartHttpServletRequest request){
        Map<String,MultipartFile> fileMap = request.getFileMap();
        fileMap.forEach((name,file) -> log.info("receive file name {},{}",name,file));
        return "success";
    }

    @RequestMapping("download")
    public String download(HttpServletResponse res) throws IOException {
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + "test");
        Files.copy(Paths.get("some path"),res.getOutputStream());
        return null;
    }
}
