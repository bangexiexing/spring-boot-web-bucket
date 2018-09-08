package bucket.user;

import tk.mybatis.mapper.common.Mapper;

//@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {
    User selectById(int userId);
    int insertByName(String name, String password);
}
