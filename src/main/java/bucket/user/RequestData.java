package bucket.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 17:16 2018/9/8
 */
@Data
public class RequestData {
    @NotEmpty(message = "name can not be null")
    private String name;
    @Pattern(regexp = "[abc]", message = "string error")
    private String number;
}
