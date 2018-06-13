package componet;

import bucket.Application;
import bucket.user.User;
import bucket.user.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PersistentTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserMapper userMapper;

    @Test
    public void testConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testMapper() throws SQLException {
//        User user = new User();
//        user.setName("kd");
//        user.setPassword("1232323");
//        System.out.println(userMapper.insert(user));
//        System.out.println(userMapper.selectById(1));
        PageHelper.startPage(1,10);
        List<User> userList = userMapper.selectAll();
        System.out.println(userList.size());
        System.out.println(new PageInfo<>(userList));
    }

}
