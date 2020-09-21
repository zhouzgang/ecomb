package cn.ecomb.common.web.annotation;

/**
 * http 头信息常量
 * @author zhouzg
 * @date 2019/8/2
 */
public enum  CryptKeyConstant {

    /** 默认加密key */
    DEFAULT_CRYPT_KEY(null),
    /** oppo加解密key */
    OPPO_CRYPT_KEY("d797d0cec33a22912551ae679b54c404"),
    /** 随行优惠加解密key */
    COUPON_QUICK_CRYPT_KEY("d41d8cd98f00b204e9800998ecf8427e"),
    /** 埋点统计加解密key */
    STATISTICS_CRYPT_KEY("4ba3c387a24049cf935f20dcca2c89f3"),
    /** 响指加解密key */
    XIANGZHI_CRYPT_KEY("125de679b54c797d0ce5c33a2291a404"),
    /** 负一屏加解密key */
    HIBOARD_CRYPT_KEY("54c4d797d0ce04c33551ae6a2291279b"),
    /** 出行加密key */
    TRIP_QUICK_CRYPT_KEY("279bc3354c797d2404924049d797c33e"),
    /** vivo加解密key */
    VIVO_CRYPT_KEY("385d3395-4d5a-b6da-55fb-da2daf6a"),
    /** 小米加解密key */
    XIAOMI_CRYPT_KEY("ab57fd0432e25d5b3013133a1c910d56"),
    ;


    private String key;

    CryptKeyConstant(String key) {
        this.key = key;
    }

    /**
     * 根据接口 url 的命名空间获取不同品台的加解密 key
     * @param urlNaming
     * @return
     */
    protected static String getCryptKey(String urlNaming) {

        return "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
