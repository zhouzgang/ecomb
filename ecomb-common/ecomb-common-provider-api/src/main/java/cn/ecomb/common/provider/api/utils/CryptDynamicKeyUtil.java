package cn.ecomb.common.provider.api.utils;


import cn.ecomb.common.utils.util.ThreadLocalUtil;

import java.util.Objects;

/**
 * @Description: 签名动态key处理工具
 * @author: jaffee
 * @date: 2019/12/23 13:50
 */

public class CryptDynamicKeyUtil extends ThreadLocalUtil {

    private static final String DYNAMIC_KEY = "dynamicKey";



    public static void setDynamicKey(String key) {
        setAttribute(DYNAMIC_KEY, key);
    }


    public static String getDynamicKey() {
        Object key = getAttribute(DYNAMIC_KEY);
        return Objects.isNull(key)?null:key.toString();
    }

}
