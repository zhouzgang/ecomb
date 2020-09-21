package cn.ecomb.common.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 随行 AES 接口参数，返回结果加密
 *
 * @author zhouzhigang
 */
public class EDUtils {

    private static final Logger logger = LoggerFactory.getLogger(EDUtils.class);

    /**
     * AES 加密操作
     * @param content 待加密内容
     * @param key 秘钥
     * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 加密数据
     */
    public static byte[] encrypt(byte[] content, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * AES 解密操作
     * @param content 密文
     * @param key 秘钥
     * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 字符串加密 (base64 + aes)
     * @param content
     * @param key
     * @return
     */
    public static String encrypt(String content, String key) {
        try {
            String iv = new BigInteger(130, new SecureRandom()).toString(32).substring(0, 16);
            byte[] encrypted = encrypt(content.getBytes(StandardCharsets.UTF_8), key, iv);
            byte[] result = new byte[iv.getBytes().length + encrypted.length];
            System.arraycopy(iv.getBytes(), 0, result, 0, iv.getBytes().length);
            System.arraycopy(encrypted, 0, result, iv.getBytes().length, encrypted.length);
            return Base64Utils.encodeToString(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 字符串解密 (base64 + aes)
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        try {
            byte[] bytes = Base64Utils.decodeFromString(content);
            byte[] iv = new byte[16];
            System.arraycopy(bytes, 0, iv, 0, 16);
            byte[] encrypted = new byte[bytes.length - 16];
            System.arraycopy(bytes, 16, encrypted, 0, bytes.length - 16);
            byte[] result = decrypt(encrypted, key, new String(iv, StandardCharsets.UTF_8));
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        String s =
                "65c86251eeeed0638be6e737a136dd3060097c4094d6f2d7dc56824670519022&EBD0B5D2-F4E7-453A-9E16-01AE8F6E2977&Mozilla/5.0 (iPhone; CPU iPhone OS 12_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148";
        String pass = "EBD0B5D2F4E7453A9E1601AE8F6E2977";
        String code = encrypt(s, pass);
        System.out.println("加密之前：" + s);
        System.out.println("加密结果：" + code);
        System.out.println(("解密结果：" + decrypt(code, pass)));
    }
}