package bucket.user;

import lombok.Data;

import javax.persistence.Table;

@Data
public class User {
    private Integer userId;
    private String name;
    private String password;
    private String role;
}
