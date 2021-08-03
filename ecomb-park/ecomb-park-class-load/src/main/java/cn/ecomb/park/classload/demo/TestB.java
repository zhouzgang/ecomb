package cn.ecomb.park.classload.demo;

/**
 * @author brian.zhou
 * @date 2021/7/12
 */
public class TestB {

    public void hello() {
        System.out.println("TestB: " + this.getClass().getClassLoader());
    }
}
