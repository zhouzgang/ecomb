package cn.ecomb.park.state.machine.pattern;

/**
 * @author brian.zhou
 * @date 2021/8/20
 */
public abstract class AbstractState implements State{

    protected Context context;

    public AbstractState(Context context) {
        this.context = context;
    }
}
