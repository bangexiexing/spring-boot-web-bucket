package bucket.component.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 17:41 2018/6/9
 */
@Data
@NoArgsConstructor
public class MyUserDetails implements UserDetails {

    private String userName;
    private String role;
    private List<SimpleGrantedAuthority> authority;

    public MyUserDetails(String userName,String role){
        this.userName = userName;
        this.role = role;
        authority = Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
