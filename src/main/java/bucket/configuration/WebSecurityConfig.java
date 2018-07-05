package bucket.configuration;

import bucket.component.security.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 17:12 2018/6/9
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//for upload file
                .authorizeRequests().antMatchers("/","/login","/favicon.ico","/oauth/**","/upload","/upload/batch","/static/**","/test").permitAll()
                .anyRequest().authenticated();
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .and()
//                .logout().permitAll();
    }


    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new MyUserDetailsService();
    }

    //oauth2 need
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
