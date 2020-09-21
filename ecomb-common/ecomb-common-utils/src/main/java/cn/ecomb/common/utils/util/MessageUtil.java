package cn.ecomb.common.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author zhouzg
 * @date 2019/6/27
 */
public class MessageUtil {

    private static Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    public static String fetchPostByTextPlain(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            char[] buf = new char[512];
            int len = 0;
            StringBuilder contentBuffer = new StringBuilder();
            while ((len = reader.read(buf)) != -1) {
                contentBuffer.append(buf, 0, len);
            }
            return contentBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[获取request中用POST方式“Content-type”是“text/plain”发送的json数据]异常:{}", e.getCause());
        }
        return null;
    }

    public static <T> T fetchPostByTextPlain(HttpServletRequest request, Class<T> clazz) {
        String content = fetchPostByTextPlain(request);
        logger.info("请求原文本:{}", content);
        return FastJsonUtils.fromJson(content, clazz);
    }
}
