package cn.ecomb.park.state.machine.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine
@Data
@Slf4j
public class BizBean {

    /**
     * @see States
     */
    private String status = States.SI.name();

    @OnTransition(target = "S1")
    public void online() {
        log.info("操作上线，待发布. target status:{}", States.S1.name());
        setStatus(States.S1.name());
    }

    @OnTransition(target = "S2")
    public void publish() {
        log.info("操作发布,发布完成. target status:{}", States.S2.name());
        setStatus(States.S2.name());
    }

    @OnTransition(target = "SI")
    public void rollback() {
        log.info("操作回滚,回到草稿状态. target status:{}", States.SI.name());
        setStatus(States.SI.name());
    }

}
