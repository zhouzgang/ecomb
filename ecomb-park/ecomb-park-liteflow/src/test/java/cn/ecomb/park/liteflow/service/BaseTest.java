package cn.ecomb.park.liteflow.service;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试服务基类
 *
 * @author brian.zhou
 * @date 2020/9/18
 **/
@Ignore
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = LiteFlowApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
}
