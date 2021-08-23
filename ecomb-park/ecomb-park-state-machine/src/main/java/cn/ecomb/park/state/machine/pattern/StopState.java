package cn.ecomb.park.state.machine.pattern;

/**
 * @author brian.zhou
 * @date 2021/8/20
 */
public class StopState extends AbstractState{

    public StopState(Context context) {
        super(context);
    }

    @Override
    public void doAction() {
        System.out.println("stop do");
    }
}
