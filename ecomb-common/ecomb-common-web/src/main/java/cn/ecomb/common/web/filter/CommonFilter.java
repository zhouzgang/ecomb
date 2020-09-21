package cn.ecomb.common.web.filter;

import cn.ecomb.common.utils.util.MdcHelper;
import cn.ecomb.common.utils.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理一般问题过滤器
 * 过滤器优先级规则为：1、java 文件名字长的优先级高
 *                  2、java 文件名字长度一样的，名字的第一个字母在字母表的顺序，靠前的优先级高，不区分大小写。
 *
 * @author zhouzg
 * @date 2019/8/1
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "CommonFilter")
public class CommonFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(CommonFilter.class);
    private static final String UNIQUE_ID = "traceId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        MdcHelper.insertUnique();
        logger.info("请求Url：{}, content-type: {}", request.getRequestURL(), request.getContentType());
        Long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
            logger.warn("请求总耗时：{}", System.currentTimeMillis() - start);
        } finally {
            MDC.clear();
            ThreadLocalUtil.remove();
        }
    }


    @Override
    public void destroy() {

    }
}
