package bucket.component.filter;

import bucket.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 19:31 2018/9/8
 */
@Order(2)
@WebFilter(filterName="SecondFilter",urlPatterns="/*")
@Slf4j
public class SecondFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("SecondFilter doing");
        if (System.currentTimeMillis() % 2 == 0) {
            throw new AppException("filter error");
        }
        filterChain.doFilter(request, response);
        log.info("SecondFilter after doing");
    }
}
