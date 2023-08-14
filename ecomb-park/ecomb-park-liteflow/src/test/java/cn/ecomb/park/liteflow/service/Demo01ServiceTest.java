package cn.ecomb.park.liteflow.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author brian.zhou
 * @date 4/7/23
 */
public class Demo01ServiceTest extends BaseTest{

    @Autowired
    private Demo01Service demo01Service;

    @Test
    public void testConfig() {
        demo01Service.testConfig();
    }
}