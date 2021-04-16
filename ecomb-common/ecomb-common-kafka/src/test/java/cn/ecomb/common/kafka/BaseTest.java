package cn.ecomb.common.kafka;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试服务基类
 *
 * @author brian.zhou
 * @date 2020/9/18
 **/
@Ignore
@SpringBootTest(classes = KafkaTestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("kafka")
public class BaseTest {
}
