package cn.ecomb.park.liteflow.service;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author brian.zhou
 * @date 4/7/23
 */
@Service
public class Demo01Service {

    @Resource
    private FlowExecutor flowExecutor;

    public void testConfig(){
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
    }
}
