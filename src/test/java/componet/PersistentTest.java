package componet;

import bucket.Application;
import bucket.user.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

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
        System.out.println(userMapper.insert("tom","123456789"));
        System.out.println(userMapper.selectOne(1));
    }

}
