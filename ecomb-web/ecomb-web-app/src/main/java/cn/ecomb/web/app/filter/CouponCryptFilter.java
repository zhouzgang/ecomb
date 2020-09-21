package cn.ecomb.web.app.filter;

import cn.ecomb.common.web.filter.RequestContentTypeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 加密过滤器，通过继承ServletRequest，重写getHeader()方法，
 * 设置将加密使用的content-type=text/plain转换为application/json，
 * 后端接口默认为 application/json，后续有其他类型接口再做扩展。
 *
 * @author zhouzg
 * @date 2019/8/1
 */
@Component
@WebFilter(urlPatterns = "/*",filterName = "CryptFilter")
public class CouponCryptFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(CouponCryptFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        RequestContentTypeWrapper requestWrapper = new RequestContentTypeWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
//        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
