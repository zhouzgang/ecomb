package cn.ecomb.common.provider.api.utils;


import cn.ecomb.common.provider.api.constant.SxApiUserConstant;
import cn.ecomb.common.utils.util.AesGcmCipher;
import cn.ecomb.common.utils.util.EDUtils;

import java.util.Objects;

/**
 * 随行 AES 接口参数，返回结果加密
 *
 * @author zhouzhigang
 */
public class SxEDUtils {

    /**
     * 字符串加密 (base64 + aes)
     *
     * @param content 加密内容
     * @param key   加密key
     * @param apiUser   加密方法
     * @return
     */
    public static String encrypt(String content, String key, SxApiUserConstant apiUser) {
        if (Objects.isNull(apiUser)) {
            return EDUtils.encrypt(content, key);
        }
        switch (apiUser) {
            case OPPO:
            case VIVO:
                return AesGcmCipher.doEncrypt(content, key);
            default:
                return EDUtils.encrypt(content, key);
        }
    }

    /**
     * 字符串解密 (base64 + aes)
     *
     * @param content 解密内容
     * @param key   解密key
     * @param apiUser   解密方法
     * @return
     */
    public static String decrypt(String content, String key, SxApiUserConstant apiUser) {
        if (Objects.isNull(apiUser)) {
            return EDUtils.decrypt(content, key);
        }
        switch (apiUser) {
            case OPPO:
            case VIVO:
                return AesGcmCipher.doDecrypt(content, key);
            default:
                return EDUtils.decrypt(content, key);
        }
    }

    public static void main(String[] args) {
        String key = "54c4d797d0ce04c33551ae6a2291279b";
        String cryptStr = EDUtils.encrypt("{\"mallCode\":\"MKWh278d\"}", key);
        System.out.println("加密结果：" + cryptStr);
        System.out.println("解密结果：" + EDUtils.decrypt("bDJoM3RtYTRodXQzOTY3brT/9OWDdEsajYvD1HEz/5KLFJc9US9EMz+VtpkaGEP9pG7TgsOcN3sZ" +
                "/aWqOJrWNMznHLPCksnq6eRSiqgHe0LquKwb56H1ylBIDG9NvJCFNG6H39HbHE8rycw+NnPac3Hc" +
                "pIy0KXGjc2yVpMCxHwam+RSyNoFgf8uMh9Y5EPq7", key));
    }
}