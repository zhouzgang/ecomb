package cn.ecomb.park.pattern;

import cn.ecomb.park.pattern.behavior.Strategy;

/**
 * 测试所有的设计模式，以及模式之间的组合
 *
 * @author brian.zhou
 * @date 2021/7/2
 */
public class PatternApplication {

    public static void main(String[] args) {
        Strategy.getInstance().execute("12");
    }
}
