package bucket.component.filter;

import bucket.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 19:24 2018/9/8
 */
@Order(1)
@WebFilter(filterName="MyFilter",urlPatterns="/*")
@Slf4j
public class MyFilter implements Filter {

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("myFilter doing" + userService.toString());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("filter destroy");
    }
}
