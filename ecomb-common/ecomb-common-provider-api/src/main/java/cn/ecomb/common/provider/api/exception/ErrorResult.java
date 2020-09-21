package cn.ecomb.common.provider.api.exception;

/**
 * 错误对象封装
 * - 错误码A-BB-CCC
 * - A  : 错误级别编号： 1-系统级  2-内部业务  3-外部业务
 * - BB : 模块编号: 数据模块 与 业务模块使用同一位置编码，分别使用不同段
 *     - 数据模块编号[00~49]：00-scene 01-admin 02-area 03-user 04-service 05-trip 06-sync 07-customer
 *     - 业务模块编号[50~99]：50-common 51-admin 52-coupon 53-mobile 54-trip
 * - CCC: 具体错误编号: 错误编码根据功能块分段，具体分段划分备注在 XXXErrorCode 的错误枚举类中，ex：
 *   - 优惠: 252
 *     - 公共：00 ~ 49
 *     - 商场：50 ~ 99
 *     - 店铺：100 ~ 149
 *     - 排号：150 ~ 199
 *     - 用户中心：200 ~ 249
 * @author zhouzhigang
 */
public interface ErrorResult {

    /**
     * 错误编码
     * @return
     */
    int getCode();

    /**
     * 错误信息
     * @return
     */
    String getMsg();
}
