package cn.ecomb.park.common;

import java.math.BigDecimal;

/**
 * @author brian.zhou
 * @date 2021/7/21
 */
public class ChangeTypePark {

    public static void main(String[] args) {
        Double d = new Double(1.22);
        System.out.println(d.toString());
        new BigDecimal(d);
    }
}
