package cn.ecomb.common.web.filter;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;

/**
 * Request 包装类，重写 getHeader() 方法，
 * 将 Content-type 设置为 MediaType.APPLICATION_JSON_UTF8_VALUE。
 * 此包装类在 {@link } 中使用
 *
 * @author zhouzg
 * @date 2019/8/2
 */
public class RequestContentTypeWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestContentTypeWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (null != name && name.equalsIgnoreCase("Content-Type")) {
            return new Enumeration<String>() {
                private boolean hasGetted = false;

                @Override
                public String nextElement() {
                    if (hasGetted) {
                        return "";
                    } else {
                        hasGetted = true;
                        return MediaType.APPLICATION_JSON_UTF8_VALUE;
                    }
                }

                @Override
                public boolean hasMoreElements() {
                    return !hasGetted;
                }
            };
        }
        return super.getHeaders(name);
    }
}
