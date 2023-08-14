package cn.ecomb.park.liteflow.demo;

import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author brian.zhou
 * @date 4/7/23
 */
@Slf4j
@Component("c")
public class CCmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        log.info("CCmp");
    }
}
