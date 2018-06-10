package bucket.component.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 18:02 2018/6/9
 */
public class MyUserDetailsService implements UserDetailsService {

    private static final  Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("username want auth :{}",username);
        //todo some datasource action
        return new MyUserDetails("admin","admin");
    }
}
