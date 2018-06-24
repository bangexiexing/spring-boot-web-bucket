package bucket.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 18:08 2018/6/24
 */
@RestController
@Slf4j
public class FileController {

    @RequestMapping("upload")
    public String uploadFile(MultipartFile myfile){
        log.info("receive file :{}",myfile);
        //do save
        return "success";
    }

    @RequestMapping("upload/batch")
    public String batchUploadFile(MultipartHttpServletRequest request){
        Map<String,MultipartFile> fileMap = request.getFileMap();
        fileMap.forEach((name,file) -> log.info("receive file name {},{}",name,file));
        return "success";
    }
}
