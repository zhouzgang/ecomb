package cn.ecomb.park.state.machine.pattern;

import lombok.Data;

/**
 * 类的行为是基于它的状态改变的
 * @author brian.zhou
 * @date 2021/8/20
 */
@Data
public class Context implements State{

    private State state;

    public Context() {
        this.state = new StartState(this);
    }

    @Override
    public void doAction() {
        state.doAction();
    }
}
