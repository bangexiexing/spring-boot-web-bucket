package bucket.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectOne(int userId);
    int insert(String name,String password);
}
